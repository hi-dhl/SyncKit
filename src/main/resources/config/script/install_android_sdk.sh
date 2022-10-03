#!/bin/bash
set -e

HOME_PATH=${HOME}
cd ${HOME_PATH}

DOWNLOAD_FILE_NAME="commandlinetools-linux-8512546_latest"
CMDLINE_TOOLS="cmdline-tools"
BUILD_PATH="build"
ANDROID_SDK="android-sdk"
ANDROID_SDK_TEMP="${ANDROID_SDK}/temp"

rm -rf "${BUILD_PATH}/${ANDROID_SDK}" || true
mkdir -p "${BUILD_PATH}/${ANDROID_SDK_TEMP}"
wget https://dl.google.com/android/repository/${DOWNLOAD_FILE_NAME}.zip

unzip -d  ${HOME_PATH}/${BUILD_PATH}/${ANDROID_SDK_TEMP}   ${DOWNLOAD_FILE_NAME}.zip
mkdir -p "${BUILD_PATH}/${ANDROID_SDK}/${CMDLINE_TOOLS}"
mv ${HOME_PATH}/${BUILD_PATH}/${ANDROID_SDK_TEMP}/${CMDLINE_TOOLS}  ${HOME_PATH}/${BUILD_PATH}/${ANDROID_SDK}/${CMDLINE_TOOLS}/latest

yes | ./${BUILD_PATH}/${ANDROID_SDK}/${CMDLINE_TOOLS}/latest/bin/sdkmanager "platforms;android-30" | ./${BUILD_PATH}/${ANDROID_SDK}/${CMDLINE_TOOLS}/latest/bin/sdkmanager "build-tools;30.0.3" | ./${BUILD_PATH}/${ANDROID_SDK}/${CMDLINE_TOOLS}/latest/bin/sdkmanager "platforms;android-31" || ./${BUILD_PATH}/${ANDROID_SDK}/${CMDLINE_TOOLS}/latest/bin/sdkmanager "build-tools;31.0.0"
yes | ./${BUILD_PATH}/${ANDROID_SDK}/${CMDLINE_TOOLS}/latest/bin/sdkmanager --licenses

BASH_SRC=${HOME_PATH}/.bashrc
BASH_PROFILE=${HOME_PATH}/.bash_profile
BASH_FILE=${BASH_SRC}
if [ -f "${BASH_SRC}" ]; then
  BASH_FILE=${BASH_SRC}
elif [ -f "${BASH_PROFILE}" ]; then
  BASH_FILE=${BASH_PROFILE}
fi

echo "sdk.dir=${HOME_PATH}/${BUILD_PATH}/${ANDROID_SDK}"
echo "" >> ${BASH_FILE}
echo "export ANDROID_HOME=${HOME_PATH}/${BUILD_PATH}/${ANDROID_SDK}" >> ${BASH_FILE}
echo "export PATH=$PATH:$ANDROID_SDK_HOME/platform-tools" >> ${BASH_FILE}
source ${BASH_FILE}

rm -rf "${BUILD_PATH}/${ANDROID_SDK_TEMP}"
rm -rf ${HOME_PATH}/${DOWNLOAD_FILE_NAME}*

