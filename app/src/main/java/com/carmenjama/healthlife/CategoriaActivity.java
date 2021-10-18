package com.carmenjama.healthlife;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.AlimentoDao;
import com.healthLife.DAO.model.Categoria;
import com.healthLife.DAO.model.CategoriaDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;

import java.util.List;

/**
 * Created by USUARIO on 27/08/2015.
 */
public class CategoriaActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    CategoriaDao categoriaDao;
    AlimentoDao alimentoDao;
    ListView listaCate;
    Spinner buscar;
    AdapterCat adapterCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        categoriaDao = daoSession.getCategoriaDao();
        alimentoDao = daoSession.getAlimentoDao();
        listaCate = (ListView)findViewById(R.id.listAlimento);
        buscar = (Spinner)findViewById(R.id.spinner2);
        cargar();
        buscar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                List<Categoria> datos = categoriaDao.loadAll();
                Categoria categoria = datos.get(pos);
                cargarAlimento(categoria);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void cargar(){
        List<Categoria> datos = categoriaDao.loadAll();
        int tam = (int)categoriaDao.count();
        String categorias [] = new String[tam];
        for (int i=0; i<tam;i++){
            Categoria categoria = datos.get(i);
            categorias [i] =  new String(categoria.getNombre());
        }
        ArrayAdapter datillos =  new  ArrayAdapter ( this , android . R . layout . simple_spinner_dropdown_item , categorias);
        buscar.setAdapter(datillos);
    }
    public void cargarAlimento(Categoria categoria){
        List<Alimento> listAlmimento=this.alimentoDao.queryBuilder().where(AlimentoDao.Properties.Id_categoria.eq(categoria.getId())).list();
        adapterCat = new AdapterCat(this, R.layout.formato_cat, listAlmimento);
        listaCate.setAdapter(adapterCat);
    }
}