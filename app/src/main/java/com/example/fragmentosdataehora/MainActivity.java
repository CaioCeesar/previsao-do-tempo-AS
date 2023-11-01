package com.example.fragmentosdataehora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText et_descricao;
    Button bt_ok, bt_data, bt_hora, bt_hoje, bt_outra_data;
    TextView tv_consulta;
    String data, horas, outra_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_descricao = (EditText) findViewById(R.id.et_descricao);
        bt_ok = (Button) findViewById(R.id.bt_ok);
        bt_data = (Button) findViewById(R.id.bt_data);
        bt_hora = (Button) findViewById(R.id.bt_hora);
        bt_hoje = (Button) findViewById(R.id.bt_hoje);
        bt_outra_data = (Button) findViewById(R.id.bt_outra_data);
        tv_consulta = (TextView) findViewById(R.id.tv_consulta);

        bt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        bt_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });
        bt_outra_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateConsulta();
            }
        });
        bt_hoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHoje();
            }
        });
    }

    public void ok(View v){
        String st_descricao, st_data, st_hora;
        st_descricao = et_descricao.getText().toString();
        st_data = data;
        st_hora = horas;

        Log.i("Novo Compromisso", "Nome: " + st_descricao + " - Data: " + data + " - Hora: " + st_hora);

        bt_hora.setText("Hora");
        bt_data.setText("Data");
        et_descricao.setText(null);
    }

    public void mostraDialogoTimePicker(View v) {
        DialogFragment newFragment = new FragmentoTimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void mostraDialogoDatePicker(View v) {
        DialogFragment newFragment = new FragmentoDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void selectTime() {
        final Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String horaSelecionada = String.format("%02d:%02d", hourOfDay, minute);
                        bt_hora.setText(horaSelecionada);
                        horas = horaSelecionada;
                    }
                }, hora, minuto, true);
        timePickerDialog.show();
    }

    public void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dataSelecionada = day + "/" + (month + 1) + "/" + year;
                        bt_data.setText(dataSelecionada);
                        data = dataSelecionada;
                    }
                }, ano, mes, dia);
        datePickerDialog.show();
    }
    public void selectDateConsulta() {
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dataSelecionada = day + "/" + (month + 1) + "/" + year;
                        bt_outra_data.setText(dataSelecionada);
                        outra_data = dataSelecionada;
                        tv_consulta.setText(dataSelecionada);
                    }
                }, ano, mes, dia);
        datePickerDialog.show();
    }

    public void selectHoje() {
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        String dataSelecionada = dia + "/" + (mes + 1) + "/" + ano;
        tv_consulta.setText(dataSelecionada);
        bt_outra_data.setText("Outra data");
    }
}