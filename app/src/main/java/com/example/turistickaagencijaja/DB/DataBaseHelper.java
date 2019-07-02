package com.example.turistickaagencijaja.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

@DatabaseTable(tableName = "atrakcija")
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {


    private static String DATABASE_NAME= "vodic";
    private static int DATABASE_VERSION= 1;

    Dao<Atrakcija, Integer> mAtrakcijaDao = null;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Atrakcija.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Atrakcija.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Atrakcija, Integer> getmAtrakcijaDao () throws SQLException{
        if (mAtrakcijaDao==null){
            mAtrakcijaDao= getDao(Atrakcija.class);
        }
        return mAtrakcijaDao;
    }


    @Override
    public void close() {
        mAtrakcijaDao=null;
        super.close();
    }
}
