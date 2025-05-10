package model;
import exceptions.*;
import interfaces.Playable;

public abstract class Pet implements Playable {
    private String name;
    protected int hunger;
    protected int energy;
    protected int happiness;

    public Pet(String name) {
        this.name = name;
        this.hunger = 5;
        this.energy = 5;
        this.happiness = 5;
    }

    public String getName() {
        return this.name;
    }

    public int getEnergy(){
        return this.energy;
    }


    public void feed() throws isHungryException{
        if(this.hunger > 0){
            this.hunger--;
        }
        if(hunger >= 10){
            throw new isHungryException("Pet is hungry");
        }
    }

    public abstract void play() throws NoEnergyException, NotHappyException;

    public abstract void sleep() throws isHungryException;


    public String showStatus(){
        return "Name: " + this.name +  "   Hunger: " + this.hunger + "   Energy: " + this.energy + "   Happiness: " + this.happiness;
    }
}
