import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private static int raceLength;
    private static Horse[] Horses;
    private static int contestents;
    private static String trackboundaries;
    private static String trackedge;
    private static int currentRace = 0;
    private static String races = "";
    private static String message = "";
    public static void main(String args[]){
        //new Race();
        
        JFrame frame = new JFrame("Details");
        frame.setLayout(new FlowLayout());
        frame.setSize(750, 170);;
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel1 = new JPanel(new GridLayout(4,2));

        JLabel l1 = new JLabel("How many horses do you have?");
        panel1.add(l1);
        JTextField t1 = new JTextField(10);
        panel1.add(t1);

        JLabel l2 = new JLabel("Whats the length of the track?");
        panel1.add(l2);
        JTextField t2 = new JTextField(10);
        panel1.add(t2);

        JLabel l3 = new JLabel("What symbol do you want to represent the top of the track?");
        panel1.add(l3);
        JTextField t3 = new JTextField(10);
        panel1.add(t3);

        JLabel l4 = new JLabel("What symbol do you want to represent the edge of the track?");
        panel1.add(l4);
        JTextField t4 = new JTextField(10);
        panel1.add(t4);

        panel1.setLocation(200, 10);

        frame.add(panel1);

        JPanel panel2 = new JPanel(new FlowLayout());

        JButton done = new JButton("Done");
        done.setLocation(20,80);
        panel2.add(done);
        frame.add(panel2);
        try{
            done.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    int check1 = Integer.valueOf(t1.getText());
                    int check2 = Integer.valueOf(t2.getText());

                    if(check1 > 0 && check2 > 0){
                        contestents = check1;
                        raceLength = check2;
                        trackboundaries = t3.getText();
                        trackedge = t4.getText();

                        frame.setVisible(false);

                        // Taking inputs of horses

                        JFrame ftemp = new JFrame("Horse Details");
                        ftemp.setLayout(new FlowLayout());
                        ftemp.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        ftemp.setVisible(true);

                        JPanel panelinputs = new JPanel(new GridLayout(contestents,2));
                        JPanel panelbutton = new JPanel(new FlowLayout());

                        JTextField[] N = new JTextField[contestents];
                        JTextField[] S = new JTextField[contestents];
                        JTextField[] C = new JTextField[contestents];

                        for(int i = 0; i<contestents; i++){
                            N[i] = new JTextField(10);
                            S[i] = new JTextField(10);
                            C[i] = new JTextField(10);

                            int lane = i + 1;
                            JLabel name = new JLabel("What is the name of horse " + lane + " ?");
                            JLabel symbol = new JLabel("What is the symbol of horse " + lane + " ?");
                            JLabel confidence = new JLabel("What is the confidence of horse " + lane + " ?");

                            panelinputs.add(name);
                            panelinputs.add(N[i]);
                            panelinputs.add(symbol);
                            panelinputs.add(S[i]);
                            panelinputs.add(confidence);
                            panelinputs.add(C[i]);
                        }

                        JButton add = new JButton("Add");
                        panelbutton.add(add);

                        ftemp.add(panelinputs);
                        ftemp.add(panelbutton);

                        add.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e3){
                                Horse H = null;
                                boolean end = false;
                                for(int lane = 0; lane<contestents; lane++){
                                    for(int concheck = 0 ; concheck<contestents; concheck++){
                                        if(Double.valueOf(C[lane].getText()) > 0 && Double.valueOf(C[lane].getText()) < 1){
                                            end = true;
                                        }
                                        else{
                                            end = false;
                                            break;
                                        }
                                    }
                                    
                                }
                                if(end == true){
                                    Horses = new Horse[contestents];
                                    for(int lane=0; lane<contestents;lane++){
                                        char horseSymbol = S[lane].getText().charAt(0); 
                                        String horseName = N[lane].getText();
                                        double horseConfidence = Double.valueOf(C[lane].getText());
                                        H = new Horse(horseSymbol, horseName, horseConfidence);
                                        addHorse(H,lane);
                                    }

                                    ftemp.setVisible(false);

                                    JFrame Race = new JFrame("Horse Race");
                                    Race.setLayout(new BorderLayout());
                                    Race.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                    Race.setVisible(true);

                                    JPanel Details = new JPanel(new BorderLayout());
                                    JPanel Options = new JPanel(new GridLayout(1,3));

                                    JButton Stats = new JButton("Statistics");
                                    JButton Customise = new JButton("Customise");
                                    JButton Bets = new JButton("Bets");
                                    JButton start = new JButton("Start");

                                    JLabel currentrace = new JLabel();

                                    Options.add(Stats);
                                    Options.add(Customise);
                                    Options.add(Bets);

                                    JTextArea completerace = new JTextArea();
                                    JLabel winners = new JLabel();

                                    start.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e){
                                            //declare a local variable to tell us when the race is finished
                                            boolean finished = false;
        
                                            //reset all the lanes (all horses not fallen and back to 0).
                                            int lane=0;
                                            while(lane < contestents){
                                                Horses[lane].goBackToStart();
                                                Horses[lane].stand();
                                                lane++;
                                            }
                                            
                                            while(!finished){
                                                //move each horse
                                                lane=0;
                                                while(lane < contestents){
                                                    moveHorse(Horses[lane]);
                                                    lane++;
                                                }

                                                //print the race positions
                                                races += printRace() + "\n";

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

                                                    if (winners == 1 && won == true){
                                                        message += winnernames + "has won the race!! ";
                                                        finished = true;
                                                    }
                                                    else if (won == true && winners>1){
                                                        message += "The race was a tie between " + winnernames;
                                                        finished = true;
                                                    }
                                                }

                                                else {
                                                    message += "All horses have fallen race has ended!! :( ";
                                                    finished = true;
                                                }
                                            }

                                            completerace.setText(races);
                                            winners.setText(message);
                                            winners.setHorizontalAlignment(SwingConstants.CENTER);
                                            races = "";
                                            message = "";
                                            currentRace++;
                                            currentrace.setText("Race Number is: " + String.valueOf(currentRace));
                                        }
                                    });

                                    JScrollPane Raceprint = new JScrollPane(completerace);

                                    Details.add(currentrace, BorderLayout.WEST);
                                    Details.add(winners,BorderLayout.CENTER);
                                    Details.add(start, BorderLayout.EAST);

                                    Race.add(Details, BorderLayout.NORTH);
                                    Race.add(Raceprint, BorderLayout.CENTER);
                                    Race.add(Options, BorderLayout.SOUTH);

                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e4){
            System.out.println("Error has occured");
        }
        
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
        int number = 0;
        while (number <= 0){
            number = Integer.valueOf(input("\n"+ "How many contestants are running in the race?"));
        }
        int length = 0;
        while (length <= 0){
            length = Integer.valueOf(input("\n"+ "What is the distance of the track?"));
        }
        contestents = number;
        Horses = new Horse[contestents];
        raceLength = length;
        trackboundaries = input("\n"+ "What symbol do you want to represent the top of the track?");
        trackedge = input("\n"+ "What symbol do you want to represent the edge of the track?");
        startRace();
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public static void addHorse(Horse theHorse, int laneNumber)
    {
        // Adding Horses to the array of horses
        Horses[laneNumber] = theHorse;   
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
        boolean start = true;
        int race = 1;

        // Loop to take in details of horses to be added to the lanes
        while(lane <= contestents){
            char horseSymbol = input("What is the symbol of horse " + lane + " ?").charAt(0); 
            String horseName = input("What is the name of horse " + lane + " ?");
            double horseConfidence = Double.valueOf(input("What is the confidence of horse " + lane + " ?"));
            H = new Horse(horseSymbol, horseName, horseConfidence);
            addHorse(H,lane);
            lane++;
            System.out.println("\n");
        }
        
        while (start){
            System.out.println("\n" + "Race number: " + race + "\n");

            //declare a local variable to tell us when the race is finished
            boolean finished = false;
        
            //reset all the lanes (all horses not fallen and back to 0).
            lane=0;
            while(lane < contestents){
                Horses[lane].goBackToStart();
                Horses[lane].stand();
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

                    if (winners == 1 && won == true){
                        System.out.println(winnernames + "has won the race!! ");
                        finished = true;
                    }
                    else if (won == true && winners>1){
                        System.out.println("The race was a tie between " + winnernames);
                        finished = true;
                    }
                }

                else {
                    System.out.println("All horses have fallen race has ended!! :( ");
                    finished = true;
                }

                //wait for 100 milliseconds
                try{
                    //System.out.println("\033[H\033[2J");
                    //System.out.print('\u000C');  //clear the terminal window
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
    private static void moveHorse(Horse theHorse)
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
                theHorse.setConfidence(theHorse.getConfidence() - 0.1);
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
    private static boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            theHorse.setConfidence(theHorse.getConfidence() + 0.1);
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
    private static String printRace()
    {
        String race = "";
        race += multiplePrint(trackboundaries.charAt(0),raceLength+3) + "\n"; //top edge of track
        
        int lane = 0;
        while(lane<contestents){
            race += printLane(Horses[lane]) + "\n";
            lane++;
        }
        
        race += multiplePrint(trackboundaries.charAt(0),raceLength+3); //bottom edge of track
        return race;    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private static String printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        String lane = "";
        
        //print a custom symbol for the beginning of the lane
        lane += trackedge.charAt(0);
        
        //print the spaces before the horse
        lane += multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            lane += '\u274c';
        }
        else 
        {
            lane += theHorse.getSymbol();
        }
        
        //print the spaces after the horse
        lane += multiplePrint(' ',spacesAfter);
        
        //print the custom symbol for the end of the track
        lane += trackedge.charAt(0);

        // Printing the Details of the contestant
        lane += "  " + theHorse.getName().toUpperCase() + " (Current confidence " + theHorse.getConfidence() + ")";

        return lane;
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private static String multiplePrint(char aChar, int times)
    {
        String mul = "";
        for(int i =0; i<times; i++){
            mul += aChar;
        }
        int i = 0;
        return mul;
    }
}