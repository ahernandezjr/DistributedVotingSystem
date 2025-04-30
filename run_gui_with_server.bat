@echo off
echo -Running Vote GUI with server-

rem Run the VoteServer
start java -cp classes vote.VoteServer

timeout /t 5

rem Run the VoteGUI
start java -cp classes gui.VoteGUI

echo Vote GUI with server started.
exit
