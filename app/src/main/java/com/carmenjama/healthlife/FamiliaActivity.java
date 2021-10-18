package com.carmenjama.healthlife;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.healthLife.DAO.model.AlimenRestring;
import com.healthLife.DAO.model.AlimenRestringDao;
import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.AlimentoDao;
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
import com.healthLife.DAO.model.TempAlimento;
import com.healthLife.DAO.model.TempAlimentoDao;
import com.healthLife.DAO.model.TempPersona;
import com.healthLife.DAO.model.TempPersonaDao;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by USUARIO on 22/08/2015.
 */
public class FamiliaActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    PersonaDao personaDao;
    FamiliarDao familiarDao;
    TempPersonaDao tempPersonaDao;
    EnfermedadDao enfermedadDao;
    ListEnfermedadDao listEnfermedadDao;
    AlimenRestringDao alimenRestringDao;
    AlimentoDao alimentoDao;
    TempAlimentoDao tempAlimentoDao;
    AdapterAlimento adapterAlimento;

    Spinner opFamilia;
    EditText txtAltura, txtPeso, txtEdad, txtPesoIdeal;
    ListView listEnfer;
    List<Familiar> familiar;
    List<Persona> person;
    List<TempPersona> persona;
    List<TempPersona> listTemp;

    int idAdmin;
    Long idFamilia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        idAdmin=settings.getInt("administradorSesion", 0);
        instanciar();
        opFamilia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                TempPersona actual = persona.get(pos);
                buscarFamilia(actual.getId_person());
                idFamilia = actual.getId_person();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void instanciar(){
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        personaDao = daoSession.getPersonaDao();
        familiarDao = daoSession.getFamiliarDao();
        tempPersonaDao = daoSession.getTempPersonaDao();
        enfermedadDao = daoSession.getEnfermedadDao();
        listEnfermedadDao = daoSession.getListEnfermedadDao();
        alimenRestringDao = daoSession.getAlimenRestringDao();
        alimentoDao = daoSession.getAlimentoDao();
        tempAlimentoDao = daoSession.getTempAlimentoDao();
        opFamilia=(Spinner)findViewById(R.id.opFamilia);
        txtAltura=(EditText)findViewById(R.id.txtAltura);
        txtPeso=(EditText)findViewById(R.id.txtPeso);
        txtEdad=(EditText)findViewById(R.id.txtEdad);
        txtPesoIdeal=(EditText)findViewById(R.id.txtVida);
        listEnfer=(ListView)findViewById(R.id.listEnfermedades);
        eliminar();
        generarFamilia();
    }
    public void generarFamilia(){
        familiar = this.familiarDao.queryBuilder().where(FamiliarDao.Properties.Id_usuario.eq(idAdmin)).list();
        for (int i=0 ; i<familiar.size(); i++){
            Familiar actual = familiar.get(i);
            person = this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(actual.getId_persona())).list();
            if(person!=null){
                for(Persona comparar: person){
                    TempPersona tempPersona = new TempPersona();
                    tempPersona.setId_person(comparar.getId());
                    tempPersona.setNombre(comparar.getNombre());
                    tempPersonaDao.insert(tempPersona);
                }
            }
        }
        persona = tempPersonaDao.loadAll();
        int tam = (int)tempPersonaDao.count();
        String datos [] = new String[tam];
        for (int i=0; i<tam;i++){
            TempPersona actual = persona.get(i);
            datos [i] =  new String(actual.getNombre());
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        opFamilia.setAdapter(aa);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adFamilia:
                Intent intent0 = new Intent(FamiliaActivity.this, AdFamiliaActivity.class);
                startActivity(intent0);
                break;
            case R.id.ediFamiliar:
                if(idFamilia!=null){
                    editarPerfil(idFamilia);
                }
                break;
            case R.id.delFamilia:
                if(idFamilia!=null){
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                    dialogo1.setTitle("Desea eliminar a su familiar");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            eliminarFamiliar();
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });
                    dialogo1.show();
                }
                break;
            case R.id.actualizar:
                finish();
                Intent intent1 = new Intent(FamiliaActivity.this, FamiliaActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }
    public void editarPerfil(Long id){
        List<Persona> listAdmin=this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(id)).list();
        for(Persona persona : listAdmin){
            final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = settings.edit();
            editor.putInt("personaSesion", persona.getId().intValue());
            editor.commit();
            Intent intent1 = new Intent(FamiliaActivity.this, PerfilActivity.class);
            startActivity(intent1);
        }
    }
    public void eliminar (){
        listTemp =  tempPersonaDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempPersona selec = listTemp.get(i);
            tempPersonaDao.deleteByKey(selec.getId());
        }
    }
    public void buscarFamilia(Long id_buscar){
        List<Persona> list=this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(id_buscar)).list();
        for(Persona seleccionada : list){
            txtAltura.setText(seleccionada.getAltura().toString());
            txtPeso.setText(seleccionada.getPeso().toString());
            txtEdad.setText(seleccionada.getEdad());
            txtPesoIdeal.setText(seleccionada.getEst_vida());
        }
        eliminarAlimento();
        List<ListEnfermedad> list2=this.listEnfermedadDao.queryBuilder().where(ListEnfermedadDao.Properties.Id_persona.eq(id_buscar)).list();
        for(int i=0; i<list2.size();i++){
            ListEnfermedad actual = list2.get(i);
            List<Enfermedad> list3=this.enfermedadDao.queryBuilder().where(EnfermedadDao.Properties.Id.eq(actual.getId_enfermedad())).list();
            for(int j=0; j<list3.size();j++){
                Enfermedad actual2 = list3.get(j);
                List<AlimenRestring> list4 = alimenRestringDao.loadAll();
                for(int k=0; k<list4.size();k++){
                    AlimenRestring alimenRestring = list4.get(k);
                    if(alimenRestring.getId_enfermedad()==actual2.getId()){
                        List<Alimento> lis5=this.alimentoDao.queryBuilder().where(AlimentoDao.Properties.Id.eq(alimenRestring.getId_alimento())).list();
                        for(Alimento alimento : lis5){
                            List<TempAlimento> list6=this.tempAlimentoDao.queryBuilder().where(TempAlimentoDao.Properties.Id_alimento.eq(alimento.getId())).list();
                            if(list6.size()==0){
                                TempAlimento tempAlimento = new TempAlimento();
                                tempAlimento.setId_alimento(alimento.getId());
                                tempAlimento.setNombre(alimento.getNombre());
                                tempAlimentoDao.insert(tempAlimento);
                            }
                        }
                    }
                }
            }
        }
        List <TempAlimento> listTemp = tempAlimentoDao.loadAll();
        adapterAlimento = new AdapterAlimento( this, R.layout.formato_fila,listTemp);
        listEnfer.setAdapter(adapterAlimento);
    }
    public void eliminarAlimento (){
        List <TempAlimento> listTemp =  tempAlimentoDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempAlimento alimentoSelec = listTemp.get(i);
            tempAlimentoDao.deleteByKey(alimentoSelec.getId());
        }
    }
    public void eliminarFamiliar(){
        List<Persona> list =this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(idFamilia)).list();
        for(Persona objeto : list){
            personaDao.deleteByKey(objeto.getId());
        }
        List<ListEnfermedad> list2 =this.listEnfermedadDao.queryBuilder().where(ListEnfermedadDao.Properties.Id_persona.eq(idFamilia)).list();
        for (int i=0; i<list2.size(); i++){
            ListEnfermedad listEnfermedad = list2.get(i);
            listEnfermedadDao.deleteByKey(listEnfermedad.getId());
        }
        List<Familiar> list3 =this.familiarDao.queryBuilder().where(FamiliarDao.Properties.Id_persona.eq(idFamilia)).list();
        for(Familiar objeto : list3){
            familiarDao.deleteByKey(objeto.getId());
        }
        Toast.makeText(this, "AVISO: Familiar eliminado",Toast.LENGTH_LONG).show();
        finish();
        Intent intent1 = new Intent(FamiliaActivity.this, FamiliaActivity.class);
        startActivity(intent1);
    }
}