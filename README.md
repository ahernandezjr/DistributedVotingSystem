# VoteRMI

## Project Structure
Vote  
├── src  
│   ├── gui  
│   │   ├── VoteGUI.java  
│   │   └── VoteWindow.java  
│   └── vote  
│       ├── VoteServer.java  
│       ├── VoteServerInterface.java  
│       ├── VoteServerImpl.java  
│       ├── VoteClient.java  
│       ├── VoteClientInterface.java  
│       └── VoteClientImpl.java  
├── tests  
├── classes  
├── compile.bat  
├── run_clients_and_server.bat  
└── run_gui_with_server.bat  

- src: Contains the source code for the project.
	- gui: Package containing GUI-related classes.
	- vote: Package containing classes related to voting functionality.
- tests: Directory for storing test-related files.
- classes: Directory for storing compiled Java class files.
- compile.bat: Batch file for compiling the project source code.
- run_clients_and_server.bat: Batch file for running VoteServer and VoteClient's.
- run_gui_with_server.bat: Batch file for running Vote GUI with server.

## Usage/How to Run

### Compilation
**To compile the project**, run the compile.bat script. This will compile the Java source files from the src directory and store the classes in the classes directory.

### Running Vote Clients and Server
**To run Vote clients and server**, execute the run_clients_and_server.bat script. This will start the VoteServer and two instances of VoteClient. The VoteServer and VoteClient have default argument values. Run file without .bat (read .bat for Java command) to choose host/port.

### Running Vote GUI with Server
**To run Vote GUI with the server**, execute the run_gui_with_server.bat script. This will start the VoteServer and the VoteGUI application. The VoteServer has default argument values. Run file without .bat (read .bat for Java command) to choose host/port.
