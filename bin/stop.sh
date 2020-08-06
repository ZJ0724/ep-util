#!/bin/bash

# base.sh
. ./base.sh

if [ "${pid}" != "" ]; then
  kill -9 "${pid}"
  echo -e "\033[0;32;1m ep-util is stop! \033[0m"
fi