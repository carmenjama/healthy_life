package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.healthLife.DAO.model.Administrador;
import com.healthLife.DAO.model.AdministradorDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Persona;
import com.healthLife.DAO.model.PersonaDao;
import com.healthLife.DAO.model.Usuario;
import com.healthLife.DAO.model.UsuarioDao;

import java.util.List;

/**
 * Created by USUARIO on 01/08/2015.
 */
public class SesionActivity extends Activity{
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    UsuarioDao usuarioDao;
    AdministradorDao administradorDao;
    PersonaDao personaDao;

    Button btnResgitro, btnSesion;
    EditText txtUusario, txtPass;
    TextView txtRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();

        Instanciar();
        btnResgitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SesionActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        txtRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SesionActivity.this, PassActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Instanciar(){
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        administradorDao = daoSession.getAdministradorDao();
        personaDao = daoSession.getPersonaDao();

        btnResgitro=(Button)findViewById(R.id.btnRegistro);
        btnSesion=(Button)findViewById(R.id.btnSesion);
        txtUusario=(EditText)findViewById(R.id.txtUsuario);
        txtPass=(EditText)findViewById(R.id.txtPass);
        txtRecuperar=(TextView)findViewById(R.id.txtPass2);
    }
    public void iniciarSesion(){
        String usuario = txtUusario.getText().toString();
        String pass = txtPass.getText().toString();
        List<Usuario> listUsuario=this.usuarioDao.queryBuilder().where(UsuarioDao.Properties.Nombre.eq(usuario)).list();
        List<Usuario> listPass=this.usuarioDao.queryBuilder().where(UsuarioDao.Properties.Pass.eq(pass)).list();
        if((usuario.equals(""))||(pass.equals(""))){
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show();
        }else{
            if(listUsuario!=null || listPass!=null){
                for(Usuario objeto : listUsuario){
                    for(Usuario objeto2 : listPass){
                        if(objeto2.getNombre().equals(usuario)){
                            final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                            final SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("usuarioSesion", objeto2.getId().intValue());
                            List<Administrador> listAdmin=this.administradorDao.queryBuilder().where(AdministradorDao.Properties.Id_usuario.eq(objeto2.getId())).list();
                            for(Administrador objeto3 : listAdmin){
                                Long comparar = objeto3.getId_persona();
                                editor.putInt("administradorSesion", objeto3.getId().intValue());
                            }
                            editor.commit();
                            finish();
                            Intent intent = new Intent(SesionActivity.this, InicioActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
            if(listUsuario.size()==0 || listPass.size()==0){
                Toast.makeText(this, "AVISO: Usuario o contrasena incorrectos",Toast.LENGTH_LONG).show();
            }
        }
    }
}