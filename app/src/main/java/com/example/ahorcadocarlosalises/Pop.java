package com.example.ahorcadocarlosalises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Pop extends AppCompatActivity {

    Context context;
    Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_partida);

        Button btnJugar = (Button) findViewById(R.id.btnJugar);
        Button btnSalir = (Button) findViewById(R.id.btnSalir);
        TextView lblFinPartida = (TextView) findViewById(R.id.lblFinPartida);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int) (w*.8), (int) (h*.6));

        WindowManager.LayoutParams prms = getWindow().getAttributes();
        prms.gravity = Gravity.CENTER;
        prms.x = 0;
        prms.y =- 20;

        getWindow().setAttributes(prms);

        String correoJugador = getIntent().getStringExtra("CORREO");
        boolean estadoPartida = getIntent().getBooleanExtra("ESTADO",false);
        String idiomaMensaje = getIntent().getStringExtra("IDIOMA");

        switch (idiomaMensaje){
                case "CASTELLANO":
                    if(estadoPartida == true){
                        lblFinPartida.setText("HAS GANADO "+ correoJugador.toString());
                    }else{
                        lblFinPartida.setText("HAS PERDIDO "+ correoJugador.toString());
                    }
                    context = LocaleHelper.setLocale(Pop.this, "es");
                    break;
                case "CATALAN":
                    if (estadoPartida == true) {
                        lblFinPartida.setText("HAS GUANYAT "+ correoJugador.toString());
                    }else{
                        lblFinPartida.setText("HAS PERDUT "+ correoJugador.toString());
                    }
                    context = LocaleHelper.setLocale(Pop.this, "ca");
                    break;
                case "INGLES":
                    if(estadoPartida == true){
                        lblFinPartida.setText("YOU WIN "+ correoJugador.toString());
                    }else {
                        lblFinPartida.setText("YOU LOSE "+ correoJugador.toString());
                    }
                    context = LocaleHelper.setLocale(Pop.this, "en");
            default:
                break;
            }
        resources = context.getResources();
        btnJugar.setText(resources.getString(R.string.btnpop_jugar));
        btnSalir.setText(resources.getString(R.string.btnpop_salir));

        View.OnClickListener btnPop = new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                switch (view.getId()){
                    case R.id.btnJugar:
                        Intent itnt = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(itnt);
                        finishAffinity();
                        break;
                    case R.id.btnSalir:
                        finishAffinity();
                        break;
                    default:
                        break;
                }



            }
        };

        btnJugar.setOnClickListener(btnPop);
        btnSalir.setOnClickListener(btnPop);



    }
}