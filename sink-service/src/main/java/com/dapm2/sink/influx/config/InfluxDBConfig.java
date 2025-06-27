package com.dapm2.sink.influx.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

public class InfluxDBConfig {
    public static final String INFLUX_URL = "http://localhost:5086";
    public static final String INFLUX_TOKEN = "dapm_token";
    public static final String INFLUX_ORG = "dapm";
    public static final String INFLUX_BUCKET = "dapm_bucket";

    public static InfluxDBClient createClient() {
        return InfluxDBClientFactory.create(
                INFLUX_URL, INFLUX_TOKEN.toCharArray(), INFLUX_ORG, INFLUX_BUCKET
        );
    }
}