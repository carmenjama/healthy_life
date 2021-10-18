package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.healthLife.DAO.model.AlimenRestring;
import com.healthLife.DAO.model.AlimenRestringDao;
import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.AlimentoDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Diario;
import com.healthLife.DAO.model.DiarioDao;
import com.healthLife.DAO.model.DiarioLista;
import com.healthLife.DAO.model.DiarioListaDao;
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

import org.w3c.dom.Text;

import java.lang.annotation.ElementType;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by USUARIO on 26/08/2015.
 */
public class InformeFamilia extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    FamiliarDao familiarDao;
    PersonaDao personaDao;
    DiarioDao diarioDao;
    TempPersonaDao tempPersonaDao;
    ListEnfermedadDao listEnfermedadDao;
    AlimentoDao alimentoDao;
    DiarioListaDao diarioListaDao;
    EnfermedadDao enfermedadDao;
    AlimenRestringDao alimenRestringDao;
    TempAlimentoDao tempAlimentoDao;
    int idAdmin, caloriasMaximas;
    Double  caloriasDiario;
    Date date;
    List<Familiar> familiar;
    List<Persona> person;
    List<TempAlimento> temAliemntos;
    List<TempPersona> listTemp;
    Spinner opFamilia;
    ListView listMostrar;
    AdapterAlimento adapterAlimento;
    TextView txtEstado, txtConsu,txtReco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_familia);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        idAdmin=settings.getInt("administradorSesion", 0);
        int day, month, year;
        day = settings.getInt("diafecha", 0);
        month = settings.getInt("mesfecha", 0);
        year = settings.getInt("anofecha", 0);
        date= new Date(year,month,day);
        instanciar();
        opFamilia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TempPersona lala = listTemp.get(position);
                consultar(lala);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void instanciar(){
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        familiarDao = daoSession.getFamiliarDao();
        personaDao = daoSession.getPersonaDao();
        tempPersonaDao = daoSession.getTempPersonaDao();
        diarioDao = daoSession.getDiarioDao();
        listEnfermedadDao = daoSession.getListEnfermedadDao();
        alimentoDao = daoSession.getAlimentoDao();
        tempAlimentoDao = daoSession.getTempAlimentoDao();
        diarioListaDao = daoSession.getDiarioListaDao();
        enfermedadDao = daoSession.getEnfermedadDao();
        listMostrar =(ListView)findViewById(R.id.listAlimento);
        alimenRestringDao = daoSession.getAlimenRestringDao();
        opFamilia = (Spinner)findViewById(R.id.spinnerFamilia);
        txtEstado =(TextView)findViewById(R.id.txtEstado);
        txtConsu = (TextView)findViewById(R.id.txtConsu);
        txtReco = (TextView)findViewById(R.id.txtReco);
        generarFamilia();
    }
    public void generarFamilia(){
        eliminar();
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
        listTemp= tempPersonaDao.loadAll();
        int tam = (int)tempPersonaDao.count();
        String datos [] = new String[tam];
        for (int i=0; i<tam;i++){
            TempPersona actual = listTemp.get(i);
            datos[i]=actual.getNombre();
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        opFamilia.setAdapter(aa);
    }
    public void eliminar (){
        listTemp =  tempPersonaDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempPersona selec =listTemp.get(i);
            tempPersonaDao.deleteByKey(selec.getId());
        }
    }
    public void consultar(TempPersona tempPersona){
        person = this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(tempPersona.getId_person())).list();
        for(Persona comparar: person){
            if(comparar.getSexo().equals("Mujer")){
                consultaMujer(comparar);
                AlimentoDiario(comparar);
            }else{
                consultaHombre(comparar);
                AlimentoDiario(comparar);
            }
        }
    }
    public void consultaMujer (Persona persona){
        List<Diario> datillo2 = this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(date)).where(DiarioDao.Properties.Id_admin.eq(idAdmin)).list();
        for(Diario objeto : datillo2){
            caloriasDiario=objeto.getTot_calorias();
        }
        int calMinimas = edadMujer(persona.getEdad(), persona.getEst_vida());
        String datCal = String.valueOf(calMinimas);
        Double pesoIdeal = persona.getAltura()*2/persona.getPeso();
        String estadoPeso = peso(pesoIdeal);
        txtEstado.setText("Recuerde que el estado de su peso es "+estadoPeso);
        txtReco.setText(datCal +" - "+caloriasMaximas);
        if(caloriasDiario>caloriasMaximas){
            if(caloriasDiario==0){

            }else{
                txtConsu.setText("Su consumo de calorias sobrepaso el recomendado");
            }
        }else{
            if(caloriasDiario<calMinimas){
                txtConsu.setText("Su consumo de calorias es menor al recomendado");
            }else{
                txtConsu.setText("Su consumo de calorias esta dentro del rango recomendado");
            }
        }
    }
    public int edadMujer (String edad, String estiVida){
        int caloriasMinimas=0;
        switch (edad) {
            case "2-3":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1000;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=1000;
                        caloriasMaximas=1400;
                    }else{
                        caloriasMinimas=1000;
                        caloriasMaximas=1400;
                    }
                }
                break;
            case "4-8":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1200;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=1400;
                        caloriasMaximas=1600;
                    }else{
                        caloriasMinimas=1400;
                        caloriasMaximas=1800;
                    }
                }
                break;
            case "9-13":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1600;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=1600;
                        caloriasMaximas=2000;
                    }else{
                        caloriasMinimas=1800;
                        caloriasMaximas=2200;
                    }
                }
                break;
            case "14-18":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1800;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=2000;
                        caloriasMaximas=0;
                    }else{
                        caloriasMinimas=2400;
                        caloriasMaximas=0;
                    }
                }
                break;
            case "19-30":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=2000;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=2000;
                        caloriasMaximas=2200;
                    }else{
                        caloriasMinimas=2400;
                        caloriasMaximas=0;
                    }
                }
                break;
            case "31-50":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1800;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=2000;
                        caloriasMaximas=0;
                    }else{
                        caloriasMinimas=2200;
                        caloriasMaximas=0;
                    }
                }
                break;
            case "+50":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1600;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=1800;
                        caloriasMaximas=0;
                    }else{
                        caloriasMinimas=2000;
                        caloriasMaximas=2200;
                    }
                }
                break;
        }
        return caloriasMinimas;
    }
    public String peso(Double pesoIdeal){
        String retorno;
        if(pesoIdeal<=18){
            retorno ="por debajo de lo normal";
        }else{
            if(pesoIdeal>10 && pesoIdeal<=24.9){
                retorno ="peso normal";
            }else{
                if(pesoIdeal>30 && pesoIdeal<=34.90){
                    retorno ="obesidad de 2do. Grado";
                }else{
                    if(pesoIdeal>35 && pesoIdeal<=39.90){
                        retorno ="obesidad de 3er. Grado";
                    }else{
                        retorno ="obesidad de 4to. Grado";
                    }
                }
            }
        }
        return retorno;
    }
    public void AlimentoDiario(Persona persona){
        eliminar2();
        List<ListEnfermedad> listEnfermedad=this.listEnfermedadDao.queryBuilder().where(ListEnfermedadDao.Properties.Id_persona.eq(persona.getId())).list();
        for (int i=0; i< listEnfermedad.size(); i++){
            ListEnfermedad enfermedad = listEnfermedad.get(i);
            List<Enfermedad> listEnfer = this.enfermedadDao.queryBuilder().where(EnfermedadDao.Properties.Id.eq(enfermedad.getId_enfermedad())).list();
            for(Enfermedad objeto : listEnfer){
                List<AlimenRestring> listRestring = this.alimenRestringDao.queryBuilder().where(AlimenRestringDao.Properties.Id_enfermedad.eq(objeto.getId())).list();
                for (int j=0; j<listRestring.size(); j++){
                    AlimenRestring alimenRestring = listRestring.get(j);
                    List<Alimento> datillo = this.alimentoDao.queryBuilder().where(AlimentoDao.Properties.Id.eq(alimenRestring.getId_alimento())).list();
                    for(Alimento dato : datillo){
                        buscarDiario(dato);
                    }
                }
            }
        }
        temAliemntos=tempAlimentoDao.loadAll();
        adapterAlimento=new AdapterAlimento(this, R.layout.formato_fila,temAliemntos);
        listMostrar.setAdapter(adapterAlimento);
    }
    public void buscarDiario(Alimento alimento){
        List<Diario> datillo = this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(date)).where(DiarioDao.Properties.Id_admin.eq(idAdmin)).list();
        for(Diario objeto : datillo){
            caloriasDiario=objeto.getTot_calorias();
            List<DiarioLista> datillo2 = this.diarioListaDao.queryBuilder().where(DiarioListaDao.Properties.Id_diario.eq(objeto.getId())).where(DiarioListaDao.Properties.Id_alimento.eq(alimento.getId())).list();
            for(DiarioLista objeto2 : datillo2){
                TempAlimento tempAlimento = new TempAlimento();
                tempAlimento.setId_alimento(alimento.getId());
                tempAlimento.setNombre(alimento.getNombre());
                tempAlimentoDao.insert(tempAlimento);
            }
        }
    }
    public void eliminar2 (){
        temAliemntos =  tempAlimentoDao.loadAll();
        for (int i=0; i<temAliemntos.size();i++){
            TempAlimento alimentoSelec = temAliemntos.get(i);
            tempAlimentoDao.deleteByKey(alimentoSelec.getId());
        }
    }
    public void consultaHombre (Persona persona){
        List<Diario> datillo2 = this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(date)).where(DiarioDao.Properties.Id_admin.eq(idAdmin)).list();
        for(Diario objeto : datillo2){
            caloriasDiario=objeto.getTot_calorias();
        }
        int calMinimas = edadHombre(persona.getEdad(), persona.getEst_vida());
        String datCal = String.valueOf(calMinimas);
        Double pesoIdeal = persona.getAltura()*2/persona.getPeso();
        String estadoPeso = peso(pesoIdeal);
        txtEstado.setText("Recuerde que el estado de su peso es "+estadoPeso);
        txtReco.setText(datCal +" - "+caloriasMaximas);
        if(caloriasDiario<caloriasMaximas){
            if(caloriasDiario==0){

            }else{
                txtConsu.setText("Su consumo de calorias sobrepaso el recomendado");
            }
        }else{
            if(caloriasDiario<calMinimas){
                txtConsu.setText("Su consumo de calorias es menor al recomendado");
            }else{
                txtConsu.setText("Su consumo de calorias esta dentro del rango recomendado");
            }
        }
    }
    public int edadHombre (String edad, String estiVida){
        int caloriasMinimas=0;
        switch (edad) {
            case "2-3":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1000;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=1000;
                        caloriasMaximas=1400;
                    }else{
                        caloriasMinimas=1000;
                        caloriasMaximas=1400;
                    }
                }
                break;
            case "4-8":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1400;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=1400;
                        caloriasMaximas=1600;
                    }else{
                        caloriasMinimas=1600;
                        caloriasMaximas=2000;
                    }
                }
                break;
            case "9-13":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=1800;
                    caloriasMaximas=0;

                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=1800;
                        caloriasMaximas=2200;
                    }else{
                        caloriasMinimas=2000;
                        caloriasMaximas=2600;
                    }
                }
                break;
            case "14-18":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=2200;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=2400;
                        caloriasMaximas=2800;
                    }else{
                        caloriasMinimas=2800;
                        caloriasMaximas=3200;
                    }
                }
                break;
            case "19-30":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=2400;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=2600;
                        caloriasMaximas=2800;
                    }else{
                        caloriasMinimas=3000;
                        caloriasMaximas=0;
                    }
                }
                break;
            case "31-50":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=2200;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=2400;
                        caloriasMaximas=2600;
                    }else{
                        caloriasMinimas=2800;
                        caloriasMaximas=3000;
                    }
                }
                break;
            case "+50":
                if(estiVida.equals("Sedentario")){
                    caloriasMinimas=2000;
                    caloriasMaximas=0;
                }else{
                    if(estiVida.equals("Moderadamente Activo")){
                        caloriasMinimas=2200;
                        caloriasMaximas=2400;
                    }else{
                        caloriasMinimas=2400;
                        caloriasMaximas=2800;
                    }
                }
                break;
        }
        return caloriasMinimas;
    }
}