#!/bin/bash
BASR_DIR="$(pwd)"
rm -rf "build/jdk-11"
mkdir -p "build"
wget https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz
tar -zxvf openjdk-11+28_linux-x64_bin.tar.gz -C "${BASR_DIR}/build/"
echo "export JAVA_HOME=${BASR_DIR}/build/jdk-11" >> /etc/profile
echo "export CLASSPATH=.:${JAVA_HOME}/lib" >> /etc/profile
echo "export PATH=${JAVA_HOME}/bin:$PATH" >> /etc/profile
source /etc/profile