package com.example.ahorcadocarlosalises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ahorcado extends AppCompatActivity {

    ArrayList<String> palabraElegida = new ArrayList<>();
    ArrayList<String> palabraAuxiliar = new ArrayList<>();
    ArrayList<String> letrasUsadas = new ArrayList<>();
    TextView lblAhorcado;
    TextView lblLetrasUsadas;
    TextView lblLetrasEncontradas;
    ImageView imagenAhorcado;
    boolean letraEncontrada;
    boolean limpiarPalabra;
    String palabra;
    int posicion;
    int contLetrasDoble;
    int contErrores;
    SQLiteDatabase db;
    Context context;
    Resources resources;
    //String canvioIdioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String Letra;
        ArrayList<Character> casillasAhorcado = new ArrayList<>();
        List<String> palabras = Arrays.asList("ARTRITIS","CIRCULACION","COCODRILO","ANTITESIS","TERMOMETRO","PESADILLA");

        int contLetra = 0;

        MiAgendaBBDD misJugadores = new MiAgendaBBDD(this, "AhorcadoGame", null, 1);
        db = misJugadores.getWritableDatabase();


        //Escoger palabra aletoria de la lista
        int numeroRandom = (int)(Math.random()*palabras.size()+0);
        palabra = palabras.get(numeroRandom);

        //Text Views Ahorcado
        lblAhorcado = (TextView) findViewById(R.id.palabraAhorcado);
        lblLetrasUsadas = (TextView) findViewById(R.id.lblLetrasUsadas);
        lblLetrasEncontradas = (TextView) findViewById(R.id.letrasEncontradas);
        //Image Views Ahorcado
        imagenAhorcado = (ImageView) findViewById(R.id.imagenAhorcado);
        TextView lbltitleEncontradas = (TextView) findViewById(R.id.titleEncontradas);
        TextView lbltitleNoEncontradas = (TextView) findViewById(R.id.titleNoEncontradas);
        EditText letraEnviada = (EditText) findViewById(R.id.lblLetra);

        Button btncogerLetra = (Button) findViewById(R.id.btncogerLetra);


        //Datos Cogidos de la Main Activity
        String correoJugador = getIntent().getStringExtra("CORREO");
        //


        limpiarPalabra = false;



        //COGER LETRAS PALABRAS
        int b = 0;
        for (int i = 0; i < palabra.length(); i++) {
            palabraElegida.add(palabra.substring(b, i + 1));
            b++;
        }

        for (int i = 0; i < palabraElegida.size(); i++) {
            lblAhorcado.setText(lblAhorcado.getText()+" "+"_");
            palabraAuxiliar.add("_");
        }




        //COMPROVAR IDIOMA
        String idiomaEscogido = getIntent().getStringExtra("IDIOMA");
        if(idiomaEscogido.equals("CASTELLANO")){
            context = LocaleHelper.setLocale(Ahorcado.this, "es");
        }else if (idiomaEscogido.equals("CATALAN")){
            context = LocaleHelper.setLocale(Ahorcado.this, "ca");
        }else if(idiomaEscogido.equals("INGLES")){
            context = LocaleHelper.setLocale(Ahorcado.this, "en");
        }
        resources = context.getResources();
        btncogerLetra.setText(resources.getString(R.string.envio_letra));
        lbltitleEncontradas.setText(resources.getString(R.string.letras_encontradas));
        lbltitleNoEncontradas.setText(resources.getString(R.string.letras_noencontradas));
        letraEnviada.setHint(resources.getString(R.string.letra_introducida));


        View.OnClickListener enviarLetra = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ComprovarLetra(letraEnviada);
                UpdateImageAhorcado();
                ComprovarEstadoPartida(btncogerLetra,contLetra,correoJugador,idiomaEscogido);
            }
        };


        btncogerLetra.setOnClickListener(enviarLetra);

    }

    public void ComprovarLetra(EditText lletraEnviada) {
        String letraEnviada = String.valueOf(lletraEnviada.getText()).toUpperCase();
        contLetrasDoble = 0;
        letraEncontrada = false;
        boolean isUsed = false;

        for (int p = 0; p < letrasUsadas.size(); p++) {
            if(letrasUsadas.get(p).equals(letraEnviada)){
                isUsed = true;
            }
        }

        if (!letraEnviada.isEmpty() && !letraEnviada.equals("LETRA") && letraEnviada.length() == 1){
            if (isUsed == false){
                lblAhorcado.setText("");
                for (int x = 0; x < palabraElegida.size(); x++) {
                    if (palabraElegida.get(x).equals(letraEnviada)) {
                        posicion = x;
                        letraEncontrada = true;
                        if(contLetrasDoble < 1){
                            contLetrasDoble++;
                            Log.e("AA",letraEnviada);
                            lblLetrasEncontradas.setText(lblLetrasEncontradas.getText()+" "+letraEnviada);
                            letrasUsadas.add(letraEnviada);
                        }
                        for (int y = 0; y < palabraAuxiliar.size(); y++){
                            if(y == posicion){
                                palabraAuxiliar.remove(y);
                                palabraAuxiliar.add(y,letraEnviada);
                            }
                        }
                    }
                }
                String l;
                for (int a = 0; a < palabraAuxiliar.size(); a++) {
                    l = palabraAuxiliar.get(a);
                    //Log.e("p",l);
                    lblAhorcado.setText(lblAhorcado.getText()+l+" ");
                }
                if(letraEncontrada == false) {
                    letrasUsadas.add(letraEnviada);
                    //LetrasUsadas();
                    contErrores++;
                    lblLetrasUsadas.setText(lblLetrasUsadas.getText()+" "+letraEnviada);
                }
            }else{
                Toast.makeText(this, "ESTA LETRA YA HA SIDO UTILIZADA", Toast.LENGTH_SHORT).show();
            }
        }else {
            if(letraEnviada.isEmpty()){
                Toast.makeText(this, "NINGUNA LETRA INTRODUCIDA", Toast.LENGTH_SHORT).show();
            }else if(letraEnviada.length() != 1) {
                Toast.makeText(this, "FORMATO NO VALIDO", Toast.LENGTH_SHORT).show();
            }

        }







        /*if(limpiarPalabra == false){
            lblAhorcado.setText("");
            limpiarPalabra = true;
        }
        for (int x = 0; x < palabraElegida.size(); x++) {
            if(palabraElegida.get(x).equals(letraEnviada)){
                lblAhorcado.setText(lblAhorcado.getText()+" "+letraEnviada);
                letraEncontrada = true;
            }else{
                lblAhorcado.setText(lblAhorcado.getText()+" "+"_");
            }
        }
        if(letraEncontrada) {
            lblLetrasUsadas.setText(lblLetrasUsadas.getText()+" "+letraEnviada);
            letraEncontrada = false;
        }*/
    }

    private void LetrasUsadas() {
        String letraUsada;
        lblLetrasUsadas.setText("");
        for (int i = 0; i < letrasUsadas.size(); i++) {
            letraUsada = letrasUsadas.get(i);
            Log.e("letraUsada",letraUsada);
            lblLetrasUsadas.setText(lblLetrasUsadas.getText()+" "+letraUsada);
        }
    }
    private void IsUsed() {
        String letraUsada;
        for (int i = 0; i < letrasUsadas.size(); i++) {
            letraUsada = letrasUsadas.get(i);
            Log.e("letraUsada",letraUsada);
            lblLetrasUsadas.setText(lblLetrasUsadas.getText()+" "+letraUsada);
        }
    }

    private void UpdateImageAhorcado() {
        switch (contErrores){
            case 1:
                imagenAhorcado.setImageResource(R.drawable.fase1);
                break;
            case 2:
                imagenAhorcado.setImageResource(R.drawable.fase2);
                break;
            case 3:
                imagenAhorcado.setImageResource(R.drawable.fase3);
                break;
            case 4:
                imagenAhorcado.setImageResource(R.drawable.fase4);
                break;
            case 5:
                imagenAhorcado.setImageResource(R.drawable.fase5);
                break;
            case 6:
                imagenAhorcado.setImageResource(R.drawable.fase6);
                break;

        }
    }

    private void ComprovarEstadoPartida(Button boton, int cont, String correoJugador, String idioma) {
        if (contErrores==6){
            Toast.makeText(this, "HAS PERDIDO", Toast.LENGTH_SHORT).show();
            boton.setEnabled(false);

            String [] c = correoJugador.split("@");
            correoJugador = c[0];


            InsertarPartida(correoJugador,palabra,false);

            Intent itnt = new Intent(getApplicationContext(), Pop.class);
            itnt.putExtra("CORREO",correoJugador);
            itnt.putExtra("ESTADO",false);
            itnt.putExtra("IDIOMA", idioma);
            startActivity(itnt);

        }else{
            for (int y = 0; y < palabraElegida.size(); y++) {
                if(palabraAuxiliar.get(y).equals(palabraElegida.get(y))){
                    cont++;
                }
            }
            if(cont == palabraElegida.size()) {
                Toast.makeText(this, "HAS GANADO", Toast.LENGTH_SHORT).show();
                boton.setEnabled(false);

                String [] c = correoJugador.split("@");
                correoJugador = c[0];

                InsertarPartida(correoJugador,palabra,true);

                Intent itnt = new Intent(getApplicationContext(), Pop.class);
                itnt.putExtra("CORREO",correoJugador);
                itnt.putExtra("ESTADO",true);
                itnt.putExtra("IDIOMA", idioma);
                startActivity(itnt);
            }
        }
    }

    private void InsertarPartida(String nombreJugador, String palabraJugada, boolean estado) {

        if (db != null) {
            db.execSQL("INSERT INTO partidas (jugador, palabraJugada, acertada) VALUES ('" + nombreJugador + "', '" + palabraJugada + "','" + estado + "')");
        }

    }
}