package com.easytrip.userservice.models;

import lombok.Data;
import java.util.List;

@Data
public class WeatherResponse {
    private Main main;
    private List<Weather> weather;

    @Data
    public static class Main {
        private double temp;
        private int humidity;
    }

    @Data
    public static class Weather {
        private String description;
    }
}
