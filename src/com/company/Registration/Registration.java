package com.company.Registration;

import com.company.GUI;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Registration<userInfo> extends JDialog {
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField; //password validation input
    private JLabel text;
    private JLabel passwordConfirm; //password validation text
    private JPanel registerPanel;
    private JLabel registerText; //password requirement text for the form
    private JButton registerButton;
    private JButton cancelButton;
    public String password;
    public String username;
    public String email;
    private boolean success;
    private Pattern emailPattern;
    private Pattern passwordPattern;
    private Matcher matcher;

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    //1-64 characters
    //allows A-Z, a-z, 0-9, _, -
    //@ is a must
    //can't start with . or @ at the start or end.

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    //8-20 characters
    //needs 1 uppercase, 1 special character, 1 number

    JFrame frame = new JFrame();


    public Registration() {
        passwordPattern = Pattern.compile(PASSWORD_REGEX);
        emailPattern = Pattern.compile(EMAIL_REGEX);

        //complies regex patterns for email and password

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
        //enables frame to be seen
    }

    public String encryptString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);

        return bigInt.toString(16);
        //allows for an input to be encrypted with a mp5 hash

    }

    public String encryptPassword() throws NoSuchAlgorithmException {
        Registration encryption = new Registration();

        password = encryption.encryptString(password);
        return password;
        //password is passed through the encryptString method so password is hashed when saved to database
    }

    private void registerUser() {


        username = usernameField.getText();
        email = emailField.getText();
        password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(passwordConfirmField.getPassword());

        matcher = emailPattern.matcher(email);
        if (matcher.matches()) { //compares the email given to the regex value and will return an error if the email isn't valid

        } else {
            JOptionPane.showMessageDialog(this,
                    "email is not valid,",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

//        matcher = passwordPattern.matcher(password);
//        if (matcher.matches()) {
//
//
//        } else {
//            JOptionPane.showMessageDialog(this,
//                    "password is not valid,",
//                    "Try again",
//                    JOptionPane.ERROR_MESSAGE);
//            return;
//        }


        if (username.length() > 20) {
            JOptionPane.showConfirmDialog(this,
                    "Too many characters. Please enter less than 20",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showConfirmDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }// if any of the form sections are empty an error message will prompt the user


        if (!password.equals(confirmPassword)) {
            JOptionPane.showConfirmDialog(this,
                    "Password doesn't match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            encryptPassword(); //calls the encryptPassword method which will return the encrypted string
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }



        addUserToDatabase(username, email, password); //calls addUserToDatabase and passes username and email to be
        //inserted into the database


        if (success == true) { //once a addUserToDatabase is run, it will set success to true and close the window and run the gui class
            frame.dispose();
            GUI.GUI();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }


    public userInfo user;

    private void addUserToDatabase(String username, String email, String password) {

        success = true;

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://loginSystem.accdb");

            Statement st = con.createStatement();

            String template = "INSERT INTO Login(email,password,username) values (?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, username);
            stmt.executeUpdate();


        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
    }
}
