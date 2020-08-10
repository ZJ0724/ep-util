#!/bin/bash

# 项目根目录
projectPath="$(cd `dirname $0`;pwd)/.."

# pid.sh
. ${projectPath}/bin/pid.sh

if [ "${pid}" != "" ]; then
  kill -9 "${pid}"
  echo -e "\033[0;32;1m ep-util is stop! \033[0m"
fi