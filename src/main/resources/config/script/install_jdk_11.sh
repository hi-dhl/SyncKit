#!/bin/bash
BASR_DIR=${HOME}
cd ${BASR_DIR}
rm -rf "build/jdk-11"
mkdir -p "build"
wget https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz
tar -zxvf openjdk-11+28_linux-x64_bin.tar.gz -C "${BASR_DIR}/build/"
echo "export JAVA_HOME=${BASR_DIR}/build/jdk-11" >> ${BASR_DIR}/.bashrc
echo "export CLASSPATH=.:${JAVA_HOME}/lib" >> ${BASR_DIR}/.bashrc
echo "export PATH=${JAVA_HOME}/bin:$PATH" >> ${BASR_DIR}/.bashrc
source ${BASR_DIR}/.bashrc
rm -rf ${BASR_DIR}/openjdk-11+28_linux-x64_bin.tar.gz*
