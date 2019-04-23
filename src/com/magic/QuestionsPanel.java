package com.magic;

import com.magic.Question.OperationType;
import com.magic.Question.Question;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionsPanel extends JPanel {

    private int currLives;
    private int currQuestions;
    private int maxQuestion;
    private int score = 0;
    private Question currentQuestion;
    private List<OperationType> answers = new ArrayList<OperationType>();
    private static String livesStr = "Жизней: ";
    private static String questionStr = "Вопросов: ";
    // панель и лейбл отвечающие за надписи количества жизней и вопросов
    private JPanel upperBound;
    private JLabel livesLabel;
    private JLabel questionsLabel;
    //панель ответа и вопроса
    private JTextArea questionText;
    private JTextArea answersText;
    private JPanel questionPanel;
    // панель кнопок и все кнопки
    private JButton plusBtn;
    private JButton minusBtn;
    private JButton multiplyBtn;
    private JButton resetBtn;
    private JButton nextBtn;
    private JPanel buttonsPanel;

    public QuestionsPanel(int lives, int count, int deep) {
        Font font = new Font("serif", Font.PLAIN, Main.fontSize);
        this.currLives = lives;
        this.maxQuestion = count;
        this.currQuestions = 1;
        currentQuestion = new Question(deep);
        currentQuestion.generateQuestion();
        upperBound = new JPanel(new BorderLayout());
        livesLabel = new JLabel(livesStr + Integer.toString(lives));
        questionsLabel = new JLabel(questionStr + Integer.toString(currQuestions) + "/" + Integer.toString(count));

        livesLabel.setFont(font);
        questionsLabel.setFont(font);

        upperBound.add(livesLabel, BorderLayout.LINE_START);
        upperBound.add(questionsLabel, BorderLayout.LINE_END);
        // создаем панель вопроса, выключаем ее избежав редактирования
        questionText = new JTextArea(currentQuestion.getQuestionString());
        questionText.setEnabled(false);
        questionText.setDisabledTextColor(Color.BLACK);
        questionText.setFont(font);
        // аналогично и для текстового поля ответов
        answersText = new JTextArea("");
        answersText.setFont(font);
        answersText.setEnabled(false);
        answersText.setDisabledTextColor(Color.BLACK);
        // создаем панель для вопросов и добавляем в нее текстовое поле с вопросом
        questionPanel = new JPanel();
        questionPanel.add(questionText);
        // инициализируем все кнопки и их наазвания
        plusBtn = new JButton("+");
        minusBtn = new JButton("-");
        multiplyBtn = new JButton("*");
        resetBtn = new JButton("Сброс");
        nextBtn = new JButton("Дальше");
        // вешаем на каждую кнопку перехват событий в зависимости от ее типа
        plusBtn.addActionListener(e -> {
            this.plus();
        });
        minusBtn.addActionListener(e -> {
            this.minus();
        });
        multiplyBtn.addActionListener(e -> {
            this.multiply();
        });
        resetBtn.addActionListener(e -> {
            this.reset();
        });
        nextBtn.addActionListener(e -> {
            this.nextQuestion();
        });
        // создаем панель для кнопок. добавляем туда кнопки
        buttonsPanel = new JPanel();
        buttonsPanel.add(minusBtn);
        buttonsPanel.add(plusBtn);
        buttonsPanel.add(multiplyBtn);
        buttonsPanel.add(resetBtn);
        buttonsPanel.add(nextBtn);
        // создаем панель для ответов и добавляем туда поле ответов
        JPanel answer = new JPanel();
        answer.add(answersText);
        JPanel holder = new JPanel(new GridLayout(2,1));
        holder.add(answer);
        holder.add(buttonsPanel);
        this.setLayout(new BorderLayout());
        this.add(upperBound, BorderLayout.NORTH);
        this.add(questionPanel, BorderLayout.CENTER);
        this.add(holder, BorderLayout.SOUTH);
    }

    private void nextQuestion(){
        if(!currentQuestion.checkAnswers(answers)){
            currLives--;
            if(currLives == 0){
                this.endGame();
            }
            livesLabel.setText(livesStr + Integer.toString(currLives));
        } else {
            score++; //
        }
        if(currQuestions == maxQuestion){
            this.endGame();
        }
        currQuestions++;
        questionsLabel.setText(questionStr + Integer.toString(currQuestions) + "/" + Integer.toString(maxQuestion));
        answers.clear();
        answersText.setText("");
        questionText.setText(currentQuestion.generateQuestion());
    }

    private void reset(){
        answers.clear();
        answersText.setText("");
    }

    private void plus(){
        answersText.append("+");
        answers.add(OperationType.PLUS);
    }

    private void minus(){
        answersText.append("-");
        answers.add(OperationType.MINUS);
    }

    private void multiply(){
        answersText.append("*");
        answers.add(OperationType.MULTIPLY);
    }

    private void endGame(){
        JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(this);
        mainFrame.remove(this);
        boolean isLose = currLives == 0;
        mainFrame.add(new EndPanel(isLose, score, maxQuestion));
        mainFrame.validate();
    }
}