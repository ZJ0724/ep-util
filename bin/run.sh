#!/bin/bash

cd $(dirname $0)
cd ..

. ./bin/base.sh

if [ "${pid}" != "" ]; then
    echo -e "\033[0;31;1m run fail, ${projectName} is run! \033[0m"
    exit 0
fi

nohup java -Xmx${rom} -Xms${rom} -jar ${projectName}.jar > /dev/null 2>&1 &
echo -e "\033[0;32;1m ${projectName} is run! \033[0m"