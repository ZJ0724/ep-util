#!/bin/bash

# 项目根目录
projectPath="$(cd `dirname $0`;pwd)/.."
# 启动命令
command=""
# 进程id
pid="$(ps -aux | grep 'ep-util.jar' | grep -v "grep" | awk '{print $2}')"

# isRun
function isRun() {
    if [ "${pid}" != "" ]; then
        echo "true"
        exit 0
    fi

    echo "false"
}