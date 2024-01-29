package com.example.negocioselectronicos.Formularios;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.negocioselectronicos.DB.DbTrabajadores;
import com.example.negocioselectronicos.R;

public class ResultadosBusquedaTrabajadoresActivity extends AppCompatActivity {

    private DbTrabajadores dbTrabajadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_busqueda_trabajadores);

        dbTrabajadores = new DbTrabajadores(this);

        String campo = getIntent().getStringExtra("campo");
        String query = getIntent().getStringExtra("query");

        Cursor cursor = dbTrabajadores.buscarTrabajadorPorCampo(campo, query);

        TableLayout table = findViewById(R.id.table);

        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);

            TextView codigo = new TextView(this);
            codigo.setText(cursor.getString(cursor.getColumnIndex("codigo")));
            row.addView(codigo);

            TextView nombre = new TextView(this);
            nombre.setText(cursor.getString(cursor.getColumnIndex("nombre")));
            row.addView(nombre);

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
