/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/** start game in given seconds*/
	private static final int GAME_STARTS_IN=5;

/**  pause ball in air for given seconds*/
	private static final int PAUSE_BALL_FOR=5;
	
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		//puts name of the game and starts game in 5 sec 
		introAndStart();
		//runs game with NTURNS lives left while it isnt over 
		playGame();
		//game over text 
		gameOverText();
	}
	
	private void introAndStart() {
		setBackground(Color.black);
		gameName();
		countToStartFor(GAME_STARTS_IN);
	}
	
	/*
	 * adds name of the game "BREAK OUT"
	 */
	private void gameName() {
		
		name = new GLabel("BREAK OUT");
		((GLabel) name).setFont("Serif-50");
		name.setColor(Color.RED);
				int nameX = (int)((getWidth()/2)-(name.getWidth()/2));
				int nameY = (int)((getHeight()/3));
		add(name,nameX,nameY);
	}
	
	//count given x second
	private void countToStartFor(int x) {
		
		for(int i=x ; i>0; i--) {
			String count = "STARTING IN "+i;
			GLabel text = new GLabel(count);
				text.setColor(Color.WHITE);
				text.setFont("Plain-20");
			add(text,getWidth()/2-(text.getWidth()/2),getHeight()/2+(text.getHeight()/2));
			pause(1000);
			remove(text);
		}
		
	}
	
	/*
	 * for given NTURNS game runs 
	 * removes everything setingup
	 * and then counts for 5 secinds before droping ball
	 * and runs game while game isnt over 
	 * if all bricks are gone loop ends
	 */
	private  void playGame() {
		for(int i=NTURNS; i>0;i--) {
			removeAll();
			setup();
			textBallMovingInAndTurnsLeft(PAUSE_BALL_FOR,i);
			while(!gameOver()) {
				runGame();
			}
			
			//if only paddle or ball(or not) present on the screen game is done 
			if (getElementCount()<=2) {
				i=0;
			}
		}
		
	}
	
	/*
	 * shows number of turns left
	 * and timer before ball droping
	 */
	private void textBallMovingInAndTurnsLeft(int x ,int y) {
		
		GLabel turnsLeft = new GLabel("TURNS LEFT "+y);
			turnsLeft.setColor(Color.WHITE);
			turnsLeft.setFont("Plain-20");
		add(turnsLeft,getWidth()/2-(turnsLeft.getWidth()/2),getHeight()/1.5+(turnsLeft.getHeight()/2));
		
		pause(1000);
		for(int i=x ; i>0; i--) {
			String count = "BALL DROPING IN "+i+" SEC";
			GLabel text = new GLabel(count);
				text.setColor(Color.WHITE);
				text.setFont("Plain-20");
			add(text,getWidth()/2-(text.getWidth()/2),getHeight()/1.4+(text.getHeight()/2));
			pause(1000);
			remove(text);
		}
		remove(turnsLeft);
	}
	
	/*
	 * add bricks, paddle, ball on the canvas,
	 * setup values for speed of ball 
	 * Y is fixed 
	 * X is random
	 * and at last adds mouse listner for further use
	 */
	private void setup() {
		addBricks();
		addPaddle();
		addBall();
		setValuesOfXAndYVelocities();
		addMouseListeners();
	}
	
	/* 
	 * add bricks and colour to them,
	 * if we increase no. of bricks black on will be added 
	 */
	private void addBricks() {
		
		int firstBrickX = (WIDTH-((BRICK_SEP*(NBRICKS_PER_ROW-1))+(NBRICKS_PER_ROW*BRICK_WIDTH)))/2 ;
		int firstBrickY = BRICK_Y_OFFSET;
		
		for ( int i=0; i<NBRICK_ROWS ; i++ ) {
			for(int j=0; j<NBRICKS_PER_ROW ; j++) {
				 	brick = new GRect(firstBrickX+(j*(BRICK_WIDTH+BRICK_SEP)),firstBrickY+(i*(BRICK_HEIGHT+BRICK_SEP)),BRICK_WIDTH,BRICK_HEIGHT);
					((GRect)brick).setFilled(true);
					if (i==0||i==1) ((GRect)brick).setFillColor(Color.RED);
					if (i==2||i==3) ((GRect)brick).setFillColor(Color.ORANGE);
					if (i==4||i==5) ((GRect)brick).setFillColor(Color. YELLOW);
					if (i==6||i==7) ((GRect)brick).setFillColor(Color.GREEN);
					if (i==8||i==9) ((GRect)brick).setFillColor(Color.CYAN);
				add(brick);
			}
		}
		
	}
	
	/*
	 * paddle is added at location
	 * y givven Y Offset
	 * X if in between the screen
	 */
	private void addPaddle() {
		paddle = new GRect(PADDLE_WIDTH,PADDLE_HEIGHT);
			paddle.setLocation((getWidth()/2-(paddle.getWidth()/2)), getHeight()-PADDLE_Y_OFFSET);
			((GRect) paddle).setFilled(true);
			((GRect)paddle).setFillColor(Color.LIGHT_GRAY);
		add(paddle);
	}
	
	/*
	 * adds ball in the mid of the screen
	 */
	private void addBall() {
		ball= new GOval(2*BALL_RADIUS,2*BALL_RADIUS);
		((GOval)ball).setFilled(true);
		((GOval)ball).setFillColor(Color.red);
		add(ball,(getWidth()/2)-BALL_RADIUS,(HEIGHT/2)-BALL_RADIUS);
		
	}
	
	private void setValuesOfXAndYVelocities(){
		//Y velocity
		vy = 3.0;
				
		//random x velocity
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
	}
	
	// checks for element at mouse press and record coordinates
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
		gobj = getElementAt(lastX,lastY);
	}
	
	//adds  movement to paddle
	public void mouseDragged(MouseEvent e) {
		if (gobj == paddle) {
			
			
			if ((paddle.getX()>0) && (paddle.getX()+PADDLE_WIDTH<getWidth()))  {
				gobj.move(e.getX() - lastX, 0);
				lastX = e.getX();
			}
			
			//limits paddle getting out of screen from left side of screen
			if(paddle.getX()<=0 && (e.getX() - lastX)>0) {
				gobj.move(e.getX() - lastX, 0);
				lastX = e.getX();
		    }
			
			//limits paddle getting out of screen from right side of screen
			if(paddle.getX()+PADDLE_WIDTH>=getWidth() && (e.getX() - lastX)<0) {
				gobj.move(e.getX() - lastX, 0);
				lastX = e.getX();
			}
			
		}
	}
	
	//adds ball and its movement 
	private void runGame() {
			moveBall();
			bounceIfWall();
			CheckForObjectCollision();
			pause(GAME_SPEED);
		
	}
	
	
	
	//add movement and function to ball
	private void moveBall() {
		//movement
		ball.move(vx,vy);
	}
	
	// bounce ball if ball collides with North/east/west wall 
	@SuppressWarnings("deprecation")
	private void bounceIfWall() {
		
		AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
		
		
		//For north wall
		if (ball.getY()<=0) {
			vy=-vy;
			bounceClip.play();
		}
		//for east and west wall
		if ( (ball.getX()+(2*BALL_RADIUS) >= WIDTH) || (ball.getX() <= 0) ) {
			vx = -vx;
			bounceClip.play();
		}
		
	} 
	
	/*
	 * if ball collides with paddle it bounces back.
	 * if it collides with brick it breaks it and
	 * bounces back.
	 */
	@SuppressWarnings("deprecation")
	private void CheckForObjectCollision() {
		
		GObject collider = getCollidingObject();
		
		AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
		
		// ball will bounce back upon collision with paddle
		if (collider==paddle) {
			vy=-vy;
			bounceClip.play();
		}
		
		// if object present except paddle ball will remove it on collision and bouce
		if (collider!=null && collider!=paddle) {
			remove(collider);
			vy=-vy;
			bounceClip.play();
		}
	}
	
	/* return object with which ball collides.
	*  hitbox of ball is square,
	*  defined by corners.
	*/
	private GObject getCollidingObject() {
		
		//upper left corner fo ball
		double X = ball.getX();
		double Y = ball.getY();
		
		//Object if present at top left corner of ball else null
		GObject topLeftCornerCollidingObject = getElementAt(X, Y);
		
		//Object if present at top right corner of ball else null
		GObject topRightCornerCollidingObject = getElementAt(X+2*BALL_RADIUS, Y);
		
		//Object if present at top right corner of ball else null
		GObject bottomLeftCornerCollidingObject = getElementAt(X, Y+2*BALL_RADIUS);
				
		//Object if present at top right corner of ball else null
		GObject bottomRightCornerCollidingObject = getElementAt(X+2*BALL_RADIUS, Y+2*BALL_RADIUS);
		
		//return object if present at top left corner 
		if (topLeftCornerCollidingObject != null) return topLeftCornerCollidingObject;
		
		//return object if present at top right corner
		if (topRightCornerCollidingObject != null) return topRightCornerCollidingObject;
		
		//return object if present at bottom left corner
		if (bottomLeftCornerCollidingObject != null) return bottomLeftCornerCollidingObject;
		
		//return object if present at bottom right corner
		if (bottomRightCornerCollidingObject != null) return bottomRightCornerCollidingObject;
		
		//if nothing at all four corners return null
		return null;
	}
	
	/*
	 * game over conditions
	 * 1. if ball touches the bottom
	 * 2. if all the bricks are gone
	 */
	private boolean gameOver(){
		return ball.getY()+2*BALL_RADIUS>=getHeight() || getElementCount()==2;
	}
	
	//flashes game over text
	private void gameOverText() {
		removeAll();
		GLabel text = new GLabel("GAME OVER UWU");
			text.setFont("Serif-30");
			text.setColor(Color.red);
			text.setLocation((getWidth()/2)-text.getWidth()/2,getHeight()/2+(text.getHeight()/2));
			add(text);
		
	}
	 
	private double vx,vy;
	private GObject name,paddle,gobj,brick,ball;
	private double lastX, lastY;
	private double GAME_SPEED = 10 ;
	private RandomGenerator rgen = new RandomGenerator();
}
