package com.magic;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    private int btnWidth = 120;
    private int btnHeight = 30;

    private int easyDifficultLives = 5;
    private int mediumDifficultLives = 3;
    private int hardDifficultLives = 1;

    private int easyDifficultCount = 5;
    private int mediumDifficultCount = 6;
    private int hardDifficultCount = 7;

    private int easyDifficultAnswers = 1;
    private int mediumDifficultAnswers = 2;
    private int hardDifficultAnswers = 3;
    // правила
    private String rules = "Игра 'Волшебные числа'\n\n" +
            "Загаданы выражения. Нужно угадать какой знак (+, - или *) стоит вместо знака(ов) вопроса. \n" +
            "Выберите уровень игры на котором хотите поиграть. \n" +
            "\nЛегкий:" + easyDifficultLives + " жизней, " + easyDifficultCount + " вопросов\n" +
            "\nСредний:" + mediumDifficultLives + " жизни, " + mediumDifficultCount + " вопросов\n" +
            "\nВысокий:" + hardDifficultLives + " жизнь, " + hardDifficultCount + " вопросов\n";

    StartPanel(){
        JPanel panelBtns = new JPanel(new FlowLayout());

        JButton easy = new JButton("Легкий");
        JButton medium = new JButton("Средний");
        JButton hard = new JButton("Сложный");

        easy.setPreferredSize(new Dimension(btnWidth,btnHeight));
        medium.setPreferredSize(new Dimension(btnWidth,btnHeight));
        hard.setPreferredSize(new Dimension(btnWidth,btnHeight));
        easy.addActionListener(e -> {
            this.startGame(DifficultLvl.EASY);
        });

        medium.addActionListener(e -> {
            this.startGame(DifficultLvl.MEDIUM);
        });

        hard.addActionListener(e -> {
            this.startGame(DifficultLvl.HARD);
        });

        panelBtns.add(easy);
        panelBtns.add(medium);
        panelBtns.add(hard);
        JTextArea rules = new JTextArea(this.rules);
        rules.setEnabled(false);

        rules.setDisabledTextColor(Color.BLACK);
        JPanel textPanel = new JPanel();
        textPanel.add(rules);
        this.add(textPanel, BorderLayout.CENTER);
        this.add(panelBtns, BorderLayout.PAGE_END);
    }
        // метод отвечающий за старт игры. принимает один из enum сложности
    private void startGame(DifficultLvl difficultLvl){
        JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(this);
        mainFrame.remove(this);
        switch (difficultLvl){
            case EASY:
                mainFrame.add(new QuestionsPanel(easyDifficultLives,easyDifficultCount,easyDifficultAnswers));
                break;
            case MEDIUM:
                mainFrame.add(new QuestionsPanel(mediumDifficultLives,mediumDifficultCount,mediumDifficultAnswers));
                break;
            case HARD:
                mainFrame.add(new QuestionsPanel(hardDifficultLives,hardDifficultCount,hardDifficultAnswers));
                break;
        }
        mainFrame.validate();
    }
}
