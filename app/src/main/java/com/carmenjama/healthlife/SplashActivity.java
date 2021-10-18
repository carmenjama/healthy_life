package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.AlimentoDao;
import com.healthLife.DAO.model.Categoria;
import com.healthLife.DAO.model.CategoriaDao;
import com.healthLife.DAO.model.DaoMaster;
import com.healthLife.DAO.model.DaoSession;
import com.healthLife.DAO.model.Menu;
import com.healthLife.DAO.model.MenuDao;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends Activity{
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    AlimentoDao alimentoDao;
    CategoriaDao categoriaDao;
    MenuDao menuDao;
    int tiempo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "BD",null);
        db =  openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        alimentoDao = daoSession.getAlimentoDao();
        categoriaDao = daoSession.getCategoriaDao();
        menuDao = daoSession.getMenuDao();
        if(categoriaDao.count()==0){
            agCategrias();
            agCategrias2();
            agCategrias3();
            agCategrias4();
            agCategrias5();
            agCategrias6();
            agCategrias7();
            agCategrias8();
            agMenu();
            TimerTask timerTask= new TimerTask() {
                @Override
                public void run() {
                    finish();
                    Intent intent=new Intent(SplashActivity.this, SesionActivity.class);
                    startActivity(intent);
                }
            };
            Timer timer =new Timer();
            timer.schedule(timerTask, 1000);

        }else{
            TimerTask timerTask= new TimerTask() {
                @Override
                public void run() {
                    finish();
                    Intent intent=new Intent(SplashActivity.this, SesionActivity.class);
                    startActivity(intent);
                }
            };
            Timer timer =new Timer();
            timer.schedule(timerTask, 1000);
        }
    }
    //AGREGAR CATEGORIAS
    public void agCategrias(){
        Categoria categoria = new Categoria();
        categoria.setNombre("Carnes");//1
        categoriaDao.insert(categoria);
        agregar1(categoria.getId());
    }
    public void agCategrias2(){
        Categoria categoria2 = new Categoria();
        categoria2.setNombre("Frutas");//2
        categoriaDao.insert(categoria2);
        agregar2(categoria2.getId());
    }
    public void agCategrias3(){
        Categoria categoria3 = new Categoria();
        categoria3.setNombre("Vegetales");//3
        categoriaDao.insert(categoria3);
        agregar3(categoria3.getId());
    }
    public void agCategrias4(){
        Categoria categoria4 = new Categoria();
        categoria4.setNombre("Pastas");//4
        categoriaDao.insert(categoria4);
        agregar4(categoria4.getId());
    }
    public void agCategrias5(){
        Categoria categoria5 = new Categoria();
        categoria5.setNombre("Lacteos");//5
        categoriaDao.insert(categoria5);
        agregar5(categoria5.getId());
    }
    public void agCategrias6(){
        Categoria categoria6 = new Categoria();
        categoria6.setNombre("Trigos y Cereales");//6
        categoriaDao.insert(categoria6);
        agregar6(categoria6.getId());
    }
    public void agCategrias7(){
        Categoria categoria7 = new Categoria();
        categoria7.setNombre("Bebidas");//7
        categoriaDao.insert(categoria7);
        agregar7(categoria7.getId());
    }
    public void agCategrias8(){
        Categoria categoria8 = new Categoria();
        categoria8.setNombre("Huevos");//8
        categoriaDao.insert(categoria8);
        agregar8(categoria8.getId());
    }

    //AGREGAR ALIMENTOS
    public  void agregar1(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Bandera(filete crudo)");
        alimento.setCalorias(90.00);
        alimento.setCarbohidratos(0.00);
        alimento.setGrasas(1.25);
        alimento.setProteinas(18.00);
        alimentoDao.insert(alimento);

        Alimento alimento1 = new Alimento();
        alimento1.setId_categoria(id);
        alimento1.setNombre("Blanco del Nilo(filete)");
        alimento1.setCalorias(90.00);
        alimento1.setCarbohidratos(1.00);
        alimento1.setGrasas(1.50);
        alimento1.setProteinas(17.78);
        alimentoDao.insert(alimento1);

        Alimento alimento2 = new Alimento();
        alimento2.setId_categoria(id);
        alimento2.setNombre("Lenguado");
        alimento2.setCalorias(75.56);
        alimento2.setCarbohidratos(0.00);
        alimento2.setGrasas(1.11);
        alimento2.setProteinas(16.67);
        alimentoDao.insert(alimento2);

        Alimento alimento3 = new Alimento();
        alimento3.setId_categoria(id);
        alimento3.setNombre("Salmon");
        alimento3.setCalorias(183.33);
        alimento3.setCarbohidratos(8.9);
        alimento3.setGrasas(16.67);
        alimento3.setProteinas(0.00);
        alimentoDao.insert(alimento3);

        Alimento alimento4 = new Alimento();
        alimento4.setId_categoria(id);
        alimento4.setNombre("Hígado");
        alimento4.setCalorias(134.00);
        alimento4.setCarbohidratos(3.6);
        alimento4.setGrasas(3.9);
        alimento4.setProteinas(19.8);
        alimentoDao.insert(alimento4);

        Alimento alimento5 = new Alimento();
        alimento5.setId_categoria(id);
        alimento5.setNombre("Carne de Pollo");
        alimento5.setCalorias(170.00);
        alimento5.setCarbohidratos(0.0);
        alimento5.setGrasas(10.2);
        alimento5.setProteinas(18.2);
        alimentoDao.insert(alimento5);

        Alimento alimento6 = new Alimento();
        alimento6.setId_categoria(id);
        alimento6.setNombre("Carne de Pavo");
        alimento6.setCalorias(268.00);
        alimento6.setCarbohidratos(0.5);
        alimento6.setGrasas(20.1);
        alimento6.setProteinas(20.0);
        alimentoDao.insert(alimento6);

        Alimento alimento7 = new Alimento();
        alimento7.setId_categoria(id);
        alimento7.setNombre("Atún en aceite");
        alimento7.setCalorias(288.00);
        alimento7.setCarbohidratos(0.0);
        alimento7.setGrasas(20.5);
        alimento7.setProteinas(24.2);
        alimentoDao.insert(alimento7);

    }
    public  void agregar2(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Aguacate");
        alimento.setCalorias(32.00);
        alimento.setCarbohidratos(3.2);
        alimento.setGrasas(23.5);
        alimento.setProteinas(1.9);
        alimentoDao.insert(alimento);

        Alimento alimento1 = new Alimento();
        alimento1.setId_categoria(id);
        alimento1.setNombre("Arandano");
        alimento1.setCalorias(41.00);
        alimento1.setCarbohidratos(10.1);
        alimento1.setGrasas(0.4);
        alimento1.setProteinas(0.6);
        alimentoDao.insert(alimento1);

        Alimento alimento2 = new Alimento();
        alimento2.setId_categoria(id);
        alimento2.setNombre("Cereza");
        alimento2.setCalorias(48.00);
        alimento2.setCarbohidratos(11.7);
        alimento2.setGrasas(0.1);
        alimento2.setProteinas(0.8);
        alimentoDao.insert(alimento2);

        Alimento alimento3 = new Alimento();
        alimento3.setId_categoria(id);
        alimento3.setNombre("Ciruela");
        alimento3.setCalorias(36.00);
        alimento3.setCarbohidratos(8.9);
        alimento3.setGrasas(0.1);
        alimento3.setProteinas(0.5);
        alimentoDao.insert(alimento3);

        Alimento alimento4 = new Alimento();
        alimento4.setId_categoria(id);
        alimento4.setNombre("Frambuesa");
        alimento4.setCalorias(30.00);
        alimento4.setCarbohidratos(5.6);
        alimento4.setGrasas(0.6);
        alimento4.setProteinas(1.00);
        alimentoDao.insert(alimento4);

        Alimento alimento5 = new Alimento();
        alimento5.setId_categoria(id);
        alimento5.setNombre("Fresa");
        alimento5.setCalorias(27.00);
        alimento5.setCarbohidratos(5.6);
        alimento5.setGrasas(0.4);
        alimento5.setProteinas(0.9);
        alimentoDao.insert(alimento5);

        Alimento alimento6 = new Alimento();
        alimento6.setId_categoria(id);
        alimento6.setNombre("Granada");
        alimento6.setCalorias(62.00);
        alimento6.setCarbohidratos(15.9);
        alimento6.setGrasas(0.1);
        alimento6.setProteinas(0.5);
        alimentoDao.insert(alimento6);

        Alimento alimento7 = new Alimento();
        alimento7.setId_categoria(id);
        alimento7.setNombre("Limón");
        alimento7.setCalorias(14.00);
        alimento7.setCarbohidratos(3.2);
        alimento7.setGrasas(0.00);
        alimento7.setProteinas(0.6);
        alimentoDao.insert(alimento7);

        Alimento alimento8 = new Alimento();
        alimento8.setId_categoria(id);
        alimento8.setNombre("Mandarina");
        alimento8.setCalorias(41.00);
        alimento8.setCarbohidratos(9.1);
        alimento8.setGrasas(0.4);
        alimento8.setProteinas(0.7);
        alimentoDao.insert(alimento8);

        Alimento alimento9 = new Alimento();
        alimento9.setId_categoria(id);
        alimento9.setNombre("Mango");
        alimento9.setCalorias(73.00);
        alimento9.setCarbohidratos(16.8);
        alimento9.setGrasas(0.4);
        alimento9.setProteinas(0.7);
        alimentoDao.insert(alimento9);

        Alimento alimento10 = new Alimento();
        alimento10.setId_categoria(id);
        alimento10.setNombre("Manzana");
        alimento10.setCalorias(45.00);
        alimento10.setCarbohidratos(10.4);
        alimento10.setGrasas(0.3);
        alimento10.setProteinas(0.2);
        alimentoDao.insert(alimento10);

        Alimento alimento11 = new Alimento();
        alimento11.setId_categoria(id);
        alimento11.setNombre("Melón");
        alimento11.setCalorias(30.00);
        alimento11.setCarbohidratos(7.4);
        alimento11.setGrasas(0.2);
        alimento11.setProteinas(0.8);
        alimentoDao.insert(alimento11);

        Alimento alimento12 = new Alimento();
        alimento12.setId_categoria(id);
        alimento12.setNombre("Mora");
        alimento12.setCalorias(35.00);
        alimento12.setCarbohidratos(6.5);
        alimento12.setGrasas(0.6);
        alimento12.setProteinas(1.00);
        alimentoDao.insert(alimento12);

        Alimento alimento13 = new Alimento();
        alimento13.setId_categoria(id);
        alimento13.setNombre("Naranja");
        alimento13.setCalorias(53.00);
        alimento13.setCarbohidratos(11.7);
        alimento13.setGrasas(0.6);
        alimento13.setProteinas(1.00);
        alimentoDao.insert(alimento13);
    }
    public  void agregar3(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Alimento5");
        alimento.setCalorias(1.1);
        alimento.setCarbohidratos(1.1);
        alimento.setGrasas(1.1);
        alimento.setProteinas(1.1);
        alimentoDao.insert(alimento);
    }
    public  void agregar4(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Alimento8");
        alimento.setCalorias(1.1);
        alimento.setCarbohidratos(1.1);
        alimento.setGrasas(1.1);
        alimento.setProteinas(1.1);
        alimentoDao.insert(alimento);
    }
    public  void agregar5(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Leche entera");
        alimento.setCalorias(63.00);
        alimento.setCarbohidratos(4.6);
        alimento.setGrasas(3.7);
        alimento.setProteinas(3.2);
        alimentoDao.insert(alimento);

        Alimento alimento1 = new Alimento();
        alimento1.setId_categoria(id);
        alimento1.setNombre("Leche semidescremada");
        alimento1.setCalorias(49.00);
        alimento1.setCarbohidratos(5.0);
        alimento1.setGrasas(1.8);
        alimento1.setProteinas(3.5);
        alimentoDao.insert(alimento1);

        Alimento alimento2 = new Alimento();
        alimento2.setId_categoria(id);
        alimento2.setNombre("Leche descremada");
        alimento2.setCalorias(45.00);
        alimento2.setCarbohidratos(4.7);
        alimento2.setGrasas(1.5);
        alimento2.setProteinas(3.1);
        alimentoDao.insert(alimento2);

        Alimento alimento3 = new Alimento();
        alimento3.setId_categoria(id);
        alimento3.setNombre("Yogurt entero");
        alimento3.setCalorias(61.00);
        alimento3.setCarbohidratos(4.0);
        alimento3.setGrasas(3.5);
        alimento3.setProteinas(3.3);
        alimentoDao.insert(alimento3);

        Alimento alimento4 = new Alimento();
        alimento4.setId_categoria(id);
        alimento4.setNombre("Yogurt con cereales");
        alimento4.setCalorias(48.00);
        alimento4.setCarbohidratos(9.0);
        alimento4.setGrasas(0.05);
        alimento4.setProteinas(3.0);
        alimentoDao.insert(alimento4);

        Alimento alimento5 = new Alimento();
        alimento5.setId_categoria(id);
        alimento5.setNombre("Yogurt con frutas");
        alimento5.setCalorias(89.00);
        alimento5.setCarbohidratos(12.6);
        alimento5.setGrasas(3.3);
        alimento5.setProteinas(2.8);
        alimentoDao.insert(alimento5);

        Alimento alimento6 = new Alimento();
        alimento6.setId_categoria(id);
        alimento6.setNombre("Yogurt con fibras y frutas");
        alimento6.setCalorias(71.00);
        alimento6.setCarbohidratos(12.5);
        alimento6.setGrasas(0.2);
        alimento6.setProteinas(4.7);
        alimentoDao.insert(alimento6);

        Alimento alimento7 = new Alimento();
        alimento7.setId_categoria(id);
        alimento7.setNombre("Yogurt descremado saborizado");
        alimento7.setCalorias(34.5);
        alimento7.setCarbohidratos(4.4);
        alimento7.setGrasas(0.05);
        alimento7.setProteinas(3.6);
        alimentoDao.insert(alimento7);

        Alimento alimento8 = new Alimento();
        alimento8.setId_categoria(id);
        alimento8.setNombre("Queso Mozzarella");
        alimento8.setCalorias(245.00);
        alimento8.setCarbohidratos(4.9);
        alimento8.setGrasas(16.1);
        alimento8.setProteinas(19.9);
        alimentoDao.insert(alimento8);

        Alimento alimento9 = new Alimento();
        alimento9.setId_categoria(id);
        alimento9.setNombre("Queso fresco");
        alimento9.setCalorias(307.00);
        alimento9.setCarbohidratos(1.0);
        alimento9.setGrasas(23.0);
        alimento9.setProteinas(24.0);
        alimentoDao.insert(alimento9);

        Alimento alimento10 = new Alimento();
        alimento10.setId_categoria(id);
        alimento10.setNombre("Queso rallado");
        alimento10.setCalorias(427.00);
        alimento10.setCarbohidratos(0.0);
        alimento10.setGrasas(28.5);
        alimento10.setProteinas(42.5);
        alimentoDao.insert(alimento10);

        Alimento alimento11 = new Alimento();
        alimento11.setId_categoria(id);
        alimento11.setNombre("Queso Cheddar");
        alimento11.setCalorias(381.00);
        alimento11.setCarbohidratos(0.5);
        alimento11.setGrasas(31.0);
        alimento11.setProteinas(25.0);
        alimentoDao.insert(alimento11);

        Alimento alimento12 = new Alimento();
        alimento12.setId_categoria(id);
        alimento12.setNombre("Queso Parmesano");
        alimento12.setCalorias(374.00);
        alimento12.setCarbohidratos(0.0);
        alimento12.setGrasas(25.6);
        alimento12.setProteinas(36.0);
        alimentoDao.insert(alimento12);

        Alimento alimento13 = new Alimento();
        alimento13.setId_categoria(id);
        alimento13.setNombre("Queso Crema");
        alimento13.setCalorias(245.00);
        alimento13.setCarbohidratos(3.7);
        alimento13.setGrasas(22.0);
        alimento13.setProteinas(8.2);
        alimentoDao.insert(alimento13);

    }
    public  void agregar6(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Arroz");
        alimento.setCalorias(362.00);
        alimento.setCarbohidratos(87.6);
        alimento.setGrasas(0.6);
        alimento.setProteinas(7.00);
        alimentoDao.insert(alimento);

        Alimento alimento1 = new Alimento();
        alimento1.setId_categoria(id);
        alimento1.setNombre("Cebada");
        alimento1.setCalorias(373.00);
        alimento1.setCarbohidratos(82.3);
        alimento1.setGrasas(1.4);
        alimento1.setProteinas(10.4);
        alimentoDao.insert(alimento1);

        Alimento alimento2 = new Alimento();
        alimento2.setId_categoria(id);
        alimento2.setNombre("Centeno");
        alimento2.setCalorias(350.00);
        alimento2.setCarbohidratos(76.00);
        alimento2.setGrasas(1.00);
        alimento2.setProteinas(9.4);
        alimentoDao.insert(alimento2);

        Alimento alimento3 = new Alimento();
        alimento3.setId_categoria(id);
        alimento3.setNombre("Harina Integral");
        alimento3.setCalorias(321.00);
        alimento3.setCarbohidratos(69.7);
        alimento3.setGrasas(1.9);
        alimento3.setProteinas(11.00);
        alimentoDao.insert(alimento3);

        Alimento alimento4 = new Alimento();
        alimento4.setId_categoria(id);
        alimento4.setNombre("Harina");
        alimento4.setCalorias(345.00);
        alimento4.setCarbohidratos(73.6);
        alimento4.setGrasas(0.7);
        alimento4.setProteinas(11.00);
        alimentoDao.insert(alimento4);

        Alimento alimento5 = new Alimento();
        alimento5.setId_categoria(id);
        alimento5.setNombre("Maíz");
        alimento5.setCalorias(363.00);
        alimento5.setCarbohidratos(73.00);
        alimento5.setGrasas(3.8);
        alimento5.setProteinas(9.2);
        alimentoDao.insert(alimento5);

        Alimento alimento6 = new Alimento();
        alimento6.setId_categoria(id);
        alimento6.setNombre("Pan Blanco");
        alimento6.setCalorias(270.00);
        alimento6.setCarbohidratos(64.00);
        alimento6.setGrasas(0.5);
        alimento6.setProteinas(8.1);
        alimentoDao.insert(alimento6);

        Alimento alimento7 = new Alimento();
        alimento7.setId_categoria(id);
        alimento7.setNombre("Pan Integral");
        alimento7.setCalorias(230.00);
        alimento7.setCarbohidratos(47.5);
        alimento7.setGrasas(1.00);
        alimento7.setProteinas(9.00);
        alimentoDao.insert(alimento7);

        Alimento alimento8 = new Alimento();
        alimento8.setId_categoria(id);
        alimento8.setNombre("Pan tostado");
        alimento8.setCalorias(420.00);
        alimento8.setCarbohidratos(83.00);
        alimento8.setGrasas(6.00);
        alimento8.setProteinas(11.3);
        alimentoDao.insert(alimento8);

        Alimento alimento9 = new Alimento();
        alimento9.setId_categoria(id);
        alimento9.setNombre("Tapioca");
        alimento9.setCalorias(363.00);
        alimento9.setCarbohidratos(86.4);
        alimento9.setGrasas(0.2);
        alimento9.setProteinas(0.6);
        alimentoDao.insert(alimento9);
    }
    public  void agregar7(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Chocolate caliente");
        alimento.setCalorias(290.00);
        alimento.setCarbohidratos(47.00);
        alimento.setGrasas(0.00);
        alimento.setProteinas(14.00);
        alimentoDao.insert(alimento);

        Alimento alimento1 = new Alimento();
        alimento1.setId_categoria(id);
        alimento1.setNombre("Chocolate Blanco");
        alimento1.setCalorias(420.00);
        alimento1.setCarbohidratos(62.00);
        alimento1.setGrasas(12.00);
        alimento1.setProteinas(16.00);
        alimentoDao.insert(alimento1);

        Alimento alimento2 = new Alimento();
        alimento2.setId_categoria(id);
        alimento2.setNombre("Capuccino");
        alimento2.setCalorias(60.00);
        alimento2.setCarbohidratos(9.00);
        alimento2.setGrasas(0.00);
        alimento2.setProteinas(6.00);
        alimentoDao.insert(alimento2);

        Alimento alimento3 = new Alimento();
        alimento3.setId_categoria(id);
        alimento3.setNombre("Café Latte");
        alimento3.setCalorias(100.00);
        alimento3.setCarbohidratos(15.00);
        alimento3.setGrasas(0.00);
        alimento3.setProteinas(10.00);
        alimentoDao.insert(alimento3);

        Alimento alimento4 = new Alimento();
        alimento4.setId_categoria(id);
        alimento4.setNombre("Café Mocha");
        alimento4.setCalorias(170.00);
        alimento4.setCarbohidratos(32.00);
        alimento4.setGrasas(2.00);
        alimento4.setProteinas(10.00);
        alimentoDao.insert(alimento4);

        Alimento alimento5 = new Alimento();
        alimento5.setId_categoria(id);
        alimento5.setNombre("Café Mocha Skinny");
        alimento5.setCalorias(110.00);
        alimento5.setCarbohidratos(14.00);
        alimento5.setGrasas(1.00);
        alimento5.setProteinas(11.00);
        alimentoDao.insert(alimento5);
    }

    public  void agregar8(Long id){
        Alimento alimento = new Alimento();
        alimento.setId_categoria(id);
        alimento.setNombre("Clara de huevo");
        alimento.setCalorias(53.0);
        alimento.setCarbohidratos(1.0);
        alimento.setGrasas(0.2);
        alimento.setProteinas(11.0);
        alimentoDao.insert(alimento);

        Alimento alimento1 = new Alimento();
        alimento1.setId_categoria(id);
        alimento1.setNombre("Huevo de codorniz");
        alimento1.setCalorias(179.0);
        alimento1.setCarbohidratos(3.6);
        alimento1.setGrasas(13.1);
        alimento1.setProteinas(11.6);
        alimentoDao.insert(alimento1);

        Alimento alimento2 = new Alimento();
        alimento2.setId_categoria(id);
        alimento2.setNombre("Yema de huevo");
        alimento2.setCalorias(341.0);
        alimento2.setCarbohidratos(2.0);
        alimento2.setGrasas(29.2);
        alimento2.setProteinas(16.0);
        alimentoDao.insert(alimento2);
    }
    public void agMenu(){
        Menu menu = new Menu();
        menu.setNomAlmu("Lentejas con pimiento verde, cebolla y tomate, pescado al horno, pan, lacteo.");
        menu.setNomDes("Cereales como copos de avena integral,  taza de leche descremada o baja en grasa, leche de soja o agua, uvas pasas o frutos rojos frescos o secos");
        menu.setNomMeri("Un vaso de leche acompañado de una fruta o cereales, pan con chocolate y una fruta");
        menuDao.insert(menu);
    }
}