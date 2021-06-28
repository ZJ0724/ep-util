#!/bin/bash

projectName="ep-util"

pid="$(ps -aux | grep ${projectName}.jar | grep -v "grep" | awk '{print $2}')"

rom="1024m"