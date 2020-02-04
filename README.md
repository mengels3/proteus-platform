# Proteus Platform
An environmental monitoring platform based on IoT-sensor networks.

## Motivation
A platform and hardware artifact that aims at helping to tackle the ongoing fresh water crisis in rural parts of Chile. Was build by a team of graduatae students during the 2019/2020 semester's course on [Systems Engineering for Digital Innovations](https://www.is4.uni-koeln.de/de/teaching/master-modules/systems-engineering-for-digital-innovations/) at University of Cologne.

## Components
### arduino_skeches
The sketch used to gather certain metrics from wells:
* temperature
* pH value
* level

Compatible with the [Arduino Uno Rev3](https://store.arduino.cc/arduino-uno-rev3).

### Dashboard
A user-friendly dashboard to visualize data and predictions.

### DB
Docker files for the default database.

### Rest-Extractor
The API that receives all the environment data and persists it. See [here](rest-extractor/README.md) for build instructions.

### Scripts
Contains a sample script that runs on a [Dragino LG01-S LoRa Gateway](https://www.dragino.com/products/lora/item/119-lg01-s.html) and is used to transfer measurement data collected from all Arduinos nearby to the Proteus API.

### well-admin-dashboard
An admin dashboard to manage master data on wells. See [here](well-admin-dashboard/README.md) for build instructions.
