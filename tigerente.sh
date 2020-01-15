#!/bin/sh

file=`uci get customized_script.general.para1`
#logger "[IoT]: Store Sensor Data to "

CHECK_INTERVAL=5


old=`date +%s`

echo "Tigerente 1" >> /tmp/tigerente.log

#Run Forever
while [ 1 ]
do
        now=`date +%s`
        # Check if there is sensor data arrive at /var/iot/channels/ every 5 sec                                                                                                         onds
        if [ `expr $now - $old` -gt $CHECK_INTERVAL ];then
                old=`date +%s`
                CID=`ls /var/iot/channels/`
                if [ -n "$CID" ];then
                        echo "I'm Tigerente, there are channels!" >> /tmp/tigere                                                                                                         nte.log
                        for channel in $CID; do
                                data=`cat /var/iot/channels/$channel`
                                #logger "[IoT]: Found $data at Local Channel:" $                                                                                                         CID

                                #logger "[IoT]: Append at $file"

                                echo "I'm Tigerente, sending curl: $data" >> /tm                                                                                                         p/tigerente.log
                                $result = `curl -k -d "data=$data" http://sedich                                                                                                         ilevm.northeurope.cloudapp.azure.com:8080/publish`
                                #
                                #echo "$result" >> /tmp/tigerente.log
                                #logger "-> $data"
                                echo `date` ":<$channel> $data" >> $file
                                rm /var/iot/channels/$channel
                        done
                fi
        fi
done
