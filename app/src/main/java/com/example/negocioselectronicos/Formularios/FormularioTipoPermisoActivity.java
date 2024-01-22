package com.example.negocioselectronicos.Formularios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.negocioselectronicos.DB.DbTipoPermiso;
import com.example.negocioselectronicos.DB.DbTrabajadores;
import com.example.negocioselectronicos.R;

public class FormularioTipoPermisoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_tipo_permiso);

        final EditText editTextNombre = findViewById(R.id.editTextNombre);

        /* Boton Guardar */

        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = editTextNombre.getText().toString();
                DbTipoPermiso dbTipoPermiso = new DbTipoPermiso(FormularioTipoPermisoActivity.this);

                try {
                    long id = dbTipoPermiso.insertarTipoPermiso(nombre);
                    if (id > 0) {
                        Toast.makeText(FormularioTipoPermisoActivity.this, "Tipo de permiso creado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FormularioTipoPermisoActivity.this, "Error al crear el tipo de permiso", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                finish();
            }
        });

        /* Boton Cancelar*/

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}