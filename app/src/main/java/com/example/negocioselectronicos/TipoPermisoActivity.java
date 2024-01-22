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
import com.example.negocioselectronicos.Formularios.FormularioModificarTipoPermisoActivity;
import com.example.negocioselectronicos.Formularios.FormularioTipoPermisoActivity;

public class TipoPermisoActivity extends AppCompatActivity {

    private TableRow selectedRow = null;
    private DbTipoPermiso dbTipoPermiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_permiso);
        dbTipoPermiso = new DbTipoPermiso(this);

        /* Boton Agregar */

        Button btnAgregar = findViewById(R.id.button_add);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipoPermisoActivity.this, FormularioTipoPermisoActivity.class);
                startActivity(intent);
            }
        });

        /* Boton Modificar */

        Button btnModificar = findViewById(R.id.button_modify);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TipoPermisoActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(TipoPermisoActivity.this, FormularioModificarTipoPermisoActivity.class);
                intent.putExtra("codigo", ((TextView) selectedRow.getChildAt(0)).getText().toString());
                intent.putExtra("nombre", ((TextView) selectedRow.getChildAt(1)).getText().toString());
                intent.putExtra("estado_registro", ((TextView) selectedRow.getChildAt(2)).getText().toString());
                startActivity(intent);
            }
        });

        /* Boton Inactivar */

        Button btnInactivar = findViewById(R.id.button_deactivate);
        btnInactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TipoPermisoActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(2)).getText().toString();
                if (estadoRegistro.equals("I")) {
                    Toast.makeText(TipoPermisoActivity.this, "El registro ya está inactivo", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigo = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTipoPermiso.actualizarEstadoRegistro(codigo, "I");
                Toast.makeText(TipoPermisoActivity.this, "Registro inactivado correctamente", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });


        /* Boton Reactivar */

        Button btnReActivar = findViewById(R.id.button_reactivate);
        btnReActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TipoPermisoActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(2)).getText().toString();
                if (estadoRegistro.equals("A")) {
                    Toast.makeText(TipoPermisoActivity.this, "El registro ya está activo", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigo = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTipoPermiso.actualizarEstadoRegistro(codigo, "A");
                Toast.makeText(TipoPermisoActivity.this, "Registro reactivado correctamente", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        /* Boton Eliminar */

        Button btnEliminar = findViewById(R.id.button_delete);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TipoPermisoActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(2)).getText().toString();
                if (estadoRegistro.equals("*")) {
                    Toast.makeText(TipoPermisoActivity.this, "El registro ya está eliminado", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigo = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTipoPermiso.actualizarEstadoRegistro(codigo, "*");
                Toast.makeText(TipoPermisoActivity.this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show();
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

        DbTipoPermiso dbTipoPermiso = new DbTipoPermiso(this);
        Cursor cursor = dbTipoPermiso.obtenerTodosLosTipoPermiso();

        // Luego, agrega una fila a la tabla por cada trabajador en la base de datos
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