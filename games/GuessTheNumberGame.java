package games;

import exceptions.NoEnergyException;
import exceptions.NotHappyException;
import model.Pet;

import java.util.Random;

public class GuessTheNumberGame {
    private Pet pet;

    public GuessTheNumberGame(Pet pet) {
        this.pet = pet;
    }

    public boolean checkGuess(int userGuess) throws NoEnergyException, NotHappyException {
        Random random = new Random();
        int correctNumber = random.nextInt(10) + 1;

        if (userGuess == correctNumber) {
            pet.play();
            return true;
        } else {
            return false;
        }
    }
}