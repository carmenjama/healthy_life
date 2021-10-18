package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.healthLife.DAO.model.Administrador;
import com.healthLife.DAO.model.AdministradorDao;
import com.healthLife.DAO.model.Categoria;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Persona;
import com.healthLife.DAO.model.PersonaDao;
import com.healthLife.DAO.model.Usuario;
import com.healthLife.DAO.model.UsuarioDao;

import java.util.List;

public class InicioActivity extends Activity {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    UsuarioDao usuarioDao;
    PersonaDao personaDao;
    AdministradorDao administradorDao;

    int idUsuario, idAdmin;
    ListViewAdapter adapter;
    String [] titulo = new String[]{
            "Mi Familia",
            "Diario de Alimentos",
            "Categorias",
            "Que preparo hoy",
            "Acerca de Nosotros"
    };
    int [] icono = new int[]{
            R.drawable.icon2,
            R.drawable.icon3,
            R.drawable.icon4,
            R.drawable.icon7,
            R.drawable.icon6,
    };
    ListView listOpciones;
    TextView txtUsuario, txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        idUsuario=settings.getInt("usuarioSesion",0);
        idAdmin=settings.getInt("administradorSesion",0);
        instanciar();
    }
    public void instanciar(){
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        administradorDao = daoSession.getAdministradorDao();
        personaDao = daoSession.getPersonaDao();

        listOpciones=(ListView)findViewById(R.id.listOPciones);
        txtNombre=(TextView)findViewById(R.id.txtNombre);
        txtUsuario=(TextView)findViewById(R.id.txtUsuario);
        List<Usuario> listUsua=this.usuarioDao.queryBuilder().where(UsuarioDao.Properties.Id.eq(idUsuario)).list();
        for(Usuario objeto : listUsua){
            txtUsuario.setText(objeto.getNombre());
        }
        List<Administrador> listAdmin=this.administradorDao.queryBuilder().where(AdministradorDao.Properties.Id.eq(idAdmin)).list();
        for(Administrador objeto2 : listAdmin){
            List<Persona> listPersona=this.personaDao.queryBuilder().where(PersonaDao.Properties.Id.eq(objeto2.getId())).list();
            for(Persona objeto3 : listPersona){
                txtNombre.setText(objeto3.getNombre());
            }
        }

        adapter = new ListViewAdapter(this, titulo, icono);
        listOpciones.setAdapter(adapter);
        listOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                View container = listOpciones.getChildAt(i);
                container.findViewById(R.id.textLista).setVisibility(View.VISIBLE);
                container.findViewById(R.id.iconLista).setVisibility(View.VISIBLE);
                switch (i) {
                    case 0:
                        Intent intent = new Intent(InicioActivity.this, FamiliaActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(InicioActivity.this, DiarioActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(InicioActivity.this, CategoriaActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(InicioActivity.this, ActivityMenu.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Configuracion", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    public void ingresoPerfil(){
        List<Administrador> listAdmin = this.administradorDao.queryBuilder().where(AdministradorDao.Properties.Id.eq(idAdmin)).list();
        for (Administrador objeto : listAdmin) {
            final SharedPreferences settings = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = settings.edit();
            editor.putInt("personaSesion", objeto.getId_persona().intValue());
            editor.commit();
            Intent intent0 = new Intent(InicioActivity.this, PerfilActivity.class);
            startActivity(intent0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editar:
                ingresoPerfil();
                break;
            case R.id.menu_modificar:
                Intent intent = new Intent(InicioActivity.this, PassActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_salir:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Desea cerrar sesion");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        finish();
                        Intent intent = new Intent(InicioActivity.this, SesionActivity.class);
                        startActivity(intent);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                break;
            case R.id.menu_actualizar:
                finish();
                Intent intent2 = new Intent(InicioActivity.this, InicioActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}
