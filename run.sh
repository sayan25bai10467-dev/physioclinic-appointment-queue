#!/usr/bin/env bash
set -euo pipefail

rm -rf out
mkdir -p out
javac --release 17 -d out @sources.txt
java -cp out com.vit.physioclinic.Main
