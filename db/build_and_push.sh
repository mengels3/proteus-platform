#!/bin/bash
if [ -z "$1" ]; then 
echo "Error! Usage please provide a versionnumber: build_and_push.sh 1.0"
exit 0
fi;
echo "$1" > version.txt
docker build . -t sedichile/database:$1 -t sedichile/database:latest
docker login
docker push sedichile/database:$1
docker push sedichile/database:latest

