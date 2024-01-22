package com.example.negocioselectronicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPermisos = findViewById(R.id.btnPermisos);
        Button btnTrabajadores = findViewById(R.id.btnTrabajadores);
        Button btnTipoPermiso = findViewById(R.id.btnTipoPermiso);

        btnPermisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransaccionesActivity.class);
                startActivity(intent);
            }
        });

        btnTrabajadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrabajadoresActivity.class);
                startActivity(intent);
            }
        });

        btnTipoPermiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipoPermisoActivity.class);
                startActivity(intent);
            }
        });
    }
}
