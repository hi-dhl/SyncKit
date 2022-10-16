#!/bin/sh
set +e
./gradlew --stop;
rm -rf ~/.gradle/caches/build-cache-*;
./gradlew clean
