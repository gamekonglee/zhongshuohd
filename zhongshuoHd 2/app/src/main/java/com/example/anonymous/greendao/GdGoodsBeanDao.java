package com.example.anonymous.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import bc.juhaohd.com.bean.GdGoodsBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GD_GOODS_BEAN".
*/
public class GdGoodsBeanDao extends AbstractDao<GdGoodsBean, Long> {

    public static final String TABLENAME = "GD_GOODS_BEAN";

    /**
     * Properties of entity GdGoodsBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Gid = new Property(0, Long.class, "gid", true, "_id");
        public final static Property Filter = new Property(1, String.class, "filter", false, "FILTER");
        public final static Property Id = new Property(2, int.class, "id", false, "ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property CurrentPrice = new Property(4, String.class, "currentPrice", false, "CURRENT_PRICE");
        public final static Property OrignalImg = new Property(5, String.class, "orignalImg", false, "ORIGNAL_IMG");
        public final static Property SortKey = new Property(6, String.class, "sortKey", false, "SORT_KEY");
        public final static Property SortValue = new Property(7, String.class, "sortValue", false, "SORT_VALUE");
    };


    public GdGoodsBeanDao(DaoConfig config) {
        super(config);
    }
    
    public GdGoodsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GD_GOODS_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: gid
                "\"FILTER\" TEXT," + // 1: filter
                "\"ID\" INTEGER NOT NULL ," + // 2: id
                "\"NAME\" TEXT," + // 3: name
                "\"CURRENT_PRICE\" TEXT," + // 4: currentPrice
                "\"ORIGNAL_IMG\" TEXT," + // 5: orignalImg
                "\"SORT_KEY\" TEXT," + // 6: sortKey
                "\"SORT_VALUE\" TEXT);"); // 7: sortValue
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GD_GOODS_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GdGoodsBean entity) {
        stmt.clearBindings();
 
        Long gid = entity.getGid();
        if (gid != null) {
            stmt.bindLong(1, gid);
        }
 
        String filter = entity.getFilter();
        if (filter != null) {
            stmt.bindString(2, filter);
        }
        stmt.bindLong(3, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String currentPrice = entity.getCurrentPrice();
        if (currentPrice != null) {
            stmt.bindString(5, currentPrice);
        }
 
        String orignalImg = entity.getOrignalImg();
        if (orignalImg != null) {
            stmt.bindString(6, orignalImg);
        }
 
        String sortKey = entity.getSortKey();
        if (sortKey != null) {
            stmt.bindString(7, sortKey);
        }
 
        String sortValue = entity.getSortValue();
        if (sortValue != null) {
            stmt.bindString(8, sortValue);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GdGoodsBean entity) {
        stmt.clearBindings();
 
        Long gid = entity.getGid();
        if (gid != null) {
            stmt.bindLong(1, gid);
        }
 
        String filter = entity.getFilter();
        if (filter != null) {
            stmt.bindString(2, filter);
        }
        stmt.bindLong(3, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String currentPrice = entity.getCurrentPrice();
        if (currentPrice != null) {
            stmt.bindString(5, currentPrice);
        }
 
        String orignalImg = entity.getOrignalImg();
        if (orignalImg != null) {
            stmt.bindString(6, orignalImg);
        }
 
        String sortKey = entity.getSortKey();
        if (sortKey != null) {
            stmt.bindString(7, sortKey);
        }
 
        String sortValue = entity.getSortValue();
        if (sortValue != null) {
            stmt.bindString(8, sortValue);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GdGoodsBean readEntity(Cursor cursor, int offset) {
        GdGoodsBean entity = new GdGoodsBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // gid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // filter
            cursor.getInt(offset + 2), // id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // currentPrice
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // orignalImg
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sortKey
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // sortValue
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GdGoodsBean entity, int offset) {
        entity.setGid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFilter(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setId(cursor.getInt(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCurrentPrice(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOrignalImg(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSortKey(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSortValue(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GdGoodsBean entity, long rowId) {
        entity.setGid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GdGoodsBean entity) {
        if(entity != null) {
            return entity.getGid();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
