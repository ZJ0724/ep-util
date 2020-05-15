@echo off
chcp 65001
java -jar ../lib/epUtil.jar --spring.config.location=../config/application.properties
pause