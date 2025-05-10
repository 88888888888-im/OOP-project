package model;
import exceptions.*;
import interfaces.Playable;

public class Cat extends Pet implements Playable {


    public Cat(String name) {
        super(name);

    }


    public void play() throws NoEnergyException, NotHappyException {
        if (this.happiness < 10) {
            this.happiness += 3;
        }
        if (this.energy > 0) {
            this.energy -= 2;
        }else{
            throw new NoEnergyException("Not enough energy to play!");
        }

        if (this.hunger < 10) {
            this.hunger += 2;
        }
    }

    public void sleep() throws isHungryException {
        if (this.energy < 10) {
            this.energy ++;
        }
        if (this.hunger < 10) {
            this.hunger++;
        }
    }


    public String showStatus() {
      return super.showStatus() ;
    }
}
