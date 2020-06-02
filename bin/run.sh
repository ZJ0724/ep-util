#!/bin/bash

# epUtil进程id
epUtilPid=$(ps -aux | grep "epUtil" | grep -v "grep" | awk '{print $2}')

# 检查是否已经运行
if [ "${epUtilPid}" != "" ]; then
    echo "run fail,epUtil is run!"
    exit 0
fi

nohup java -jar ../lib/epUtil.jar --spring.config.location=../config/application.properties > /dev/null 2>&1 &

echo "epUtil is run!"