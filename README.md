# HorseRaceSImulator
This project consists of two parts, the main basis behind this project is to create a simulation of a race between multiple horses.
My application consists of two parts, the first part is a textual version of the horse race and the second part is a virtual version of the same race but with more customisable options.

## Set Up Steps:
A java compiler is (JDK) Java Devolopment Kit is rquired to be downloaded in the computer running the following code.
A Java Development Kit can be downloaded from Google Chrome by searching up "JDK Download" and download a version compatible from Oracle.

## Steps to run the code:
### Running Part I: (On Windows)
1) Download and unzip file
2) Open terminal
3) Open PowerShell
4) Find location of the folder and change directories from the main directory to where the folder is located, after which chnage directory to part 1 of the folder and get to files required to run the project
   (use command "cd" wiht foldername to the folder you want to switch to)
5) Type javac Race.java (to compile the file)
6) Then type java Race (to run the file)
7) File will be run in the terminal and will require inputs from the user about basic requirements of the race

### Running Part II: (On Windows)
1) Download and unzip file
2) Open terminal
3) Open PowerShell
4) Find location of the folder and change directories from the main directory to where the folder is located, after which chnage directory to part 1 of the folder and get to files required to run the project
   (use command "cd" wiht foldername to the folder you want to switch to)
5) Type javac Race.java (to compile the file)
6) Then type java Race (to run the file)

OR

2) Open the folder in any IDE of your own choice
3) Open the Race.java file in the IDE
4) Press the run button in the IDE

THEN

7) This will open another window and will require the user to interact with the window to further progress into the race

## Usage Guidlines:
For both files the code will require inputs from the user, the data types of the inputs are important as entering the wrong input could result in returning an error and possibly cuasing the program to crash, I have made use of a try and catch clause in both parts to prevent the program from crashing.
The user must enter an approipriate data type for the type of input required for the code to execute succefully.
- For inputs related to "number of" a user is required to input a whole number/Inetger for the code to excute
- For inputs that relate to "name" or "symbol" a user is required to enter a charcter or a string of characters where deemed appropriate

The cross symbol on any terminal will cause the program to end and starting them up again will require the user to follow "Steps to run code" from step 2 onwards.

## Dependencies:
### Part 1:
Inputs required to run code:
1) Enter number of contestants
2) Enter length of track
3) Enter the details of contestants


This will then automatically run the race and ask user is they would like to restart and run the race again
### Part 2:
1) Enter multiple inputs whose details will be provided on the terminal itself
2) Press the "Done" button which will either then proceed to next terminal, if not then recheck inputs for any wrong data types being entered or data being entered out of bounds
3) In the next terminal enter details of everyone involved in the race (horses and users)
4) Press the "Add" button which will either then proceed to next terminal, if not then recheck inputs for any wrong data types being entered or data being entered out of bounds
5) In the main Race terminal the user will be presented with multiple buttons
6) The "Start" button will always take the user to the betting page first where it will be asked to place a bet with an amount and horse to be entered
7) to procced back to the race the user will be required to enter the "Place Bet" button
8) User will be then required to again press the "Start" button to run the race
9) Follow steps 6 to 8 for multiple runs of the race

- Other options presented such as "Statistics" will open a terminal which will present all the history with statistics of the horses and thier performances over the races
- "Customise" will take the user to a terminal that will allow the user to edit aspects such as details of the horse or the conditions of the track

All buttons pointing out of the "Race" terminal will require the buttons to be pressed to allow access back to the "Race" terminal from the terminal they are in
