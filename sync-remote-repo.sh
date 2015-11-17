#!/bin/bash

set -x
set -e

SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

REPO_DIR=${SCRIPT_DIR}/../flink
ls $REPO_DIR

pushd $REPO_DIR
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
echo ${CURRENT_BRANCH}
git checkout master
git fetch upstream
git merge upstream/master
git checkout ${CURRENT_BRANCH}
popd


