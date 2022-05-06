package com.example.actividad2_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    AnimadorImagenes animadorLogan;
    ImageView ivLogan;
    int[] imgsReposo;
    int[]imgsLadrando;
    Button btnEmpezar;
    Button btnSalir;
    boolean iniciado = false; // Para que la animacion solo se cree una vez al inicio

    private long retardoActivity = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capturamos los botones del juego
        btnEmpezar = (Button)findViewById(R.id.btnVolverJugar);
        btnSalir= (Button)findViewById(R.id.btnSalir);

        // Preparamos el sonido del boton
        sonido = new SonidoRecursos(this, R.raw.ladrido, 500);
        // Capturamos el ImageView
        ivLogan = (ImageView)findViewById(R.id.ivLogan);

        // Creamos las animaciones de reposo y de ladrido
        imgsReposo = new int[]{R.raw.logan3,R.raw.logan4, R.raw.logan5};
        imgsLadrando = new int[]{R.raw.logan0,  R.raw.logan1, R.raw.logan2, R.raw.logan2,
                R.raw.logan1, R.raw.logan2, R.raw.logan2, R.raw.logan1, R.raw.logan0};

        // Creamos un objeto de la clase que anima imagenes, pasandole el contexto, pedido por el
        // SurfaceView padre, el ImageView contenedor, y las animaciones de reposo y accion
        animadorLogan = new AnimadorImagenes(this, ivLogan, imgsReposo, imgsLadrando);

        // Damos funcionamiento al boton para que anime a Loganin
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciamos la animacion y el sonido
                animadorLogan.animar();
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
        this.animadorLogan.cerrar();
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

    /**
     * Hago una sobreescritura de onResume para que la animacion del perrin vuelva a funcionar si
     * vuelve de la actividad de juego (no me dio fallos haciendo otras pruebas aun)
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Pruebas", "On resume activado");
        if(this.animadorLogan!= null && this.animadorLogan.isAcabado()){
            Log.d("Pruebas", "Activada la nueva animacion");
            animadorLogan = new AnimadorImagenes(this, ivLogan, imgsReposo, imgsLadrando);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK)  this.finish();
        else if(requestCode == 100 && resultCode == 1000)  this.iniciarJuego();
    }
}