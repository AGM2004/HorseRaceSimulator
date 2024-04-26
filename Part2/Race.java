import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;
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
    private static ArrayList<Long> times = new ArrayList<>();
    private static ArrayList<int[]> AllDistance = new ArrayList<>();
    private static ArrayList<int[]> RaceWins = new ArrayList<>();
    private static int NoUsers;
    private static ArrayList<String[][]> AllBets = new ArrayList<>();
    private static String[] users = null;
    private static Double[] wratio = null;
    private static Double[] owed = null;
    private static Boolean betmade = false;

    public static void main(String args[]){
        startRaceGUI();
    }

    public synchronized static void startRaceGUI() {
        JFrame frame = new JFrame("Details");
        frame.setLayout(new BorderLayout());
        frame.setSize(750, 200);;
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel(new GridLayout(5,2));

        JLabel l1 = new JLabel("How many horses do you have?    ");
        panel1.add(l1);
        JTextField t1 = new JTextField(10);
        panel1.add(t1);

        JLabel l2 = new JLabel("Whats the length of the track?  ");
        panel1.add(l2);
        JTextField t2 = new JTextField(10);
        panel1.add(t2);

        JLabel l3 = new JLabel("What symbol do you want to represent the top of the track?  ");
        panel1.add(l3);
        JTextField t3 = new JTextField(10);
        panel1.add(t3);

        JLabel l4 = new JLabel("What symbol do you want to represent the edge of the track? ");
        panel1.add(l4);
        JTextField t4 = new JTextField(10);
        panel1.add(t4);

        panel1.add(new JLabel("How many user are betting?   "));
        JTextField numberofusers = new JTextField(10);
        panel1.add(numberofusers);

        panel1.setLocation(200, 10);

        frame.add(panel1, BorderLayout.CENTER);

        JButton done = new JButton("Done");
        done.setLocation(20,80);
        frame.add(done, BorderLayout.SOUTH);
        frame.setVisible(true);
        try{
            done.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    int check1 = Integer.valueOf(t1.getText());
                    int check2 = Integer.valueOf(t2.getText());
                    int check3 = Integer.valueOf(numberofusers.getText());

                    if(check1 > 0 && check2 > 0 && check3 > 0){
                        contestents = check1;
                        raceLength = check2;
                        trackboundaries = t3.getText();
                        trackedge = t4.getText();
                        NoUsers = check3;

                        frame.dispose();
                        
                        wratio = new Double[contestents];
                        for(int w =0; w<contestents; w++){
                            wratio[w] = 0.0;
                        }
                        users = new String[NoUsers];
                        owed = new Double[NoUsers];
                        for(int i =0; i<NoUsers; i++){
                            owed[i] = 0.0;
                        }

                        // Taking inputs of horses

                        JFrame ftemp = new JFrame("Horse Details");
                        ftemp.setLayout(new BorderLayout());
                        ftemp.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        ftemp.setVisible(true);
                        ftemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        JPanel panelinputs = new JPanel(new GridLayout(contestents,8));
                        JPanel userinput = new JPanel(new GridLayout(NoUsers, 2));
                        JPanel Center = new JPanel(new GridLayout(4,1));

                        JTextField[] N = new JTextField[contestents];
                        JTextField[] S = new JTextField[contestents];
                        JLabel[] C = new JLabel[contestents];
                        JTextField[] B = new JTextField[contestents];
                        JTextField[] U = new JTextField[NoUsers];
                        Random rd = new Random();

                        for(int i = 0; i<contestents; i++){
                            N[i] = new JTextField();
                            S[i] = new JTextField();
                            C[i] = new JLabel(String.valueOf(Double.valueOf(rd.nextInt(9) +1)/10.0));
                            B[i] = new JTextField();

                            int lane = i + 1;
                            JLabel name = new JLabel("What is the name of horse " + lane + " ?");
                            JLabel symbol = new JLabel("What is the symbol of horse " + lane + " ?");
                            JLabel confidence = new JLabel("Confidence of horse " + lane);
                            JLabel breed = new JLabel("What is the breed of horse " + lane + " ?");

                            panelinputs.add(name);
                            panelinputs.add(N[i]);
                            panelinputs.add(symbol);
                            panelinputs.add(S[i]);
                            panelinputs.add(confidence);
                            panelinputs.add(C[i]);
                            panelinputs.add(breed);
                            panelinputs.add(B[i]);
                        }

                        for(int u=0; u<NoUsers;u++){
                            U[u] = new JTextField();

                            JLabel user = new JLabel("What is the name of User " + (u+1) + "?");
                            user.setHorizontalAlignment(SwingConstants.CENTER);
                            userinput.add(user);
                            userinput.add(U[u]);
                        }

                        JButton add = new JButton("Add");

                        JLabel HI = new JLabel("Horse Inputs");
                        HI.setHorizontalAlignment(SwingConstants.CENTER);
                        Center.add(HI);
                        Center.add(panelinputs);
                        JLabel UI = new JLabel("User Inputs");
                        UI.setHorizontalAlignment(SwingConstants.CENTER);
                        Center.add(UI);
                        Center.add(userinput);
                        JScrollPane CINFO = new JScrollPane(Center);
                        ftemp.add(CINFO, BorderLayout.CENTER);
                        ftemp.add(add, BorderLayout.SOUTH);

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
                                        String horsebreed = B[lane].getText(); 
                                        H = new Horse(horseSymbol, horseName, horseConfidence, horsebreed);
                                        addHorse(H,lane);
                                    }
                                    for(int u=0; u<NoUsers;u++){
                                        users[u] = U[u].getText();
                                    }

                                    ftemp.dispose();

                                    // Opening the Race Page

                                    JFrame Race = new JFrame("Horse Race");
                                    Race.setLayout(new BorderLayout());
                                    Race.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                    Race.setVisible(true);
                                    Race.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                    JPanel Details = new JPanel(new BorderLayout());
                                    JPanel Options = new JPanel(new GridLayout(1,2));

                                    JButton Stats = new JButton("Statistics");
                                    JButton Customise = new JButton("Customise");
                                    JButton Bets = new JButton("Bets");
                                    JButton start = new JButton("Start");

                                    JLabel currentrace = new JLabel();

                                    Options.add(Stats);
                                    Options.add(Customise);

                                    JTextArea completerace = new JTextArea();
                                    JLabel winners = new JLabel();

                                    start.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e){
                                            if (betmade == false){
                                                Bets.doClick();
                                            }
                                            else{
                                                //reset all the lanes (all horses not fallen and back to 0).
                                                int lane=0;
                                                while(lane < contestents){
                                                    Horses[lane].goBackToStart();
                                                    Horses[lane].stand();
                                                    lane++;
                                                }

                                                int[] HorseDistances = new int[contestents];
                                                int[] HorseWin = new int[contestents];

                                                Long StartTime = System.nanoTime();  
                                                new Thread(()->{
                                                //declare a local variable to tell us when the race is finished
                                                boolean finished = false; 
                                                while(!finished){
                                                    //move each horse
                                                    int lane2=0;
                                                    while(lane2 < contestents){
                                                        moveHorse(Horses[lane2]);
                                                        lane2++;
                                                    }

                                                    //print the race positions
                                                    races = printRace();

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
                                                                HorseWin[i] = 1;
                                                            }
                                                            else{
                                                                HorseWin[i] = 0;
                                                            }
                                                        }
                                                     if (winners == 1 && won == true){
                                                            message = winnernames + "has won the race!! ";
                                                            finished = true;
                                                        }
                                                        else if (won == true && winners>1){
                                                            message = "The race was a tie between " + winnernames;
                                                            finished = true;
                                                        }
                                                     if(won == true){
                                                            for(int i = 0; i<Horses.length; i++){
                                                                HorseDistances[i] = Horses[i].getDistanceTravelled();
                                                            }
                                                        }
                                                    }
                                                    else {
                                                        message = "All horses have fallen race has ended!! :( ";
                                                        finished = true;
                                                        for(int i = 0; i<contestents; i++){
                                                            HorseDistances[i] = Horses[i].getDistanceTravelled();
                                                            HorseWin[i] = 0;
                                                        }
                                                    }

                                                    try{
                                                        TimeUnit.MILLISECONDS.sleep(10);
                                                    }catch(Exception e2){}
                                                    completerace.setText(races);
                                                    Race.add(completerace, BorderLayout.CENTER);
                                                    Race.add(new JLabel("                           "), BorderLayout.WEST);
                                                    Race.add(new JLabel("                           "), BorderLayout.EAST);
                                                    winners.setText(message);
                                                }}).start();
                                                Long EndTime = System.nanoTime();

                                                currentRace++;
                                                RaceWins.add(HorseWin);

                                                wratio = new Double[contestents];
                                                for(int r1 = 1; r1<=currentRace; r1++){
                                                    for(int h1 = 0; h1< contestents; h1++){
                                                        int Twins = 0;
                                                        for (int[] R : RaceWins){
                                                            Twins += R[h1];  
                                                        }
                                                        wratio[h1] = (Double.valueOf(Twins)/Double.valueOf(currentRace));
                                                    }
                                                }

                                                if(betmade == true){
                                                    String[][] currentracebet = AllBets.get(currentRace - 1);
                                                    for(int u=0; u<NoUsers; u++){
                                                        for(int w =0; w<HorseWin.length; w++){
                                                            if(Integer.valueOf(currentracebet[u][1]) == w && HorseWin[w] == 1){
                                                                owed[u] += Double.valueOf(currentracebet[u][2])*(1+(1-wratio[w]));
                                                            }
                                                            else if (Integer.valueOf(currentracebet[u][1]) == w && HorseWin[w] == 0){
                                                                owed[u] -= Double.valueOf(currentracebet[u][2]) ;   
                                                            }
                                                        }
                                                    }
                                                }

                                                AllDistance.add(HorseDistances);
                                                times.add(EndTime - StartTime);

                                                winners.setHorizontalAlignment(SwingConstants.CENTER);
                                                races = "";
                                                message = "";

                                                currentrace.setText("Race Number is: " + String.valueOf(currentRace));
                                                start.setText("Restart");
                                                betmade = false;
                                            }
                                        }
                                    });
                                    
                                    Stats.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e){
                                            JFrame StatsPage = new JFrame("Statistics");
                                            StatsPage.setLayout(new BorderLayout());
                                            StatsPage.setLocation(500, 100);
                                            StatsPage.setSize(400,500);
                                            //StatsPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                            String det = "";

                                            for(int r = 0; r< currentRace; r++){
                                                det += "Race Number " + (r+1) + "\n\n";                                                
                                                for(int h=0; h<Horses.length; h++){                                                                                                     
                                                    det += Horses[h].getName() + ":" + "\n";
                                                    int[] distances = AllDistance.get(r);                                              
                                                    det += "Average Speed:  " + (Double.valueOf(distances[h])/(Double.valueOf(times.get(r))*(Math.pow(10, -9)))) + "\n";
                                                    det += "Race Finishing Time:  " + (Double.valueOf(times.get(r))*(Math.pow(10, -9)))  + " Seconds" + "\n";
                                                    det += "The ratio of wins are:  " + wratio[h] + "\n\n";  
                                                                                         
                                                }
                                            }
                                            JTextArea statsdisplay = new JTextArea(det);
                                            JScrollPane scrollpane = new JScrollPane(statsdisplay);

                                            StatsPage.add(scrollpane, BorderLayout.CENTER);
                                            StatsPage.setVisible(true);
                                        }
                                    });

                                    Customise.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e){
                                            JFrame CustomisePage = new JFrame("Customise Horse");
                                            CustomisePage.setLayout(new BorderLayout());
                                            CustomisePage.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                            CustomisePage.setVisible(true);
                                            CustomisePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                            JPanel Top = new JPanel(new GridLayout(3,3));
                                            JPanel HorseDetails = new JPanel(new GridLayout(2,3));

                                            JTextField input = new JTextField();
                                            
                                            String[] S = {"Change Symbol of horse", "Change breed"};
                                            String[] C = {"Black", "Green", "Blue", "Gray", "Red"};
                                            JComboBox Options = new JComboBox<>(S);
                                            JComboBox Colours = new JComboBox<>(C);
                                            
                                            String horses = "";
                                            for(int h=0; h<Horses.length;h++){
                                                horses+=Horses[h].getName() + ",";
                                            }

                                            String[] hnames = horses.split(",");
                                            JComboBox horsename = new JComboBox<>(hnames);

                                            HorseDetails.add(new JLabel());
                                            HorseDetails.add(new JLabel(String.valueOf(horsename.getSelectedItem())));
                                            HorseDetails.add(new JLabel());
                                            for(int hd =0; hd<Horses.length; hd++){
                                                if(String.valueOf(horsename.getSelectedItem()).equals(Horses[hd].getName())){
                                                    HorseDetails.add(new JLabel("Confidence: "+String.valueOf(Horses[hd].getConfidence())));
                                                    HorseDetails.add(new JLabel("Symbol: " + String.valueOf(Horses[hd].getSymbol())));
                                                    HorseDetails.add(new JLabel("Breed: " + String.valueOf(Horses[hd].getBreed())));
                                                }
                                            }

                                            JButton change = new JButton("Change");

                                            Top.add(new JLabel("What action do you want to perform?"));
                                            Top.add(new JLabel("Name of horse for action to be performed on"));
                                            Top.add(new JLabel("What do you want to change it to ?"));

                                            Top.add(Options);
                                            Top.add(horsename);
                                            Top.add(input);

                                            Top.add(new JLabel("Change color of Race to "));
                                            Top.add(Colours);
                                            Top.add(new JLabel());

                                            change.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e){
                                                    
                                                    if(Options.getSelectedIndex() == 0){
                                                        for(Horse i : Horses){
                                                            if(horsename.getSelectedItem().equals(i.getName())){
                                                                i.setSymbol(input.getText().charAt(0));
                                                            }
                                                        }
                                                    }

                                                    else if(Options.getSelectedIndex() == 1){
                                                        for(Horse i : Horses){
                                                            if(horsename.getSelectedItem().equals(i.getName())){
                                                                i.setBreed(input.getText());
                                                            }
                                                        }
                                                    }
                                                    
                                                    if(Colours.getSelectedIndex() == 1){
                                                        completerace.setForeground(Color.green);
                                                    }
                                                    else if(Colours.getSelectedIndex() == 2){
                                                        completerace.setForeground(Color.blue);
                                                    }
                                                    else if(Colours.getSelectedIndex() == 3){
                                                        completerace.setForeground(Color.gray);
                                                    }
                                                    else if(Colours.getSelectedIndex() == 4){
                                                        completerace.setForeground(Color.red);
                                                    }
                                                    else if(Colours.getSelectedIndex() == 0){
                                                        completerace.setForeground(Color.black);
                                                    }

                                                    CustomisePage.dispose();
                                                }
                                            });

                                            CustomisePage.add(Top, BorderLayout.NORTH);
                                            CustomisePage.add(new JLabel("              "), BorderLayout.WEST);
                                            CustomisePage.add(HorseDetails, BorderLayout.CENTER);
                                            CustomisePage.add(new JLabel("              "), BorderLayout.EAST);
                                            CustomisePage.add(change, BorderLayout.SOUTH);
                                        }
                                    });


                                    Bets.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e){
                                            JFrame Betting = new JFrame("Betting Page");
                                            Betting.setLayout(new BorderLayout());
                                            Betting.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                            Betting.setVisible(true);
                                            Betting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                            JPanel BettingPage = new JPanel(new GridLayout(3,1));

                                            JPanel UserInputs = new JPanel(new GridLayout(NoUsers,6));

                                            JTextField[] B = new JTextField[NoUsers];
                                            JComboBox[] H = new JComboBox[NoUsers];

                                            String[] horsenames = new String[contestents];
                                            for(int hs = 0; hs< contestents; hs++){
                                                horsenames[hs] = Horses[hs].getName();
                                            }

                                            for(int i = 0; i<NoUsers; i++){
                                                H[i] = new JComboBox<>(horsenames);
                                                B[i] = new JTextField("0");

                                                JLabel username = new JLabel("User Name " + (i+1) + ":"); 
                                                username.setHorizontalAlignment(SwingConstants.CENTER);
                                                JLabel amount = new JLabel("Amount of user " + (i+1) + ":");
                                                amount.setHorizontalAlignment(SwingConstants.CENTER);
                                                JLabel horse = new JLabel("Horse:");
                                                horse.setHorizontalAlignment(SwingConstants.CENTER);

                                                UserInputs.add(username);
                                                UserInputs.add(new JLabel(users[i]));
                                                UserInputs.add(amount);
                                                UserInputs.add(B[i]);
                                                UserInputs.add(horse);
                                                UserInputs.add(H[i]);
                                            }

                                            Betting.add(UserInputs, BorderLayout.NORTH);

                                            JPanel horseratios = new JPanel(new GridLayout(2,contestents));

                                            Double bestratio = 0.0;
                                            String besthorse = "";
                                            for(int h =0; h<contestents; h++){
                                                JLabel hname = new JLabel("Winning ratio of " + Horses[h].getName() + " is ");
                                                hname.setHorizontalAlignment(SwingConstants.CENTER);
                                                horseratios.add(hname);
                                            }
                                            for(int h2 =0; h2<contestents && currentRace > 0; h2++){
                                                JLabel hr = new JLabel(String.valueOf(wratio[h2]));
                                                hr.setHorizontalAlignment(SwingConstants.CENTER);
                                                horseratios.add(hr);
                                                if(wratio[h2]>bestratio){
                                                    bestratio = wratio[h2];
                                                    besthorse = Horses[h2].getName();
                                                }
                                            }

                                            BettingPage.add(horseratios);

                                            JLabel bestbet = new JLabel("The best horse to bet on is " + besthorse + " with a current winning ratio of " + bestratio);
                                            bestbet.setHorizontalAlignment(SwingConstants.CENTER);
                                            BettingPage.add(bestbet);

                                            
                                            JPanel RaceBetHistory = new JPanel(new GridLayout(2,currentRace));
                                            for(int race = 1; race <= currentRace; race++){
                                                JLabel racename = new JLabel("Race " + race);
                                                racename.setHorizontalAlignment(SwingConstants.CENTER);
                                                RaceBetHistory.add(racename);                                            
                                            }

                                            for(int ab =0; ab<AllBets.size(); ab++){
                                                JPanel UserBetCalc = new JPanel(new GridLayout(NoUsers,2));
                                                String[][] bethistory = AllBets.get(ab); 
                                                for(int u=0; u<NoUsers; u++){
                                                    UserBetCalc.add(new JLabel(" User: " + bethistory[u][0]));
                                                    UserBetCalc.add(new JLabel(" Horse at lane: " + (Integer.valueOf(bethistory[u][1])+1)));
                                                    UserBetCalc.add(new JLabel(" Money bet: " + bethistory[u][2]));
                                                    UserBetCalc.add(new JLabel(" Moeny owed : " + bethistory[u][3]));
                                                }
                                                RaceBetHistory.add(UserBetCalc);
                                            }

                                            JScrollPane Userbethistory = new JScrollPane(RaceBetHistory);
                                            BettingPage.add(Userbethistory);

                                            Betting.add(BettingPage, BorderLayout.CENTER);

                                            JButton PlaceBet = new JButton("Place Bet");
                                            Betting.add(PlaceBet, BorderLayout.SOUTH);
                                            PlaceBet.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e){
                                                    String[][] HistoryAdd = new String[NoUsers][4];
                                                    for(int u=0; u<NoUsers; u++){
                                                        HistoryAdd[u][0] = users[u];
                                                        HistoryAdd[u][1] = String.valueOf(H[u].getSelectedIndex());
                                                        HistoryAdd[u][2] = B[u].getText();   
                                                        HistoryAdd[u][3] = String.valueOf(owed[u]);
                                                    }
                                                    AllBets.add(HistoryAdd);
                                                    betmade = true;
                                                    Race.setVisible(true);
                                                    Betting.dispose();
                                                }
                                            });
                                        }
                                    });


                                    Details.add(currentrace, BorderLayout.WEST);
                                    Details.add(winners,BorderLayout.CENTER);
                                    Details.add(start, BorderLayout.EAST);

                                    Race.add(Details, BorderLayout.NORTH);
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
    private static boolean raceWonBy(Horse theHorse)
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