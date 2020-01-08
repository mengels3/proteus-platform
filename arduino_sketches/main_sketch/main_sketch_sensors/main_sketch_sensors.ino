#include <SoftwareSerial.h>
#define rx 2
#define tx 3
#include <OneWire.h>
#include<DallasTemperature.h>
#define ONE_WIRE_BUS 4
OneWire oneWire(ONE_WIRE_BUS);
SoftwareSerial myserial(rx, tx);
float pH;
float temp = 0.0;
String sensorstring = "";
boolean sensor_string_complete = false;
DallasTemperature sensors(&oneWire);
float Voltage,Iamp = 0;
const int Shunt_Res = 200;
int Raw_value = 0;
float Depth = 0.0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  myserial.begin(9600);
  sensorstring.reserve(30);
  sensors.begin();
}


void loop() {
  // put your main code here, to run repeatedly:
  
  if (myserial.available() > 0) {                     //if we see that the Atlas Scientific product has sent a character
    char inchar = (char)myserial.read();              //get the char we just received
    sensorstring += inchar;                           //add the char to the var called sensorstring
    if (inchar == '\r') {                             //if the incoming character is a <CR>
      sensor_string_complete = true;                  //set the flag
    }
  }

  if (sensor_string_complete == true) {
    Serial.println(sensorstring);
    sensorstring = "";
    sensor_string_complete = false;
    
    sensors.requestTemperatures(); 
    temp = sensors.getTempCByIndex(0);
    Serial.println(temp);

    Raw_value = analogRead(A0);
    Voltage = (Raw_value * 5.0) / 1023.0;
    Iamp = (Voltage * 1000) / Shunt_Res;
    Depth = (Iamp-4) / 3.2;
    Serial.println(Depth);
    Serial.println();
    
    delay(5000);
  }

}
