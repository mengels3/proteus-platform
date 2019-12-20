// RF95-based client that transmit water measurements to our custom (RF95-based) Dragino-server.
// The code is based on: rf95_client.pde

#include <SPI.h>
#include <RH_RF95.h>

RH_RF95 rf95;

void setup() 
{
  Serial.begin(9600);
  while (!Serial) ;
  if (!rf95.init())
    Serial.println("init failed");
}

void loop()
{
  uint8_t depth_string[7];
  
  // read the water level:
  float depth = read_water_level();
  
  // send a message to the rf95_server:
  send_via_rf95(depth);;
  
  delay(400);
}

float read_water_level()
{
  float Voltage,Iamp = 0;
  const int Shunt_Res = 200;
  int Raw_value = 0;
  float Depth = 0.0;

  Raw_value = analogRead(A0);
   
  Voltage = (Raw_value * 5.0) / 1023.0;
  Iamp = (Voltage * 1000) / Shunt_Res;

  Depth = (Iamp-4) / 3.2;
  
  Serial.print("\n\nRaw = ");
  Serial.print(Raw_value);
  Serial.println("");
  Serial.print("Volatge = ");
  Serial.print(Voltage);
  Serial.println("V");
  Serial.print("Current = ");
  Serial.print(Iamp);
  Serial.println("mA");
  Serial.print("Depth = ");
  Serial.print(Depth);
  Serial.println("m");

  return Depth;
}

/*
 * Sends a given string via RF95.
 */
void send_via_rf95(float depth) {
  String s = String(depth);
  uint8_t data[s.length()];
  s.toCharArray(data, s.length());
  rf95.send(data, sizeof(data));
  
  rf95.waitPacketSent();
  
  uint8_t buf[RH_RF95_MAX_MESSAGE_LEN];
  uint8_t len = sizeof(buf);

  if (rf95.waitAvailableTimeout(3000))
  {
    if (rf95.recv(buf, &len))
   {
      Serial.print("got reply: ");
      Serial.println((char*)buf);
    }
    else
    {
      Serial.println("recv failed");
    }
  }
  else
  {
    Serial.println("No reply, is rf95_server running?");
  }
}