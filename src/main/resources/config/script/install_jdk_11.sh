#!/bin/bash
set -e

HOME_PATH=${HOME}
cd ${HOME_PATH}

DOWNLOAD_FILE_NAME="openjdk-11+28_linux-x64_bin"
BUILD_PATH="build"
JDK_11="jdk-11"

rm -rf "${BUILD_PATH}/${JDK_11}" || true
mkdir -p "${BUILD_PATH}"
wget https://download.java.net/openjdk/jdk11/ri/${DOWNLOAD_FILE_NAME}.tar.gz
tar -zxvf ${DOWNLOAD_FILE_NAME}.tar.gz -C "${HOME_PATH}/${BUILD_PATH}/"

BASH_SRC=${HOME_PATH}/.bashrc
BASH_PROFILE=${HOME_PATH}/.bash_profile
BASH_FILE=${BASH_SRC}
if [ -f "${BASH_SRC}" ]; then
  BASH_FILE=${BASH_SRC}
elif [ -f "${BASH_PROFILE}" ]; then
  BASH_FILE=${BASH_PROFILE}
fi

echo "JAVA_HOME=${HOME_PATH}/${BUILD_PATH}/${JDK_11}"
echo "" >> ${BASH_FILE}
echo "export JAVA_HOME=${HOME_PATH}/${BUILD_PATH}/${JDK_11}" >> ${BASH_FILE}
echo "export CLASSPATH=.:${HOME_PATH}/${BUILD_PATH}/${JDK_11}/lib" >> ${BASH_FILE}
echo "export PATH=${JAVA_HOME}/bin:$PATH" >> ${BASH_FILE}
source ${BASH_FILE}

rm -rf ${HOME_PATH}/${DOWNLOAD_FILE_NAME}.tar.gz*
