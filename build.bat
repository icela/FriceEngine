@echo off

cd demos
git submodule init
git submodule update

cd ..

java -jar dokka.jar ./src

echo ===========!!!build success!!!===========