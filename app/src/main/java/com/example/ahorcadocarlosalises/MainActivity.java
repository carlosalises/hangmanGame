package com.example.ahorcadocarlosalises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Context context;
    Resources resources;
    String canvioIdioma;
    String numRegistros = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceso_ahorcado);


        MiAgendaBBDD misJugadores = new MiAgendaBBDD(this, "AhorcadoGame", null, 1);
        db = misJugadores.getWritableDatabase();


        TextView numJugadores = (TextView) findViewById(R.id.numJugadores);
        TextView jugadoresRegistrados = (TextView) findViewById(R.id.lbljugadoresRegistrados);
        SharedPreferences sharpref = PreferenceManager.getDefaultSharedPreferences(this);
        String valorNum = sharpref.getString("UsuariosRegistrados","0");
        numJugadores.setText(valorNum);


        //BOTONES ACCESO AHORCADO
        Button btnAcceder = (Button) findViewById(R.id.btnAcceder);
        Button btnCastellano = (Button) findViewById(R.id.btnCastellano);
        Button btnCatalan = (Button) findViewById(R.id.btnCatalan);
        Button btnIngles = (Button) findViewById(R.id.btnIngles);
        //Button btnenviarLetra = findViewById(R.id.btncogerLetra);
        Button btnConsulta = (Button) findViewById(R.id.btnconsultarPartidas);
        Button btnFuncionamiento = (Button) findViewById(R.id.btnFuncionamiento);


        //TEXT VIEW ACCESO AHORCADO
        TextView titleAhorcado = (TextView) findViewById(R.id.lblTitle);
        TextView correoJugador = (TextView) findViewById(R.id.nombreJugador);
        TextView pwdJugador = (TextView) findViewById(R.id.correoJugador);




        //

        
        
        TextView lblError = (TextView) findViewById(R.id.lblError);
        canvioIdioma = "CASTELLANO";
        jugadoresRegistrados.setText("USUARIOS REGISTRADOS");
        context = LocaleHelper.setLocale(MainActivity.this, "es");
        resources = context.getResources();
        correoJugador.setHint(resources.getString(R.string.correo));
        titleAhorcado.setText(resources.getString(R.string.title_acceso_ahorcado));
        pwdJugador.setHint(resources.getString(R.string.pwd));
        btnAcceder.setText(resources.getString(R.string.btn_acceso));
        btnCastellano.setText(resources.getString(R.string.btn_es));
        btnCatalan.setText(resources.getString(R.string.btn_ca));
        btnIngles.setText(resources.getString(R.string.btn_en));
        btnConsulta.setText(resources.getString(R.string.btn_partidas_jugadores));
        btnFuncionamiento.setText(resources.getString(R.string.btn_funcionamiento));

        View.OnClickListener Acceder = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String correo = correoJugador.getText().toString();
                String password = pwdJugador.getText().toString();
                if(correo.isEmpty() || correo.equals("CORREO ELECTRÓNICO") || password.isEmpty() || correo.equals("PASSWORD")){
                    lblError.setText("NO DEJES NADA VACIO");
                }else if(!validarEmail(correo)){
                    lblError.setText("FORMATO CORREO NO VALIDO");

                }else{
                    if (db != null) {
                        db.execSQL("INSERT INTO jugadores (correo, contraseña) VALUES ('" + correo + "', '" + password + "')");
                    }
                    Intent itnt = new Intent(getApplicationContext(), Ahorcado.class);
                    itnt.putExtra("CORREO", correo);
                    itnt.putExtra("PASSWORD", password);
                    itnt.putExtra("IDIOMA", canvioIdioma);


                    SharedPreferences.Editor editor = sharpref.edit();
                    editor.putString("UsuariosRegistrados", String.valueOf(Integer.parseInt(numJugadores.getText().toString())+1));
                    editor.commit();

                    startActivityForResult(itnt, 4);
                    finish();
                }

            }
        };
        btnAcceder.setOnClickListener(Acceder);


        View.OnClickListener ChangeLenguage = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.btnCastellano:
                        context = LocaleHelper.setLocale(MainActivity.this, "es");
                        canvioIdioma = "CASTELLANO";
                        jugadoresRegistrados.setText("USUARIOS REGISTRADOS");
                     break;
                    case  R.id.btnCatalan:
                        context = LocaleHelper.setLocale(MainActivity.this, "ca");
                        canvioIdioma = "CATALAN";
                        jugadoresRegistrados.setText("USUARIS REGISTRATS");
                      break;
                    case  R.id.btnIngles:
                        context = LocaleHelper.setLocale(MainActivity.this, "en");
                        canvioIdioma = "INGLES";
                        jugadoresRegistrados.setText("REGISTERED USERS");
                        break;
                    default:
                        break;
                }

                resources = context.getResources();
                correoJugador.setHint(resources.getString(R.string.correo));
                titleAhorcado.setText(resources.getString(R.string.title_acceso_ahorcado));
                pwdJugador.setHint(resources.getString(R.string.pwd));
                btnAcceder.setText(resources.getString(R.string.btn_acceso));
                btnCastellano.setText(resources.getString(R.string.btn_es));
                btnCatalan.setText(resources.getString(R.string.btn_ca));
                btnIngles.setText(resources.getString(R.string.btn_en));
                btnConsulta.setText(resources.getString(R.string.btn_partidas_jugadores));
                btnFuncionamiento.setText(resources.getString(R.string.btn_funcionamiento));

            }
        };

        btnCastellano.setOnClickListener(ChangeLenguage);
        btnCatalan.setOnClickListener(ChangeLenguage);
        btnIngles.setOnClickListener(ChangeLenguage);


        View.OnClickListener Others = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent itnt;
                switch (view.getId()) {
                    case R.id.btnconsultarPartidas:
                        itnt = new Intent(getApplicationContext(), ConsultaPartida.class);
                        itnt.putExtra("IDIOMA",canvioIdioma);
                        startActivity(itnt);
                        lblError.setText("");
                        break;
                    case R.id.btnFuncionamiento:
                        itnt = new Intent(getApplicationContext(), FuncionamientoAhorcado.class);
                        itnt.putExtra("IDIOMA",canvioIdioma);
                        startActivity(itnt);
                        lblError.setText("");
                }

                //finishAffinity();

            }
        };

        btnConsulta.setOnClickListener(Others);
        btnFuncionamiento.setOnClickListener(Others);




    }

    public boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }



}