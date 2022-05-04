package com.example.actividad2_1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * Para manejar graficos controlando el rendimiento necesitamos crear una clase que herede de SurfaceView
 * Ademas la haremos que implemente la interfaz Callback, para procesar los eventos de creacion, destruccion
 * y modificacion generados en la superficie de dibujo
 *
 * Tambien deberemos crearle dentro una clase hilo para manejar las animaciones sin interferir en el programa
 *
 * Creo esta clase que servira para el ladrado de logan y para mas animaciones. Tendra una animacion de reposo,
 * que estara esperando una accion, y otra animacion antes de cerrar que se ejecutara cuando se detecte la accion
 */
public class AnimadorImagenes extends SurfaceView implements SurfaceHolder.Callback{
    private Context contexto;
    private ImageView iv;
    private int imgsReposo[]; // Cogeran el id del recurso
    private int imgsAccion[]; // Cogeran el id del recurso
    private boolean acabado = false;
    SurfaceHolder sHolder;
    HiloDibujo hilo;

    public boolean isAcabado(){ return this.acabado; }

    public AnimadorImagenes(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public AnimadorImagenes(Context contexto, ImageView iv, int imgsReposo[], int imgsAccion[]){
        super(contexto);
        this.contexto = contexto;
        this.iv = iv;
        this.imgsReposo = imgsReposo;
        this.imgsAccion = imgsAccion;
        hilo = new HiloDibujo(this.getHolder());
        hilo.start();
    }

    // Para iniciar la animacion
    public void animar(){
        this.hilo.accion = true;
    }

    // Para cerrar el hilo
    public void cerrar(){
        this.hilo.salir = true;
    }

    /**
     * Metodo de la interfaz Callback
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Pruebas", "Superficie creada");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("Pruebas", "Superficie modificada");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Pruebas", "Superficie destruida");
    }

    /**
     * Clase hilo para dibujar sin interferir en el programa
     */
    class HiloDibujo extends Thread{
        private SurfaceHolder miSurfaceHolder;
        public boolean accion = false;
        public boolean salir = false;
        // Constructor
        public HiloDibujo(SurfaceHolder miSurfaceHolder){
            this.miSurfaceHolder = miSurfaceHolder;
        }

        @Override
        public void run() {
            int i = 0;
            while (!salir) {

                // Mientras la animacion no sea accionada, estara con una de reposo
                if(!accion){
                    iv.setImageBitmap(BitmapFactory.decodeResource(contexto.getResources(), imgsReposo[i]));

                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i+1 < imgsReposo.length) i++;
                    else i=0;
                }
                else{ // Ha detectado que entre en accion, asi que ejecutamos la animacion de accion
                    i=0; // Ponemos el indice a cero para que empiece desde el principio
                    while(!salir && i < imgsAccion.length){
                        iv.setImageBitmap(BitmapFactory.decodeResource(contexto.getResources(), imgsAccion[i]));

                        try {
                            sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                    i = 0; // Ponemos a cero otra vez para que la animacion de reposo empiece de cero
                    this.accion = false;
                }
            }

            acabado = true;
        }
    }
}
