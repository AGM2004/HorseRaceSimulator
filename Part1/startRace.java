class Horse{
    String horseName;
    char horseSymbol;
    int distance;
    boolean fallen;
    double horseConfidence;

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
        this.horseConfidence = newConfidence;
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
