package com.example.anonymous.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import bc.juhaohd.com.bean.GdAttrBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GD_ATTR_BEAN".
*/
public class GdAttrBeanDao extends AbstractDao<GdAttrBean, Long> {

    public static final String TABLENAME = "GD_ATTR_BEAN";

    /**
     * Properties of entity GdAttrBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Aid = new Property(0, Long.class, "aid", true, "_id");
        public final static Property Filter_attr_name = new Property(1, String.class, "filter_attr_name", false, "FILTER_ATTR_NAME");
        public final static Property Index = new Property(2, int.class, "index", false, "INDEX");
        public final static Property Attr_list_index = new Property(3, int.class, "attr_list_index", false, "ATTR_LIST_INDEX");
    };


    public GdAttrBeanDao(DaoConfig config) {
        super(config);
    }
    
    public GdAttrBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GD_ATTR_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: aid
                "\"FILTER_ATTR_NAME\" TEXT," + // 1: filter_attr_name
                "\"INDEX\" INTEGER NOT NULL ," + // 2: index
                "\"ATTR_LIST_INDEX\" INTEGER NOT NULL );"); // 3: attr_list_index
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GD_ATTR_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GdAttrBean entity) {
        stmt.clearBindings();
 
        Long aid = entity.getAid();
        if (aid != null) {
            stmt.bindLong(1, aid);
        }
 
        String filter_attr_name = entity.getFilter_attr_name();
        if (filter_attr_name != null) {
            stmt.bindString(2, filter_attr_name);
        }
        stmt.bindLong(3, entity.getIndex());
        stmt.bindLong(4, entity.getAttr_list_index());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GdAttrBean entity) {
        stmt.clearBindings();
 
        Long aid = entity.getAid();
        if (aid != null) {
            stmt.bindLong(1, aid);
        }
 
        String filter_attr_name = entity.getFilter_attr_name();
        if (filter_attr_name != null) {
            stmt.bindString(2, filter_attr_name);
        }
        stmt.bindLong(3, entity.getIndex());
        stmt.bindLong(4, entity.getAttr_list_index());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GdAttrBean readEntity(Cursor cursor, int offset) {
        GdAttrBean entity = new GdAttrBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // aid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // filter_attr_name
            cursor.getInt(offset + 2), // index
            cursor.getInt(offset + 3) // attr_list_index
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GdAttrBean entity, int offset) {
        entity.setAid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFilter_attr_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIndex(cursor.getInt(offset + 2));
        entity.setAttr_list_index(cursor.getInt(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GdAttrBean entity, long rowId) {
        entity.setAid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GdAttrBean entity) {
        if(entity != null) {
            return entity.getAid();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}