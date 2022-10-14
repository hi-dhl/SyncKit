#!/bin/sh
launchActivtiy=$1
find . -name "*.apk" | while read fname; do
  echo $fname
#  -r : 覆盖原来安装的 APK 并保留数据
  adb install -r -t $fname
  if [ "$launchActivtiy" ]; then
    adb shell am start $launchActivtiy
  fi
  break
done
