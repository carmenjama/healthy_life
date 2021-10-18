package com.healthLife.DAO;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToOne;

public class miDAO {
    public static void main(String [] args){
        try{
            Schema schema = new Schema(1,"com.healthLife.DAO.model");
            crearUsuario(schema);
            crearPersona(schema);
            crearEnfermedad(schema);
            crearAdministrador(schema);
            crearCategoria(schema);
            crearAlimento(schema);
            listEnfermedad(schema);
            alimenRestring(schema);
            tempAlimento(schema);
            tempEnfermedad(schema);
            tempPersona(schema);
            Familiar(schema);
            diario(schema);
            diarioLista(schema);
            tempDiario(schema);
            menu(schema);
            new DaoGenerator().generateAll(schema,"../HealthLife/app/src/main/java");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void crearUsuario(Schema schema){
        try{
            Entity usuario = schema.addEntity("Usuario");
            usuario.addIdProperty();
            usuario.addStringProperty("nombre").notNull();
            usuario.addStringProperty("pass").notNull();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void crearEnfermedad(Schema schema){
        try{
            Entity enfermedad = schema.addEntity("Enfermedad");
            enfermedad.addIdProperty();
            enfermedad.addStringProperty("descripcion").notNull();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void crearPersona(Schema schema){
        try{
            Entity persona = schema.addEntity("Persona");
            persona.addIdProperty();
            persona.addStringProperty("nombre");
            persona.addDoubleProperty("altura");
            persona.addDoubleProperty("peso");
            persona.addStringProperty("sexo");
            persona.addStringProperty("edad");
            persona.addStringProperty("est_vida");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void crearAdministrador(Schema schema){
        try{
            Entity administrador = schema.addEntity("Administrador");
            administrador.addIdProperty();
            administrador.addLongProperty("id_persona");
            administrador.addLongProperty("id_usuario");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void crearCategoria(Schema schema){
        try{
            Entity categoria = schema.addEntity("Categoria");
            categoria.addIdProperty();
            categoria.addStringProperty("nombre");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void crearAlimento(Schema schema){
        try{
            Entity alimento = schema.addEntity("Alimento");
            alimento.addIdProperty();
            alimento.addLongProperty("id_categoria");
            alimento.addStringProperty("nombre");
            alimento.addDoubleProperty("calorias");
            alimento.addDoubleProperty("carbohidratos");
            alimento.addDoubleProperty("grasas");
            alimento.addDoubleProperty("proteinas");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void listEnfermedad(Schema schema){
        try{
            Entity listEnfermedad = schema.addEntity("ListEnfermedad");
            listEnfermedad.addIdProperty();
            listEnfermedad.addLongProperty("id_persona");
            listEnfermedad.addLongProperty("id_enfermedad");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void tempAlimento(Schema schema){
        try{
            Entity tempAlimento = schema.addEntity("TempAlimento");
            tempAlimento.addIdProperty();
            tempAlimento.addLongProperty("id_alimento");
            tempAlimento.addStringProperty("nombre");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void alimenRestring(Schema schema){
        try{
            Entity alimenRestring = schema.addEntity("AlimenRestring");
            alimenRestring.addIdProperty();
            alimenRestring.addLongProperty("id_enfermedad");
            alimenRestring.addLongProperty("id_alimento");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void tempEnfermedad(Schema schema){
        try{
            Entity tempEnfermedad = schema.addEntity("TempEnfermedad");
            tempEnfermedad.addIdProperty();
            tempEnfermedad.addLongProperty("id_enfermedad");
            tempEnfermedad.addStringProperty("nombre");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void Familiar(Schema schema){
        try{
            Entity familiar = schema.addEntity("Familiar");
            familiar.addIdProperty();
            familiar.addLongProperty("id_persona");
            familiar.addLongProperty("id_usuario");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void tempPersona(Schema schema){
        try{
            Entity tempPersona = schema.addEntity("TempPersona");
            tempPersona.addIdProperty();
            tempPersona.addLongProperty("id_person");
            tempPersona.addStringProperty("nombre");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void diario (Schema schema){
        try{
            Entity diario = schema.addEntity("Diario");
            diario.addIdProperty();
            diario.addLongProperty("id_admin");
            diario.addDateProperty("fecha");
            diario.addDoubleProperty("tot_calorias");
            diario.addDoubleProperty("tot_grasa");
            diario.addDoubleProperty("tot_proteina");
            diario.addDoubleProperty("tot_carbo");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void diarioLista (Schema schema){
        try{
            Entity diarioLista = schema.addEntity("DiarioLista");
            diarioLista.addIdProperty();
            diarioLista.addLongProperty("id_diario");
            diarioLista.addLongProperty("id_alimento");
            diarioLista.addDoubleProperty("porcion");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void tempDiario (Schema schema){
        try{
            Entity tempDiario = schema.addEntity("TempDiario");
            tempDiario.addIdProperty();
            tempDiario.addLongProperty("id_diario");
            tempDiario.addLongProperty("id_lista");
            tempDiario.addLongProperty("id_alimento");
            tempDiario.addStringProperty("nombre");
            tempDiario.addDoubleProperty("calorias");
            tempDiario.addDoubleProperty("carbohidratos");
            tempDiario.addDoubleProperty("grasas");
            tempDiario.addDoubleProperty("proteinas");
            tempDiario.addDoubleProperty("total");
            tempDiario.addDoubleProperty("porcion");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void menu (Schema schema){
        try{
            Entity menu = schema.addEntity("Menu");
            menu.addIdProperty();
            menu.addStringProperty("nomDes");
            menu.addStringProperty("nomAlmu");
            menu.addStringProperty("nomMeri");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}