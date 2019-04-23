package com.magic;

import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    public EndPanel(boolean isLose, int score, int maxQuestions) {
        JLabel resultLabel = new JLabel();
        resultLabel.setFont(new Font("serif", Font.PLAIN, Main.fontSize));
        JButton backBtn = new JButton("В начало");
        backBtn.addActionListener(e -> {
            this.goToMainScreen();
        });
        if(isLose){
            resultLabel.setText("Вы проиграли. Увы ¯\\_(ツ)_/¯");
        } else {
            resultLabel.setText("Ваш результат: " + score + "/" + maxQuestions);
        }
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(resultLabel, BorderLayout.CENTER);
        this.add(backBtn, BorderLayout.SOUTH);
    }

    private void goToMainScreen() {
        JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(this);
        mainFrame.remove(this);
        mainFrame.add(new StartPanel());
        mainFrame.validate();
    }
}
