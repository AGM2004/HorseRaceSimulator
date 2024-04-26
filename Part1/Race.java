import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.Random;
import java.util.Scanner;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength = 0;
    private HorsePart1[] Horses;
    private int contestents;

    public static void main(String args[]){
        new Race();
    }

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race()
    {
        // initialise instance variables
        contestents = Integer.valueOf(input("\n" + "How many contestants are running in the race?"));
        Horses = new HorsePart1[contestents];
        while(raceLength <= 0){
            raceLength = Integer.valueOf(input("\n" + "What is the length of the race?"));
        }
        startRace();
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(HorsePart1 theHorse, int laneNumber)
    {
        // Adding Horses to the array of horses
        Horses[laneNumber-1] = theHorse;   
    }

    // This method is used to tale an input from a user, a string in specific
    public String input (String output)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(output);
        String text = scanner.nextLine();
        return text;
    } // End of input
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        // declaring local variables that will be used to take an input from users
        int lane= 1;
        HorsePart1 H = null;
        boolean start = true;
        int race = 1;
        Random rd = new Random();

        // Loop to take in details of horses to be added to the lanes
        while(lane <= contestents){
            String horseName = input("\n"+"What is the name of horse " + lane + " ?");
            char horseSymbol = input("What is the symbol of horse " + lane + " ?").charAt(0); 
            double horsec = Double.valueOf(rd.nextInt(9) +1)/10.0;
            System.out.println("What is the confidence of horse " + horsec); 
            H = new HorsePart1(horseSymbol, horseName, horsec);
            addHorse(H,lane);
            lane++;
            System.out.println("\n");
        }

        while (start){
            System.out.println("\n" + "Race number: " + race + "\n");
        
            //reset all the lanes (all horses not fallen and back to 0).
            lane=0;
            while(lane < contestents){
                Horses[lane].goBackToStart();
                Horses[lane].stand();
                lane++;
            }

            //declare a local variable to tell us when the race is finished
            boolean finished = false;

            while (!finished){
                //move each horse
                int lane2=0;
                while(lane2 < contestents){
                    moveHorse(Horses[lane2]);
                    lane2++;
                }

                //print the race positions
                printRace();

                // Checking if all horses have fallen or not to either continue or end the race
                boolean allfallen = false;
                for(int i =0; i<Horses.length; i++){
                    if(Horses[i].hasFallen() == true){
                        allfallen = true;
                    }
                    else {
                        allfallen = false;
                        break;
                    }
                }

                if(allfallen == false){
                    //if any of the horses has won the race is finished
                    int winners = 0;
                    String winnernames = "";
                    boolean won = false;
                    for(int i =0; i < contestents; i++){
                        if(raceWonBy(Horses[i])){
                            winnernames += Horses[i].getName() + " ";
                            winners++;
                            won = true;
                        }
                    }

                    // Printing winners names acording to the amount of winners
                    if (winners == 1 && won == true){
                        System.out.println(winnernames + "has won the race!! ");
                        finished = true;
                    }
                    else if (won == true && winners>1){
                        System.out.println("The race was a tie between " + winnernames);
                        finished = true;
                    }
                }

                // Showing that all horses have fallen thus no winner
                else {
                    System.out.println("All horses have fallen race has ended!! :( ");
                    finished = true;
                }

                //wait for 100 milliseconds
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch(Exception e){}

            }

            // Option to restart the race
            if(input("\n" + "------- Retart? (y/n) -------").toLowerCase().equals("n")){
                start = false;
            }
            race++;
        }
        
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(HorsePart1 theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                if(theHorse.getConfidence() > 0.1){
                    theHorse.setConfidence(theHorse.getConfidence() - 0.1);
                }
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(HorsePart1 theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            if(theHorse.getConfidence() < 0.9){
                theHorse.setConfidence(theHorse.getConfidence() + 0.1);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        int lane = 0;
        while(lane<contestents){
            printLane(Horses[lane]);
            System.out.println();
            lane++;
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(HorsePart1 theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u274c');
        }
        else 
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

        // Printing the Details of the contestant
        System.out.print("  " + theHorse.getName().toUpperCase() + " (Current confidence " + theHorse.getConfidence() + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}