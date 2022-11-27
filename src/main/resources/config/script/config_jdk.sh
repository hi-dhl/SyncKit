#!/bin/bash

JDK_PATH=$(which java)
if [[ ${#JDK_PATH} >0 ]]
then
    echo ${JDK_PATH}
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
    echo "export JAVA_HOME=${JDK_PATH}" >> ${BASH_FILE}
    echo "export CLASSPATH=.:${JDK_PATH}/lib" >> ${BASH_FILE}
    echo "export PATH=${JAVA_HOME}/bin:$PATH" >> ${BASH_FILE}
    source ${BASH_FILE} > /dev/null 2>&1
else
    echo "不包含"
fi



