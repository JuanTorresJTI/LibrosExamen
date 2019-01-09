package com.example.juan.librosexamen;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ConexionBBDD CBD;
    private ListView lvLibros;
    private FloatingActionButton fabLibros;
    private LibrosAdapter lvAdapter;
    private ArrayList<Libros> libros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CBD = new ConexionBBDD(this);
        libros = CBD.getLibros();
        lvLibros = findViewById(R.id.lvLibros);
        fabLibros = findViewById(R.id.fabAñadirContactos);
        lvAdapter = new LibrosAdapter(libros,this);

        lvLibros.setAdapter(lvAdapter);

        View.OnClickListener oyente = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirLibro();
            }
        };
        fabLibros.setOnClickListener(oyente);
    }

    private void anadirLibro() {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setTitle("Añadir Libro");
        constructor.setMessage("Añade un Libro");

        LayoutInflater inflador = LayoutInflater.from(this);
        View vista = inflador.inflate(R.layout.fab_layout, null);

        constructor.setView(vista);
        final EditText etTitulo, etAutor, etEditorial;
        final Spinner spGenero;

        etTitulo = vista.findViewById(R.id.etTitulo);
        etAutor = vista.findViewById(R.id.etAutor);
        etEditorial = vista.findViewById(R.id.etEditorial);
        spGenero = vista.findViewById(R.id.spGeneros);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.mispinner, generosLista());
        spGenero.setAdapter(adapter);


        constructor.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String titulo, autor, editorial, genero;
                titulo = etTitulo.getText().toString();
                autor = etAutor.getText().toString();
                editorial = etEditorial.getText().toString();
                genero = spGenero.getSelectedItem().toString();
                CBD.insercionBBDD(titulo, autor, editorial, genero);
                libros = CBD.getLibros();
                lvAdapter.setLibros(libros);
                lvAdapter.notifyDataSetChanged();
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

    private ArrayList<String> generosLista() {
        ArrayList<String> generos = new ArrayList<>();
        generos.add("Terror");
        generos.add("Erotica");
        generos.add("Ficción");
        generos.add("Romántica");
        generos.add("Historica");
        generos.add("Biográfica");
        generos.add("Autoayuda");
        generos.add("Poesía");
        generos.add("Infantil");
        return generos;
    }
}
