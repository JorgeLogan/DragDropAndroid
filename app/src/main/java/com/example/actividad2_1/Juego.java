package com.example.actividad2_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.security.spec.ECField;

public class Juego extends AppCompatActivity {

    Columna fragSuperior;
    Columna fragInferior;
    FrameLayout frameSup;
    FrameLayout frameInf;

    ImageView ivPrueba;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
/*
        // Cargamos los contenedores de los fragments
        this.frameSup = (FrameLayout)findViewById(R.id.frameFragSup);
        this.frameInf = (FrameLayout)findViewById(R.id.frameFragInf);
*/
        // Cargamos los fragments
        try{
        //    this.fragSuperior = new Columna(R.raw.logan0, R.raw.logan2, R.raw.logan3);
  //          getSupportFragmentManager().beginTransaction().replace(R.id.frameFragSup, this.fragSuperior).commit();

      //      this.fragInferior = new Columna(R.raw.logan1, R.raw.logan4, R.raw.logan5);
    //        getSupportFragmentManager().beginTransaction().replace(R.id.frameFragInf, this.fragInferior).commit();
        }catch(Exception e){
            Log.d("Pruebas", e.getMessage());
        }

        SonidoRecursos sonido = new SonidoRecursos(this, R.raw.sonido_ok, 2000);
        sonido.sonar();

        ivPrueba = (ImageView) findViewById(R.id.ivPrueba);

        ivPrueba.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("Pruebas", "Moviendo " + motionEvent.getAction() + " " + view.getX() + " " + motionEvent.getRawX());
                    inicio_x = motionEvent.getX();
                    inicio_y = motionEvent.getY();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    float motion_x = motionEvent.getX() - inicio_x;
                    float motion_y = motionEvent.getY() - inicio_y;

                    view.setX( view.getX() + motion_x);
                    view.setY( view.getY() + motion_y);
                }
                return true;
            }
        });
    }

    float inicio_x = 0,inicio_y = 0;

    private void cargarFragment(Fragment frag, int id){

    }
}