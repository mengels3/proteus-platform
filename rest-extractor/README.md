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

### View Measurements for Well
```bash
$ curl http://localhost:8080/well/
[
  {
    "id": 1,
    "name": "New Well 01",
    "latitude": "51° 28′ 38″ N",
    "longtitude": "0°",
    "altitude": 0.0,
    "maxDepth": 0.0,
    "diameter": 0.0,
    "sensorTypes": [
      {
        "id": 1,
        "sensorTypeValue": "PHVALUE"
      },
      {
        "id": 2,
        "sensorTypeValue": "TEMPERATURE"
      }
    ],
    "measurements": [
      {
        "id": 1,
        "timestamp": "2020-01-06T19:28:00.777650Z",
        "value": "3.3",
        "sensorType": {
          "id": 1,
          "sensorTypeValue": "PHVALUE"
        }
      },
      {
        "id": 2,
        "timestamp": "2020-01-06T19:28:00.777650Z",
        "value": "77.0",
        "sensorType": {
          "id": 2,
          "sensorTypeValue": "TEMPERATURE"
        }
      }
    ]
  }
]
```
