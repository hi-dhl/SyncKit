#!/bin/bash
set -e
JDK_11=$(which java)
echo ${JDK_11}

HOME_PATH=${HOME}
cd ${HOME_PATH}
BASH_SRC=${HOME_PATH}/.bashrc
BASH_PROFILE=${HOME_PATH}/.bash_profile
BASH_FILE=${BASH_SRC}
if [ -f "${BASH_SRC}" ]; then
  BASH_FILE=${BASH_SRC}
elif [ -f "${BASH_PROFILE}" ]; then
  BASH_FILE=${BASH_PROFILE}
fi

echo "" >> ${BASH_FILE}
echo "export JAVA_HOME=${JDK_11}" >> ${BASH_FILE}
echo "export CLASSPATH=.:${JDK_11}/lib" >> ${BASH_FILE}
echo "export PATH=${JAVA_HOME}/bin:$PATH" >> ${BASH_FILE}
source ${BASH_FILE} > /dev/null 2>&1

