#!/bin/sh
set -e

which "sshpass" >/dev/null 2>&1
if [ $? -ne 0 ]; then
  WORK_PATH=$(pwd)
  FILE_NAME="sshpass-1.08"
  FILES="${WORK_PATH}/.sync/files/${FILE_NAME}.tar.gz"
  tar -zxvf ${FILES} -C "${WORK_PATH}/.sync/files"
  cd "${WORK_PATH}/.sync/files/${FILE_NAME}"
  ./configure
  make&&make install
  cd ${WORK_PATH}
  rm -rf "${WORK_PATH}/.sync/files/${FILE_NAME}"
fi

