@echo off
chcp 65001
java -jar ../lib/EpUtilServer.jar --spring.config.location=../config/application.properties
pause