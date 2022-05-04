package com.example.actividad2_1;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Columna extends Fragment {

    // Las imagenes a controlar
    ImageView imagen1, imagen2, imagen3;
    int img1, img2, img3;

    public Columna() {
        // Required empty public constructor
    }

    public Columna(int img1, int img2, int img3){
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =inflater.inflate(R.layout.fragment_columna, container, false);

        this.imagen1 = (ImageView)vista.findViewById(R.id.img_1);
        this.imagen2 = (ImageView)vista.findViewById(R.id.img_2);
        this.imagen3 = (ImageView)vista.findViewById(R.id.img_3);

        this.imagen1.setImageResource(this.img1);
        this.imagen2.setImageResource(this.img2);
        this.imagen3.setImageResource(this.img3);

        return vista;
    }
}