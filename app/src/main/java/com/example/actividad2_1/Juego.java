package com.example.actividad2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class Juego extends AppCompatActivity {
    ImageView inDestino1;
    ImageView inDestino2;
    ImageView inDestino3;
    FichaJuego ficha1;
    FichaJuego ficha2;
    FichaJuego ficha3;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        SonidoRecursos sonido = new SonidoRecursos(this, R.raw.sonido_ok, 2000);
        sonido.sonar();
        // Buscamos los image view de destino
        inDestino1 = (ImageView) findViewById(R.id.ivDestino1);
        inDestino2 = (ImageView) findViewById(R.id.ivDestino2);
        inDestino3 = (ImageView) findViewById(R.id.ivDestino3);

        // Buscamos las fichas y les asignamos un destino
        ficha1 = (FichaJuego)findViewById(R.id.ficha1);
        ficha1.setImagenDestino(inDestino1);
        ficha2 = (FichaJuego)findViewById(R.id.ficha2);
        ficha2.setImagenDestino(inDestino2);
        ficha3 = (FichaJuego)findViewById(R.id.ficha3);
        ficha3.setImagenDestino(inDestino3);

        try{
            MiPaint miPaint = new MiPaint(this);
            miPaint.pintarRectangulo(inDestino1.getX(), inDestino1.getY(),
                    inDestino1.getX() + inDestino1.getWidth(),
                    inDestino1.getY() + inDestino1.getHeight(),
                    Color.valueOf(1212));
            miPaint.refreshDrawableState();
        }catch(Exception e){
            Log.d("Pruebas", "Error al hacer el paint" + e.getMessage());
        }
    }
}