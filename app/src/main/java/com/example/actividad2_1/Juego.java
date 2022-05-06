package com.example.actividad2_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class Juego extends AppCompatActivity{
    ImageView inDestino1;
    ImageView inDestino2;
    ImageView inDestino3;
    TextView tvIntentoTxt;
    TextView tvIntentoValor;
    TextView tvBravo;
    FichaJuego ficha1;
    FichaJuego ficha2;
    FichaJuego ficha3;
    MediaPlayer mp;
    int intentos = 0;
    int conseguidos = 0;
    RelativeLayout rl;
    LinearLayout llBotones;
    Button btnVolver, btnSalir, btnJugar;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        // Para las fichas paso nuestra actividad
        FichaJuego.setJuego(this);

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

        // Preparamos los textview
        tvIntentoTxt = (TextView)findViewById(R.id.txtIntentos);
        tvIntentoValor = (TextView)findViewById(R.id.txtNumIntentos);
        tvIntentoValor.setText(String.valueOf(this.intentos));
        tvBravo = (TextView)findViewById(R.id.tvBravo);

        // El layout para el fondo y el de botones
        this.rl = (RelativeLayout)findViewById(R.id.rl);
        this.llBotones = (LinearLayout)findViewById(R.id.llBotones);
        this.btnJugar = (Button)findViewById(R.id.btnVolverJugar);
        this.btnVolver = (Button) findViewById(R.id.btnInicio);
        this.btnSalir = (Button) findViewById(R.id.btnSalirJuego);

        // Damos funcionalidad a los botones
        this.btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.release();
                setResult(1000); // Interpretara 1000 como volver a jugar
                finish();
            }
        });

        this.btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK); // Para salir de la app
                mp.release();
                finish();
            }
        });

        this.btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.release();
                setResult(2000); // Un codigo que no se usa, para quedar en la pantalla main
                finish();
            }
        });

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

    // Metodo para cuando la ficha este en posicion correcta al final
    public void posicionOK() {
        this.conseguidos++;
        MediaPlayer mpOk = MediaPlayer.create(this, R.raw.sonido_ok);
        mpOk.start();

        if(this.conseguidos == 3){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                animacionFinalOK();
            }else{
                Log.d("Pruebas", "No se puede reproducir animacion por ser build inferior");
            }
        }
    }


    // Metodo para cuando la ficha no este en posicion correcta al final
    public void posicionMal() {
        MediaPlayer mpMal = MediaPlayer.create(this, R.raw.error);
        mpMal.start();
        this.intentos++;
        tvIntentoValor.setText(String.valueOf(this.intentos));
        animacionFondoMal();
    }


    private void animacionFondoMal(){
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.anim_ficha_mal);
        this.tvIntentoTxt.startAnimation(animacion);

        int rojo = getResources().getColor(R.color.rojo);
        int blanco = getResources().getColor(R.color.white);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), rojo, blanco);
        colorAnimation.setDuration(500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                rl.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animacionFinalOK(){
        this.animarFondoOK();
        this.animarBravo();

        this.llBotones.setVisibility(View.VISIBLE);

        Animation animBoton =  AnimationUtils.loadAnimation(this, R.anim.anim_botones);
        animBoton.setDuration(1000);
        this.btnSalir.startAnimation(animBoton);
        this.btnVolver.startAnimation(animBoton);
        this.btnJugar.startAnimation(animBoton);
    }


    private void animarFondoOK() {
        // Para la animacion del fondo
        int verde = getResources().getColor(R.color.verde);
        int blanco = getResources().getColor(R.color.white);

        // Animamos el color del fondo
        ValueAnimator animColor = ValueAnimator.ofObject(new ArgbEvaluator(), Color.GREEN, Color.WHITE);
        animColor.setDuration(2000);
        animColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rl.setBackgroundColor((int) animColor.getAnimatedValue());
            }
        });
        animColor.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animarBravo(){
        // Para la animacion del texto
        int transparente = getResources().getColor(R.color.transparente);
        int negro = getResources().getColor(R.color.black);

        // Animamos el color del texto de bravo
        ValueAnimator animTexto = ValueAnimator.ofArgb(transparente, negro, transparente);
        animTexto.setDuration(3000);
        animTexto.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tvBravo.setTextColor((int) animTexto.getAnimatedValue());
            }
        });
        animTexto.start();

        ValueAnimator animEscala = ValueAnimator.ofFloat(1, 2,1);
        animEscala.setDuration(3000);
        animEscala.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tvBravo.setScaleX((Float) animEscala.getAnimatedValue());
                tvBravo.setScaleY((float) animEscala.getAnimatedValue());
            }
        });
        animEscala.start();
    }
}