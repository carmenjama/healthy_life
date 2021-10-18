package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.healthLife.DAO.model.Administrador;
import com.healthLife.DAO.model.AdministradorDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Enfermedad;
import com.healthLife.DAO.model.EnfermedadDao;
import com.healthLife.DAO.model.FamiliarDao;
import com.healthLife.DAO.model.ListEnfermedad;
import com.healthLife.DAO.model.ListEnfermedadDao;
import com.healthLife.DAO.model.Persona;
import com.healthLife.DAO.model.PersonaDao;
import com.healthLife.DAO.model.TempEnfermedad;
import com.healthLife.DAO.model.TempEnfermedadDao;
import com.healthLife.DAO.model.UsuarioDao;

import java.util.List;

public class PerfilActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    PersonaDao personaDao;
    EnfermedadDao enfermedadDao;
    ListEnfermedadDao listEnfermedadDao;
    TempEnfermedadDao tempEnfermedadDao;
    FamiliarDao familiarDao;
    int idUsuario, idAdmin, idPerson;
    String vida="Seleccione";
    AdapterEnfermedad adapterEnfermedad;
    List<TempEnfermedad> listTemp;
    List<TempEnfermedad> listTemp2;

    EditText txtNombre, txtAltura, txtPeso, txtEnfermedad;
    RadioButton sexMujer, sexHombre, edad0, edad1, edad2, edad3, edad4, edad5, edad6;
    Spinner estiVida;
    ListView listEnferme, listDatos;
    Button btnAceptar, btnAgLista, btnBuscar, btnAgregar;
    String []opciones= new String[]{"Seleccione","Sedentario","Moderadamente Activo","Activo"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();

        final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        idUsuario=settings.getInt("usuarioSesion", 0);
        idAdmin=settings.getInt("administradorSesion", 0);
        idPerson=settings.getInt("personaSesion",0);
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
                Intent intent = new Intent(PerfilActivity.this, AdEnfermedadActivity.class);
                startActivity(intent);
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
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
                Intent intent = new Intent(PerfilActivity.this, ListEnferActivity.class);
                startActivity(intent);
            }
        });
        listEnferme.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TempEnfermedad alimentoSelec = listTemp.get(position);
                tempEnfermedadDao.deleteByKey(alimentoSelec.getId());
                mensaje2();
                generarEnfermedad();
                return false;
            }
        });
        listDatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TempEnfermedad alimento = listTemp2.get(position);
                mensaje(alimento.getId_enfermedad());
                return false;
            }
        });
    }
    public void mensaje(Long id){
        List<ListEnfermedad> list = this.listEnfermedadDao.queryBuilder().where(ListEnfermedadDao.Properties.Id_persona.eq(idPerson)).where(ListEnfermedadDao.Properties.Id_enfermedad.eq(id)).list();
        for(ListEnfermedad objeto : list){
            listEnfermedadDao.deleteByKey(objeto.getId());
        }
        Toast.makeText(this, "Enfermedad eliminada...",Toast.LENGTH_LONG).show();
        generarEnfermedadGuardada();
    }
    public void mensaje2(){
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
        listDatos=(ListView)findViewById(R.id.listView2);
        btnAceptar=(Button)findViewById(R.id.btnAceptar);
        generarDatos();
    }
    public void generarDatos(){
            List<Persona> listPersona = this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(idPerson)).list();
            for(Persona objeto3 : listPersona){
                txtNombre.setText(objeto3.getNombre());
                txtAltura.setText(objeto3.getAltura().toString());
                txtPeso.setText(objeto3.getPeso().toString());
                if(objeto3.getSexo().equals("Mujer")){
                    sexMujer.setChecked(true);
                    sexHombre.setChecked(false);
                }else{
                    if(objeto3.getSexo().equals("Hombre")){
                        sexMujer.setChecked(false);
                        sexHombre.setChecked(true);
                    }else{
                        sexMujer.setChecked(false);
                        sexHombre.setChecked(false);
                    }
                }
                switch (objeto3.getEdad()) {
                    case "0":
                        edad0.setChecked(false);
                        edad1.setChecked(false);
                        edad2.setChecked(false);
                        edad3.setChecked(false);
                        edad4.setChecked(false);
                        edad5.setChecked(false);
                        edad6.setChecked(false);
                        break;
                    case "2-3":
                        edad0.setChecked(true);
                        break;
                    case "4-8":
                        edad1.setChecked(true);
                        break;
                    case "9-13":
                        edad2.setChecked(true);
                        break;
                    case "14-18":
                        edad3.setChecked(true);
                        break;
                    case "19-30":
                        edad4.setChecked(true);
                        break;
                    case "31-50":
                        edad5.setChecked(true);
                        break;
                    case "+50":
                        edad6.setChecked(true);
                        break;
                }
                ArrayAdapter datillos =  new  ArrayAdapter ( this , android . R . layout . simple_spinner_dropdown_item , opciones );
                estiVida.setAdapter(datillos);
                eliminar();
                generarEnfermedadGuardada();
            }
    }
    public void generarEnfermedadGuardada(){
        List<Enfermedad> list = enfermedadDao.loadAll();
        for(int i=0; i<list.size(); i++){
            Enfermedad actual = list.get(i);
            List<ListEnfermedad> objetos = this.listEnfermedadDao.queryBuilder().where(ListEnfermedadDao.Properties.Id_persona.eq(idPerson)).where(ListEnfermedadDao.Properties.Id_enfermedad.eq(actual.getId())).list();
            for(ListEnfermedad comparar: objetos){
                TempEnfermedad temp = new TempEnfermedad();
                temp.setNombre(actual.getDescripcion());
                temp.setId_enfermedad(actual.getId());
                tempEnfermedadDao.insert(temp);
            }
        }
        listTemp = tempEnfermedadDao.loadAll();
        adapterEnfermedad = new AdapterEnfermedad( this, R.layout.formato_fila,listTemp);
        listDatos.setAdapter(adapterEnfermedad);
        listTemp2=tempEnfermedadDao.loadAll();
        eliminar();
    }
    public void AgLista(){
        String dato = txtEnfermedad.getText().toString();
        if(dato.equals("")){
            Toast.makeText(this, "AVISO: No ha ingresado dato",Toast.LENGTH_LONG).show();
        }else{
            List<Enfermedad> list=this.enfermedadDao.queryBuilder().where(EnfermedadDao.Properties.Descripcion.eq(dato)).list();
            if(list.size()==0){
                Toast.makeText(this, "AVISO: Enfermedad no encontrada",Toast.LENGTH_LONG).show();
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
    public void guardar(){
        String datNombre = txtNombre.getText().toString();
        Double datAltura = Double.parseDouble(txtAltura.getText().toString());
        Double datPeso = Double.parseDouble(txtPeso.getText().toString());

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
        if(datNombre.equals("") ||datNombre.equals("Debe editar perfil") || datAltura==0.0 || datPeso==0.0 || datSexo.equals("0") || edad.equals("0") || vida.equals("")|| vida.equals("Seleccione")){
            Toast.makeText(getApplicationContext(), "AVISO: Datos incompletos", Toast.LENGTH_SHORT).show();
        }else{
            regDatosUsu(datNombre, datAltura, datPeso, datSexo, edad);
        }

    }
    public void regDatosUsu(String datNombre, Double datAltura, Double datPeso, String datSexo, String datEdad){
        List<Persona> listPerson=this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(idPerson)).list();
        for(Persona persona : listPerson){
            persona.setNombre(datNombre);
            persona.setAltura(datAltura);
            persona.setPeso(datPeso);
            persona.setSexo(datSexo);
            persona.setEdad(datEdad);
            persona.setEst_vida(vida);
            personaDao.update(persona);
            listTemp =  tempEnfermedadDao.loadAll();
            for (int i=0; i<listTemp.size();i++){
                TempEnfermedad alimentoSelec = listTemp.get(i);
                ListEnfermedad listEnfermedad = new ListEnfermedad();
                listEnfermedad.setId_enfermedad(alimentoSelec.getId_enfermedad());
                listEnfermedad.setId_persona(persona.getId());
                listEnfermedadDao.insert(listEnfermedad);
            }
            Toast.makeText(getApplicationContext(), "AVISO: Datos actualizados", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent=new Intent(PerfilActivity.this, PerfilActivity.class);
            startActivity(intent);
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
}