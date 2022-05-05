package com.example.actividad2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

public class Juego extends AppCompatActivity {
    ImageView inDestino1;
    ImageView inDestino2;
    ImageView inDestino3;
    FichaJuego ficha1;
    FichaJuego ficha2;
    FichaJuego ficha3;

    MediaPlayer mp;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        // Preparamos unos sonidos
        SonidoRecursos sonidoOK = new SonidoRecursos(this, R.raw.sonido_ok, 100);
        SonidoRecursos sonidoMal = new SonidoRecursos(this, R.raw.partida_perdida, 100);

        this.ponerMusica();

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
    }

    // Metodo que pone la musica. Es muy cutre, pero ocupa poco
    private void ponerMusica(){
        // Cargamos musica desde los recursos
        mp = MediaPlayer.create(this, R.raw.musica);
        mp.setLooping(true);
        mp.start();
    }

    // Metodo para quitar la musica cuando se cambie la aplicacion o apague la pantalla
    @Override
    protected void onPause() {
        super.onPause();
        if(mp!= null){
            mp.release();
        }
    }

    //Metodo para que vuelva a sonar la musica si reinicia la actividad
    @Override
    protected void onRestart() {
        super.onRestart();
        ponerMusica();
    }
}