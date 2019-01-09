package com.example.juan.librosexamen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LibrosAdapter extends BaseAdapter{
    private ArrayList<Libros> libros;
    private Context contexto;
    private ConexionBBDD CBD;
    public LibrosAdapter(ArrayList<Libros> libros, Context contexto) {
        this.libros = libros;
        this.contexto = contexto;
        CBD = new ConexionBBDD(contexto);
    }

    public void setLibros(ArrayList<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public int getCount() {
        return libros.size();
    }

    @Override
    public Object getItem(int position) {
        return libros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflador = LayoutInflater.from(contexto);
        View vista = inflador.inflate(R.layout.list_view_layout, null);

        final Libros l = libros.get(position);
        TextView tvAutor, tvTitulo, tvEditorial, tvGenero;
        Button btnEliminar;

        tvAutor = vista.findViewById(R.id.tvAutor1);
        tvTitulo = vista.findViewById(R.id.tvTitulo1);
        tvEditorial = vista.findViewById(R.id.tvEditorial1);
        tvGenero = vista.findViewById(R.id.tvGenero1);
        btnEliminar = vista.findViewById(R.id.btnDelete);

        tvAutor.setText(l.getAutor());
        tvTitulo.setText(l.getTitulo());
        tvEditorial.setText(l.getEditorial());
        tvGenero.setText(l.getGenero());

        View.OnClickListener eliminar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDato(l.getAutor(),l.getTitulo(),l.getEditorial(),l.getGenero());
            }
        };
        vista.setBackgroundColor(colorGenero(l.getGenero()));
        btnEliminar.setOnClickListener(eliminar);
        return vista;
    }

    private int colorGenero(String genero) {
        int color = 0;
        switch (genero){
            case "Terror":
                color = Color.argb(50,161,144,144);
                break;
            case "Erotica":
                color = Color.argb(50,224,105,230);
                break;
            case "Ficción":
                color = Color.argb(75,62, 177, 223);
                break;
            case "Romántica":
                color = Color.argb(75,223, 62, 97);
                break;
            case "Historica":
                color = Color.argb(50,48, 54, 32);
                break;
            case "Biográfica":
                color = Color.argb(50,216, 204, 28);
                break;
            case "Autoayuda":
                color = Color.argb(50,237, 116, 17);
                break;
            case "Poesía":
                color = Color.argb(50,215, 56, 224);
                break;
            case "Infantil":
                color = Color.argb(50,64, 224, 56);
                break;
        }
        return color;
    }

    private void eliminarDato(final String autor, final String titulo, final String editorial, final String genero) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(contexto);
        constructor.setTitle("Eliminar");
        constructor.setMessage("¿Realmente quieres eliminar el contacto?");
        constructor.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CBD.eliminar(autor,titulo,editorial,genero);
                libros = CBD.getLibros();
                notifyDataSetChanged();
            }
        });
        constructor.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("CancelarBTN", "Se ha cancelado");
            }
        });
        AlertDialog alert = constructor.create();
        alert.show();
    }

}
