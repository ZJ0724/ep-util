#!/bin/bash

nohup java -jar ../lib/EpUtilServer.jar --spring.config.location=../config/application.properties > /dev/null 2>&1 &

echo "EpUtilServer is run!"

