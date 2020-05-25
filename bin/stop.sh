#!/bin/bash

# shellcheck disable=SC2006

# shellcheck disable=SC2009

pid=$(ps -ef | grep "epUtil" | grep -v grep | awk '{print $2}')

if [ "${pid}" = "" ]; then

  echo "epUtil is not run!"

else

  kill -9 "${pid}"

  echo "epUtil is stop!"

fi

