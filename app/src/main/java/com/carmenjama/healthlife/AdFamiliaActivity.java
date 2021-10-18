package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Enfermedad;
import com.healthLife.DAO.model.EnfermedadDao;
import com.healthLife.DAO.model.Familiar;
import com.healthLife.DAO.model.FamiliarDao;
import com.healthLife.DAO.model.ListEnfermedad;
import com.healthLife.DAO.model.ListEnfermedadDao;
import com.healthLife.DAO.model.Persona;
import com.healthLife.DAO.model.PersonaDao;
import com.healthLife.DAO.model.TempEnfermedad;
import com.healthLife.DAO.model.TempEnfermedadDao;
import com.carmenjama.healthlife.PerfilActivity;

import java.util.List;

/**
 * Created by USUARIO on 22/08/2015.
 */
public class AdFamiliaActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    PersonaDao personaDao;
    EnfermedadDao enfermedadDao;
    ListEnfermedadDao listEnfermedadDao;
    TempEnfermedadDao tempEnfermedadDao;
    FamiliarDao familiarDao;
    PerfilActivity perfilActivity = new PerfilActivity();
    EditText txtNombre, txtAltura, txtPeso, txtEnfermedad;
    RadioButton sexMujer, sexHombre, edad0, edad1, edad2, edad3, edad4, edad5, edad6;
    Spinner estiVida;
    ListView listEnferme;
    Button btnAceptar, btnAgLista, btnBuscar, btnAgregar;
    String []opciones= new String[]{"Seleccione","Sedentario","Moderadamente Activo","Activo"};

    int idAdmin;
    String vida="Seleccione";
    AdapterEnfermedad adapterEnfermedad;
    List<TempEnfermedad> listTemp;
    List<ListEnfermedad> listTemp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adfamilia);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();

        final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        idAdmin=settings.getInt("administradorSesion", 0);
        Instanciar();
        btnAgLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgLista();
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdFamiliaActivity.this, AdEnfermedadActivity.class);
                startActivity(intent);
            }
        });
        estiVida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                vida = item.toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdFamiliaActivity.this, ListEnferActivity.class);
                startActivity(intent);
            }
        });
        listEnferme.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TempEnfermedad alimentoSelec = listTemp.get(position);
                tempEnfermedadDao.deleteByKey(alimentoSelec.getId());
                mensaje();
                generarEnfermedad();
                return false;
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }
    public void mensaje(){
        Toast.makeText(this, "Enfermedad eliminada...",Toast.LENGTH_LONG).show();
    }
    public void Instanciar(){
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        personaDao = daoSession.getPersonaDao();
        enfermedadDao = daoSession.getEnfermedadDao();
        listEnfermedadDao = daoSession.getListEnfermedadDao();
        tempEnfermedadDao = daoSession.getTempEnfermedadDao();
        familiarDao = daoSession.getFamiliarDao();

        txtNombre=(EditText)findViewById(R.id.txtNombre);
        txtAltura=(EditText)findViewById(R.id.txtAltura);
        txtPeso=(EditText)findViewById(R.id.txtPeso);
        txtEnfermedad=(EditText)findViewById(R.id.txtBuscar);
        estiVida=(Spinner)findViewById(R.id.spinner);
        sexMujer=(RadioButton)findViewById(R.id.sexMujer);
        sexHombre=(RadioButton)findViewById(R.id.sexHombre);
        edad0=(RadioButton)findViewById(R.id.edad0);
        edad1=(RadioButton)findViewById(R.id.edad1);
        edad2=(RadioButton)findViewById(R.id.edad2);
        edad3=(RadioButton)findViewById(R.id.edad3);
        edad4=(RadioButton)findViewById(R.id.edad4);
        edad5=(RadioButton)findViewById(R.id.edad5);
        edad6=(RadioButton)findViewById(R.id.edad6);
        btnAgLista=(Button)findViewById(R.id.btnLista);
        btnBuscar=(Button)findViewById(R.id.btnBuscar);
        btnAgregar=(Button)findViewById(R.id.btnAgregar);
        listEnferme=(ListView)findViewById(R.id.listView);
        btnAceptar=(Button)findViewById(R.id.btnAceptar);
        ArrayAdapter datillos =  new  ArrayAdapter ( this , android . R . layout . simple_spinner_dropdown_item , opciones );
        estiVida.setAdapter(datillos);
        eliminar();
    }
    public void AgLista(){
        String dato = txtEnfermedad.getText().toString();
        if(dato.equals("")){
            Toast.makeText(this, "AVISO: No ha ingresado dato", Toast.LENGTH_LONG).show();
        }else{
            List<Enfermedad> list=this.enfermedadDao.queryBuilder().where(EnfermedadDao.Properties.Descripcion.eq(dato)).list();
            if(list.size()==0){
                Toast.makeText(this, "AVISO: Enfermedad no encontrada", Toast.LENGTH_LONG).show();
            }
            if(list!=null){
                for(Enfermedad objeto : list){
                    TempEnfermedad temp = new TempEnfermedad();
                    temp.setId_enfermedad(objeto.getId());
                    temp.setNombre(objeto.getDescripcion());
                    tempEnfermedadDao.insert(temp);
                }
            }
            generarEnfermedad();
        }
    }
    public void generarEnfermedad(){
        listTemp = tempEnfermedadDao.loadAll();
        adapterEnfermedad = new AdapterEnfermedad( this, R.layout.formato_fila,listTemp);
        listEnferme.setAdapter(adapterEnfermedad);
    }
    public void eliminar (){
        listTemp =  tempEnfermedadDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempEnfermedad alimentoSelec = listTemp.get(i);
            tempEnfermedadDao.deleteByKey(alimentoSelec.getId());
        }
    }
    public void guardar(){
        String datNombre = txtNombre.getText().toString();
        String datAltura = txtAltura.getText().toString();
        String datPeso = txtPeso.getText().toString();
        String datSexo="0";
        if(sexHombre.isChecked()) {
            datSexo="Hombre";
        }else{
            datSexo="Mujer";
        }
        String edad="0";
        if(edad0.isChecked()){edad="2-3";}
        else{if(edad1.isChecked()){edad="4-8";}
        else{if(edad2.isChecked()){edad="9-13";}
        else{if(edad3.isChecked()){edad="14-18";}
        else{if(edad4.isChecked()){edad="19-30";}
        else{if(edad5.isChecked()){edad="31-50";}
        else{edad="+50";}
        }
        }
        }
        }
        }
        if(datNombre.equals("") || datAltura.equals("") || datPeso.equals("") || datSexo.equals("0") || edad.equals("0") || vida.equals("")|| vida.equals("Seleccione")){
            Toast.makeText(getApplicationContext(), "AVISO: Datos incompletos", Toast.LENGTH_SHORT).show();
        }else{
            regDatosUsu(datNombre,Double.parseDouble(datAltura),Double.parseDouble(datPeso), datSexo, edad);
        }
    }
    public void regDatosUsu(String datNombre, Double datAltura, Double datPeso, String datSexo, String datEdad){
        Persona persona = new Persona();
        persona.setNombre(datNombre);
        persona.setAltura(datAltura);
        persona.setPeso(datPeso);
        persona.setSexo(datSexo);
        persona.setEdad(datEdad);
        persona.setEst_vida(vida);
        personaDao.insert(persona);
        Familiar familiar = new Familiar();
        familiar.setId_persona(persona.getId());
        familiar.setId_usuario(Long.valueOf(idAdmin));
        familiarDao.insert(familiar);
        listTemp =  tempEnfermedadDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempEnfermedad alimentoSelec = listTemp.get(i);
            ListEnfermedad listEnfermedad = new ListEnfermedad();
            listEnfermedad.setId_enfermedad(alimentoSelec.getId_enfermedad());
            listEnfermedad.setId_persona(persona.getId());
            listEnfermedadDao.insert(listEnfermedad);
        }
        Toast.makeText(this, "AVISO: Datos Guardados", Toast.LENGTH_LONG).show();
        finish();
    }
}
