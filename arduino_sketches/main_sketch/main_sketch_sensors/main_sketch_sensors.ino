#include <SoftwareSerial.h>
#define rx 5
#define tx 6
#include <OneWire.h>
#include<DallasTemperature.h>
#define ONE_WIRE_BUS 4
#include <SPI.h>
#include <LoRa.h>
#include <SoftwareSerial.h>
#include <TinyGPS.h>
OneWire oneWire(ONE_WIRE_BUS);
TinyGPS gps;
SoftwareSerial gpsserial(10, 11);
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

static String print_float(float val, float invalid, int len, int prec);
static void smartdelay(unsigned long ms);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  ph_value.reserve(30);
  sensors.begin();
  while (!Serial) {
     ;
  };
  gpsserial.begin(9600);
  myserial.begin(9600);
  if (!LoRa.begin(8681E5)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }
  LoRa.setSyncWord(0x34);
}


void loop() {
  // put your main code here, to run repeatedly
  
  if (myserial.available() > 0) {                     //if we see that the Atlas Scientific product has sent a character
    char inchar = (char)myserial.read();              //get the char we just received
    ph_value += inchar;                           //add the char to the var called sensorstring
    if (inchar == '\r') {                             //if the incoming character is a <CR>
      sensor_string_complete = true;                  //set the flag
    }
  }

  if (sensor_string_complete == true) {

    Serial.println("Read Sensors");
    String lat = "";
    String lon = "";
    float flon, flat;
    unsigned long age = 0;
    gps.f_get_position(&flat, &flon, &age);
    lat = print_float(flat, TinyGPS::GPS_INVALID_F_ANGLE, 10, 6);
    lon = print_float(flon, TinyGPS::GPS_INVALID_F_ANGLE, 11, 6);
    if (lat ==  "False"){
      lat = "50.916766";
      Serial.print("Static GPS lat: ");
      Serial.println(lat);
    }
    else {
      Serial.print("GPS lat: ");
      Serial.println(lat);
    }
    if (lon == "False"){
      lon = "6.941198";
      Serial.print("Static GPS lon: ");
      Serial.println(lon);
    }
    else {
      Serial.print("GPS lon: ");
      Serial.println(lat);
    }

    Serial.print("PH Value: ");
    Serial.println(ph_value);
    
    sensors.requestTemperatures(); 
    temp = sensors.getTempCByIndex(0);
    Serial.print("Temperature: ");
    Serial.println(temp);

    raw_value = analogRead(A0);
    voltage = (raw_value * 5.0) / 1023.0;
    iamp = (voltage * 1000) / shunt_Res;
    depth = (iamp-4) / 3.2;
    Serial.print("Waterlevel: ");
    Serial.println(depth);

    LoRa.beginPacket();
    LoRa.print("<");
    LoRa.print(device_id);
    LoRa.print(">device_id=");
    LoRa.print(device_id);
    LoRa.print(";level=");
    LoRa.print(depth);
    LoRa.print(";temp=");
    LoRa.print(temp); 
    LoRa.print(";ph=");
    LoRa.print(ph_value);
    LoRa.print(";long=");
    LoRa.print(lon);
    LoRa.print(";lat=");
    LoRa.print(lat);
   // LoRa.print(counter);
    LoRa.endPacket();
    Serial.println("Sent Lora Msg");
    Serial.println();

    ph_value = "";
    //delay(1000);
    sensor_string_complete = false;
    smartdelay(10000);
  }

}

static String print_float(float val, float invalid, int len, int prec)
{
  if (val == invalid)
  {
    while (len-- > 1)
      Serial.print('*');
    Serial.print(' ');
    return "False";
  }
  else
  {
    String ret = String(val);
    Serial.print(val, prec);
    int vi = abs((int)val);
    int flen = prec + (val < 0.0 ? 2 : 1); // . and -
    flen += vi >= 1000 ? 4 : vi >= 100 ? 3 : vi >= 10 ? 2 : 1;
    for (int i=flen; i<len; ++i)
      Serial.print(' ');
     return ret;
  }
  smartdelay(0);
}


static void smartdelay(unsigned long ms)
{
  unsigned long start = millis();
  do 
  {
    while (gpsserial.available())
    {
      //ss.print(Serial.read());
      gps.encode(gpsserial.read());
    }
  } while (millis() - start < ms);
}
