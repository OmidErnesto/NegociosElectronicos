package com.example.negocioselectronicos.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbTransacciones extends DbHelper{

    Context context;
    public DbTransacciones(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPermiso(int codigoTrabajador, int tipoPermiso, String fecha, String horas){
        long id = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("codigo_trabajador", codigoTrabajador);
            values.put("tipo_permiso", tipoPermiso);
            values.put("fecha", fecha);
            values.put("horas", horas);
            values.put("estado_registro", "A");
            id = db.insert(TABLE_PERMISOS, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public Cursor obtenerTodosLosPermisos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PERMISOS, null);
    }

    public int actualizarPermiso(int numeroPermiso, int codigoTrabajador, int tipoPermiso, String fecha, String horas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo_trabajador", codigoTrabajador);
        values.put("tipo_permiso", tipoPermiso);
        values.put("fecha", fecha);
        values.put("horas", horas);
        return db.update(TABLE_PERMISOS, values, "numero_permiso = ?", new String[] { String.valueOf(numeroPermiso) });
    }

    public int actualizarEstadoRegistro(int numeroPermiso, String estadoRegistro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado_registro", estadoRegistro);
        return db.update(TABLE_PERMISOS, values, "numero_permiso = ?", new String[] { String.valueOf(numeroPermiso) });
    }

}
