#!/bin/sh

version="1.0.0"
appName="ep-util"

# 前端
cd ui
./package.sh
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
cp -r chromedriver build/${appName}

cd build
zip -q -r ${appName}-${version}.zip ${appName}
cd ..