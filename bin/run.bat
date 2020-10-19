@echo off

cd %~dp0
cd ..

java -Xms2G -Xmx2G -jar lib/ep-util.jar

pause