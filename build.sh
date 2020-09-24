#!/bin/bash

echo "Building docker images ${BUILD_ID}"

docker build --progress=plain --build-arg BUILD_ID="${BUILD_ID}" -t base-img:b${BUILD_ID} -f   0-Dockerfile.base .
docker build --progress=plain --build-arg BUILD_ID="${BUILD_ID}" -t common-img:b${BUILD_ID} -f 1-Dockerfile.common .
docker build --progress=plain --build-arg BUILD_ID="${BUILD_ID}" -t ansible-img:b${BUILD_ID} -f 2-Dockerfile.ansible .
