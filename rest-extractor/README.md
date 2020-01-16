# rest-extractor
Spring Boot-based LoRa to ReST to DB service.

## How to build it
Build via [Apache Maven](https://maven.apache.org/):
```bash
$ mvn install
```

## How to test it (locally)
### Publish Sensor Data
```bash
$ mvn spring-boot:run
$ curl -d 'data=test12345' http://localhost:8080/publish
```

### View measurements and wells
Show all wells:
```bash
$ curl http://localhost:8080/well/
```

Show all measurements for a specific well:
```bash
$ curl http://localhost:8080/well/c3aca5f0-5612-4ff8-ba31-80f5153c5de9/measurements
```

### Insert a test well
Via Curl:
```bash
$ curl -H "Content-Type: application/json" -d '{"id":null,"deviceId":"10002","name":"New Well 02","latitude":54.777,
    "longtitude":65.223,"altitude":1.0,"maxDepth":2.0,"diameter":3.0,"sensorTypes":[{"id":null,"sensorTypeValue":"level"}]}' http://localhost:8080/well/
```

Via Postgres CLI:
```bash
=# insert into well (w_id, w_device_id, w_name, w_lat, w_long, w_altitude, w_maxdepth, w_diameter)
    values ('14db6581-d804-4b5b-b45f-247f6cc21b87', 10010, 'Bill Clinton', 33.11, 22.77, 0, 67, 2);
```
