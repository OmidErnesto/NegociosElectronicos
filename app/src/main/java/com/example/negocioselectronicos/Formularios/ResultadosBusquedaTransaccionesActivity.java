package com.example.negocioselectronicos.Formularios;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.negocioselectronicos.DB.DbTransacciones;
import com.example.negocioselectronicos.R;

public class ResultadosBusquedaTransaccionesActivity extends AppCompatActivity {

    private DbTransacciones dbTransacciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_busqueda_transacciones);

        dbTransacciones = new DbTransacciones(this);

        String campo = getIntent().getStringExtra("campo");
        String query = getIntent().getStringExtra("query");

        Cursor cursor = dbTransacciones.buscarPermisoPorCampo(campo, query);

        TableLayout table = findViewById(R.id.table);

        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);

            TextView numeroPermiso = new TextView(this);
            numeroPermiso.setText(cursor.getString(cursor.getColumnIndex("numero_permiso")));
            row.addView(numeroPermiso);

            TextView codigoTrabajador = new TextView(this);
            codigoTrabajador.setText(cursor.getString(cursor.getColumnIndex("codigo_trabajador")));
            row.addView(codigoTrabajador);

            TextView tipoPermiso = new TextView(this);
            tipoPermiso.setText(cursor.getString(cursor.getColumnIndex("tipo_permiso")));
            row.addView(tipoPermiso);

            TextView fecha = new TextView(this);
            fecha.setText(cursor.getString(cursor.getColumnIndex("fecha")));
            row.addView(fecha);

            TextView horas = new TextView(this);
            horas.setText(cursor.getString(cursor.getColumnIndex("horas")));
            row.addView(horas);

            TextView estadoRegistro = new TextView(this);
            estadoRegistro.setText(cursor.getString(cursor.getColumnIndex("estado_registro")));
            row.addView(estadoRegistro);

            table.addView(row);
        }

        cursor.close();

        /* Boton Retornar */

        Button btnRetornar = findViewById(R.id.button_return);

        btnRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}