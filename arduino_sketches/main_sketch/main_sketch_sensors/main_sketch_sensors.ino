#include <SoftwareSerial.h>
#define rx 2
#define tx 3
#include <OneWire.h>
#include<DallasTemperature.h>
#define ONE_WIRE_BUS 4
#include <SPI.h>
#include <LoRa.h>
OneWire oneWire(ONE_WIRE_BUS);
SoftwareSerial myserial(rx, tx);
float pH;
float temp = 0.0;
String ph_value = "";
boolean sensor_string_complete = false;
DallasTemperature sensors(&oneWire);
float voltage,iamp = 0;
const int shunt_Res = 200;
int raw_value = 0;
float depth = 0.0;
int device_id=10009;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  myserial.begin(9600);
  ph_value.reserve(30);
  sensors.begin();
  if (!LoRa.begin(8681E5)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }
  LoRa.setSyncWord(0x34);
}


void loop() {
  // put your main code here, to run repeatedly:
  
  if (myserial.available() > 0) {                     //if we see that the Atlas Scientific product has sent a character
    char inchar = (char)myserial.read();              //get the char we just received
    ph_value += inchar;                           //add the char to the var called sensorstring
    if (inchar == '\r') {                             //if the incoming character is a <CR>
      sensor_string_complete = true;                  //set the flag
    }
  }

  if (sensor_string_complete == true) {
    Serial.println(sensorstring);
    ph_value = "";
    sensor_string_complete = false;
    
    sensors.requestTemperatures(); 
    temp = sensors.getTempCByIndex(0);
    Serial.println(temp);

    Raw_value = analogRead(A0);
    voltage = (raw_value * 5.0) / 1023.0;
    iamp = (voltage * 1000) / Shunt_Res;
    depth = (iamp-4) / 3.2;
    Serial.println(depth);
    Serial.println();

    LoRa.beginPacket();
    LoRa.print("<");
    LoRa.print(device_id);
    LoRa.print(">level=");
    LoRa.print(depth);
    LoRa.print("&temp=");
    LoRa.print(temp); 
    LoRa.print("&ph=");
    LoRa.print(ph_value); 
   // LoRa.print(counter);
    LoRa.endPacket();
    
    delay(5000);
  }

}
