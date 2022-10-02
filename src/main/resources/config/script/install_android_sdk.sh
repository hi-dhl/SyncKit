#!/bin/bash
BASR_DIR=${HOME}
cd ${BASR_DIR}
rm -rf "build/android-sdk/"
mkdir -p "build/android-sdk/temp"
wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip

unzip  -d  ${BASR_DIR}/build/android-sdk/temp/ commandlinetools-linux-8512546_latest.zip
mkdir -p "build/android-sdk/cmdline-tools"
mv ${BASR_DIR}/build/android-sdk/temp/cmdline-tools  ${BASR_DIR}/build/android-sdk/cmdline-tools/latest
rm -rf "build/android-sdk/temp"
rm -rf ${BASR_DIR}/commandlinetools-linux-8512546_latest*

yes | ./build/android-sdk/cmdline-tools/latest/bin/sdkmanager "platforms;android-30" | ./build/android-sdk/cmdline-tools/latest/bin/sdkmanager "build-tools;30.0.3" | ./build/android-sdk/cmdline-tools/latest/bin/sdkmanager "platforms;android-31" || ./build/android-sdk/cmdline-tools/latest/bin/sdkmanager "build-tools;31.0.0"
yes | ./build/android-sdk/cmdline-tools/latest/bin/sdkmanager --licenses

echo "export ANDROID_HOME=${BASR_DIR}/build/android-sdk/" >> ${BASR_DIR}/.bashrc
echo "export PATH=$PATH:$ANDROID_HOME/platform-tools" >> ${BASR_DIR}/.bashrc
source ${BASR_DIR}/.bashrc

