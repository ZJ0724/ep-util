#!/bin/bash

releasePath="release"
packageName=""

# --- #
mvn clean package

for dirName in $(ls target/buildPath)
do
  packageName="${dirName}"
done

rm -rf ${releasePath}

mkdir ${releasePath}

tar -zcvf ${releasePath}/${packageName}.tar.gz -C target/buildPath ${packageName}
