package com.example.appbasedatos;

import android.provider.BaseColumns;

/**
 * Contiene las tablas, etc. La estructura de la BD
 **/

public class EstructuraBD {

    //Sentencia para crear la tabla
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + EstructuraOperas.TABLE_NAME_OPERAS + "("
            + EstructuraOperas._ID + " integer PRIMARY KEY, "
            + EstructuraOperas.COLUMN_NAME_TITULO + " text, "
            + EstructuraOperas.COLUMN_NAME_COMPOSITOR + " text, "
            + EstructuraOperas.COLUMN_NAME_YEAR + " integer);";

    //Sentencia para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + EstructuraOperas.TABLE_NAME_OPERAS;

    /**
     * Clase que define la estructura de la tabla operas
     **/
    public static class EstructuraOperas implements BaseColumns {
        public static final String TABLE_NAME_OPERAS = "operas";
        public static final String COLUMN_NAME_TITULO = "titulo";
        public static final String COLUMN_NAME_COMPOSITOR = "compositor";
        public static final String COLUMN_NAME_YEAR = "year";
    }

}
