package com.example.appbasedatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

public class ModificarBD extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView txtTexto1, txtTexto2, txtTexto3;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    String tituloModificado;
    String compositorModificado;
    int yearModificado;
    int _idCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_b_d);

        txtTexto1 = findViewById(R.id.editTextTitulo2);
        txtTexto2 = findViewById(R.id.editTextCompositor2);
        txtTexto3 = findViewById(R.id.editTextYear);
        lv = findViewById(R.id.lstListaModif2);

        consultaOperas();

        lv.setOnItemClickListener(this); /* Necesita una interfaz, que luego
        exigirá sobreescribir un método, el OnItemClick. En él pondremos lo
        que queremos que haga al hacer click en un item de la lista.*/

    }

    private void consultaOperas() {
        helper = new SQLiteHelper(this);

        db = helper.getReadableDatabase();
        Cursor cursor =
                db.query(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS,
                        null, null,
                        null, null, null, null);

        //adaptamos el cursor a nuestro ListView con un SimpleCursorAdapter
        String[] from = {EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO,
                EstructuraBD.EstructuraOperas.COLUMN_NAME_COMPOSITOR,
                EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR};
        int[] to = {R.id.textViewlista2Titulo, R.id.textViewlista2Compositor,
                R.id.textViewlista2Year};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,
                R.layout.lista2, cursor,
                from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }

    // Hemos cambiado parent por listView en los parámetros.
    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position,
                            long id) {
        // mostramos el contido del item en los cuadros de texto de la parte
        // de arriba (título y compositor)
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);
        int _id = cursor.getInt(0);
        String titulo = cursor.getString(1);
        String compositor = cursor.getString(2);
        int year = cursor.getInt(3);

        // Mostramos el resultado
        txtTexto1.setText(_id + ", " + titulo);
        txtTexto2.setText(compositor);
        txtTexto3.setText(String.valueOf(year));


        _idCursor = _id;

    }

    public void modificarDB(View view) {
        db = helper.getReadableDatabase();

        tituloModificado = txtTexto1.getText().toString();
        compositorModificado = txtTexto2.getText().toString();
        yearModificado = Integer.parseInt(txtTexto3.getText().toString());

        ContentValues values = new ContentValues();
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO,
                tituloModificado);
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_COMPOSITOR,
                compositorModificado);
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR,
                yearModificado);

        String selection = EstructuraBD.EstructuraOperas._ID + "=?";
        String[] selectionArgs = {String.valueOf(_idCursor)};

        int filasModificadas =
                db.update(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS,
                        values, selection, selectionArgs);

        consultaOperas();

        if (filasModificadas != 0) {
            Toast.makeText(getApplicationContext(),
                    "Se han modificado " + filasModificadas + " filas",
                    Toast.LENGTH_LONG);
        } else {
            Toast.makeText(getApplicationContext(), "No se ha modificado nada", Toast.LENGTH_SHORT);
        }

        db.close();
    }
}