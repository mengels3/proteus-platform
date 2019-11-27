package org.uni.cgn;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

@Component
public class SerialReader {

    static SerialPort comPort;
    static String stringBuffer;
    static InfluxDB influxDB;
    
    public SerialReader() {
    	influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "admin", "admin");
    	String dbName = "well";
    	influxDB.query(new Query("CREATE DATABASE " + dbName));
    	influxDB.setDatabase(dbName);
    }

    private static final class DataListener implements SerialPortDataListener
    {
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                return;
            byte[] newData = new byte[comPort.bytesAvailable()];
            int numRead = comPort.readBytes(newData, newData.length);
            stringBuffer = new String(newData,0,numRead);
            System.out.println(stringBuffer);
            if(stringBuffer.startsWith("got")) {
            	float depths = 10;
            	try {
            	String[] split = stringBuffer.split(":");
            	String number = split[1].replaceAll(" ", "");
            	depths = Float.parseFloat(number);
            	}catch(Exception e) {
            		e.printStackTrace();
            	}
            	Point point = Point.measurement("well")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("water_level", depths)
                        .addField("id", "uno 1")
                        .tag("id", "uno1")
                        .build();
    			influxDB.write(point);
            }
            

            


        }
    }
    @PostConstruct
    public void start_listen()
    {
        comPort = SerialPort.getCommPorts()[2];
        comPort.openPort();
        System.out.println("COM port open: " + comPort.getDescriptivePortName());
        DataListener listener = new DataListener();
        comPort.addDataListener(listener);
        System.out.println("Event Listener open.");
    }
}
