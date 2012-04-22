@echo off
if not exist bin (mkdir bin)

echo Compiling classes...
javac src/main/*.java src/model/*.java src/gameLogic/*.java src/engine/*.java src/game/*.java src/game/menu/*.java src/game/level/*.java -d bin -encoding UTF-8

cd bin
echo Creating jar...
jar cfm ../Continuity.jar ../src/manifest.txt main/*.class model/*.class engine/*.class game/*.class gameLogic/*.class game/menu/*.class game/level/*.class
cd ..

echo Done
@echo on