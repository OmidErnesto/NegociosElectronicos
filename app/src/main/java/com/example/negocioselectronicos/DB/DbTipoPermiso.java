package com.example.negocioselectronicos.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
public class DbTipoPermiso extends DbHelper{

    Context context;
    public DbTipoPermiso(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarTipoPermiso(String nombre){
        long id = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("estado_registro", "A");
            id = db.insert(TABLE_TIPO_PERMISO, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public Cursor obtenerTodosLosTipoPermiso() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TIPO_PERMISO, null);
    }

    public int actualizarTipoPermiso(int codigo, String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        return db.update(TABLE_TIPO_PERMISO, values, "codigo = ?", new String[] { String.valueOf(codigo) });
    }

    public int actualizarEstadoRegistro(int codigo, String estadoRegistro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado_registro", estadoRegistro);
        return db.update(TABLE_TIPO_PERMISO, values, "codigo = ?", new String[] { String.valueOf(codigo) });
    }

    public Cursor obtenerTipoPermisoActivos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TIPO_PERMISO + " WHERE estado_registro = 'A'", null);
    }

    public Cursor buscarTipoPermisoPorCampo(String campo, String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TIPO_PERMISO + " WHERE " + campo + " = ?", new String[] { query });
    }

}