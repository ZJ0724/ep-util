#!/bin/bash

cd $(dirname $0)
cd ..

. ./bin/base.sh

if [ "${pid}" != "" ]; then
  kill -9 "${pid}"
  echo -e "\033[0;32;1m ${projectName} is stop! \033[0m"
fi