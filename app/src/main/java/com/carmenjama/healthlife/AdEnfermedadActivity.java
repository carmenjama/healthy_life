package com.carmenjama.healthlife;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.healthLife.DAO.model.AlimenRestring;
import com.healthLife.DAO.model.AlimenRestringDao;
import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.AlimentoDao;
import com.healthLife.DAO.model.CategoriaDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Enfermedad;
import com.healthLife.DAO.model.EnfermedadDao;
import com.healthLife.DAO.model.ListEnfermedadDao;
import com.healthLife.DAO.model.PersonaDao;
import com.healthLife.DAO.model.TempAlimento;
import com.healthLife.DAO.model.TempAlimentoDao;
import java.util.List;

/**
 * Created by USUARIO on 13/08/2015.
 */
public class AdEnfermedadActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    PersonaDao personaDao;
    EnfermedadDao enfermedadDao;
    AlimenRestringDao alimenRestringDao;
    AlimentoDao alimentoDao;
    TempAlimentoDao tempAlimentoDao;
    AdapterAlimento adapterAlimento;
    List<TempAlimento> listTemp;

    EditText txtEnfermedad, txtBuscar;
    Button btnGuardar, btnAgregar, btnCancelar;
    ListView listAlimentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenfermedad);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        Instanciar();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarLista();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacion();
            }
        });
        listAlimentos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TempAlimento alimentoSelec = listTemp.get(position);
                tempAlimentoDao.deleteByKey(alimentoSelec.getId());
                generarAlimentos();
                return false;
            }
        });
    }
    public void Instanciar(){
        txtEnfermedad=(EditText)findViewById(R.id.txtNombre);
        txtBuscar=(EditText)findViewById(R.id.txtBuscar);
        btnAgregar=(Button)findViewById(R.id.btnLista);
        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        listAlimentos=(ListView)findViewById(R.id.listAlimento);
        btnCancelar=(Button)findViewById(R.id.button);

        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tempAlimentoDao = daoSession.getTempAlimentoDao();
        personaDao = daoSession.getPersonaDao();
        enfermedadDao = daoSession.getEnfermedadDao();
        alimenRestringDao = daoSession.getAlimenRestringDao();
        alimentoDao = daoSession.getAlimentoDao();
        eliminar();

    }
    public void agregarLista(){
        String dato = txtBuscar.getText().toString();
        if(dato.equals("")){
            Toast.makeText(this, "AVISO: No ha ingresado dato",Toast.LENGTH_LONG).show();
        }else{
            List<Alimento> listAlimento=this.alimentoDao.queryBuilder().where(AlimentoDao.Properties.Nombre.eq(dato)).list();
            if(listAlimento.size()==0){
                Toast.makeText(this, "AVISO: Alimento no encontrado",Toast.LENGTH_LONG).show();
            }
            if(listAlimento!=null){
                for(Alimento objeto : listAlimento){
                    TempAlimento temp = new TempAlimento();
                    temp.setId_alimento(objeto.getId());
                    temp.setNombre(objeto.getNombre());
                    tempAlimentoDao.insert(temp);
                }
            }
            generarAlimentos();
        }
    }
    public void generarAlimentos(){
        listTemp = tempAlimentoDao.loadAll();
        adapterAlimento = new AdapterAlimento( this, R.layout.formato_fila,listTemp);
        listAlimentos.setAdapter(adapterAlimento);
    }
    public void validacion (){
        String datEnferfedad=txtEnfermedad.getText().toString();
        if(datEnferfedad.equals("")||tempAlimentoDao.count()==0){
            Toast.makeText(this, "AVISO: Datos imcompletos", Toast.LENGTH_LONG).show();
        }else{
            guardar(datEnferfedad);
        }
    }
    public void guardar(String dato){
        Enfermedad enfermedad = new Enfermedad();
        enfermedad.setDescripcion(dato);
        enfermedadDao.insert(enfermedad);
        listTemp =  tempAlimentoDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempAlimento alimentoSelec = listTemp.get(i);
            AlimenRestring alimenRestring = new AlimenRestring();
            alimenRestring.setId_alimento(alimentoSelec.getId_alimento());
            alimenRestring.setId_enfermedad(enfermedad.getId());
            alimenRestringDao.insert(alimenRestring);
        }
        Toast.makeText(this, "AVISO: Enfermedad guardada", Toast.LENGTH_LONG).show();
    }
    public void eliminar (){
        listTemp =  tempAlimentoDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempAlimento alimentoSelec = listTemp.get(i);
            tempAlimentoDao.deleteByKey(alimentoSelec.getId());
        }
    }
}