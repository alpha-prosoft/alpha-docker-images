#!/bin/bash


line_number=$(grep -n "# BUILDING" 2-Dockerfile.ansible-img  | sed 's/\([0-9]*\):.*/\1/' | head -1)

rm -f 5-*

for i in $(ls 4*); do 
  name="${i##*.}"
  file="5-Dockerfile.${name%-*}-ansible-img"

  touch $file
  
  cp "4-Dockerfile.${i##*.}" $file
  cat 2-Dockerfile.ansible-img | tail -n +${line_number} >> ${file}; 
done
