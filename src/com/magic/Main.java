package com.magic;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static int fontSize = 20;

    public static void main(String[] args) {
        JFrame frame = new MainForm();
        frame.setTitle("Magic Numbers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
