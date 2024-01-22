package com.example.negocioselectronicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.negocioselectronicos.DB.DbTipoPermiso;
import com.example.negocioselectronicos.DB.DbTrabajadores;
import com.example.negocioselectronicos.DB.DbTransacciones;
import com.example.negocioselectronicos.Formularios.FormularioModificarTransaccionesActivity;
import com.example.negocioselectronicos.Formularios.FormularioTransaccionesActivity;

public class TransaccionesActivity extends AppCompatActivity {

    private TableRow selectedRow = null;
    private DbTransacciones dbTransacciones;
    private DbTrabajadores dbTrabajadores;
    private DbTipoPermiso dbTipoPermiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);
        dbTransacciones = new DbTransacciones(this);
        dbTrabajadores = new DbTrabajadores(this);
        dbTipoPermiso = new DbTipoPermiso(this);

        /* Boton Agregar */

        Button btnAgregar = findViewById(R.id.button_add);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransaccionesActivity.this, FormularioTransaccionesActivity.class);
                startActivity(intent);
            }
        });

        /* Boton Modificar */

        Button btnModificar = findViewById(R.id.button_modify);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TransaccionesActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(TransaccionesActivity.this, FormularioModificarTransaccionesActivity.class);
                intent.putExtra("numero_permiso", ((TextView) selectedRow.getChildAt(0)).getText().toString());
                intent.putExtra("codigo_trabajador", ((TextView) selectedRow.getChildAt(1)).getText().toString());
                intent.putExtra("tipo_permiso", ((TextView) selectedRow.getChildAt(2)).getText().toString());
                intent.putExtra("fecha", ((TextView) selectedRow.getChildAt(3)).getText().toString());
                intent.putExtra("horas", ((TextView) selectedRow.getChildAt(4)).getText().toString());
                intent.putExtra("estado_registro", ((TextView) selectedRow.getChildAt(5)).getText().toString());
                startActivity(intent);
            }
        });

        /* Boton Inactivar */

        Button btnInactivar = findViewById(R.id.button_deactivate);
        btnInactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TransaccionesActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(5)).getText().toString();
                if (estadoRegistro.equals("I")) {
                    Toast.makeText(TransaccionesActivity.this, "El registro ya está inactivo", Toast.LENGTH_SHORT).show();
                    return;
                }
                int numeroPermiso = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTransacciones.actualizarEstadoRegistro(numeroPermiso, "I");
                Toast.makeText(TransaccionesActivity.this, "Registro inactivado correctamente", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        /* Boton Reactivar */

        Button btnReActivar = findViewById(R.id.button_reactivate);
        btnReActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TransaccionesActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(5)).getText().toString();
                if (estadoRegistro.equals("A")) {
                    Toast.makeText(TransaccionesActivity.this, "El registro ya está activo", Toast.LENGTH_SHORT).show();
                    return;
                }
                int numeroPermiso = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTransacciones.actualizarEstadoRegistro(numeroPermiso, "A");
                Toast.makeText(TransaccionesActivity.this, "Registro reactivado correctamente", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        /* Boton Eliminar */

        Button btnEliminar = findViewById(R.id.button_delete);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TransaccionesActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(5)).getText().toString();
                if (estadoRegistro.equals("*")) {
                    Toast.makeText(TransaccionesActivity.this, "El registro ya está eliminado", Toast.LENGTH_SHORT).show();
                    return;
                }
                int numeroPermiso = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTransacciones.actualizarEstadoRegistro(numeroPermiso, "*");
                Toast.makeText(TransaccionesActivity.this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        /* Boton Salir */

        Button btnSalir = findViewById(R.id.button_exit);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        TableLayout table = findViewById(R.id.table);
        // Primero, limpia la tabla
        table.removeViews(1, table.getChildCount() - 1);

        if (selectedRow != null) {
            selectedRow.setBackgroundColor(Color.TRANSPARENT);
            selectedRow = null;
        }

        Cursor cursor = dbTransacciones.obtenerTodosLosPermisos();

        // Luego, agrega una fila a la tabla por cada permiso en la base de datos
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

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRow == row) {
                        selectedRow.setBackgroundColor(Color.TRANSPARENT);
                        selectedRow = null;
                    } else {
                        if (selectedRow != null) {
                            selectedRow.setBackgroundColor(Color.TRANSPARENT);
                        }
                        selectedRow = row;
                        row.setBackgroundColor(Color.BLUE);
                    }
                }
            });

            table.addView(row);
        }

        cursor.close();
    }
}
