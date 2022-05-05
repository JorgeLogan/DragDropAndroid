package com.example.actividad2_1;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class FichaJuego extends androidx.appcompat.widget.AppCompatImageView {
    // Atributos
    private float inicioX = -1; // Inicializado a -1 para que coja la posicion en el primer click
    private float inicioY = -1;
    private ImageView imagenDestino;
    private long tiempoVolverOrigen = 1000;
    private boolean conseguido = false;

    public boolean isConseguido(){ return this.conseguido; }

    // Como las coordenadas centrales hay que calcularlas uso 2 variables
    private float destinoX = 0;
    private float destinoY = 0;
    private float tolerancia = 100;     // Para darle un poco de margen al juego
    private boolean puedeCogerse = true; // Para bloquear si tiene que ir solo al origen

    // Atributos para el drag. Si va dentro de la funcion, no funcionara, tiene que estar fuera
    float pulsado_x =0;
    float pulsado_y = 0;

    // Setter para la imagen. No necesito mas
    public void setImagenDestino(ImageView imagenDestino) {
        this.imagenDestino = imagenDestino;

        this.setBackground(imagenDestino.getBackground());
    }


    // Constructores necesarios
    public FichaJuego(Context context) {
        super(context);
        this.inicializar();
    }

    public FichaJuego(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inicializar();
    }

    public FichaJuego(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.inicializar();
    }

    // Metodo que inicializa el objeto y el control del toque
    private void inicializar(){
        // El origen lo toma con el punto superior izquierda, pero no puedo buscarlo ahora, porque
        // no coge coordenadas hasta ejecutar un touch.
        this.puedeCogerse = true;

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(puedeCogerse == false) return false;


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("Pruebas", "Moviendo " + motionEvent.getAction() + " " + view.getX() + " " + motionEvent.getRawX());
                    if(inicioX == -1){
                        inicioX = view.getX();
                        inicioY = view.getY();
                    }

                    // Cogemos la posicion pulsada, para poder calcular el movimineto
                    pulsado_x = motionEvent.getX();
                    pulsado_y = motionEvent.getY();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    // Ahora, teniendo en cuenta la posicion pulsada, podemos calcular el movimiento
                    float motion_x = motionEvent.getX() - pulsado_x;
                    float motion_y = motionEvent.getY() - pulsado_y;

                    // Sumanos al valor que tiene, el movimiento a aplicar
                    view.setX( view.getX() + motion_x);
                    view.setY( view.getY() + motion_y);
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(comprobarDestino()){
                        Log.d("Pruebas", "OK!!!!!. Ficha: " + (view.getX() + view.getWidth()/2)
                                +"," + (view.getY() + view.getHeight()/2) + "  Destino: " + (imagenDestino.getX() + imagenDestino.getWidth()/2 )+ "," + imagenDestino.getY());


                    }else{
                        Log.d("Pruebas", "No esta en el sitio. Ficha: " + (view.getX() + view.getWidth()/2)
                                +"," + (view.getY() + view.getHeight()/2) + "  Destino: " + (imagenDestino.getX() + imagenDestino.getWidth()/2 )+ "," + imagenDestino.getY());
                        volverInicio();
                    }
                }
                return true;
            }
        });
        Log.d("Pruebas", "Inicializada ficha");
    }

    // Metodo que comprueba si el objeto esta en la posicion del destino con una tolerancia
    private boolean comprobarDestino(){
        boolean resultado = false;

        float finX = this.getX() + this.getWidth()/2;
        float finY = this.getY() + this.getHeight()/2;

        this.destinoX = imagenDestino.getX() + imagenDestino.getWidth()/2;
        this.destinoY = imagenDestino.getY() + imagenDestino.getHeight()/2;

        if(finX > this.destinoX - tolerancia && finX < this.destinoX + tolerancia &&
                finY > this.destinoY - tolerancia && finY < this.destinoY + tolerancia ){
            resultado = true;
        }
        return resultado;
    }

    // Metodo que devuelve el objeto a su posicion inicial
    private void volverInicio(){
        //  Primero bloqueamos interaccion del usuario
        this.puedeCogerse = false;

        ObjectAnimator animacionX = ObjectAnimator.ofFloat(this, "x", getX(), inicioX);
        animacionX.setDuration(this.tiempoVolverOrigen);

        ObjectAnimator animacionY = ObjectAnimator.ofFloat(this, "y", getY(), inicioY);
        animacionY.setDuration(this.tiempoVolverOrigen);

        AnimatorSet animatorX = new AnimatorSet(); // Necesario para reproducir los object animator
        animatorX.playTogether(animacionX, animacionY);
        animatorX.start();

        this.retardoActivarToque();
    }

    // Metodo que tarda un tiempo para volver a activar el touch
    private void retardoActivarToque(){
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(tiempoVolverOrigen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                puedeCogerse = true;
            }
        };
        t.start();
    }
}
