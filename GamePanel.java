// imports:
// old imports (told to only use the new ones)
//	import java.awt.Graphics;
//	import java.awt.event.ActionEvent;
//	import java.awt.event.ActionListener;
//  import java.awt.event.KeyAdapter;
//	import java.awt.event.KeyEvent;

// new imports:
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	// before the constructor:
	// declaring everything needed.
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	// matrix
	static final int UNIT_SIZE = 25;
	
	// change 1
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	//static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	static final int DELAY = 75;
	// arrays which will hold coordinates for body parts of snake
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	// 
	// change 2
	//		int applesEaten = 0;
	int applesEaten;
	int appleX; // x-coordinate where apple is located.
	int appleY; // y-coordinate where apple is located.
	char direction = 'R'; // have the snake begin going right.
	boolean running = false;
	Timer timer;
	Random random;
	
	// constructor
	GamePanel() {
		// creating the instance of the random class
		random = new Random();
		// set preferred size
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	// methods will need:
	public void startGame() {
		newApple(); // create new apple on screen
		running = true; // set to false at beginning before start
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		// creating a grid for visualization
		if (running) { // added after first implementation (if game is running, do the following)
			// commenting grid lines out
			/*
			for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); // x-axis
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); // y-aixs
				// leaving creation of oval inside loop created red lines
			} */
			// drawing the apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			// drawing the head and body of the snake.
			for(int i = 0; i < bodyParts; i++) {
				// dealing with the head of the snake
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				// dealing with the body of the snake
				else {
					g.setColor(new Color(45, 180, 0));
					// optional color change of the snake
					//g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			//Game Over Text V2 for draw
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont()); // to center
			g.drawString("Score: " + applesEaten,  (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		}
		else {
			// game over
			gameOver(g);
		}
	}
	// late addition of apple
	public void newApple() {
		// generate the coordinates of a new apple every time we score.
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {
		// iterate through all body parts of the snake
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		// switching directions base on instructions
		// ##### FIX THE MOVE METHOD #####
		// its ==> var[] = var[] - UNIT_SIZE, not var[] = var[ # - UNIT_SIZE]
		switch(direction) {
		case 'U': // Up
			//		y[0] = y[0 - UNIT_SIZE];
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D': // Down
			//		y[0] = y[0 + UNIT_SIZE];
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L': // Left
			//		x[0] = x[0 - UNIT_SIZE];
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R': // right
			//		x[0] = x[0 + UNIT_SIZE];
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		// examine coordinates of the snake, and coordinates of the apple
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		// check if head collides with body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false; // trigger game over
			}
		}
		// check if head of the snake hits any borders
		if(x[0] < 0) {
			running = false;
		}
		// check to see if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		// check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		// Score in game:
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont()); // to center
		g.drawString("Score: " + applesEaten,  (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		// Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont()); // to center
		g.drawString("Game Over",  (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// ####################
		// call move function
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		// #####################
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				// no 180 degree turn
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				// no 180 degree turn
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				// no 180 degree turn
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				// no 180 degree turn
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}
