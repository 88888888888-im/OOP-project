package games;

import exceptions.NoEnergyException;
import exceptions.NotHappyException;
import model.Pet;

import java.util.Random;

public class ProblemSolverGame {
    private Pet pet;

    public ProblemSolverGame(Pet pet) {
        this.pet = pet;
    }

    public String generateNewQuestion()  {
        Random random = new Random();
        int num1 = random.nextInt(10) + 1;
        int num2 = random.nextInt(10) + 1;
        boolean isAddition = random.nextBoolean();

        int correctAnswer = isAddition ? num1 + num2 : num1 * num2;
        String questionText = isAddition ? num1 + " + " + num2 : num1 + " * " + num2;
        return "What is " + questionText + "?|CorrectAnswer:" + correctAnswer;
    }

    public boolean checkAnswer(String answer, String question) throws NoEnergyException, NotHappyException {
        int correctAnswer = Integer.parseInt(question.split("\\|")[1].split(":")[1]);
        if (Integer.parseInt(answer) == correctAnswer) {
            pet.play();
            return true;
        }
        return false;
    }
}