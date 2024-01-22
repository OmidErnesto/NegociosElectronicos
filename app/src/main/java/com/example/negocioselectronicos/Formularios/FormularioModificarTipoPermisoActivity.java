package com.example.negocioselectronicos.Formularios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.negocioselectronicos.DB.DbTipoPermiso;
import com.example.negocioselectronicos.DB.DbTrabajadores;
import com.example.negocioselectronicos.R;

public class FormularioModificarTipoPermisoActivity extends AppCompatActivity {

    private EditText editTextCodigo;
    private EditText editTextNombre;
    private EditText editTextEstadoRegistro;
    private DbTipoPermiso dbTipoPermiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_modificar_tipo_permiso);

        editTextCodigo = findViewById(R.id.editText_codigo);
        editTextNombre = findViewById(R.id.editText_nombre);
        editTextEstadoRegistro = findViewById(R.id.editText_estado_registro);

        Intent intent = getIntent();
        editTextCodigo.setText(intent.getStringExtra("codigo"));
        editTextNombre.setText(intent.getStringExtra("nombre"));
        editTextEstadoRegistro.setText(intent.getStringExtra("estado_registro"));

        dbTipoPermiso = new DbTipoPermiso(this);

        Button buttonGuardar = findViewById(R.id.button_guardar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextNombre.getText().toString();
                int codigo = Integer.parseInt(editTextCodigo.getText().toString());
                dbTipoPermiso.actualizarTipoPermiso(codigo, nombre);
                Toast.makeText(FormularioModificarTipoPermisoActivity.this, "Tipo de permiso modificado correctamente", Toast.LENGTH_SHORT).show();
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