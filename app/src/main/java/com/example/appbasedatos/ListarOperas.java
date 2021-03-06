package com.example.appbasedatos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

public class ListarOperas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView txtTexto1, txtTexto2;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_operas);

        txtTexto1 = findViewById(R.id.textViewTitulo);
        txtTexto2 = findViewById(R.id.textViewCompositor);
        lv = findViewById(R.id.lstListaModif);

        consultaOperas();

        lv.setOnItemClickListener(this); // Necesita una interfaz, que luego exigirá sobreescribir un método, el OnItemClick. En él pondremos lo que queremos que haga al hacer click en un item de la lista.

    }

    private void consultaOperas() {
        helper = new SQLiteHelper(this);

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, null, null, null, null, null, null);

        //adaptamos el cursor a nuestro ListView
        String[] from = {EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO, EstructuraBD.EstructuraOperas.COLUMN_NAME_COMPOSITOR};
        int[] to = {R.id.textView3, R.id.textView4};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }

    // Hemos cambiado parent por listView en los parámetros.
    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
        // mostramos el contido del item en los cuadros de texto de la parte de arriba (título y compositor)
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);
        int _id = cursor.getInt(0);
        String titulo = cursor.getString(1);
        String compositor = cursor.getString(2);
        int year = cursor.getInt(3);

        // Mostramos el resultado
        txtTexto1.setText(_id + ", " + titulo);
        txtTexto2.setText(compositor + ", Estreno: " + year);
    }

    public void siguienteActividad(View view) {
        Intent i = new Intent(this, ModificarBD.class);
        startActivity(i);
    }

}