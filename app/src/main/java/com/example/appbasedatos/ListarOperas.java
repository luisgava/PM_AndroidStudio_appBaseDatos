package com.example.appbasedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ListarOperas extends AppCompatActivity {

    TextView txtTexto1, txtTexto2;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_operas);

        txtTexto1 = findViewById(R.id.textViewTitulo);
        txtTexto1 = findViewById(R.id.textViewCompositor);

    }
}