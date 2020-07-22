#!/bin/bash

# 存放目录
releasePath="release"
# 应用名
projectName="epUtil"
# 版本
version="$(cat version)"
# 包名
packageName="${projectName}-${version}"
# 编译目录
buildPath="target/build"

# 重新生成存放目录
rm -rf ${releasePath}
mkdir ${releasePath}

# 清空maven
mvn clean

# 打包
mvn package

# 打包
tar -zcvf ${releasePath}/${packageName}.tar.gz -C ${buildPath} ${projectName}
tar -cvf ${releasePath}/${packageName}.zip -C ${buildPath} ${projectName}
