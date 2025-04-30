@echo off
echo -Running Vote clients and server-

rem Run the VoteServer
start java -cp classes vote.VoteServer localhost 1234

timeout /t 5

rem Run two instances of VoteClient
start java -cp classes vote.VoteClient localhost 1234
start java -cp classes vote.VoteClient localhost 1234

echo Vote clients and server started.
exit
