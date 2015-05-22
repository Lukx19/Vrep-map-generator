# Vrep map generator
This program should be used for map generation. Program is written in Java. 
Output from algorithm might be directly send to [Vrep](http://www.coppeliarobotics.com/downloads.html) 
or saved as file in specific format described bellow. Algorithm generates random rooms which sizes
 might be changed by modified config file.
 
# Algorithm usage
 1. Generates specified number of rooms by clicking on button GENERATE
 2. Rooms might be moved by clicking on certain room or room's number in top part of window. 
Selected room is colored blue. It is possible to move room with keyboard arrows and rising and lowering room 
with Page up and Page down. Room movement is bounded by size of room 0 which is considered as map environment (garden)
 3.  Clicking on button CALCULATE WALLS AND DOORS may be performed recalculation after rome movement or when 
 different door configuration is desired.
 4. Any time during the run of application it is possible to load config file or change number of generated rooms 
 in left column. Button GENERATE should be pressed after any configuration change.
 5. It is possible to output data by saving generated map in parsable text file by clicking on SAVE MAP AS.
 6. If desired it is possible to send generated map directly to Vrep. For more information please see section below.
 
# Configuration file
Configuration file can be loaded from GUI. This has strictly defined structure

    number_of_rooms
    width:height
    width:height
    width:height
    ....

At the first line must be number of rooms we want to generate to the map. On all other lines is described one room type 
per line. Parameter width and height must be specified. Both parameters should be integers from range [2,20]. 
Numbers above this interval are not advised.

# Parsable text output
It is possible to save map as a text file. one field of the map is bounded by :. One line represents one row of the map.

    8:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:7
    0:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:0
    0:-2:-2:-2:8:1:-1:7:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:0:8:1:1:1:1:1:7:-2:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:0:0:-2:-2:-2:-2:-2:-1:-2:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:0:0:-2:-2:-2:-2:-2:0:-2:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:0:0:-2:-2:-2:-2:-2:0:-2:-2:-2:-2:0
    0:-2:-2:-2:3:-1:1:5:2:-2:-2:-2:-2:-2:0:-2:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:-2:-1:-2:-2:-2:-2:-2:0:-2:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:-2:3:1:1:1:1:1:2:-2:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:-2:-1:-2:-2:-2:-2:-2:0:-2:-2:-2:-2:0
    0:-2:-2:-2:3:1:-1:1:2:8:1:1:1:-1:5:7:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:-2:3:2:-2:-2:-2:-2:-2:0:-2:-2:-2:0
    0:-2:-2:-2:0:-2:-2:-2:0:0:-2:-2:-2:-2:-2:0:-2:-2:-2:0
    0:-2:-2:-2:9:1:1:1:6:0:-2:-2:-2:-2:-2:0:-2:-2:-2:0
    0:-2:-2:-2:-2:-2:-2:-2:-2:9:1:1:1:1:1:6:-2:-2:-2:0
    0:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:0
    0:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:0
    0:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:-2:0
    9:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:1:6

    -2 - empty field
    -1 - door
     0 - horizontal line
     1 - vertical line
     2 - horizontal line with top edge -'-
     3 - horizontal line with bottom edge -,-
     4 - vertical line with right edge |-
     5 - vertical line with left edge -|
     6 - left top corner -'
     7 - right top corner '-
     8 - right down corner ,-
     9 - left down corner -,
     10 - cross -|-
     
# Vrep support
Vrep receives map data through socket on default port 9999.
  
  1. Download [Vrep](http://www.coppeliarobotics.com/downloads.html) Pro Edu (complete) or player edition (just preview)
  2. Load the scene by clicking File->Open Scene -> navigate to folder with java program Vrep-map-generator/vrep/ and
  open file java_gen.ttt
  3. Configuring local PATHS. Double click on the list like icon next to object renderer in project explorer. 
  Integrated editor should open. Change absolute paths for Walls objects and Map storage based on your hierarchy structure. 

        SOURCE_WALLS="/home/lukas/workspace/java/MFF/Vrep-map-generator/vrep/walls/"
        SOURCE_MAP="/home/lukas/workspace/java/MFF/Vrep-map-generator/vrep/maps/"
  
  4. Double click on blue list like icon next to Resizable_floor object in explorer. 
  Change port number if default is in conflict with some services. Port number in Java application GUI must be also 
  updated when port number in script changes. 
  
        local server = assert(socket.bind("*", 9999))
  5. Press run button located at top bar in Vrep.
  6. Generate map and press SEND TO VREP. If ports are configured correctly map should be displayed in Vrep. 
  Map is also saved to folder Vrep-map-generator/vrep/maps. After stopping simulation maps disappears but might be 
  loaded from file stored in folder maps. Use File -> Load Model. In Player distribution of Vrep is saving not 
  working because of limited API 
  7. Stopping of simulation takes approximately 10 seconds to correctly end all socket communication protocols. 
  
# Building app
Java 1.8 is required. Project should be builded with Ant.
For correct build with Ant please change field

    jdk.home.1.8=/usr/lib/jvm/jdk-8-oracle-x64

in build.properties in root folder of project for your local path to Java 1.8 JDK.

![](docs/generated1.png?raw=true)
![](docs/generated2.png?raw=true)
 
