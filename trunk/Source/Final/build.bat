@echo off
if not exist bin (mkdir bin)

echo Compiling classes...
javac src/main/*.java src/model/*.java src/gameLogic/*.java src/game/*.java src/game/renderer/*.java src/game/level/*.java -d bin -encoding UTF-8

cd bin
echo Creating jar...
jar cfm ../Continuity.jar ../src/manifest.txt main/*.class model/*.class game/*.class gameLogic/*.class game/renderer/*.class game/level/*.class
cd ..

echo Done
@echo on