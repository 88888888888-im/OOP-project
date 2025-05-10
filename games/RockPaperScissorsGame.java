package games;

import exceptions.NoEnergyException;
import exceptions.NotHappyException;
import model.Pet;

import java.util.Random;

public class RockPaperScissorsGame {
    private Pet pet;
    private String programChoice;  // To store the program's choice
    private boolean roundComplete; // To track the round completion status

    public RockPaperScissorsGame(Pet pet) {
        this.pet = pet;
        this.roundComplete = false;  // Initially, round is not complete
    }

    // Main game logic for playing a round
    public String playRound(String playerChoice) throws NoEnergyException, NotHappyException {
        String[] choices = {"Rock", "Paper", "Scissors"};
        Random random = new Random();
        programChoice = choices[random.nextInt(3)];  // Randomly choose for the program


        String result = "";
        if (playerChoice.equals(programChoice)) {
            result = "It's a Draw!";
        } else if ((playerChoice.equals("Rock") && programChoice.equals("Scissors")) ||
                (playerChoice.equals("Paper") && programChoice.equals("Rock")) ||
                (playerChoice.equals("Scissors") && programChoice.equals("Paper"))) {
            result = "You Win!";
            pet.play();
        } else {
            result = "You Lose!";
        }

        return "You chose " + playerChoice + ". Program chose " + programChoice + ". " + result;
    }


    public boolean isRoundComplete() {
        return roundComplete;
    }


    public void setProgramChoice(String programChoice) {
        this.programChoice = programChoice;
    }

    public String getProgramChoice() {
        return programChoice;
    }


    public void setRoundComplete(boolean b) {
        this.roundComplete = b;
    }
}