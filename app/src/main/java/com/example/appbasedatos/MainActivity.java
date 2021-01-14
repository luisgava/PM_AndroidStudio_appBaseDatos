package com.example.appbasedatos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtTexto;
    SQLiteDatabase db;
    SQLiteHelper helper;
    EditText edtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTexto = findViewById(R.id.txtValor);

        //Para que el textview tenga scroll
        txtTexto.setMovementMethod(new ScrollingMovementMethod());

        edtTitulo = findViewById((R.id.edtBusqueda));

        helper = new SQLiteHelper(this);

        // Habilitamos la bd para poder escribir
        db = helper.getWritableDatabase();

        //Creamos los datos de la tabla
        ContentValues values = new ContentValues();
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO, "Don Giovani");
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_COMPOSITOR, "W.A. Mozart");
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR, 1787);
        // Insertamos los datos
        db.insert(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, null, values);


        // Borramos todas las filas excepto la primera porque hemos insertado más de la cuenta, una cada vez que iniciamos la app
        db.delete(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, "_ID>1", null);

        //Insertamos más datos con el método inserta()

    }
}