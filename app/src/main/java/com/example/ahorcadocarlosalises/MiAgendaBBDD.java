package com.example.ahorcadocarlosalises;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MiAgendaBBDD extends SQLiteOpenHelper {
	
	// En este caso la BBDD solo tiene una tabla, asi que solo creamos un sql de creacion�+
	// Si hubiese mas tablas, tendrianmos que crear todas las tablas y relaciones aqu�,
	
	String sqlCreacionJugadores ="CREATE TABLE jugadores (id integer primary key autoincrement, " +
			"correo text not null, contraseña text not null);";
	String sqlCreacionPartidas ="CREATE TABLE partidas (id integer primary key autoincrement, " +
			"jugador not null, palabraJugada text not null, acertada boolean not null);";
	public MiAgendaBBDD(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreacionJugadores);
		db.execSQL(sqlCreacionPartidas);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Nom�s s'executa quan la versi� de la BBDD amb la que cridem es superior a l'actual
		// S'hauria de fer una micraci� de dades. Nosaltres simplment esborrament les dades
		// antigues i crearem la nova estructura.
		
		db.execSQL("DROP TABLE IF EXISTS Agenda");
		
		db.execSQL(sqlCreacionJugadores);
		db.execSQL(sqlCreacionPartidas);
		// Tamb� �s podria fer: 
		// onCreate(db);
	}

}
