@echo off

cd %~dp0
cd ..

java -Xms600m -Xmx600m -jar lib/ep-util.jar

pause