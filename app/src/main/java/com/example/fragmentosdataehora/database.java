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
            db = cw.openOrCreateDatabase("agenda", MODE_PRIVATE, null);
        } catch (Exception ex) {
            Log.i("Erro", "Abrindo o banco de dados");
        }
    }

    public static void createTable(Activity act){
        try {
            db.execSQL("DROP TABLE compromissos;");
            db.execSQL("CREATE TABLE IF NOT EXISTS  compromissos(id INTEGER PRIMARY KEY, descricao TEXT, data DATE, hora TIME);");
        } catch (Exception ex) {
            Log.i("Erro", "Criando a tabela");
        }
    }

    public static void closeDB(){
        db.close();
    }

    public static void addCompromisso(String descricao, String data, String hora, Activity act){
        openDB(act);
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yy/MM/dd");
            String dataF = "";
            try {
                Date date = sdfInput.parse(data);
                dataF = sdfOutput.format(date);
            } catch (ParseException e){
                e.printStackTrace();
            }
            Log.i("a", dataF);
            db.execSQL("INSERT INTO compromissos(descricao, data, hora) VALUES('" + descricao + "','" + dataF + " ',' " + hora + "')");
        } catch (Exception exp) {
            Log.i("Erro", "Adicionando compromisso");
        }
        closeDB();
    }


    public static Cursor getCompromissos(Activity act, String data) {
        closeDB();
        openDB(act);
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("yy/MM/dd");
        String dataF = "";
        try {
            Date date = sdfInput.parse(data);
            dataF = sdfOutput.format(date);
        } catch (ParseException e){
            e.printStackTrace();
        }
        Log.i("a", data + " - " + dataF);
//        cursor= db.query( "compromissos",
//                new String[]{"descricao", "data", "hora"},
//                null,
//                null,
//                null,
//                null,
//                "data ASC",
//                null
//        );
        cursor= db.query(
                "compromissos",
                new String[] {"descricao", "data", "hora"},
                "data = ?",
                new String[] {dataF},
                null,
                null,
                null);
        Log.i("QueryLog", "Número de registros retornados: " + cursor.getCount());
        return cursor;
    }
}
