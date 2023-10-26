package com.example.ahorcadocarlosalises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class FuncionamientoAhorcado extends AppCompatActivity {


    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funcionamiento);

        TextView titleFuncionamiento = (TextView) findViewById(R.id.titleFuncionamiento);
        TextView lblFuncionamiento_1 = (TextView) findViewById(R.id.lblFuncionamiento);
        TextView lblFuncionamiento_2 = (TextView) findViewById(R.id.lblFuncionamiento2);

        String idiomaEscogido = getIntent().getStringExtra("IDIOMA");
        switch (idiomaEscogido){
            case "CASTELLANO":
                context = LocaleHelper.setLocale(FuncionamientoAhorcado.this, "es");
                break;
            case "CATALAN":
                context = LocaleHelper.setLocale(FuncionamientoAhorcado.this, "ca");
                break;
            case "INGLES":
                context = LocaleHelper.setLocale(FuncionamientoAhorcado.this, "en");
                break;
            default:
                break;
        }

        resources = context.getResources();
        lblFuncionamiento_1.setText(resources.getString(R.string.funcionamiento_ahorcado));
        lblFuncionamiento_2.setText(resources.getString(R.string.funcionamiento_ahorcado2));
        titleFuncionamiento.setText(resources.getString(R.string.btn_funcionamiento));





    }
}