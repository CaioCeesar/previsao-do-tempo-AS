package com.example.fragmentosdataehora;

import java.text.DecimalFormat;
import java.util.List;

public class WeatherData {
    private double latitude;
    private double longitude;
    private double generationTimeMs;
    private int utcOffsetSeconds;
    private String timezone;
    private String timezoneAbbreviation;
    private double elevation;
    private HourlyUnits hourlyUnits;
    private HourlyData hourly;

    // Getters e Setters (não mostrados para brevidade)

    public static class HourlyUnits {
        private String time;
        private String temperature_2m;
        private String rain;

        // Getters e Setters (não mostrados para brevidade)
    }

    public static class HourlyData {
        private List<String> time;
        private List<Double> temperature_2m;
        private List<Double> rain;

        public List<Double> getTemperature_2m() {
            return temperature_2m;
        }

        public List<Double> getRain() {
            return rain;
        }

        // Getters e Setters (não mostrados para brevidade)
    }

    // Método para calcular a média dos valores em uma lista
    private static double calcularMedia(List<Double> valores) {
        if (valores == null || valores.isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;
        for (Double valor : valores) {
            soma += valor;
        }

        return soma / valores.size();
    }

    // Método para calcular a média dos valores de temperature_2m
    public String calcularMediaTemperatura() {
        double media = calcularMedia(hourly.getTemperature_2m());
        return formatarParaDuasCasasDecimais(media);
    }

    // Método para calcular a média dos valores de rain
    public String calcularMediaChuva() {
        double media = calcularMedia(hourly.getRain());
        return formatarParaDuasCasasDecimais(media);
    }

    // Método para formatar um valor para duas casas decimais
    private String formatarParaDuasCasasDecimais(double valor) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(valor);
    }
}

