package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.healthLife.DAO.model.Administrador;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Usuario;
import com.healthLife.DAO.model.UsuarioDao;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by USUARIO on 21/08/2015.
 */
public class PassActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    UsuarioDao usuarioDao;

    TextView txtNombre, txtPass1, txtPass2;
    Button btnModificar, btnCancelar;
    String datBuscar, datPas, otro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        instanciar();
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void instanciar(){
        txtNombre = (EditText)findViewById(R.id.txtUsuario);
        txtPass1 = (EditText)findViewById(R.id.txtPass1);
        txtPass2 = (EditText)findViewById(R.id.txtPass2);
        btnModificar = (Button)findViewById(R.id.button3);
        btnCancelar = (Button)findViewById(R.id.button2);
    }
    public void modificar (){
        datBuscar = txtNombre.getText().toString();
        datPas = txtPass1.getText().toString();
        otro = txtPass2.getText().toString();
        if(datPas.equals("")||datBuscar.equals("")){
            Toast.makeText(this, "AVISO: Datos incompletos",Toast.LENGTH_LONG).show();
        }else{
            if(datPas.equals(otro)){
                List<Usuario> listUsu=this.usuarioDao.queryBuilder().where(UsuarioDao.Properties.Nombre.eq(datBuscar)).list();
                if(listUsu.size()==0){
                    Toast.makeText(this, "AVISO: Usuario no existe",Toast.LENGTH_LONG).show();
                }
                for(Usuario usuario : listUsu){
                    usuario.setPass(datPas);
                    usuarioDao.update(usuario);
                    Toast.makeText(this, "AVISO: Contrasena modificada",Toast.LENGTH_LONG).show();
                    finish();
                }
            }else{
                Toast.makeText(this, "AVISO: Contrasenas no coinciden",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}
