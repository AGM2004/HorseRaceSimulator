import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse[] Horses;
    private int contestents;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        contestents = Integer.valueOf(input("How mnay contestants are running in the race?"));
        Horses = new Horse[contestents];
        raceLength = distance;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
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
        boolean add = true;
        int lane= 1;
        Horse H = null;

        // Loop to take in details of horses to be added to the lanes
        while(lane <= contestents){
            char horseSymbol = input("What is the symbol of horse " + lane + " ?").charAt(0); 
            String horseName = input("What is the name of horse " + lane + " ?");
            double horseConfidence = Double.valueOf(input("What is the confidence of horse " + lane + " ?"));
            H = new Horse(horseSymbol, horseName, horseConfidence);
            addHorse(H,lane);
            lane++;
        }

        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0).
        lane=0;
        while(lane < contestents){
            Horses[lane].goBackToStart();
            lane++;
        }

        while (!finished)
        {
            //move each horse
            lane=0;
            while(lane < contestents){
                moveHorse(Horses[lane]);
                lane++;
            }

            //print the race positions
            printRace();
            
            //if any of the horses has won the race is finished
            lane=0;
            while(lane < contestents){
                if(raceWonBy(Horses[lane])){
                    Horses[lane].print(Horses[lane].getName() + " has won the race !");
                    finished = true;
                }
                lane++;
            }

            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
        
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
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
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
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
    private void printLane(Horse theHorse)
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
            System.out.print('\u2322');
        }
        else 
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
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
