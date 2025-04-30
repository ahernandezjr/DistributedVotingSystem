@echo off
echo -Compiling Vote application-

rem Create the classes directory if it doesn't exist
if not exist classes mkdir classes

rem Compile the Java files from the src folder
javac -d classes -cp classes src\gui\*.java src\vote\*.java

echo Compilation complete.
exit