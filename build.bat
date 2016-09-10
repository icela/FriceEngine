@echo off

cd demos
git submodule init
git submodule update

java -jar dokka.jar ./src

echo ===========!!!build success!!!===========

pause