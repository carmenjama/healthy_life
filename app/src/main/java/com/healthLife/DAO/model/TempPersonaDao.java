package com.healthLife.DAO.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.healthLife.DAO.model.TempPersona;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TEMP_PERSONA.
*/
public class TempPersonaDao extends AbstractDao<TempPersona, Long> {

    public static final String TABLENAME = "TEMP_PERSONA";

    /**
     * Properties of entity TempPersona.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Id_person = new Property(1, Long.class, "id_person", false, "ID_PERSON");
        public final static Property Nombre = new Property(2, String.class, "nombre", false, "NOMBRE");
    };


    public TempPersonaDao(DaoConfig config) {
        super(config);
    }
    
    public TempPersonaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TEMP_PERSONA' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_PERSON' INTEGER," + // 1: id_person
                "'NOMBRE' TEXT);"); // 2: nombre
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TEMP_PERSONA'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TempPersona entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long id_person = entity.getId_person();
        if (id_person != null) {
            stmt.bindLong(2, id_person);
        }
 
        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(3, nombre);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TempPersona readEntity(Cursor cursor, int offset) {
        TempPersona entity = new TempPersona( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id_person
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // nombre
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TempPersona entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId_person(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNombre(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TempPersona entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TempPersona entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}