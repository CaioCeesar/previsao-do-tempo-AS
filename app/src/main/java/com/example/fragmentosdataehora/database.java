package com.example.fragmentosdataehora;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;//Banco de Dados
import android.database.Cursor;//Navegar entre os registros
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import static android.content.Context.MODE_PRIVATE;

public class database {
    static SQLiteDatabase db=null;
    static Cursor cursor;

    public static void openDB(Activity act){
        try{
            ContextWrapper cw= new ContextWrapper(act);
            db = cw.openOrCreateDatabase("previsaoDoTempo", MODE_PRIVATE, null);
        } catch (Exception ex) {
            Log.i("Erro", "Abrindo o banco de dados");
        }
    }

    public static void createTable(Activity act){
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS  cidades(id INTEGER PRIMARY KEY, nome TEXT, latitude TEXT, longitude TEXT);");
        } catch (Exception ex) {
            Log.i("Erro", "Criando a tabela");
        }
    }

    public static void closeDB(){
        db.close();
    }

    public static void addCidade(String nome, String latitude, String longitude, Activity act){
        openDB(act);
        try {
            db.execSQL("INSERT INTO cidades(nome, latitude, longitude) VALUES('" + nome + " ',' " + latitude + " ',' " + longitude + "')");
        } catch (Exception exp) {
            Log.i("Erro", "Deletando cidades");
        }
        closeDB();
    }

    public static void deletarCidades(Activity act){
        openDB(act);
        try {
            db.execSQL("DELETE FROM cidades");
        } catch (Exception exp) {
            Log.i("Erro", "Deletando cidades");
        }
        closeDB();
    }


    public static Cursor getCidades(Activity act) {
        closeDB();
        openDB(act);
        Log.i("QueryLog", "bbb");
        cursor= db.query( "cidades",
                new String[]{"nome", "latitude", "longitude"},
                null,
                null,
                null,
                null,
                null,
                null
        );
        Log.i("QueryLog", "Número de registros retornados: " + cursor.getCount());
        return cursor;
    }
}
