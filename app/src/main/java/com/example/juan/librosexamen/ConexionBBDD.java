package com.example.juan.librosexamen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ConexionBBDD {
    private SQLiteOpenHelper sq;
    private Context c;

    public ConexionBBDD(Context c) {
        this.c = c;
        conexionConBBDD();
    }

    private void conexionConBBDD() {
        sq = new SQLiteOpenHelper(c, "Libros", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String query = "CREATE TABLE libros (titulo varchar(100), autor varchar(100), editorial varchar(100),genero varchar(100))";
                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
    }

    public void insercionBBDD(String titulo, String autor, String editorial, String genero) {
        SQLiteDatabase bd = sq.getWritableDatabase();
        String insercion = "INSERT INTO libros VALUES('" + titulo + "','" + autor + "','" + editorial + "','" + genero + "');";
        bd.execSQL(insercion);
    }

    public ArrayList<Libros> getLibros() {
        ArrayList<Libros> libros = new ArrayList<>();
        String select = "SELECT * FROM libros;";
        SQLiteDatabase bd = sq.getWritableDatabase();
        Cursor c = bd.rawQuery(select, null);

        while (c.moveToNext()){
            libros.add(new Libros(c.getString(0),c.getString(1),c.getString(2),c.getString(3)));
        }
        return libros;
    }

    public void eliminar(String titulo, String autor, String editorial, String genero) {
        SQLiteDatabase bd = sq.getWritableDatabase();
        String eliminar = "DELETE FROM libros WHERE titulo = '" + titulo + "' AND autor = '" + autor + "' AND editorial = '" + editorial + "' AND genero = '" + genero + "';";
        bd.execSQL(eliminar);
    }
}
