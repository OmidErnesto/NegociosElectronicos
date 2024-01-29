package com.example.negocioselectronicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.negocioselectronicos.DB.DbTrabajadores;
import com.example.negocioselectronicos.Formularios.FormularioModificarTrabajadoresActivity;
import com.example.negocioselectronicos.Formularios.FormularioTrabajadoresActivity;
import com.example.negocioselectronicos.Formularios.ResultadosBusquedaTrabajadoresActivity;

public class TrabajadoresActivity extends AppCompatActivity {

    private TableRow selectedRow = null;
    private DbTrabajadores dbTrabajadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);
        dbTrabajadores = new DbTrabajadores(this);

        /* Boton Agregar */

        Button btnAgregar = findViewById(R.id.button_add);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrabajadoresActivity.this, FormularioTrabajadoresActivity.class);
                startActivity(intent);
            }
        });

        /* Boton Modificar */

        Button btnModificar = findViewById(R.id.button_modify);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TrabajadoresActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(TrabajadoresActivity.this, FormularioModificarTrabajadoresActivity.class);
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
                    Toast.makeText(TrabajadoresActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(2)).getText().toString();
                if (estadoRegistro.equals("I")) {
                    Toast.makeText(TrabajadoresActivity.this, "El registro ya está inactivo", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigo = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTrabajadores.actualizarEstadoRegistro(codigo, "I");
                Toast.makeText(TrabajadoresActivity.this, "Registro inactivado correctamente", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });


        /* Boton Reactivar */

        Button btnReActivar = findViewById(R.id.button_reactivate);
        btnReActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TrabajadoresActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(2)).getText().toString();
                if (estadoRegistro.equals("A")) {
                    Toast.makeText(TrabajadoresActivity.this, "El registro ya está activo", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigo = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTrabajadores.actualizarEstadoRegistro(codigo, "A");
                Toast.makeText(TrabajadoresActivity.this, "Registro reactivado correctamente", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        /* Boton Eliminar */

        Button btnEliminar = findViewById(R.id.button_delete);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow == null) {
                    Toast.makeText(TrabajadoresActivity.this, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show();
                    return;
                }
                String estadoRegistro = ((TextView) selectedRow.getChildAt(2)).getText().toString();
                if (estadoRegistro.equals("*")) {
                    Toast.makeText(TrabajadoresActivity.this, "El registro ya está eliminado", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigo = Integer.parseInt(((TextView) selectedRow.getChildAt(0)).getText().toString());
                dbTrabajadores.actualizarEstadoRegistro(codigo, "*");
                Toast.makeText(TrabajadoresActivity.this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show();
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

        /* Boton buscar */
        Spinner spinner_search = findViewById(R.id.spinner_search);
        EditText editText_query = findViewById(R.id.editText_query);

        SpinnerItem[] items = new SpinnerItem[] {
                new SpinnerItem(1, "Código", "codigo"),
                new SpinnerItem(2, "Nombre", "nombre"),
                new SpinnerItem(3, "Estado de Registro", "estado_registro")
        };

        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinner_search.setAdapter(adapter);

        Button btnBuscar = findViewById(R.id.button_search);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerItem selectedItem = (SpinnerItem) spinner_search.getSelectedItem();
                String campo = selectedItem.getQueryName();
                String query = editText_query.getText().toString();

                Cursor cursor = dbTrabajadores.buscarTrabajadorPorCampo(campo, query); // Usa dbTipoPermiso.buscarTipoPermisoPorCampo para TipoPermisoActivity
                if (cursor.getCount() > 0) {
                    Intent intent = new Intent(TrabajadoresActivity.this, ResultadosBusquedaTrabajadoresActivity.class); // Usa TipoPermisoActivity y ResultadosBusquedaTipoPermisoActivity para TipoPermisoActivity
                    intent.putExtra("campo", campo);
                    intent.putExtra("query", query);
                    startActivity(intent);
                } else {
                    Toast.makeText(TrabajadoresActivity.this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show(); // Cambia TrabajadoresActivity a TipoPermisoActivity para TipoPermisoActivity
                }
                cursor.close();
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

        DbTrabajadores dbTrabajadores = new DbTrabajadores(this);
        Cursor cursor = dbTrabajadores.obtenerTodosLosTrabajadores();

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