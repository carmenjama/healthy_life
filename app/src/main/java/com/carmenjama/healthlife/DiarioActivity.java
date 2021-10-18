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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.AlimentoDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Diario;
import com.healthLife.DAO.model.DiarioDao;
import com.healthLife.DAO.model.DiarioLista;
import com.healthLife.DAO.model.DiarioListaDao;
import com.healthLife.DAO.model.TempDiario;
import com.healthLife.DAO.model.TempDiarioDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.Property;
import freemarker.template.utility.DateUtil;

public class DiarioActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    DiarioDao diarioDao;
    DiarioListaDao diarioListaDao;
    AlimentoDao alimentoDao;
    TempDiarioDao tempDiarioDao;

    DatePicker fecAlimento;
    TextView txtCaloria, txtGrasa, txtProteina, txtCarbo, txtPorcion;
    ListView listAlimento;
    EditText txtAlimento;
    Button btnAgregar, btnBuscar;
    int idAmin, day, month, year, tipoGuardado, eliminar;
    AdapterDiario adapterDiario;
    Date hoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        idAmin=settings.getInt("administradorSesion", 0);
        db =  openHelper.getWritableDatabase();
        instanciar();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarDiario(1);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarLista();
            }
        });
        listAlimento.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                List<TempDiario> listTemp= tempDiarioDao.loadAll();
                TempDiario tempDiario = listTemp.get(position);
                eliminarLista(tempDiario);
                return false;
            }
        });
    }
    public void instanciar(){
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        diarioDao = daoSession.getDiarioDao();
        diarioListaDao = daoSession.getDiarioListaDao();
        alimentoDao = daoSession.getAlimentoDao();
        tempDiarioDao =  daoSession.getTempDiarioDao();

        fecAlimento = (DatePicker)findViewById(R.id.datComida);
        txtCaloria = (TextView)findViewById(R.id.txtCaloria);
        txtGrasa = (TextView)findViewById(R.id.txtGrasas);
        txtProteina = (TextView)findViewById(R.id.txtProteina);
        txtCarbo = (TextView)findViewById(R.id.txtCarbo);
        txtPorcion = (EditText)findViewById(R.id.editText2);
        listAlimento = (ListView)findViewById(R.id.listAlimento);
        txtAlimento = (EditText)findViewById(R.id.txtAlimento);
        btnAgregar =(Button)findViewById(R.id.button5);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        txtPorcion.setText("0.00");
        day=fecAlimento.getDayOfMonth();
        month=fecAlimento.getMonth();
        year=fecAlimento.getYear();
        hoy = new Date(year,month,day);
        elimiarDiario();
        condicion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diario, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_nuevo:
                tipoGuardado=1;
                txtCaloria.setText("0.00");
                txtProteina.setText("0.00");
                txtGrasa.setText("0.00");
                txtCarbo.setText("0.00");
                txtAlimento.setText("");
                txtPorcion.setText("0.00");
                txtAlimento.setEnabled(true);
                txtPorcion.setEnabled(true);
                fecAlimento.updateDate(year, month, day);
                fecAlimento.setEnabled(true);
                elimiarDiario();
                List <TempDiario> listTemp = tempDiarioDao.loadAll();
                adapterDiario = new AdapterDiario( this, R.layout.formato_diario,listTemp);
                listAlimento.setAdapter(adapterDiario);
                break;
            case R.id.menu_inser:
                tipoGuardado=1;
                int dayNuevo, monthNuevo, yearNuevo;
                dayNuevo=fecAlimento.getDayOfMonth();
                monthNuevo=fecAlimento.getMonth();
                yearNuevo=fecAlimento.getYear();
                Date fechaNueva = new Date(yearNuevo,monthNuevo,dayNuevo);
                guardar(fechaNueva);
                break;
            case R.id.menu_informe:
                int days, months, years;
                days=fecAlimento.getDayOfMonth();
                months=fecAlimento.getMonth();
                years=fecAlimento.getYear();
                //String fechaNuevas = days+"/"+months+"/"+years;
                final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = settings.edit();
                editor.putInt("diafecha", days);
                editor.putInt("mesfecha",months);
                editor.putInt("anofecha",years);
                editor.commit();
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Tipo informe");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("General", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.setNegativeButton("Familia", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent intent = new Intent(DiarioActivity.this, InformeFamilia.class);
                        startActivity(intent);
                    }
                });
                dialogo1.show();
                break;
        }
        return true;
    }
    public void condicion (){
        List<Diario> listDiario=this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(hoy)).where(DiarioDao.Properties.Id_admin.eq(idAmin)).list();
        if(listDiario.size()==0){
            tipoGuardado=1;
            fecAlimento.setEnabled(true);
        }else{
            buscarDiario(0);
        }
    }
    public void buscarAlimento(Date date){
        elimiarDiario();
        List<Diario> list=this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(date)).where(DiarioDao.Properties.Id_admin.eq(idAmin)).list();
        for(Diario diario : list){
            List<DiarioLista> listDiario=this.diarioListaDao.queryBuilder().where(DiarioListaDao.Properties.Id_diario.eq(diario.getId())).list();
            for(int i = 0 ; i<listDiario.size() ; i++){
                DiarioLista diarioLista = listDiario.get(i);
                List<Alimento> listAlimento=this.alimentoDao.queryBuilder().where(AlimentoDao.Properties.Id.eq(diarioLista.getId_alimento())).list();
                for(Alimento temp : listAlimento) {
                    insertarTemporal(temp, diarioLista);
                }
            }
        }
        List <TempDiario> listTemp = tempDiarioDao.loadAll();
        adapterDiario = new AdapterDiario( this, R.layout.formato_diario,listTemp);
        listAlimento.setAdapter(adapterDiario);
    }
    public void elimiarDiario(){
        List <TempDiario> listTemp =  tempDiarioDao.loadAll();
        for (int i=0; i<listTemp.size();i++){
            TempDiario alimentoSelec = listTemp.get(i);
            tempDiarioDao.deleteByKey(alimentoSelec.getId());
        }
    }
    public void buscarDiario(int tipo){
        Date date;
        if(tipo==0){
            date=hoy;
        }else{
            int day, month, year;
            day=fecAlimento.getDayOfMonth();
            month=fecAlimento.getMonth();
            year=fecAlimento.getYear();
            date = new Date(year,month,day);
        }
        List<Diario> listDiario=this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(date)).where(DiarioDao.Properties.Id_admin.eq(idAmin)).list();
        for(Diario objeto : listDiario){
            fecAlimento.setEnabled(false);
            txtAlimento.setEnabled(true);
            txtPorcion.setEnabled(true);
            txtPorcion.setText("0.00");
            txtCaloria.setText(objeto.getTot_calorias().toString());
            txtGrasa.setText(objeto.getTot_grasa().toString());
            txtProteina.setText(objeto.getTot_proteina().toString());
            txtCarbo.setText(objeto.getTot_carbo().toString());
            buscarAlimento(objeto.getFecha());
        }
        if(listDiario.size()==0){
            if(tipo!=0){
                Toast.makeText(this, "AVISO: No se encontraron datos",Toast.LENGTH_LONG).show();
            }
            fecAlimento.setEnabled(true);
            txtCaloria.setText("0.00");
            txtProteina.setText("0.00");
            txtGrasa.setText("0.00");
            txtCarbo.setText("0.00");
            txtPorcion.setText("0.00");
            txtAlimento.setText("");
            txtPorcion.setText("0.00");
            elimiarDiario();
            List <TempDiario> listTemp = tempDiarioDao.loadAll();
            adapterDiario = new AdapterDiario( this, R.layout.formato_diario,listTemp);
            listAlimento.setAdapter(adapterDiario);
        }
    }
    public void agregarLista(){
        int dayNuevo, monthNuevo, yearNuevo;
        dayNuevo=fecAlimento.getDayOfMonth();
        monthNuevo=fecAlimento.getMonth();
        yearNuevo=fecAlimento.getYear();
        Date fechNueva = new Date(yearNuevo,monthNuevo,dayNuevo);
        String datAlimento = txtAlimento.getText().toString();
        Double porcion = Double.valueOf(txtPorcion.getText().toString());
        if(porcion==0.00){
            Toast.makeText(this, "AVISO: Ingrese porcion",Toast.LENGTH_LONG).show();
        }else{
            if(datAlimento.equals("")){
                Toast.makeText(this, "AVISO: Ingrese alimento",Toast.LENGTH_LONG).show();
            }else{
                List<Diario> listDiario=this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(fechNueva)).where(DiarioDao.Properties.Id_admin.eq(idAmin)).list();
                List<Alimento> listAlimento1=this.alimentoDao.queryBuilder().where(AlimentoDao.Properties.Nombre.eq(datAlimento)).list();
                if(listAlimento1.size()==0){
                    Toast.makeText(this, "AVISO: Alimento no encontrado",Toast.LENGTH_LONG).show();
                }else{
                    if(listDiario.size()==0){
                        for(Alimento alimento : listAlimento1){
                            TempDiario tempDiario = new TempDiario();
                            tempDiario.setId_lista(Long.valueOf(0));
                            tempDiario.setId_diario(Long.valueOf(0));
                            tempDiario.setId_alimento(alimento.getId());
                            tempDiario.setNombre(alimento.getNombre());
                            tempDiario.setCalorias(alimento.getCalorias());
                            tempDiario.setGrasas(alimento.getGrasas());
                            tempDiario.setProteinas(alimento.getProteinas());
                            tempDiario.setCarbohidratos(alimento.getCarbohidratos());
                            tempDiario.setPorcion(porcion);
                            tempDiario.setTotal(calculoTotales(alimento.getCalorias(), alimento.getGrasas(), alimento.getProteinas(), alimento.getCarbohidratos(), porcion, 1, 0));
                            tempDiarioDao.insert(tempDiario);
                            List <TempDiario> listTemp = tempDiarioDao.loadAll();
                            adapterDiario = new AdapterDiario( this, R.layout.formato_diario,listTemp);
                            listAlimento.setAdapter(adapterDiario);
                        }
                    }else{
                        for(Diario diario : listDiario){
                            regLisDiario(diario.getId());
                        }
                    }
                }
            }
        }
    }
    public double calculoTotales(Double cal, Double grasa, Double prot, Double carbo, Double porcion, int tipo, int tipoDos){
        Double totCaloria = cal*porcion/100;
        Double totProteina = prot*porcion/100;
        Double totGrasa = grasa*porcion/100;
        Double totCarbo = carbo*porcion/100;
        Double datCaloria, datProteina, datGrasa,datCarbo;
        if(tipo==1){
            datCaloria= Double.valueOf(txtCaloria.getText().toString())+totCaloria;
            datProteina = Double.valueOf(txtProteina.getText().toString())+totProteina;
            datGrasa = Double.valueOf(txtGrasa.getText().toString())+totGrasa;
            datCarbo = Double.valueOf(txtCarbo.getText().toString())+totCarbo;
        }else{
            datCaloria = Double.valueOf(txtCaloria.getText().toString())-totCaloria;
            datProteina = Double.valueOf(txtProteina.getText().toString())-totProteina;
            datGrasa = Double.valueOf(txtGrasa.getText().toString())-totGrasa;
            datCarbo = Double.valueOf(txtCarbo.getText().toString())-totCarbo;
        }
        if(tipoDos==0){
            txtCaloria.setText(String.format("%.2f",datCaloria));
            txtProteina.setText(String.format("%.2f",datProteina));
            txtGrasa.setText(String.format("%.2f",datGrasa));
            txtCarbo.setText(String.format("%.2f",datCarbo));
        }
        return totCaloria;
    }
    public void guardar (Date date){
        Double datCalorias = Double.valueOf(txtCaloria.getText().toString());
        if(date.after(hoy)){
            Toast.makeText(this, "AVISO: Fecha incorrecta",Toast.LENGTH_LONG).show();
        }else{
            if(datCalorias==0.00){
                Toast.makeText(this, "AVISO: No ha ingresado datos",Toast.LENGTH_LONG).show();
            }else{
                regDatos(date);
            }
        }
    }
    public void regDatos(Date date){
        Double datCalorias = Double.valueOf(txtCaloria.getText().toString());
        Double datProteinas = Double.valueOf(txtProteina.getText().toString());
        Double datGrasas = Double.valueOf(txtGrasa.getText().toString());
        Double datCarbo = Double.valueOf(txtCarbo.getText().toString());
        List<Diario> listDiario=this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(date)).where(DiarioDao.Properties.Id_admin.eq(idAmin)).list();
        if(tipoGuardado==1){
            if(listDiario.size()==0){
                Diario diario = new Diario();
                diario.setId_admin(Long.valueOf(idAmin));
                diario.setTot_calorias(datCalorias);
                diario.setTot_grasa(datGrasas);
                diario.setTot_proteina(datProteinas);
                diario.setTot_carbo(datCarbo);
                diario.setFecha(date);
                diarioDao.insert(diario);
                regLisDiario(diario.getId());
                fecAlimento.setEnabled(false);
                txtAlimento.setText("");
                txtPorcion.setText("0.00");
                Toast.makeText(this, "AVISO: Diario Guardado",Toast.LENGTH_LONG).show();
                elimiarDiario();
            }else{
                Toast.makeText(this, "AVISO: Ya existe este diario",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void regLisDiario(Long idDiario){
        List<TempDiario> listComida= tempDiarioDao.loadAll();
        if(tipoGuardado==1){
            for(int i=0; i<listComida.size(); i++){
                TempDiario temp = listComida.get(i);
                DiarioLista diarioLista = new DiarioLista();
                diarioLista.setId_diario(idDiario);
                diarioLista.setId_alimento(temp.getId_alimento());
                diarioLista.setPorcion(temp.getPorcion());
                diarioListaDao.insert(diarioLista);
            }
        }
    }
    public void eliminarLista(TempDiario tempDiario){
        int dayNuevo, monthNuevo, yearNuevo;
        dayNuevo=fecAlimento.getDayOfMonth();
        monthNuevo=fecAlimento.getMonth();
        yearNuevo=fecAlimento.getYear();
        Date fechNueva = new Date(yearNuevo,monthNuevo,dayNuevo);
        List<DiarioLista> listDiario=this.diarioListaDao.queryBuilder().where(DiarioListaDao.Properties.Id.eq(tempDiario.getId_lista())).list();
        if(listDiario.size()==0){
            calculoTotales(tempDiario.getCalorias(), tempDiario.getGrasas(), tempDiario.getProteinas(), tempDiario.getCarbohidratos(), tempDiario.getPorcion(), 2, 0);
            tempDiarioDao.deleteByKey(tempDiario.getId());
            List<TempDiario>  listTemp = tempDiarioDao.loadAll();
            adapterDiario = new AdapterDiario( this, R.layout.formato_diario,listTemp);
            listAlimento.setAdapter(adapterDiario);
        }else{
            Double datTotal = calculoTotales(tempDiario.getCalorias(), tempDiario.getGrasas(), tempDiario.getProteinas(), tempDiario.getCarbohidratos(), tempDiario.getPorcion(), 2, 0);
            List<Diario> Diario=this.diarioDao.queryBuilder().where(DiarioDao.Properties.Fecha.eq(fechNueva)).where(DiarioDao.Properties.Id_admin.eq(idAmin)).list();
            for(Diario objeto : Diario){
                objeto.setTot_calorias(Double.valueOf(txtCaloria.getText().toString()));
                objeto.setTot_grasa(Double.valueOf(txtGrasa.getText().toString()));
                objeto.setTot_proteina(Double.valueOf(txtProteina.getText().toString()));
                objeto.setTot_carbo(Double.valueOf(txtCarbo.getText().toString()));
                diarioDao.update(objeto);
                for(DiarioLista objeto2 : listDiario){
                    diarioListaDao.deleteByKey(objeto2.getId());
                }
                buscarAlimento2(objeto, datTotal);
            }
        }
    }
    public void buscarAlimento2(Diario diario, Double total){
        elimiarDiario();
        List<DiarioLista> listDiario=this.diarioListaDao.queryBuilder().where(DiarioListaDao.Properties.Id_diario.eq(diario.getId())).list();
        for(int i = 0 ; i<listDiario.size() ; i++){
            DiarioLista diarioLista = listDiario.get(i);
                List<Alimento> listAlimento=this.alimentoDao.queryBuilder().where(AlimentoDao.Properties.Id.eq(diarioLista.getId_alimento())).list();
                for(Alimento temp : listAlimento) {
                    TempDiario tempDiario = new TempDiario();
                    tempDiario.setId_lista(diarioLista.getId());
                    tempDiario.setId_diario(diarioLista.getId());
                    tempDiario.setNombre(temp.getNombre());
                    tempDiario.setCalorias(temp.getCalorias());
                    tempDiario.setGrasas(temp.getGrasas());
                    tempDiario.setProteinas(temp.getProteinas());
                    tempDiario.setCarbohidratos(temp.getCarbohidratos());
                    tempDiario.setPorcion(diarioLista.getPorcion());
                    tempDiario.setTotal(total);
                    tempDiarioDao.insert(tempDiario);
                }
            }
        List <TempDiario> listTemp = tempDiarioDao.loadAll();
        adapterDiario = new AdapterDiario( this, R.layout.formato_diario,listTemp);
        listAlimento.setAdapter(adapterDiario);
    }
    public void insertarTemporal(Alimento temp, DiarioLista diarioLista){
        TempDiario tempDiario = new TempDiario();
        tempDiario.setId_lista(diarioLista.getId());
        tempDiario.setId_diario(diarioLista.getId());
        tempDiario.setNombre(temp.getNombre());
        tempDiario.setCalorias(temp.getCalorias());
        tempDiario.setGrasas(temp.getGrasas());
        tempDiario.setProteinas(temp.getProteinas());
        tempDiario.setCarbohidratos(temp.getCarbohidratos());
        tempDiario.setPorcion(diarioLista.getPorcion());
        tempDiario.setTotal(calculoTotales(temp.getCalorias(), temp.getGrasas(), temp.getProteinas(), temp.getCarbohidratos(), diarioLista.getPorcion(),1,1));
        tempDiarioDao.insert(tempDiario);
    }
}