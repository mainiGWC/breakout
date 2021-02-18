package Breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.TargetDataLine;

import Demos.BouncingBox;
import Demos.Box;
import Demos.Paddle;
import utilities.GDV6;

/* Breakout Game: AP Computer Science A
 * By: Mansi Saini
 * 
 * Directions: Player uses right and left keys. 
 * Try to hit bricks with ball.
 */

public class Breakout extends GDV6 {
	private static final long serialVersionUID = 1L;

	BreakoutPaddle u1 = new BreakoutPaddle(220, 680, 150, 20);
	Ball b1 = new Ball(280, 380, 27, 27);
	Brick bricks[][] = new Brick[6][8];

	final int SPLASH_state = 0;
	final int GAME_state = 1;
	final int LEADER_state = 2;
	int state = SPLASH_state;

	int score = 0;
	BufferedImage intro = null;
	BufferedImage gameBkground = null;
	BufferedImage winner = null;

	public Breakout() {
		try {
			java.net.URL url = this.getClass().getClassLoader().getResource("Images/intro.PNG");
			intro = ImageIO.read(url);
		} catch (IOException e) {
		}

		// loads winner page into memory
		try {
			java.net.URL url = this.getClass().getClassLoader().getResource("Images/winner.PNG");
			winner = ImageIO.read(url);
		} catch (IOException e) {
		}

		// loads game background image into memory
		try {
			java.net.URL url = this.getClass().getClassLoader().getResource("Images/gameBkground.PNG");
			gameBkground = ImageIO.read(url);
		} catch (IOException e) {
		}

		Color[] colors = { Brick.blue, Brick.blue2, Brick.blue3, Brick.blue4, Brick.blue5, Brick.blue6 };
		for (int i = 0; i < 6; i++) {
			makeRow(23 * i + 1, bricks[i], colors[i]);
		}
	}

	@Override
	// Called upon arrow key pressed
	public void keyPressed(KeyEvent evt) {
		switch (this.state) {
		case SPLASH_state:
			// If enter is pressed on intro screen, enter Pong screen with game
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
				state = 1;
			}
			break;
		case GAME_state:
			// Press right arrow key for paddle movement
			if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
				u1.goRight();
			}
			// Press left arrow key for paddle movement
			if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
				u1.goLeft();
			}
		case LEADER_state:
			// Allows user to start another round
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
				score = 0;
				state = 1;
			}
			break;
		}
	}

	@Override
	// Called upon arrow key released
	public void keyReleased(KeyEvent evt) {

		// Press right arrow key for paddle movement
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			u1.stop();
		}
		// Press left arrow key for paddle movement
		if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
			u1.stop();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	// updates every frame
	public void update() {

		switch (this.state) {
		case SPLASH_state:
			break;
		case GAME_state:

			// Calls function to update paddle
			u1.update();
			// Calls function to update ball
			b1.update();

			// Collision detection between ball and paddle
			if (u1.getX() <= b1.getX() && b1.getX() <= (u1.getX() + u1.width())
					&& u1.getY() <= (b1.getY() + b1.getHeight())
					&& (b1.getY() + b1.getHeight()) <= (u1.getY() + u1.height())) {
				b1.relaunch();
				// beeps when the ball hits the paddle
				Toolkit.getDefaultToolkit().beep();
			}

			// Collision with wall resets ball
			if (b1.getY() >= u1.getY()) {
				b1.resetBall();
			}
			// Loops through array to check if any bricks collide with ball
			for (int i = 0; i < bricks.length; i++) {
				for (int j = 0; j < bricks[0].length; j++) {
					Brick brick = bricks[i][j];
					if (brick.collideBottom(b1)) {
						b1.hitsBrick("down");
						brick.delete();
						score++;
					} else if (brick.collideTop(b1)) {
						b1.hitsBrick("up");
						brick.delete();
						score++;
					} else if (brick.collideRight(b1)) {
						b1.hitsBrick("right");
						brick.delete();
						score++;
					} else if (brick.collideLeft(b1)) {
						b1.hitsBrick("left");
						brick.delete();
						score++;
					}
				}
			}

			break;
		case LEADER_state:
			Color[] colors = { Brick.blue, Brick.blue2, Brick.blue3, Brick.blue4, Brick.blue5, Brick.blue6 };
			for (int i = 0; i < 6; i++) {
				makeRow(23 * i + 1, bricks[i], colors[i]);
			}
			break;
		}

	}

	@Override
	public void draw(Graphics2D win) {
		switch (this.state) {
		case SPLASH_state:
			// Directions and introduction to the game + typography styling
			win.drawImage(intro, 0, 0, 620, 800, Color.black, null);
			break;
		case GAME_state:
			// draws background image for Breakout game
			win.drawImage(gameBkground, -5, 0, 620, 800, Color.black, null);
			// Creates font, coloring, and shape for paddles, ball, and score board
			// Displays paddle
			u1.draw(win);
			// Displays ball
			b1.draw(win);
			win.setColor(Color.lightGray);
			for (int i = 0; i < bricks.length; i++) {
				drawRow(win, bricks[i]);
			}
			win.setFont(new Font("Century Gothic", Font.BOLD, 50));
			win.setColor(Color.lightGray);
			// TODO: set score for player
			win.drawString("Score: " + score, 200, 400);
			// Move to end page if reach end of level
			if (score == 1) {
				for (int i = 0; i < bricks.length; i++) {
					for (int j = 0; j < bricks[0].length; j++) {
						bricks[i][j].isVisible = true;
						bricks[i][j].color = Color.pink;
					}
				}
				b1.speed += 5;
				win.drawImage(winner, -5, 0, 620, 800, Color.black, null);
			}
			break;

		case LEADER_state:
		}
	}

	// Draws a row of bricks
	public void drawRow(Graphics2D win, Brick[] bricks) {
		for (int i = 0; i < bricks.length; i++) {
			bricks[i].draw(win);
		}
	}

	// Starts moving Breakout Ball
	// Calls function to display bricks
	public static void main(String[] args) {
		Breakout bounce = new Breakout();
		bounce.start();
	}

	// Creates an array of bricks
	public void makeRow(int y, Brick[] bricks, Color color) {
		int x = 0;
		for (int i = 0; i < bricks.length; i++) {
			bricks[i] = new Brick(x, y, color);
			x += 81;
		}
	}

}
