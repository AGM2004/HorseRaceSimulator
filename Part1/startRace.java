class Horse{
    private String horseName;
    private char horseSymbol;
    private int distance;
    private boolean fallen;
    private double horseConfidence;

    public Horse(char horseSymbol, String horseName, double horseConfidence){
        this.horseName = horseName;
        this.horseSymbol = horseSymbol;
        this.horseConfidence = horseConfidence;
        this.fallen = false;
        this.distance = 0;
    }

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
