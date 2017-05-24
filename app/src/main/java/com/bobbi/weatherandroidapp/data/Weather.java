package com.bobbi.weatherandroidapp.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

@Data
public class Weather {
    private Coord coord;
    private List<WeatherDetail> weather = new ArrayList<>();
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Integer dt;
    private Sys sys;
    private Integer id;
    private String name;
    private Integer cod;

    @Data
    public static class Coord {
        private Double lat;
        private Double lon;

        public Coord() {
        }

        public Coord(Double lat, Double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }

    @Data
    public static class WeatherDetail {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Main {
        private Double temp;
        private Double pressure;
        private Integer humidity;
        @JsonProperty("temp_min")
        private Double tempMin;
        @JsonProperty("temp_max")
        private Double tempMax;
    }

    @Data
    public static class Wind {
        private Double speed;
        private Double deg;
    }

    @Data
    public static class Clouds {
        private Integer all;
    }

    @Data
    public class Sys {
        private Integer type;
        private Integer id;
        private Double message;
        private String country;
        private Integer sunrise;
        private Integer sunset;
    }
}
