package com.example.actividad2_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Clase principal.
 * La idea es que el perro haga su animacion de ladrido, suene el sonido, y empecemos la actividad
 * con un retardo para que no se vea feo
 */
public class MainActivity extends AppCompatActivity{
    SonidoRecursos sonido;
    ImageView ivLogan;
    Button btnEmpezar;
    Button btnSalir;
    boolean iniciado = false; // Para que la animacion solo se cree una vez al inicio
    AnimationDrawable loganAnim;
    private long retardoActivity = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capturamos los botones del juego
        btnEmpezar = (Button)findViewById(R.id.btnVolverJugar);
        btnSalir= (Button)findViewById(R.id.btnSalir);

        // Preparamos el sonido del boton
        sonido = new SonidoRecursos(this, R.raw.ladrido, 200);
        // Capturamos el ImageView
        ivLogan = (ImageView)findViewById(R.id.ivLogan);

        // Creamos unos animation drawable para la imagen de loganin en reposo
        ivLogan.setBackgroundResource(R.drawable.anim_reposo);
        loganAnim = (AnimationDrawable) ivLogan.getBackground();
        loganAnim.start();

        // Damos funcionamiento al boton para que anime a Loganin
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Como va a tener un delay, quito los botones
                btnEmpezar.setVisibility(View.INVISIBLE);
                btnSalir.setVisibility(View.INVISIBLE);

                // Paramos la anim actual, iniciamos la animacion de ladrido, y el sonido
                loganAnim.stop();
                ivLogan.setBackgroundResource(R.drawable.anim_ladrando);
                loganAnim = (AnimationDrawable)ivLogan.getBackground();
                loganAnim.setOneShot(true);
                loganAnim.start();
                sonido.sonar();

               // Pedimos iniciar la actividad del juego mediante el delay que declaramos al final
                MiDelay miDelay = new MiDelay();
            }
        });

        // Damos funcionamiento al boton de salir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
        this.iniciado = true;
    }


    // Creo una clase publica para iniciar la actividad cuando se acabe el sonido o la animacion
    // Segun nos convenga, en este caso, el sonido
    public void iniciarJuego(){
        Intent intent = new Intent(this, Juego.class);
        startActivityForResult(intent, 100);
    }

    // Creo una clase para ralentizar el inicio de la actividad para que el ladrido y su sonido
    // funcionen donde deben
    class MiDelay extends Thread{
        public MiDelay(){
            this.start();
        }

        @Override
        public void run(){
            try {
                sleep(retardoActivity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            iniciarJuego();
        }
    }


    // Sobreescribo metodo para reactivar la animacion
    @Override
    protected void onResume() {
        super.onResume();
        ivLogan.setBackgroundResource(R.drawable.anim_reposo);
        loganAnim = (AnimationDrawable)ivLogan.getBackground();
        loganAnim.start();
    }


    // Metodo llamado al volver de la actividad de juego.
    // Si el juego ha retornado un valor de 100, saldra del juego
    // Si ha sido 1000, volvera a jugar
    // Si ha sido 2000, se quedara en esta pantalla
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK)  this.finish();
        else if(requestCode == 100 && resultCode == 1000)  this.iniciarJuego();

        this.btnEmpezar.setVisibility(View.VISIBLE);
        this.btnSalir.setVisibility(View.VISIBLE);
    }
}