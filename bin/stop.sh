#!/bin/bash

# shellcheck disable=SC2006

# shellcheck disable=SC2009

pid=$(ps -ef | grep "java -jar ../lib/ep-util.jar --spring.config.location=../config/application.properties" | grep -v grep | awk '{print $2}')

if [ "${pid}" = "" ]; then

  echo "ep-util is not run!"

else

  kill -9 "${pid}"

  echo "ep-util is stop!"

fi