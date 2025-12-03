//idress Ahmadzai 12/2/25 
// this project allows you to play pong against an ai


package com.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PongGame extends JPanel implements MouseMotionListener {
    static int width = 640; // this is the amount of pixels to the right side of the screen
    static int height = 480; // this is the amount of pixels to the top of the screen.
    private int userMouseY;
    private Paddle aiPaddle;
    private int playerScore;
    private int aiScore;
    private Ball ball;
    // step 1 add any other private variables you may need to play the game.
    
    private SlowDown slow;
    private Paddle playerPaddle; 
    private Speedup fast;
    public PongGame() {

        aiPaddle = new Paddle(610, 240, 50, 9, Color.WHITE);
        JLabel pScore = new JLabel("0");
        JLabel aiScore = new JLabel("0");
        pScore.setBounds(280, 440, 20, 20);
        aiScore.setBounds(360, 440, 20, 20);
        pScore.setVisible(true);
        aiScore.setVisible(true);
        userMouseY = 0;
        addMouseMotionListener(this);
        ball = new Ball(200, 200, 10, 3, Color.RED, 10);
        slow = new SlowDown(200,150,100,100);
        playerPaddle= new Paddle(10,240,50,9,Color.WHITE);
        fast= new Speedup(200,300,100,100);
        //create any other objects necessary to play the game.

    }

    // precondition: None
    // postcondition: returns playerScore
    public int getPlayerScore() {
        return playerScore;
    }

    // precondition: None
    // postcondition: returns aiScore
    public int getAiScore() {
        return aiScore;
    }

    //precondition: All visual components are initialized, non-null, objects 
    //postcondition: A frame of the game is drawn onto the screen.
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.drawString("The Score is User:" + playerScore + " vs Ai:" + aiScore, 240, 20);
        ball.draw(g);
        aiPaddle.draw(g);
        slow.draw(g);
        playerPaddle.draw(g);
        fast.draw(g);
        //call the "draw" function of any visual component you'd like to show up on the screen.

    }

    // precondition: all required visual components are intialized to non-null
    // values
    // postcondition: one frame of the game is "played"
    public void gameLogic() {
        //add commands here to make the game play propperly
        
        //makes the ball move
        ball.moveBall();
        
        //makes the ai follows the ball y coordinates
        aiPaddle.moveY(ball.getY());
        
        //makes the player follows the mouse cursor
        playerPaddle.moveY(userMouseY);
        
        //if the ball touches the top or bottom of the game it bounces off
        ball.bounceOffwalls(600, 0);
        
        //if the ball touches the ai paddle it bounces off
        if (aiPaddle.isTouching(ball)) {
           ball.reverseX();
           ball.reverseY();
        }
        
        //if the ball touches the playerpaddle it bounces off
        if (playerPaddle.isTouching(ball)) {
           ball.reverseX();
           ball.reverseY();
           
        }
        
        // makes the ball speed up when it touches the speed up zone
        if (fast.isTouching(ball)){
            if (ball.getChangeX()<0){
                ball.setChangeX(ball.getChangeX()-.5);
            }
            else{
                ball.setChangeX(ball.getChangeX()+ .5);
            }
            
        }
        
        // makes the ball slow down when it touches the slow down zone
        if (slow.isTouching(ball)){
            if (ball.getChangeX()<0){
                ball.setChangeX(ball.getChangeX()+ .5);
            }
            else{
                ball.setChangeX(ball.getChangeX()- .5);
            }
            
        }
        
        //if the ball goes of the screen to the left the ai gets a point
        if (ball.getX()<=0){
            aiScore++;
            ball.setChangeX(10);
            ball.setChangeY(3);
            ball.setX(200);
            ball.sety(200);
        }
        
        //if the ball goes of the screen to the right the player gets a point
        if (ball.getX()>=width){
            playerScore++;
            ball.setChangeX(10);
            ball.setChangeY(3);
            ball.setX(200);
            ball.sety(200);
        }
        

    }

    // precondition: ball is a non-null object that exists in the world
    // postcondition: determines if either ai or the player score needs to be
    // updated and re-sets the ball
    // the player scores if the ball moves off the right edge of the screen (640
    // pixels) and the ai scores
    // if the ball goes off the left edge (0)
    

    // you do not need to edit the below methods, but please do not remove them as
    // they are required for the program to run.
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        userMouseY = e.getY();
    }

}
