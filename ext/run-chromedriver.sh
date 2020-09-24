#!/bin/bash


ldd /var/task/chromedriver
export LD_LIBRARY_PATH="${LD_LIBRARY_PATH}:/var/task/clib"

ls -la /var/task/clib
echo "$@"


/var/task/chromedriver
