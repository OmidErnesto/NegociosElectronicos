package com.example.negocioselectronicos.Formularios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.negocioselectronicos.DB.DbTransacciones;
import com.example.negocioselectronicos.DB.DbTrabajadores;
import com.example.negocioselectronicos.DB.DbTipoPermiso;
import com.example.negocioselectronicos.R;
import com.example.negocioselectronicos.SpinnerItem;

import android.database.Cursor;

public class FormularioModificarTransaccionesActivity extends AppCompatActivity {

    private EditText editTextNumeroPermiso;
    private Spinner spinnerCodigoTrabajador;
    private Spinner spinnerTipoPermiso;
    private EditText editTextFecha;
    private EditText editTextHoras;
    private EditText editTextEstadoRegistro;
    private DbTransacciones dbTransacciones;
    private DbTrabajadores dbTrabajadores;
    private DbTipoPermiso dbTipoPermiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_modificar_transacciones);

        editTextNumeroPermiso = findViewById(R.id.editText_numero_permiso);
        spinnerCodigoTrabajador = findViewById(R.id.spinner_codigo_trabajador);
        spinnerTipoPermiso = findViewById(R.id.spinner_tipo_permiso);
        editTextFecha = findViewById(R.id.editText_fecha);
        editTextHoras = findViewById(R.id.editText_horas);
        editTextEstadoRegistro = findViewById(R.id.editText_estado_registro);

        Intent intent = getIntent();
        editTextNumeroPermiso.setText(intent.getStringExtra("numero_permiso"));

        dbTrabajadores = new DbTrabajadores(this);
        dbTipoPermiso = new DbTipoPermiso(this);
        Cursor trabajadoresCursor = dbTrabajadores.obtenerTrabajadoresActivos();
        Cursor tipoPermisoCursor = dbTipoPermiso.obtenerTipoPermisoActivos();

        ArrayAdapter<SpinnerItem> trabajadoresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        ArrayAdapter<SpinnerItem> tipoPermisoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        while (trabajadoresCursor.moveToNext()) {
            int codigo = trabajadoresCursor.getInt(trabajadoresCursor.getColumnIndex("codigo"));
            String nombre = trabajadoresCursor.getString(trabajadoresCursor.getColumnIndex("nombre"));
            // CAMBIO AQUÍ: Agrega un objeto SpinnerItem en lugar de solo el nombre
            trabajadoresAdapter.add(new SpinnerItem(codigo, nombre));
        }

        while (tipoPermisoCursor.moveToNext()) {
            int codigo = tipoPermisoCursor.getInt(tipoPermisoCursor.getColumnIndex("codigo"));
            String nombre = tipoPermisoCursor.getString(tipoPermisoCursor.getColumnIndex("nombre"));
            // CAMBIO AQUÍ: Agrega un objeto SpinnerItem en lugar de solo el nombre
            tipoPermisoAdapter.add(new SpinnerItem(codigo, nombre));
        }

        spinnerCodigoTrabajador.setAdapter(trabajadoresAdapter);
        spinnerTipoPermiso.setAdapter(tipoPermisoAdapter);

        editTextFecha.setText(intent.getStringExtra("fecha"));
        editTextHoras.setText(intent.getStringExtra("horas"));
        editTextEstadoRegistro.setText(intent.getStringExtra("estado_registro"));

        dbTransacciones = new DbTransacciones(this);

        Button buttonGuardar = findViewById(R.id.button_guardar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numeroPermiso = Integer.parseInt(editTextNumeroPermiso.getText().toString());
                SpinnerItem trabajadorSeleccionado = (SpinnerItem) spinnerCodigoTrabajador.getSelectedItem();
                int codigoTrabajador = trabajadorSeleccionado.getCodigo();

                SpinnerItem permisoSeleccionado = (SpinnerItem) spinnerTipoPermiso.getSelectedItem();
                int tipoPermiso = permisoSeleccionado.getCodigo();

                String fecha = editTextFecha.getText().toString();
                String horas = editTextHoras.getText().toString();
                dbTransacciones.actualizarPermiso(numeroPermiso, codigoTrabajador, tipoPermiso, fecha, horas);
                Toast.makeText(FormularioModificarTransaccionesActivity.this, "Permiso modificado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button buttonCancelar = findViewById(R.id.button_cancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
