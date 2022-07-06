package com.company.pieces; //Contain functions to move any piece

import com.company.common.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;

public class piece {
    file column;
    Integer row;
    public int testingX,testingY;
    boolean isWhite;
    LinkedList<piece> ps;//Linked list will store a pieces functions
    public String name;
    String fileLocation;
    public BufferedImage image;



    public piece(file xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation, int x) {
        this.column = xp; //get the x and y values of each piece. x and y-values have a boundary of 1-8 for Y and A-H on for X
        this.row = yp;
        this.isWhite = isWhite;
        this.ps=ps;
        this.fileLocation = fileLocation;
        name = n;
        ps.add(this);
        setImage(fileLocation);
        this.testingX = x;
        this.testingY = yp-1;
    }

    public void move(file xp, int yp) {
        for (piece p : ps) {
            if (p.column == xp && p.row == yp) {  //if the piece move is moved into the same position as another piece it will call on the kill method
                p.kill();
            }
        }
        this.column = xp; //moves the piece after attacking or moving
        this.row = yp;

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
