#!/bin/bash

set -e -x -u

SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

export IMAGE_NAME="jaoki/flink-playground"

pushd ${SCRIPT_DIR}

docker build --rm=true -t ${IMAGE_NAME} .

popd

if [ "$(uname -s)" == "Linux" ]; then
  USER_NAME=${SUDO_USER:=$USER}
  USER_ID=$(id -u "${USER_NAME}")
  GROUP_ID=$(id -g "${USER_NAME}")
else # boot2docker uid and gid
  USER_NAME=$USER
  USER_ID=1000
  GROUP_ID=50
fi

docker build -t "${IMAGE_NAME}-${USER_NAME}" - <<UserSpecificDocker
FROM ${IMAGE_NAME}
RUN groupadd --non-unique -g ${GROUP_ID} ${USER_NAME} && \
  useradd -g ${GROUP_ID} -u ${USER_ID} -k /root -m ${USER_NAME}
ENV  HOME /home/${USER_NAME}
UserSpecificDocker

PROJECT_ROOT=${SCRIPT_DIR}/..

pushd ${PROJECT_ROOT}

docker run -i -t \
  --rm=true \
  -w ${PROJECT_ROOT} \
  -u "${USER}" \
  -v "${PROJECT_ROOT}:${PROJECT_ROOT}" \
  -v "/home/${USER_NAME}:/home/${USER_NAME}" \
  -p 8081:8081 \
  -p 8000:8000 \
  ${IMAGE_NAME}-${USER_NAME} \

popd

