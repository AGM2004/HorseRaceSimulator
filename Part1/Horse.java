
/**
 * The class Horse is made up of multiple private attributes to ensure the use of encapsulation, the attributes defined in the horse method
 * define the performance of the horse within a race, and the features of the horse such as symbol and name. All of these methods have public methods assgined to them to make any sort of changes to the values,
 * as some attributes require conditions to be met for them to be inputted or used.
 * 
 * @author Ayaan Gauhar
 * @version 1.0.1
 */
public class Horse
{
    //Fields of class Horse
    private String horseName;
    private char horseSymbol;
    private int distance;
    private boolean fallen;
    private double horseConfidence;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     * 
     * @param horseSymbol This assigns the symbol choosen by the user to this object horse created
     * @param horseName This assigns the name provided by the user to this instance of horse
     * @param horseConfidence This instantiates the horseconfidence attrubute of the current instance of horseSS
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence){
        this.horseName = horseName;
        this.horseSymbol = horseSymbol;
        this.horseConfidence = horseConfidence;
        this.fallen = false;
        this.distance = 0;
    }
    
    
    //Other methods of class Horse
    public void fall(){
        this.fallen = true;
    }

    public void goBackToStart(){
        this.distance = 0;
    }

    public void moveForward(){
        this.distance++;
    }

    public void setConfidence(double newConfidence){
        if (newConfidence >= 0 && newConfidence <= 1){
            this.horseConfidence = newConfidence;
        }
        else{
            System.out.println("Error input out of bounds");
        } 
    }

    public void setSymbol(char newSymbol){
        this.horseSymbol = newSymbol;
    }

    public double getConfidence(){
        return this.horseConfidence;
    }

    public int getDistanceTravelled(){
        return this.distance;
    }

    public String getName(){
        return this.horseName;
    }

    public char getSymbol(){
        return this.horseSymbol;
    }

    public boolean hasFallen(){
        return this.fallen;
    }
}
