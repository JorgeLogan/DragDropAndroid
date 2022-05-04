package com.example.actividad2_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.security.spec.ECField;

public class Juego extends AppCompatActivity {

    Columna fragSuperior;
    Columna fragInferior;
    FrameLayout frameSup;
    FrameLayout frameInf;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        // Cargamos los contenedores de los fragments
        this.frameSup = (FrameLayout)findViewById(R.id.frameFragSup);
        this.frameInf = (FrameLayout)findViewById(R.id.frameFragInf);

        // Cargamos los fragments
        try{
            this.fragSuperior = new Columna(R.raw.logan0, R.raw.logan2, R.raw.logan3);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameFragSup, this.fragSuperior).commit();

            this.fragInferior = new Columna(R.raw.logan1, R.raw.logan4, R.raw.logan5);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameFragInf, this.fragInferior).commit();
        }catch(Exception e){
            Log.d("Pruebas", e.getMessage());
        }

        SonidoRecursos sonido = new SonidoRecursos(this, R.raw.sonido_ok, 2000);
        sonido.sonar();

    }

    private void cargarFragment(Fragment frag, int id){

    }
}