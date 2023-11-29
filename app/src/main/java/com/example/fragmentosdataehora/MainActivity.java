package com.example.fragmentosdataehora;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText et_nome_da_cidade, et_latitude, et_longitude;
    Button bt_add_cidade;
    TextView tv_consulta;
    Cursor cursor;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ApiService apiService = retrofit.create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nome_da_cidade = (EditText) findViewById(R.id.et_nome_da_cidade);
        et_latitude = (EditText) findViewById(R.id.et_lat);
        et_longitude = (EditText) findViewById(R.id.et_lon);
        bt_add_cidade = (Button) findViewById(R.id.bt_add_cidade);
        tv_consulta = (TextView) findViewById(R.id.tv_consulta);

        database.openDB(this);
        database.createTable(this);
        database.closeDB();
        getApi();
    }

    public void getApi() {
        cursor = database.getCidades(this);

        tv_consulta.setText("");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(cursor.getColumnIndex("nome"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));

                Call<ResponseBody> call = apiService.getDados(
                        latitude.replaceAll("\\s", ""),
                        longitude.replaceAll("\\s", ""),
                        "temperature_2m,rain",
                        "1");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String responseData = response.body().string();
                                Log.d("API Response", responseData);
                                // Processar a resposta aqui conforme necessário
                                Gson gson = new Gson();
                                WeatherData weatherData = gson.fromJson(responseData, WeatherData.class);

                                // Usar os métodos para obter as médias
                                String mediaTemperatura = weatherData.calcularMediaTemperatura();
                                String mediaChuva = weatherData.calcularMediaChuva();
                                tv_consulta.append(
                                nome + " (" + latitude + ", " + longitude + ")\n" +
                                "Temperatura média: " + mediaTemperatura +
                                "\nChance de chuva (dia todo): " +  mediaChuva +
                                "%\n>----------------------------------------------<\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("API Error", "Erro na resposta da API: " + response.code());
                            // Lidar com erros aqui
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("API Failure", "Falha na chamada da API", t);
                        // Lidar com falhas na chamada aqui
                    }
                });

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public void add(View v){
        Log.i("asd", "aaa");
        String st_nome_da_cidade, st_latitude, st_longitude;
        st_nome_da_cidade = et_nome_da_cidade.getText().toString();
        st_latitude = et_latitude.getText().toString();
        st_longitude = et_longitude.getText().toString();
        database.addCidade(st_nome_da_cidade, st_latitude, st_longitude, this);
        getApi();
        et_nome_da_cidade.setText(null);
        et_latitude.setText(null);
        et_longitude.setText(null);
    }

    public void deletarCidades(View V) {
        database.deletarCidades(this);
        tv_consulta.setText("");
    }

    public void showCidades() {
        cursor = database.getCidades(this);
        Log.i("QueryLog", "Número de registros retornados fora: " + cursor.getCount());
        tv_consulta.setText("");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(cursor.getColumnIndex("nome"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));

                Log.i("Novo Compromisso",nome + " " + latitude + " - " + longitude);
                tv_consulta.append(nome + " " + latitude + " - " + longitude + '\n');
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}