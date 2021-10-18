package com.healthLife.DAO.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.healthLife.DAO.model.ListEnfermedad;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table LIST_ENFERMEDAD.
*/
public class ListEnfermedadDao extends AbstractDao<ListEnfermedad, Long> {

    public static final String TABLENAME = "LIST_ENFERMEDAD";

    /**
     * Properties of entity ListEnfermedad.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Id_persona = new Property(1, Long.class, "id_persona", false, "ID_PERSONA");
        public final static Property Id_enfermedad = new Property(2, Long.class, "id_enfermedad", false, "ID_ENFERMEDAD");
    };


    public ListEnfermedadDao(DaoConfig config) {
        super(config);
    }
    
    public ListEnfermedadDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'LIST_ENFERMEDAD' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_PERSONA' INTEGER," + // 1: id_persona
                "'ID_ENFERMEDAD' INTEGER);"); // 2: id_enfermedad
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'LIST_ENFERMEDAD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ListEnfermedad entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long id_persona = entity.getId_persona();
        if (id_persona != null) {
            stmt.bindLong(2, id_persona);
        }
 
        Long id_enfermedad = entity.getId_enfermedad();
        if (id_enfermedad != null) {
            stmt.bindLong(3, id_enfermedad);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ListEnfermedad readEntity(Cursor cursor, int offset) {
        ListEnfermedad entity = new ListEnfermedad( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id_persona
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // id_enfermedad
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ListEnfermedad entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId_persona(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setId_enfermedad(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ListEnfermedad entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ListEnfermedad entity) {
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
