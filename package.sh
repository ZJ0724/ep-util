#!/bin/bash

releasePath="release"
packageName=""

# --- #

for dirName in $(ls target/buildPath)
do
  packageName="${dirName}"
done

mvn clean package

rm -rf ${releasePath}

mkdir ${releasePath}

tar -zcvf ${releasePath}/${packageName}.tar.gz -C target/buildPath ${packageName}
