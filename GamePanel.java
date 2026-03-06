package com.BrickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private boolean play = true;
    private int score = 0;

    private int paddleX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;

    private int ballXdir = -1;
    private int ballYdir = -2;

    private Timer timer;
    private int delay = 8;

    private int[][] bricks = new int[3][7];

    public GamePanel() {

        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks[0].length;j++){
                bricks[i][j] = 1;
            }
        }

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){

        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        // bricks
        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks[0].length;j++){

                if(bricks[i][j] > 0){

                    g.setColor(Color.white);
                    g.fillRect(j*80+80,i*30+50,70,20);
                }
            }
        }

        // paddle
        g.setColor(Color.green);
        g.fillRect(paddleX,550,100,8);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);

        // score
        g.setColor(Color.white);
        g.drawString("Score: "+score,600,20);

        g.dispose();
    }

    public void actionPerformed(ActionEvent e){

        timer.start();

        ballPosX += ballXdir;
        ballPosY += ballYdir;

        // paddle collision
        if(new Rectangle(ballPosX,ballPosY,20,20)
                .intersects(new Rectangle(paddleX,550,100,8))){
            ballYdir = -ballYdir;
        }

        // bricks collision
        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks[0].length;j++){

                if(bricks[i][j] > 0){

                    int brickX = j*80+80;
                    int brickY = i*30+50;
                    int brickWidth = 70;
                    int brickHeight = 20;

                    Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                    Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);

                    if(ballRect.intersects(rect)){

                        bricks[i][j] = 0;
                        score += 5;

                        ballYdir = -ballYdir;
                    }
                }
            }
        }

        // wall collision
        if(ballPosX < 0){
            ballXdir = -ballXdir;
        }
        if(ballPosY < 0){
            ballYdir = -ballYdir;
        }
        if(ballPosX > 670){
            ballXdir = -ballXdir;
        }

        repaint();
    }

    public void keyPressed(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            paddleX += 20;
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            paddleX -= 20;
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}