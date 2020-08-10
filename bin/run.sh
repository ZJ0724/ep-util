#!/bin/bash

# 项目根目录
projectPath="$(cd `dirname $0`;pwd)/.."

# pid.sh
. ${projectPath}/bin/pid.sh

# 检查是否已经运行
if [ "${pid}" != "" ]; then
    echo -e "\033[0;31;1m run fail, ep-util is run! \033[0m"
    exit 0
fi

# 启动
nohup java -jar ${projectPath}/lib/ep-util.jar --spring.config.location=${projectPath}/config/application.properties ${projectPath} > /dev/null 2>&1 &

echo -e "\033[0;32;1m ep-util is run! \033[0m"