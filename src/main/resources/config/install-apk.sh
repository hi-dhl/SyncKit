#!/bin/sh
find . | fgrep ".apk" | while read fname; do
  echo $fname
  adb install $fname
  break
done
