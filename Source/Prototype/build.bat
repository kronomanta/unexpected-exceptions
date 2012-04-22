@echo off
if not exist bin (mkdir bin)

echo Compiling classes...
javac src/main/*.java src/model/*.java src/gameLogic/*.java -d bin -encoding UTF-8

cd bin
echo Creating jar...
jar cfm ../ContinuityPrototype.jar ../src/manifest.txt main/*.class model/*.class gameLogic/*.class
cd ..

echo Done
@echo on