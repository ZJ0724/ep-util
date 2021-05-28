#!/bin/sh

version="6.3.0"
appName="ep-util"

# 前端
cd ui
chmod 777 package.sh && ./package.sh
cd ..
rm -rf src/main/resources/static/*
cp -r ui/build/* src/main/resources/static

# 后端
rm -rf build
./gradlew build -x test

mkdir -p build/${appName}
cp -r build/libs/ep-util.jar build/${appName}
cp -r config build/${appName}
cp -r bin build/${appName}

cd build
zip -q -r ${appName}-${version}.zip ${appName}
tar -zcvf ${appName}.tar.gz ${appName}
cd ..