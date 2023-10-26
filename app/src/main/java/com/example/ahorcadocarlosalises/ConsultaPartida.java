package com.example.ahorcadocarlosalises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ConsultaPartida extends AppCompatActivity {

    String consulta = "";
    Context context;
    Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultadepartidas);

        MiAgendaBBDD miAgenda = new MiAgendaBBDD(this, "AhorcadoGame", null, 1);

        EditText nombreUsuario = (EditText) findViewById(R.id.usuarioConsulta);
        Button btnConsulta = (Button) findViewById(R.id.btnConsulta);
        TextView lblMostrarConsulta = (TextView) findViewById(R.id.lblConsulta);
        TextView lblMostrarJugadores = (TextView) findViewById(R.id.lblJugadores);
        TextView titleConsulta = (TextView) findViewById(R.id.titleConsulta);
        TextView titleJugadores = (TextView) findViewById(R.id.titleJugadores);

        String idiomaEscogido = getIntent().getStringExtra("IDIOMA");

        switch (idiomaEscogido){
            case "CASTELLANO":
                context = LocaleHelper.setLocale(ConsultaPartida.this, "es");
                break;
            case "CATALAN":
                context = LocaleHelper.setLocale(ConsultaPartida.this, "ca");
                break;
            case "INGLES":
                context = LocaleHelper.setLocale(ConsultaPartida.this, "en");
                break;
            default:
                break;
        }

        resources = context.getResources();
        nombreUsuario.setHint(resources.getString(R.string.hint_view_usuario));
        btnConsulta.setText(resources.getString(R.string.btn_verpartidas));
        titleConsulta.setText(resources.getString(R.string.title_consulta));
        titleJugadores.setText(resources.getString(R.string.title_jugadores));



        SQLiteDatabase db =  miAgenda.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM jugadores ORDER BY id ASC LIMIT 5", null);

        int idJugador;
        String correoJugador;
        int i = 0;
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            idJugador = cursor.getInt(0);
            correoJugador = cursor.getString(1);
            String [] c = correoJugador.split("@");
            correoJugador = c[0];
            lblMostrarJugadores.setText(lblMostrarJugadores.getText()+""+idJugador+" "+correoJugador+"\n");
            i++;
        }



        //ArrayList<String> consultas = new ArrayList<String>();
        //int numeroRegistros = cursor.getCount();

        View.OnClickListener hacerConsulta = new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String player = nombreUsuario.getText().toString();

                if(!consulta.equals(player)) {
                    String [] usuario = new String[] {player};
                    Cursor cursor = db.rawQuery("SELECT * FROM partidas WHERE jugador=?", usuario);

                    int idPartida;
                    String nombreJugador;
                    String palabraJugada;
                    String acertado;
                    //cursor.moveToFirst();
                    while (cursor.moveToNext()) {
                        idPartida = cursor.getInt(0);
                        nombreJugador = cursor.getString(1);
                        palabraJugada = cursor.getString(2);
                        acertado = cursor.getString(3);
                        lblMostrarConsulta.setText("ID"+" "+"Player"+" "+"Word"+" "+ "Guessed"+"\n");
                        lblMostrarConsulta.setText(lblMostrarConsulta.getText()+""+idPartida+" "+nombreJugador+" "+palabraJugada+" "+acertado+"\n");
                    }
                    consulta = player;
                }

            }


        };
        btnConsulta.setOnClickListener(hacerConsulta);


        }


    }
