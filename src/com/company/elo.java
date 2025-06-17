package com.company;

import com.company.board.Board;

import java.sql.*;
import java.util.Objects;


public class elo {
    private static double expected_score_A;
    public static double otherPlayerElo = 1000;
    public static double ourElo = 900; //change this to the elo on the database

//    public static int getElo() {
//        try {
//            GUI gui = new GUI();
//
//            Connection con = DriverManager.getConnection("jdbc:ucanaccess://loginSystem.accdb");
//
//            String query = ("SELECT 'elo' FROM statistics where 'UserID'='"+gui.returnUserID()+"'");
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//            ourElo = rs.getInt(query);
//
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//        }
//        return ourElo;
//    }

    //get elo from database


    public static double calculateElo() {

        if (otherPlayerElo > ourElo) {
            expected_score_A = (int) (otherPlayerElo - ourElo);
        } else {

        }
        double ourRatio = expected_score_A / 400;

        double expected_score1 = 1 / (1 + Math.pow(10, ourRatio));


        double our_new_player_score = ourElo + 20 * (Board.getWinState() - expected_score1);


        return Math.round(our_new_player_score);

    }


}

