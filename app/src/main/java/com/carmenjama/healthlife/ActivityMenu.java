package com.carmenjama.healthlife;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Menu;
import com.healthLife.DAO.model.MenuDao;

import java.util.List;

/**
 * Created by USUARIO on 28/08/2015.
 */
public class ActivityMenu extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    MenuDao menuDao;
    TextView txtDesa, txtAlmu, txtMeri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        menuDao = daoSession.getMenuDao();
        txtDesa = (TextView)findViewById(R.id.textView37);
        txtAlmu = (TextView)findViewById(R.id.textView38);
        txtMeri = (TextView)findViewById(R.id.textView39);
        //int valorEntero = Math.floor(Math.random()*(0-menuDao.count()+1)+menuDao.count());
        List<Menu> list=this.menuDao.queryBuilder().where(MenuDao.Properties.Id.eq(Long.valueOf(1))).list();
        for(Menu objeto : list){
            txtDesa.setText(objeto.getNomDes());
            txtAlmu.setText(objeto.getNomAlmu());
            txtMeri.setText(objeto.getNomMeri());
        }
    }
}
