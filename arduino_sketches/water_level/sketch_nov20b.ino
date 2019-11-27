float Voltage,Iamp = 0;
const int Shunt_Res = 200;
int Raw_value = 0;
float Depth = 0.0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  Raw_value = analogRead(A0);
   
  Voltage = (Raw_value * 5.0) / 1023.0;
  Iamp = (Voltage * 1000) / Shunt_Res;

  Depth = (Iamp-4) / 3.2;
  
  Serial.print("Raw = ");
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
  delay(1000);
}
