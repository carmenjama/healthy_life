package com.carmenjama.healthlife;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;

import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * Created by USUARIO on 28/08/2015.
 */
public class ActivityEstadistica extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PieChartView pieChart;
    private PieChartData data;
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasLabelForSelected = false;
    //Como ejemplo, en un examen tenemos buenas, malas y no respondidas.
    private int buenas = 0, malas = 0, noresp = 0, total = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
