#!/bin/bash

echo "Building docker images ${BUILD_ID}"

export DOCKER_URL=local
export DOCKER_ORG=local

docker build --progress=plain \
             --build-arg DOCKER_URL=${DOCKER_URL} \
             --build-arg DOCKER_ORG=${DOCKER_ORG} \
	     --build-arg BUILD_ID="${BUILD_ID}" \
	     -t ${DOCKER_URL}/${DOCKER_ORG}/base-img:b${BUILD_ID} \
	     -f 0-Dockerfile.base-img .

docker build --progress=plain \
             --build-arg DOCKER_URL=${DOCKER_URL} \
             --build-arg DOCKER_ORG=${DOCKER_ORG} \
	     --build-arg BUILD_ID="${BUILD_ID}" \
	     -t ${DOCKER_URL}/${DOCKER_ORG}/common-img:b${BUILD_ID} \
	     -f 1-Dockerfile.common-img .

docker build --progress=plain \
             --build-arg DOCKER_URL=${DOCKER_URL} \
             --build-arg DOCKER_ORG=${DOCKER_ORG} \
	     --build-arg BUILD_ID="${BUILD_ID}" \
	     -t ${DOCKER_URL}/${DOCKER_ORG}/ansible-img:b${BUILD_ID} \
	     -f 2-Dockerfile.ansible-img .

docker build --progress=plain \
             --build-arg DOCKER_URL=${DOCKER_URL} \
             --build-arg DOCKER_ORG=${DOCKER_ORG} \
	     --build-arg BUILD_ID="${BUILD_ID}" \
	     -t ${DOCKER_URL}/${DOCKER_ORG}/web-img:b${BUILD_ID} \
	     -f 3-Dockerfile.web-img .

#docker build --progress=plain --build-arg BUILD_ID="${BUILD_ID}" -t ansible-img:b${BUILD_ID} -f 2-Dockerfile.ansible .

