package com.example.actividad2_1;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

public class SonidoRecursos{
    private Context contexto;
    private int sonidoRecursos;
    private long retardo;
    private boolean sonando = false;

    public SonidoRecursos(Context contexto, int idRecurso, long retardo){
        this.contexto = contexto;
        this.sonidoRecursos = idRecurso;
        this.retardo = retardo;
    }

    public void sonar(){
        if(this.sonando == true) return;

        HiloSonido sonido = new HiloSonido();
        sonido.start();
    }

    class HiloSonido extends Thread{
        @Override
        public void run(){
            sonando = true;

            try {
                Thread.sleep(retardo);

                MediaPlayer mp = MediaPlayer.create(contexto, sonidoRecursos);
                mp.start();
                Toast.makeText(contexto, "Esta sonando", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sonando = false;
        }
    }
}
