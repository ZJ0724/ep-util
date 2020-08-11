#!/bin/bash

# 切回项目根目录
cd $(dirname $0)
cd ..

# pid.sh
. ./bin/pid.sh

# 检查是否已经运行
if [ "${pid}" != "" ]; then
    echo -e "\033[0;31;1m run fail, ep-util is run! \033[0m"
    exit 0
fi

# 启动
nohup java -jar lib/ep-util.jar > /dev/null 2>&1 &

echo -e "\033[0;32;1m ep-util is run! \033[0m"