package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class RegistroActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    UsuarioDao usuarioDao;
    PersonaDao personaDao;
    AdministradorDao administradorDao;


    EditText txtUsuario, txtPass1, txtPass2;
    Button btnRegistro;
    CheckBox terminosCondiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        Instanciar();
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro();
            }
        });
    }

    public void Instanciar(){
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        personaDao=daoSession.getPersonaDao();
        administradorDao = daoSession.getAdministradorDao();
        txtUsuario = (EditText)findViewById(R.id.txtUsuario);
        txtPass1=(EditText)findViewById(R.id.txtPass1);
        txtPass2=(EditText)findViewById(R.id.txtPass2);
        btnRegistro=(Button)findViewById(R.id.btnRegistro);
        terminosCondiciones=(CheckBox)findViewById(R.id.checkBox);
    }
    public void Registro(){
        String usuario=txtUsuario.getText().toString();
        String pass1=txtPass1.getText().toString();
        String pass2=txtPass2.getText().toString();
        if(usuario.equals("") || pass1.equals("")||pass2.equals("")){
            Toast.makeText(this, "AVISO: Campos incompletos", Toast.LENGTH_LONG).show();
        }else{
            if(pass1.equals(pass2)){
                if(terminosCondiciones.isChecked()==true){
                    regUsuario(usuario,pass1);
                }else{
                    Toast.makeText(this, "AVISO: Acepte terminos y condiciones de uso", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "AVISO: Contrasenas no coindicen", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void regUsuario(String datUsuario, String datPass){
        List<Usuario> listUsuario=this.usuarioDao.queryBuilder().where(UsuarioDao.Properties.Nombre.eq(datUsuario)).list();
        if(listUsuario.size()==0){
            Usuario usuario = new Usuario();
            usuario.setNombre(datUsuario);
            usuario.setPass(datPass);
            usuarioDao.insert(usuario);
            Persona persona = new Persona();
            persona.setNombre("Debe editar perfil");
            persona.setEdad("0");
            persona.setAltura(0.0);
            persona.setPeso(0.0);
            persona.setSexo("Seleccione");
            persona.setEst_vida("Seleccione");
            personaDao.insert(persona);
            Administrador administrador = new Administrador();
            administrador.setId_persona(persona.getId());
            administrador.setId_usuario(usuario.getId());
            administradorDao.insert(administrador);

            final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = settings.edit();
            editor.putInt("usuarioSesion", usuario.getId().intValue());
            editor.putInt("administradorSesion", administrador.getId().intValue());
            editor.commit();
            finish();
            Intent intent = new Intent(RegistroActivity.this, InicioActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "AVISO: Usuario ya existe",Toast.LENGTH_LONG).show();
        }
    }

}
