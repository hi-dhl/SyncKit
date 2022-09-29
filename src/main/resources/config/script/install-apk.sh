#!/bin/sh
launchActivtiy=$1
find . | fgrep ".apk" | while read fname; do
  echo $fname
  adb install -r $fname
  if [ "$launchActivtiy" ]; then
    adb shell am start $launchActivtiy
  fi
  break
done
