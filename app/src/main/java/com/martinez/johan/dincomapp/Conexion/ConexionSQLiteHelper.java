package com.martinez.johan.dincomapp.Conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.martinez.johan.dincomapp.Utilities.Utilities;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilities.CREATE_TABLE_TERMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnt, int versionNew) {
        db.execSQL("DROP TABLE IF EXISTS terminos");
        onCreate(db);
    }
}
