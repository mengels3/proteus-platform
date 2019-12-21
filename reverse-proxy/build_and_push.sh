#!/bin/bash
if [ -z "$1" ]; then 
echo "Error! Usage please provide a versionnumber: build_and_push.sh 1.0"
exit 0
fi;
docker build . -t sedichile/rp:$1 -t sedichile/rp:latest
docker login
docker push sedichile/rp:$1
docker push sedichile/rp:latest
