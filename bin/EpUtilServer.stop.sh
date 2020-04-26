#!/bin/bash
# shellcheck disable=SC2006
# shellcheck disable=SC2009
pid=$(ps -ef | grep EpUtilServer | grep -v grep | awk '{print $2}')
if [ "${pid}" = "" ]; then
  echo "EpUtilServer is not run!"
else
  kill -9 "${pid}"
  echo "EpUtilServer is stop!"
fi
