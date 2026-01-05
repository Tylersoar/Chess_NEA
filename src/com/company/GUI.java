package com.company;

import com.company.Registration.Registration;
import com.company.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;


public class GUI implements ActionListener {
  private static JLabel userLabel, success, passwordLabel;
  private static JTextField userText;
  public String password, user;
  private static JPasswordField passwordText;
  private static JButton loginButton, registerButton;
  private static JFrame GUIframe = new JFrame();
  public int loggedInUserID;


  public static void GUI() {
    GUIframe.setTitle("Login");
    GUIframe.setSize(350, 200);
    GUIframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    GUIframe.add(panel);

    panel.setLayout(null);
    userLabel = new JLabel("User");
    userLabel.setBounds(10, 20, 80, 25);
    panel.add(userLabel);


    userText = new JTextField(20);
    userText.setBounds(100, 20, 165, 25);
    panel.add(userText);

    passwordLabel = new JLabel("Password");
    passwordLabel.setBounds(10, 50, 80, 25);
    panel.add(passwordLabel);

    passwordText = new JPasswordField();
    passwordText.setBounds(100, 50, 165, 25);
    panel.add(passwordText);

    loginButton = new JButton("Login");
    loginButton.setBounds(10, 80, 80, 25);
    loginButton.addActionListener(new GUI());
    panel.add(loginButton);

    registerButton = new JButton("Register");
    registerButton.setBounds(100, 80, 100, 25);
    registerButton.addActionListener(new GUI());
    panel.add(registerButton);

    success = new JLabel("");
    success.setBounds(10, 110, 300, 25);
    panel.add(success);

    //text that will appear on the Jpanel and the text boxes used to allow a user to input their username and password


    GUIframe.setVisible(true);

  }

  public String encryptString(String input) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] messageDigest = md.digest(input.getBytes());
    BigInteger bigInt = new BigInteger(1, messageDigest);

    return bigInt.toString(16);

  }

  public String getPassword(String temp) {
    try {
      password = encryptString(temp); //will use password that was given in the form and pass it into a mp5 hashing algorithm
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return password;
  }

  public boolean authenticateUser(String username, String password) {
    try {
      Connection con = DriverManager.getConnection("jdbc:ucanaccess://loginSystem.accdb");

      String query = "SELECT Username, Password from Login";
      Statement st = con.createStatement();
      ResultSet rs = st.executeQuery(query);

      while (rs.next()) {
        if (Objects.equals(username, rs.getString("Username")) && Objects.equals((getPassword((password))), rs.getString("Password"))) {
          return true;//if there is a match the function will return true and will call the board class to run the game
        }
        //gets the username and password given in the login form and compares it to the username and password stored in the database
        //password is passed through the getPassword function to get a hashed version of what was entered in the login form
      }


    } catch (SQLException e) {

      e.printStackTrace();
    }
    return false;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    String user = userText.getText();
    String password = passwordText.getText();

    if (e.getSource() == registerButton) {
      System.out.println("Button registering"); //used to troubleshoot the register button
      Registration myRegister = new Registration();

    }
    if (e.getSource() == loginButton) {

      if (authenticateUser(user, password)) {
        success.setForeground(Color.green); //if the authenticateUser is true the gui will close and open board and the game will start running
        success.setText("login successful");
        GUIframe.dispose();
        Board.successfulLogin = true;

      } else {
        success.setForeground(Color.red);
        success.setText("login unsuccessful");
      }

    } else if (e.getSource() == registerButton) { //used to troubleshoot the register button
      success.setText(null);
      System.out.println("button 2 registering");

    } else {
      System.out.println("failing");
      success.setForeground(Color.red);
      success.setText("login unsuccessful");
    }
  }
}

