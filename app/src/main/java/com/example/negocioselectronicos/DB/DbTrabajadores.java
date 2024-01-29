package com.example.negocioselectronicos.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
public class DbTrabajadores extends DbHelper{

    Context context;
    public DbTrabajadores(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarTrabajador(String nombre){
        long id = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("estado_registro", "A");
            id = db.insert(TABLE_TRABAJADORES, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public Cursor obtenerTodosLosTrabajadores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TRABAJADORES, null);
    }

    public int actualizarTrabajador(int codigo, String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        return db.update(TABLE_TRABAJADORES, values, "codigo = ?", new String[] { String.valueOf(codigo) });
    }

    public int actualizarEstadoRegistro(int codigo, String estadoRegistro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado_registro", estadoRegistro);
        return db.update(TABLE_TRABAJADORES, values, "codigo = ?", new String[] { String.valueOf(codigo) });
    }

    public Cursor obtenerTrabajadoresActivos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TRABAJADORES + " WHERE estado_registro = 'A'", null);
    }

    public Cursor buscarTrabajadorPorCampo(String campo, String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TRABAJADORES + " WHERE " + campo + " = ?", new String[] { query });
    }

}