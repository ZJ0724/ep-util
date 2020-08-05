#!/bin/bash

# epUtil进程id
epUtilPid=$(ps -aux | grep "java -jar ../lib/ep-util.jar --spring.config.location=../config/application.properties" | grep -v "grep" | awk '{print $2}')

# 检查是否已经运行
if [ "${epUtilPid}" != "" ]; then
    echo "run fail,ep-util is run!"
    exit 0
fi

# 项目根目录
ABSOLUTE_PATH=$(cd `dirname $0`;pwd)/../

nohup java -jar ${ABSOLUTE_PATH}lib/ep-util.jar --spring.config.location=${ABSOLUTE_PATH}config/application.properties ${ABSOLUTE_PATH} > /dev/null 2>&1 &

echo "ep-util is run!"