package model;
import exceptions.*;
import interfaces.*;

public class Dog extends Pet implements Playable {
    private String breed;

    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    public void play() throws NoEnergyException, NotHappyException {
        if(this.happiness < 10) {
            this.happiness ++;
        }
        if(this.happiness <= 0) {
            throw new NotHappyException("The pet is too sad(.");
        }
        if(this.energy > 0) {
            this.energy --;
        }else{
            throw new NoEnergyException("Not enough energy to play!");
        }
        if(this.hunger < 10) {
            this.hunger ++;
        }
    }

    public void sleep() throws isHungryException {
        if(this.energy < 10) {
            this.energy ++;
        }
        if(this.hunger >= 10) {
            throw new isHungryException("Energy is full!");
        }
        if(energy >= 10) {
            throw new isHungryException("Energy is full!");
        }

        if(this.hunger < 10) {
            this.hunger += 2;
        }
        if(this.hunger <= 0) {
            throw new isHungryException("Not enough hunger to play!");
        }
        if(hunger >= 10) {
            throw new isHungryException("model.Pet is very hungry");

        }

    }



public String showStatus() {
    return super.showStatus() + "   Breed: " + this.breed;

}
}