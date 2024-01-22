package com.example.negocioselectronicos.Formularios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.negocioselectronicos.DB.DbTrabajadores;
import com.example.negocioselectronicos.R;

import java.io.IOException;

public class FormularioTrabajadoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_trabajadores);

        final EditText editTextNombre = findViewById(R.id.editTextNombre);

        /* Boton Guardar */

        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = editTextNombre.getText().toString();
                DbTrabajadores dbTrabajadores = new DbTrabajadores(FormularioTrabajadoresActivity.this);

                try {
                    long id = dbTrabajadores.insertarTrabajador(nombre);
                    if (id > 0) {
                        Toast.makeText(FormularioTrabajadoresActivity.this, "Empleado creado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FormularioTrabajadoresActivity.this, "Error al crear el empleado", Toast.LENGTH_SHORT).show();
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