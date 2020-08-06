#!/bin/bash

# 项目根目录
ABSOLUTE_PATH=$(cd `dirname $0`;pwd)/../
# 启动命令
command=java -jar ${ABSOLUTE_PATH}lib/ep-util.jar --spring.config.location=${ABSOLUTE_PATH}config/application.properties ${ABSOLUTE_PATH}
# 进程id
pid=$(ps -aux | grep ${command} | grep -v "grep" | awk '{print $2}')

# 检查是否已经运行
if [ "${epUtilPid}" != "" ]; then
    echo "run fail, ep-util is run!"
    exit 0
fi

# 启动
nohup ${command} > /dev/null 2>&1 &

echo "ep-util is run!"