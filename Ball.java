package Breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball {

	double vx;
	double vy;
	double speed;
	int width;
	int height;
	Rectangle r1;
	private Color color = new Color(20, 30, 40);

	// Constructor for the ball
	public Ball(int x1, int y1, int width, int height) {

		// creates rectangle object and attributes
		r1 = new Rectangle(x1, y1, width, height);
		this.width = width;
		this.height = height;
		this.vx = 3;
		this.vy = 3;
		this.speed = 6;
	}

	// Draws ball and fills it in
	public void draw(Graphics2D win) {

		// Sets ball color to black
		win.setColor(Color.lightGray);
		win.fillOval(r1.x, r1.y, r1.height, r1.width);
	}

	// updates 60 times per second
	public void update() {

		r1.translate((int) vx, (int) vy);
		// Collision detection for top of frame
		if (r1.getY() <= 0) {
			this.onCollide("up");
			// change direction of ball
			this.vy = 3;
		}

		// Collision detection for bottom of frame
		if (r1.getY() >= 730) {
			this.onCollide("down");
			// change direction of ball
			this.vy = -3;
		}

		// Collision detection for right of frame
		if (r1.getX() >= 530) {
			this.onCollide("right");
			// change direction of ball
			this.vx = -3;
		}

		// Collision detection for left of frame
		if (r1.getX() <= 0) {
			this.onCollide("left");
			// change direction of ball
			this.vx = 3;
		}
	}
	
	// Brick + Ball collision: ball changes direction
	public void hitsBrick(String dir) {
		// Dir is the desired direction
		switch(dir) {
		case "down":
			this.vy = Math.abs(vy);
			break;
		case "up":
			this.vy = -Math.abs(vy);
			break;
		case "left":
			this.vx = -Math.abs(vx);
			break;
		case "right":
			this.vx = Math.abs(vx);
			break;
		default:
			System.out.println("Someone messed up");
		}
	}

	// On collision, ball changes to white
	public void onCollide(String dir) {
		this.color = Color.lightGray;
	}
	
	//Ball bounces off at random angle and speed from paddle
	public void relaunch() {
		int speed = 10;
		double theta = Math.random() * 2 * Math.PI / 3.0 + Math.PI / 6.0;
		this.vx = Math.cos(theta) * speed;
		this.vy = -Math.sin(theta) * speed;
	}

	// Called for collision detection and when need x-coordinate of ball
	public double getX() {
		return r1.getX() + this.vx;
	}

	// Called for Collision detection and when need y coordinate of ball
	public double getY() {
		return r1.getY();
	}
	
	public double getVX() {
		return this.vx;
	}
	
	public double getVY() {
		return this.vy;
	}

	// Called for collision detection
	public double getHeight() {
		return this.height;
	}

	// Called when need width of ball
	public double getWidth() {
		return this.width;
	}

	// Only calls when doesn't hit paddle
	public void resetBall() {
		r1.x = 280;
		r1.y = 380;
		this.vx = 3;
		this.vy = 3;
	}
}
