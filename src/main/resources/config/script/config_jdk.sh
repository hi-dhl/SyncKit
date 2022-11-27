#!/bin/bash

JDK_PATH=$(which java)
if [[ ${#JDK_PATH} >0 ]]
then
    echo ${JDK_PATH}
    JDK_HOME_PATH=$(dirname $(dirname "$JDK_PATH"))
    cd  ${JDK_HOME_PATH}

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
    echo "export JAVA_HOME=${JDK_HOME_PATH}" >> ${BASH_FILE}
    echo "export CLASSPATH=.:${JDK_HOME_PATH}/lib" >> ${BASH_FILE}
    echo "export PATH=${JAVA_HOME}/bin:$PATH" >> ${BASH_FILE}
    source ${BASH_FILE} > /dev/null 2>&1

    echo "配置JDK环境成功"
else
    echo "尚未安装 JDK，请使用「同步工具」安装JDK"
fi



