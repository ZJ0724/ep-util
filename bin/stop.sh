#!/bin/bash

# 项目根目录
ABSOLUTE_PATH=$(cd `dirname $0`;pwd)/../
# 启动命令
command="java -jar ${ABSOLUTE_PATH}lib/ep-util.jar --spring.config.location=${ABSOLUTE_PATH}config/application.properties ${ABSOLUTE_PATH}"
# 进程id
pid=$(ps -aux | grep ${command} | grep -v "grep" | awk '{print $2}')

# 如果没运行，不进行关闭
if [ "${pid}" = "" ]; then

  echo "ep-util is not run!"

else

  kill -9 "${pid}"

  echo "ep-util is stop!"

fi