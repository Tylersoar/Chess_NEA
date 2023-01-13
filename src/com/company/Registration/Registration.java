package com.company.Registration;
import com.company.GUI;

import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Registration<userInfo> extends JDialog {
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField; //password validation input
    private JLabel text;
    private JLabel passwordConfirm; //password validation text
    private JPanel registerPanel;
    private JLabel registerText;
    private JButton registerButton;
    private JButton cancelButton;
    private String password, email, username;
    public int wins, gamesPlayed, losses, userID;
    public int elo = 500;

    private boolean success;

    JFrame frame = new JFrame();


    public Registration() {
        frame.setTitle("Registration");
        frame.setMinimumSize(new Dimension(450, 470));
        frame.setContentPane(registerPanel);
        frame.setVisible(true);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();

            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelButton) {
                    frame.dispose();

                }
            }
        });

        frame.setVisible(true);
    }



    private void registerUser() {
        username = usernameField.getText();
        email = emailField.getText();
        password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(passwordConfirmField.getPassword());
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(regexPattern);


        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showConfirmDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showConfirmDialog(this,
                    "Password doesn't match",
                    "Try again",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if (pattern.matcher(email).matches()){

        }else{
            JOptionPane.showConfirmDialog(this,
                    "invalid email",
                    "Try again",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }
        addUserToDatabase(username, email, password, wins, gamesPlayed, losses, elo);


        if (success) {
            System.out.println("it works");
            frame.dispose();
            GUI.GUI();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.PLAIN_MESSAGE);
        }

    }


    public userInfo user;

    private void addUserToDatabase(String username, String email, String password, int wins, int gamesPlayed, int losses, int elo)  {
        success = true;

//        userInfo user = null;


        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://loginSystem.accdb");

            String template = "INSERT INTO Login(email,password,username) values (?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, username);
            stmt.executeUpdate();

            String template2 = "INSERT INTO statistics(wins,gamesPlayed,losses,elo,email) values (?,?,?,?,?)";
            PreparedStatement stmt2 = con.prepareStatement(template2);
            stmt2.setInt(1, wins);
            stmt2.setInt(2, gamesPlayed);
            stmt2.setInt(3, losses);
            stmt2.setInt(4, elo);
            stmt2.setString(5, email);
            stmt2.executeUpdate();


        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
    }
}