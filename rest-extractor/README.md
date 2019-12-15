# rest-extractor
Spring Boot-based LoRa to ReST to DB service.

## How to build it
Build via [Apache Maven](https://maven.apache.org/):
```bash
$ mvn install
```

## How to test it (locally)
```bash
$ mvn spring-boot:run
$ curl -d 'data=test12345' http://localhost:8080/publish
```

## How to deploy it
Log in to Heroku and set the Heroku Git remote:
```bash
$ heroku login
$ heroku git:remote -a w3ll-qtt
```

Then deploy via Maven and test with Curl:
```bash
$ mvn heroku:deploy
$ curl -k -d 'data=test12345' https://w3ll-qtt.herokuapp.com/publish
```

## Misc
### Hwo to watch the remote logs?
```bash
$ heroku logs --tail
```
