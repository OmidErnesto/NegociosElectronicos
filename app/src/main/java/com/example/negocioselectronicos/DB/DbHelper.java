package com.example.negocioselectronicos.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static  final int DATABASE_VERSION = 1;
    private static  final String DATABASE_NOMBRE = "DbAppNE.db";
    public static final String TABLE_PERMISOS = "t_permisos";
    public static final String TABLE_TRABAJADORES = "t_trabajadores";
    public static final String TABLE_TIPO_PERMISO = "t_tipo_permiso";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_PERMISOS + "(" +
                "numero_permiso INTEGER PRIMARY KEY AUTOINCREMENT," +
                "codigo_trabajador INTEGER NOT NULL," +
                "tipo_permiso INTEGER NOT NULL," +
                "fecha DATE NOT NULL," +
                "horas TIME NOT NULL," +
                "estado_registro CHAR(1) DEFAULT 'A'," +
                "FOREIGN KEY(codigo_trabajador) REFERENCES t_trabajadores(codigo)," +
                "FOREIGN KEY(tipo_permiso) REFERENCES t_tipo_permiso(codigo))");

        db.execSQL("CREATE TABLE " + TABLE_TRABAJADORES + "(" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "estado_registro CHAR(1) DEFAULT 'A')");

        db.execSQL("CREATE TABLE " + TABLE_TIPO_PERMISO + "(" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "estado_registro CHAR(1) DEFAULT 'A')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLE_PERMISOS);
        db.execSQL("DROP TABLE " + TABLE_TRABAJADORES);
        db.execSQL("DROP TABLE " + TABLE_TIPO_PERMISO);
        onCreate(db);

    }
}
