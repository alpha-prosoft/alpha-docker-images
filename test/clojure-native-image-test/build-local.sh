#!/bin/bash

DOCKER_BUILDKIT=1 docker build --ssh default --build-arg BUILD_ID=430a --build-arg DOCKER_URL=docker.io --build-org DOCKER_ORG=alphaprosoft --build-arg BUILD_RAM=8 --progress=plain .
