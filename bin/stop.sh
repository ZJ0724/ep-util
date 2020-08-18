#!/bin/bash

# 切回项目根目录
cd $(dirname $0)
cd ..

# pid.sh
. ./bin/pid.sh

if [ "${pid}" != "" ]; then
  kill -9 "${pid}"
  echo -e "\033[0;32;1m ep-util is stop! \033[0m"
fi