#!/bin/bash


line_number=$(grep -n "# BUILDING" 2-Dockerfile.ansible-img  | sed 's/\([0-9]*\):.*/\1/' | head -1)

rm -f 4-*

for i in $(ls 3*); do 
  name="${i##*.}"
  file="4-Dockerfile.${name%-*}-ansible-img"

  touch $file
  
  cp "3-Dockerfile.${i##*.}" $file
  cat 2-Dockerfile.ansible-img | tail +${line_number} >> ${file}; 
done
