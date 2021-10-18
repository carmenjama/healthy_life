package com.carmenjama.healthlife;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.healthLife.DAO.model.AlimenRestring;
import com.healthLife.DAO.model.AlimenRestringDao;
import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.AlimentoDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Enfermedad;
import com.healthLife.DAO.model.EnfermedadDao;
import com.healthLife.DAO.model.ListEnfermedad;
import com.healthLife.DAO.model.ListEnfermedadDao;
import com.healthLife.DAO.model.TempAlimento;

import java.util.List;

/**
 * Created by USUARIO on 21/08/2015.
 */
public class ListEnferActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    EnfermedadDao enfermedadDao;
    AlimenRestringDao alimenRestringDao;
    AlimentoDao alimentoDao;
    ListEnfermedadDao listEnfermedadDao;
    ListViewEnfer listViewEnfer;
    List<Enfermedad> listTemp;

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listenferme);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        enfermedadDao = daoSession.getEnfermedadDao();
        alimenRestringDao = daoSession.getAlimenRestringDao();
        alimentoDao = daoSession.getAlimentoDao();
        listEnfermedadDao = daoSession.getListEnfermedadDao();
        list = (ListView)findViewById(R.id.listView2);
        cargar();
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Enfermedad alimentoSelec = listTemp.get(position);
                eliminarAlimento(alimentoSelec.getId());
                enfermedadDao.deleteByKey(alimentoSelec.getId());
                eliminarAlimento(alimentoSelec.getId());
                mensaje();
                cargar();
                return false;
            }
        });
    }
    public void mensaje(){
        Toast.makeText(this, "Enfermedad eliminada...", Toast.LENGTH_LONG).show();
    }
    public void cargar(){
        listTemp = enfermedadDao.loadAll();
        listViewEnfer = new ListViewEnfer( this, R.layout.formato_fila,listTemp);
        list.setAdapter(listViewEnfer);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return true;
    }
    public void eliminarAlimento(Long id){
        List<ListEnfermedad> list=this.listEnfermedadDao.queryBuilder().where(ListEnfermedadDao.Properties.Id_enfermedad.eq(id)).list();
        for (int i=0; i<list.size(); i++){
            ListEnfermedad actual = list.get(i);
            listEnfermedadDao.deleteByKey(actual.getId());
        }
    }
}