package gui;

import exceptions.NoEnergyException;
import exceptions.NotHappyException;
import exceptions.isHungryException;
import games.GuessTheNumberGame;
import games.ProblemSolverGame;
import games.RockPaperScissorsGame;
import model.Cat;
import model.Dog;
import model.Pet;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class PetGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel containerPanel;
    private JLabel gifLabel;
    private JLabel statusLabel;
    private JLabel nameLabel;
    private Pet pet;
    private JPanel gamePanel;
    private Timer sleepTimer;
    private java.util.List<Pet> petList ;
    private String petName;
    private String petType;
    private String foodType;

    public PetGUI() {
        petList = new ArrayList<>();
        frame = new JFrame("Virtual Pet Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        containerPanel.add(createWelcomePanel(), "welcome");
        frame.add(containerPanel);
        frame.setVisible(true);

    }

    private JPanel createWelcomePanel() {
        ImageIcon bgIcon = new ImageIcon("src/resources/mainPic.png");
        Image bgImage = bgIcon.getImage();

        JPanel welcomePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        welcomePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to Virtual Pet Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        welcomePanel.add(titleLabel, gbc);

        JTextField petNameField = new JTextField();
        gbc.gridy++;
        gbc.gridwidth = 1;
        welcomePanel.add(new JLabel("Pet's Name: ", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        welcomePanel.add(petNameField, gbc);

        String[] petTypes = {"Dog", "Cat"};
        JComboBox<String> petTypeBox = new JComboBox<>(petTypes);
        gbc.gridx = 0;
        gbc.gridy++;
        welcomePanel.add(new JLabel("Choose Pet: ", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        welcomePanel.add(petTypeBox, gbc);

        JButton startButton = new JButton("Start Game");
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        welcomePanel.add(startButton, gbc);

        startButton.addActionListener(e -> {
            petType = ((String) petTypeBox.getSelectedItem()).toLowerCase();
            petName = petNameField.getText().isEmpty() ? "Buddy" : petNameField.getText();

            if (petType.equals("dog")) {
                foodType = "bone";
                pet = new Dog(petName, "Labrador");
            } else {
                foodType = "fish";
                pet = new Cat(petName);
            }

            petList.add(pet);

            containerPanel.add(createGamePanel(), "game");
            cardLayout.show(containerPanel, "game");
        });


        JButton choosePetButton = new JButton("Choose Existing Pet");
        gbc.gridy++;
        welcomePanel.add(choosePetButton, gbc);

        choosePetButton.addActionListener(e -> {
            if (petList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No pets available. Please create one first.", "No Pets", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JPanel selectionPanel = createPetSelectionPanel();
                containerPanel.add(selectionPanel, "choosePet");
                cardLayout.show(containerPanel, "choosePet");
            }
        });

        return welcomePanel;
    }

    private JPanel createPetSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Choose an Existing Pet", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Pet p : petList) {
            listModel.addElement(p.getName() + " (" + p.getClass().getSimpleName() + ")");
        }

        JList<String> petJList = new JList<>(listModel);
        petJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        petJList.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(petJList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            int selectedIndex = petJList.getSelectedIndex();
            if (selectedIndex >= 0) {
                pet = petList.get(selectedIndex);
                petName = pet.getName();
                petType = (pet instanceof Dog) ? "dog" : "cat";
                foodType = (pet instanceof Dog) ? "bone" : "fish";

                containerPanel.add(createGamePanel(), "game");
                cardLayout.show(containerPanel, "game");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a pet.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(containerPanel, "welcome"));

        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createGamePanel() {

        gamePanel = new JPanel(new BorderLayout());

        nameLabel = new JLabel("Name: " + petName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gamePanel.add(nameLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        statusLabel = new JLabel(pet.showStatus(), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        centerPanel.add(statusLabel, BorderLayout.NORTH);

        gifLabel = new JLabel();
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setPetGIF("stand");
        centerPanel.add(gifLabel, BorderLayout.CENTER);

        gamePanel.add(centerPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createImageButton("src/resources/btn_feed_"+petType+".JPEG", 120, 110, e -> showFeedScene()));
        buttonPanel.add(createImageButton("src/resources/btn_sleep_"+petType+".JPEG", 120, 110, e -> startSleepMode()));
        buttonPanel.add(createImageButton("src/resources/btn_play_"+petType+".JPEG", 120, 110, e -> {
            JPanel miniGameScreen = createPlayMiniGamePanel();
            containerPanel.add(miniGameScreen, "miniGames");
            cardLayout.show(containerPanel, "miniGames");
        }));

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton switchPetButton = new JButton("Switch Pet");
        switchPetButton.addActionListener(e -> {
            cardLayout.show(containerPanel, "welcome");
        });

        gamePanel.add(switchPetButton, BorderLayout.WEST);
        return gamePanel;
    }
    private JButton createImageButton(String imagePath, int width, int height, ActionListener action) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.addActionListener(action);
        return button;
    }

    private void showFeedScene() {
        JPanel feedPanel = new JPanel(new BorderLayout());

        JLabel kitchenLabel = new JLabel(new ImageIcon("src/resources/" + petType + "_kitchen.gif"));
        kitchenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedPanel.add(kitchenLabel, BorderLayout.CENTER);

        ImageIcon icon = new ImageIcon("src/resources/btn_"+foodType+".JPEG");
        Image scaled = icon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
        JButton boneButton = new JButton(new ImageIcon(scaled));
        boneButton.setPreferredSize(new Dimension(50, 100));
        feedPanel.add(boneButton, BorderLayout.SOUTH);

        JButton goBackButton = new JButton("Go Back");
        feedPanel.add(goBackButton, BorderLayout.NORTH);

        boneButton.addActionListener(e -> {
            try {
                pet.feed();
                kitchenLabel.setIcon(getPetGIF("eat"));
                Timer delay = new Timer();
                delay.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> returnToMainScreen());
                    }
                }, 1500);
            } catch (Exception ex) {
                showError(ex.getMessage());
                returnToMainScreen();
            }
        });

        goBackButton.addActionListener(e -> returnToMainScreen());

        containerPanel.add(feedPanel, "feed");
        cardLayout.show(containerPanel, "feed");
    }

    private void startSleepMode() {
        JPanel sleepPanel = new JPanel(new BorderLayout());

        JLabel sleepGIF = new JLabel();
        sleepGIF.setHorizontalAlignment(SwingConstants.CENTER);
        sleepGIF.setIcon(getPetGIF("sleep"));
        sleepPanel.add(sleepGIF, BorderLayout.CENTER);

        JLabel sleepStatus = new JLabel(pet.showStatus(), SwingConstants.CENTER);
        sleepStatus.setFont(new Font("Monospaced", Font.BOLD, 16));
        sleepPanel.add(sleepStatus, BorderLayout.NORTH);

        JButton goBackButton = new JButton("Go Back");
        sleepPanel.add(goBackButton, BorderLayout.SOUTH);

        containerPanel.add(sleepPanel, "sleep");
        cardLayout.show(containerPanel, "sleep");

        sleepTimer = new Timer();
        sleepTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (pet.getEnergy() < 10) {
                    try {
                        pet.sleep();
                    }catch(isHungryException m){
                        showError(m.getMessage());
                    }
                    SwingUtilities.invokeLater(() -> sleepStatus.setText(pet.showStatus()));
                } else {
                    sleepTimer.cancel();
                    returnToMainScreen();
                }
            }
        }, 0, 2000);

        goBackButton.addActionListener(e -> {
            sleepTimer.cancel();
            returnToMainScreen();
        });
    }

    private void returnToMainScreen() {
        updateStatus();
        setPetGIF("stand");
        cardLayout.show(containerPanel, "game");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        returnToMainScreen();
    }

    private void setPetGIF(String action) {
        gifLabel.setIcon(getPetGIF(action));
    }


    private ImageIcon getPetGIF(String action) {
        String path = "src/resources/" + petType + "_" + action + ".gif";
        File imageFile = new File(path);

         return new ImageIcon(path);

    }

    private JPanel createPlayMiniGamePanel() {
        JPanel playMenuPanel = new JPanel(new BorderLayout());

        // Background Image
        JLabel background = new JLabel(new ImageIcon("src/resources/games_background.JPEG"));
        background.setLayout(new GridBagLayout()); // To center buttons
        playMenuPanel.add(background, BorderLayout.CENTER);

        // Center panel with 3 buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false); // transparent background
        buttonsPanel.setLayout(new GridLayout(3, 2, 70, 50));

        // Rock-Paper-Scissors Button
        JButton rpsButton = createImageButton("src/resources/btn_rps.png", 170, 110, e -> showRockPaperScissorsGame());

        // Problem Solver Button
        JButton problemButton = createImageButton("src/resources/btn_problem.png", 170, 110, e -> showProblemSolverGame());

        // Guess the Number Button
        JButton guessButton = createImageButton("src/resources/btn_guess.png", 170, 110, e -> showGuessNumberGame());

        buttonsPanel.add(rpsButton);
        buttonsPanel.add(problemButton);
        buttonsPanel.add(guessButton);

        background.add(buttonsPanel, new GridBagConstraints());

        // Back button
        JButton goBack = new JButton("Go Back");
        playMenuPanel.add(goBack, BorderLayout.SOUTH);
        goBack.addActionListener(e -> returnToMainScreen());

        return playMenuPanel;
    }


    private void showRockPaperScissorsGame() {
        RockPaperScissorsGame gameLogic = new RockPaperScissorsGame(pet);

        JPanel rpsPanel = new JPanel(new BorderLayout(20, 20));
        rpsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rpsPanel.setBackground(Color.DARK_GRAY);

        // TOP: Result label
        JLabel resultLabel = new JLabel("Choose your move!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 22));
        resultLabel.setForeground(Color.WHITE);
        rpsPanel.add(resultLabel, BorderLayout.NORTH);

        //  CENTER: Program choice image
        JLabel programChoiceLabel = new JLabel();
        programChoiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        programChoiceLabel.setPreferredSize(new Dimension(120, 120));
        rpsPanel.add(programChoiceLabel, BorderLayout.CENTER);

        //  Move buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton rockBtn = createIconButton("src/RGame/btn_rock.JPEG", "Rock");
        JButton paperBtn = createIconButton("src/RGame/btn_paper.JPEG", "Paper");
        JButton scissorsBtn = createIconButton("src/RGame/btn_scissors.JPEG", "Scissors");

        buttonPanel.add(rockBtn);
        buttonPanel.add(paperBtn);
        buttonPanel.add(scissorsBtn);

        //  Play Again button
        JButton playAgainBtn = new JButton("Play Again");
        playAgainBtn.setFont(new Font("Arial", Font.BOLD, 14));
        playAgainBtn.setVisible(false);
        playAgainBtn.addActionListener(e -> showRockPaperScissorsGame());

        //  Go Back button
        JButton goBackBtn = new JButton("Go Back");
        goBackBtn.setFont(new Font("Arial", Font.BOLD, 14));
        goBackBtn.addActionListener(e -> returnToMainScreen());

        //  Bottom panel with buttons stacked vertically
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.DARK_GRAY);

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        goBackBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(buttonPanel);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(playAgainBtn);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(goBackBtn);

        rpsPanel.add(bottomPanel, BorderLayout.SOUTH);

        //  show panel
        containerPanel.add(rpsPanel, "rpsGame");
        cardLayout.show(containerPanel, "rpsGame");

        // program choice animation
        String[] choices = {"rock", "paper", "scissors"};
        Thread animationThread = new Thread(() -> {
            while (!gameLogic.isRoundComplete()) {
                String randomChoice = choices[(int) (Math.random() * choices.length)];
                gameLogic.setProgramChoice(randomChoice);
                ImageIcon icon = new ImageIcon("src/RGame/" + randomChoice + "_image.JPEG");
                Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                programChoiceLabel.setIcon(new ImageIcon(scaled));
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();

        // actionListener for move buttons
        ActionListener moveListener = e -> {
            String playerChoice = ((JButton) e.getSource()).getActionCommand();
            try {
                gameLogic.setRoundComplete(true);
                String result = gameLogic.playRound(playerChoice);
                pet.play();
                String finalProgramChoice = gameLogic.getProgramChoice();
                ImageIcon finalIcon = new ImageIcon("src/RGame/" + finalProgramChoice + "_image.JPEG");
                Image scaled = finalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                programChoiceLabel.setIcon(new ImageIcon(scaled));
                resultLabel.setText("<html><center>" + result.replace(". ", ".<br>") + "</center></html>");
                updateStatus();

                rockBtn.setEnabled(false);
                paperBtn.setEnabled(false);
                scissorsBtn.setEnabled(false);
                playAgainBtn.setVisible(true);
            } catch (NoEnergyException ex) {
                showError("Not enough energy to play!");
            } catch (NotHappyException ex) {
                showError(ex.getMessage());
            }
        };

        rockBtn.addActionListener(moveListener);
        paperBtn.addActionListener(moveListener);
        scissorsBtn.addActionListener(moveListener);
    }
    // ==== Helper method to create resized image buttons ====
    private JButton createIconButton(String path, String actionCommand) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaled));
        button.setActionCommand(actionCommand);
        button.setPreferredSize(new Dimension(90, 90));
        button.setBackground(Color.WHITE);
        return button;
    }

    private void showProblemSolverGame() {
        ProblemSolverGame gameLogic = new ProblemSolverGame(pet);
        JPanel problemPanel = new JPanel(new BorderLayout());

        // Background image
        JLabel background = new JLabel(new ImageIcon("src/resources/problem_background.png"));
        background.setLayout(new BorderLayout());
        problemPanel.add(background, BorderLayout.CENTER);

        // Store question in array for lambda access
        String[] fullQuestion = {gameLogic.generateNewQuestion()};
        String actualQuestion = fullQuestion[0].split("\\|")[0];

        // Center panel with vertical layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        // Question label
        JLabel questionLabel = new JLabel(actualQuestion, SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Answer input
        JTextField answerField = new JTextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 18));
        answerField.setMaximumSize(new Dimension(150, 30));
        answerField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Feedback label
        JLabel feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 20));
        feedbackLabel.setForeground(Color.RED);
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to center panel
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(answerField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(feedbackLabel);
        background.add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton submitButton = new JButton("Submit");
        JButton newQuestionButton = new JButton("New Question");
        JButton goBackButton = new JButton("Go Back");
        buttonPanel.add(submitButton);
        buttonPanel.add(newQuestionButton);
        buttonPanel.add(goBackButton);
        background.add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        submitButton.addActionListener(e -> {
            String userAnswer = answerField.getText().trim();
            try {
                boolean correct = gameLogic.checkAnswer(userAnswer, fullQuestion[0]);
                if (correct) {
                    feedbackLabel.setText(" Correct! 🎉");
                    feedbackLabel.setForeground(Color.GREEN);
                } else {
                    int correctAnswer = Integer.parseInt(fullQuestion[0].split("\\|")[1].split(":")[1]);
                    feedbackLabel.setText(" Oops! The correct answer was " + correctAnswer);
                    feedbackLabel.setForeground(Color.RED);
                }


                fullQuestion[0] = gameLogic.generateNewQuestion();
                String newActual = fullQuestion[0].split("\\|")[0];
                questionLabel.setText(newActual);
                answerField.setText("");


                pet.play();
                updateStatus();

            } catch (NoEnergyException ex) {
                showError(ex.getMessage());
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("️ Please enter a valid number.");
                feedbackLabel.setForeground(Color.RED);
            }catch(NotHappyException ex){
                showError(ex.getMessage());
            }

        });

        newQuestionButton.addActionListener(e -> {
            fullQuestion[0] = gameLogic.generateNewQuestion();
            String newActual = fullQuestion[0].split("\\|")[0];
            questionLabel.setText(newActual);
            answerField.setText("");
            feedbackLabel.setText(" ");
        });

        goBackButton.addActionListener(e -> returnToMainScreen());

        containerPanel.add(problemPanel, "problemSolver");
        cardLayout.show(containerPanel, "problemSolver");
    }


    private void showGuessNumberGame() {
        GuessTheNumberGame gameLogic = new GuessTheNumberGame(pet);
        JPanel guessPanel = new JPanel(new BorderLayout());

        // Background image
        JLabel background = new JLabel(new ImageIcon("src/resources/guess_background.JPEG"));
        background.setLayout(new BorderLayout());
        guessPanel.add(background, BorderLayout.CENTER);

        // Feedback label
        JLabel feedbackLabel = new JLabel("Pick a number between 1 and 10", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 18));
        feedbackLabel.setForeground(Color.BLACK);
        background.add(feedbackLabel, BorderLayout.NORTH);

        // Buttons 1–10
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        buttonsPanel.setOpaque(false); // transparent background

        for (int i = 1; i <= 10; i++) {
            int guess = i;
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setFont(new Font("Arial", Font.BOLD, 20));

            // Optional: Set icon on buttons
             numberButton.setIcon(new ImageIcon("src/numbers/btn_" + i + ".png"));

            numberButton.addActionListener(e -> {
                try {
                    boolean correct = gameLogic.checkGuess(guess);
                    if (correct) {
                        feedbackLabel.setText("Correct! 🎉");
                    } else {
                        feedbackLabel.setText("Try Again!");
                    }
                    updateStatus(); // Update status at the top
                } catch (NoEnergyException ex) {
                    showError("model.Pet is too tired to play!");
                }catch(NotHappyException ex){
                    showError(ex.getMessage());
                }
            });

            buttonsPanel.add(numberButton);
        }

        background.add(buttonsPanel, BorderLayout.CENTER);

        // go back button
        JButton goBack = new JButton("Go Back");
        background.add(goBack, BorderLayout.SOUTH);
        goBack.addActionListener(e -> returnToMainScreen());

        containerPanel.add(guessPanel, "guessGame");
        cardLayout.show(containerPanel, "guessGame");
    }


    private void updateStatus() {
        statusLabel.setText(pet.showStatus());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PetGUI::new);
    }
}