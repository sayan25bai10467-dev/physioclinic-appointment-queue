@echo off
if exist out rmdir /s /q out
mkdir out
javac --release 17 -d out @sources.txt
if errorlevel 1 exit /b 1
java -cp out com.vit.physioclinic.tests.TestRunner
