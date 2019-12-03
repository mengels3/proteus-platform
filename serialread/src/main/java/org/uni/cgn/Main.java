package org.uni.cgn;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

public class Main {

	public static void main(String[] args) {
    	InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "admin", "admin");
    	String dbName = "well";
    	influxDB.query(new Query("CREATE DATABASE " + dbName));
    	influxDB.setDatabase(dbName);
		Point point = Point.measurement("well")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("water_level", 0.8)
                .addField("id", "uno 1")
                .tag("id", "uno1")
                .build();
		influxDB.write(point);
		Point point1 = Point.measurement("well")
                .time(System.currentTimeMillis()+20000, TimeUnit.MILLISECONDS)
                .addField("water_level", 0.9)
                .addField("id", "uno 1")
                .tag("id", "uno1")
                .build();
		influxDB.write(point1);

	}

}
