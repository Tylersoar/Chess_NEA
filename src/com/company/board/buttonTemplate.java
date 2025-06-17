package com.company.board;

import javax.swing.*;
import java.awt.*;

public class buttonTemplate extends JButton {

    public buttonTemplate(int x, int y, int width, int height, String text) {
        this.setBounds(x, y, width, height);
        this.setText(text);
        this.setBorder(BorderFactory.createBevelBorder(2)); //constructors for button, used to make intractable buttons
        this.setFocusable(false);
        this.setFont(new Font("Arial", Font.PLAIN, 20));
    }
}
