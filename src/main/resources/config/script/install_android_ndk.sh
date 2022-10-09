#!/bin/bash
set -e

HOME_PATH=${HOME}
cd ${HOME_PATH}

DOWNLOAD_FILE_NAME="android-ndk-r25b"
BUILD_PATH="build"
ANDROID_NDK=${DOWNLOAD_FILE_NAME}

mkdir -p ${BUILD_PATH}
rm -rf ${HOME_PATH}/${BUILD_PATH}/${ANDROID_NDK} || true
wget https://dl.google.com/android/repository/${ANDROID_NDK}-linux.zip
unzip  -d  ${HOME_PATH}/${BUILD_PATH} ${ANDROID_NDK}-linux.zip

BASH_SRC=${HOME_PATH}/.bashrc
BASH_PROFILE=${HOME_PATH}/.bash_profile
BASH_FILE=${BASH_SRC}
if [ -f "${BASH_SRC}" ]; then
  BASH_FILE=${BASH_SRC}
elif [ -f "${BASH_PROFILE}" ]; then
  BASH_FILE=${BASH_PROFILE}
fi

echo "ndk.dir=${HOME_PATH}/${BUILD_PATH}/${ANDROID_NDK}"
echo "" >> ${BASH_FILE}
echo "export ANDROID_NDK=${HOME_PATH}/${BUILD_PATH}/${ANDROID_NDK}" >> ${BASH_FILE}
echo "export PATH=$PATH:$ANDROID_NDK" >> ${BASH_FILE}
source ${BASH_FILE} > /dev/null 2>&1
rm -rf ${HOME_PATH}/${ANDROID_NDK}-linux*