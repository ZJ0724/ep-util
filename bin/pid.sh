#!/bin/bash

# 进程id
pid="$(ps -aux | grep 'ep-util.jar' | grep -v "grep" | awk '{print $2}')"