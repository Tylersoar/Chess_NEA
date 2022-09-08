package com.company.pieces; //Contain functions to move any piece

import com.company.board.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;

public class piece {
    public Integer column;
    public Integer row;

    public int x,y;

    public boolean isWhite;
    public boolean firstMove = true;
    LinkedList<piece> ps;//Linked list will store a pieces functions
    public String name;
    String fileLocation;
    public BufferedImage image;

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill){
        return true;
    }





    public piece(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        this.column = xp; //get the x and y values of each piece. x and y-values have a boundary of 0-7 for Y and 0-7 on for X
        this.row = yp;
        this.x=xp*64;
        this.y=yp*64;
        this.isWhite = isWhite;
        this.ps=ps;
        this.fileLocation = fileLocation;
        name = n;
        ps.add(this);
        setImage(fileLocation);
    }

    public void move(int xp, int yp) {
        boolean canMove = true;
        boolean willKill = false;
        if(Board.getPiece(xp*64,yp*64) !=null){
            if(Board.getPiece(xp*64, yp*64).isWhite!=isWhite) {
                willKill = true;
            }
            else canMove = false;
        }
        if (canMove){
            System.out.println(Board.selectedPieceOriginalXP + " " + Board.selectedPieceOriginalYP);
            System.out.println(xp + " " + yp);
            canMove = this.validMove(xp,yp,isWhite,willKill);
        }

        if(canMove){
            if(willKill){
            Board.getPiece(xp * 64, yp * 64).kill();
            }
            this.column=xp;
            this.row=yp;
            x=column*64;
            y=row*64;
            this.firstMove = false;
        }
    }
    public void setImage(String fileLocation){
        image=setup(fileLocation, 64,64 );
    }
    public void kill() {
        ps.remove(this); //if a piece is killed its removed off the board
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            image = scaleImage(image, width, height);

        } catch (Exception e) {
        }
        return image;
    }

    public BufferedImage scaleImage(BufferedImage original,int width,int height){

        BufferedImage scaledImage = new BufferedImage(width,height,original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();

        return scaledImage;
    }


}
