#!/bin/bash

# 存放目录
releasePath="release"

# 应用名
projectName="epUtil"

# 版本
version=$(cat src/main/resources/version)

# 包名
packageName="${projectName}-${version}"

# 编译目录
buildPath="target/build"

# 当前分支
branch=$(git branch | grep "*")

# 重新生成存放目录
rm -rf ${releasePath}
mkdir ${releasePath}

# 清空maven
mvn clean

# 如果是dev分支，使用默认进行编译，如果是其他分支，使用 -P release
if [ "${branch}" == "* dev" ]; then
  projectName="${projectName}-beta"
  packageName="${packageName}-beta"
  mvn package
else
  mvn package -P release
fi

# 打包
tar -zcvf ${releasePath}/${packageName}.tar.gz -C ${buildPath} ${projectName}
tar -cvf ${releasePath}/${packageName}.zip -C ${buildPath} ${projectName}
