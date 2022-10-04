#!/bin/bash
BASR_DIR=${HOME}
cd ${BASR_DIR}
mkdir -p ~/.ssh

SSH_PUBLIC_KEY=$1
if [ -z "${SSH_PUBLIC_KEY}" ]; then
  echo "please input authorized ssh public key"
  exit 1
fi
echo ${SSH_PUBLIC_KEY}
echo ${SSH_PUBLIC_KEY} >> ~/.ssh/authorized_keys
chmod 777 ~/.ssh/authorized_keys