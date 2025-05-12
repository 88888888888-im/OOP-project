package games;

import exceptions.NoEnergyException;
import exceptions.NotHappyException;
import model.Pet;

import java.util.Random;

public class RockPaperScissorsGame {
    private Pet pet;
    private String programChoice;
    private boolean roundComplete;

    public RockPaperScissorsGame(Pet pet) {
        this.pet = pet;
        this.roundComplete = false;
    }


    public String playRound(String playerChoice) throws NoEnergyException, NotHappyException {
        String[] choices = {"Rock", "Paper", "Scissors"};
        Random random = new Random();
        programChoice = choices[random.nextInt(3)];


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