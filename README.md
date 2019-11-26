# sustainabiliteam-code
The code that makes our water measuring and monitoring solution work!

## HowTo
### Sensors
TODO

### Lora
#### Gateway
See [here](https://wiki.dragino.com/index.php?title=Connect_to_TTN#Create_Single_Channel_Gateway_By_LG01). Steps:
* Connect the LG01's WAN port with your local router
* connect your notebook to the new WLAN network (e.g. "dragino-1d1e14")

> TODO: the f*cking WLAN hot spot disappears after a couple of secs...

#### Client
For the Uno-based Dragino shield see [here](https://wiki.dragino.com/index.php?title=Connect_to_TTN#Use_LoRa_Shield_and_Arduino_as_LoRa_End_Device). Steps:
* Download [arduino-lmic ](https://github.com/matthijskooijman/arduino-lmic) as a Zip file
* Unpack and copy it to your arduino/libraries folder

> Fix a compile error (in the current master) by adding a "static" at the beginning of line 231 in the
> (unpacked) file libraries/arduino-lmic-master/src/lmic/oslmic.h!
> See [this](https://github.com/matthijskooijman/arduino-lmic/issues/243) GitHub issue!

* Log into TTN and create a device
* Set Activation Method=OTAA and copy the _Device EUI_ and the _App EUI_
* Open the Arduino IDE, choose the LMIC OTAA example
* and paste the EUIs into the source code
* Copy our SEDI app key from [here](https://console.thethingsnetwork.org/applications/sedi/devices/uno1)
* and paste it into the source code as well

When trying ABP as activation method, copy
* Network Session Key, the App Session Key
* as well as the Device Address into the source code

> The EUIDs need to be pasted byte-wise! And check the endianess of the pasted values!

> See our [code](arduino_sketches/lmic_ttn_otaa_client/lmic_ttn_otaa_client.ino) for a complete sketch!

> Do *not* commit the application's key or the device's session key! Commit *only* Semtech's dunmy key!

To upload,
* choose Tools -> Board -> "Arduino/Genuino Uno"
* and Tools -> Programmer -> "AVRISP mkII"
* as well as the proper USB port
* Then click on the upload button: ez as that!

#### Library/Sketch overview
There are a couple of libraries (and sketches for each of them) for Arduino and Lora. This is what has worked for us (and did not) so far:

Sketch               | Library/Driver   | Compiles for Dragino? | ...uploads? | What works?                               | What does not work?      | Act. Method
-------------------- | ---------------- | --------------------- | ----------- | ----------------------------------------- | ------------------------ | -----------
rf95_lora_client     | RadioHead (RF95) | yes                   | yes         | Send "Hello" from one Dragino to another! | Sending packets to TTN   | ?
lmic_ttn_otaa_client | LMIC             | yes                   | yes         | -                                         | Sending anything at all  | OTAA + ABP
dragino_lmic_client  | LMIC             | yes                   | yes         | It at least sends smth. somehow!          | TTN doesnt receive pckts | ABP
TTN example sketch   | ?                | NO                    | NO          | -                                         | Compiling / uploading    | ?
