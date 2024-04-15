#!/bin/bash

docker build --ssh default --build-arg BUILD_ID=364 --build-arg BUILD_RAM=8 --progress=plain .
