#include <SPI.h>
#include <LoRa.h>
float temp = 0.0;
float ph_value = 0.0;
float voltage,iamp = 0.0;
const int shunt_Res = 200;
int raw_value = 0;
float depth = 0.0;
String flat = "";
String flon = "";
int device_id=10010;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  if (!LoRa.begin(8681E5)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }
  LoRa.setSyncWord(0x34);
}


void loop(){
  // put your main code here, to run repeatedly
  Serial.println();
  flat = "50.928255";
  Serial.println(flat); 
  flon = "6.929152";  
  Serial.println(flon);
  ph_value = 7.1;
  temp = 20.2;
  depth = 27.5;
  Serial.println(ph_value);
  Serial.println(temp);
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
  LoRa.print(flon);
  LoRa.print(";lat=");
  LoRa.print(flat);
 // LoRa.print(counter);
  LoRa.endPacket();
  Serial.println("Sent Lora Msg");
  Serial.println();

  delay(10000);

}
