package com.example.appbasedatos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
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
        ContentValues values = new ContentValues(); //objeto para el insert
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO, "Don Giovani");
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_COMPOSITOR, "W.A. Mozart");
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR, 1787);
        // Insertamos los datos
        db.insert(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, null, values);


        // Borramos todas las filas excepto la primera porque hemos insertado más de la cuenta, una cada vez que iniciamos la app
        db.delete(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, "_ID>1", null);

        //Insertamos más datos con el método inserta()
        inserta("Le Nozze di Figaro", "W. A. Mozart", 1786);
        inserta("Giulio Cesare", "G. F. Haendel", 1724);
        inserta("Orlando Furioso", "A. Vivaldi", 1727);
        inserta("Montezuma", "C. H. Graun", 1755);
        inserta("Starira", "F. Cavalli", 1656);
        inserta("Griselda", "A. Sca rlatti", 1721);
        inserta("Piramo e Tisbe", "J. A. Hasse", 1768);
        inserta("Atenaide", " A. Vivaldi", 1728);
        inserta("Tolomeo", "G. F. Haendel", 1728);
        inserta("Armida", "J. Haydn", 1784);
        inserta("Armide", "C. W. Gluck", 1777);
        inserta("II Tutore Buriato", "V. Martin y Soler", 1775);
        inserta("Berenice", "G. F. Haendel", 1737);

        // Consulta para la base de datos.
        // La query es como hacer un SELST * FROM OPERAS;
        Cursor cursor = db.query("operas", null, null, null,
                null, null, null);

        // Hace lo mismo que la anterior, pero con el SQL directamente)
        // Cursor cursor = db.rawQuery("SELECT * FROM OPERAS", null);

        mostrarTabla(cursor);
        db.close();
    }

    //creamos un método para insertar filas
    private void inserta(String titulo, String compositor, int year) {
        ContentValues values = new ContentValues();
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO, titulo);
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_COMPOSITOR, compositor);
        values.put(EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR, year);
        db.insert(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, null, values);
    }

    // Metodo para mostrar la tabla. Es importante asegurarse de que la abla cabe donde la quiero mostrar.
    private void mostrarTabla(Cursor c) {
        // Los mostramos en el cuadro de texto que tenemos en el layout.
        txtTexto.append("\n Tabla operas \n-----------");
        c.moveToFirst();

        int nfilas = c.getCount();
        int ncolumnas = c.getColumnCount();
        String fila = "\n";

        for (int i = 0; i < nfilas; i++) { // Va fila a fila.
            fila = "\n";
            for (int j = 0; j < ncolumnas; j++) { // Va campo a campo.
                fila = fila + c.getString(j) + " ";
            }
            txtTexto.append(fila);
            c.moveToNext();
        }
    }

    public void buscar(View view) {
        // Hacemos que la BD sea
        db = helper.getReadableDatabase();

        // Datos para la query.
        String[] columns = {EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO, EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR};
        String selection = EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR + " > 1750";
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR;

        Cursor cursor = db.query(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, columns, selection, selectionArgs, groupBy, having, orderBy);

        // Limpia el textView. No lo ha pedido.
        txtTexto.setText("");

        //mostrar contenido
        mostrarTabla(cursor);

        db.close();
    }

    // Este no está asignado a ningún botón. Si lo ponemos en el onClick() de buscar, funciona bien.
    public void consultaYears(View view) {
        db = helper.getReadableDatabase();

        String[] columns = {EstructuraBD.EstructuraOperas.COLUMN_NAME_TITULO, EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR};
        String selection = EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR + " in (?,?)";
        String[] selectionArgs = {"1727", "1728"};
        String groupBy = null;
        String having = null;
        String orderBy = EstructuraBD.EstructuraOperas.COLUMN_NAME_YEAR;

        Cursor cursor = db.query(EstructuraBD.EstructuraOperas.TABLE_NAME_OPERAS, columns, selection, selectionArgs, groupBy, having, orderBy);

        // Limpia el textView. No lo ha pedido.
        txtTexto.setText("");
        //mostrar contenido
        mostrarTabla(cursor);

        db.close();
    }

    public void siguienteActividad(View view) {
        Intent i = new Intent(this, ListarOperas.class);
        startActivity(i);
    }
}