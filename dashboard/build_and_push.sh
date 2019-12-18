#!/bin/bash
if [ -z "$1" ]; then 
echo "Error! Usage please provide a versionnumber: build_and_push.sh 1.0"
exit 0
fi;
docker build . -t sedichile/dashboard:$1 -t sedichile/dashboard:latest
docker login
docker push sedichile/dashboard:$1
docker push sedichile/dashboard:latest
