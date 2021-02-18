package Breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BreakoutPaddle {
	int vx;
	int vy;
	int width;
	int height;
	Rectangle u1;
	int counter;
	boolean canSmash;

	// Creates breakout paddle
	// @param int x1, int y1, int width, int height
	public BreakoutPaddle(int x1, int y1, int width, int height) {
		u1 = new Rectangle(x1, y1, width, height);
		this.width = width;
		this.height = height;
		this.vx = 0;
		this.vy = 0;
	}

	// Returns width of paddle
	public double width() {
		return this.width;
	}

	// Returns height of paddle
	public double height() {
		return this.height;
	}

	// Called when user clicks left arrow key
	public void goLeft() {
		this.vx = -4;
		counter = 0;
	}

	// Called when user clicks right arrow key
	public void goRight() {
		this.vx = 4;
		counter = 0;
	}

	// Called when user releases key
	public void stop() {
		this.vx = 0;
	}

	public double getX() {
		return this.u1.getX();
	}

	public double getY() {
		return this.u1.getY();
	}

	// Function updates every frame
	public void update() {
		// set boundaries for movement in frame
		if (this.u1.getX() >= 0 && this.u1.getX() <= 410) {
			u1.translate(vx, vy);
		}
		// right frame boundaries for paddle
		if (this.u1.getX() >= 400 && this.vx >= 0) {
			u1.translate(-vx, vy);
		}
		// left frame boundaries for paddle
		if (this.u1.getX() <= 10 && this.vx <= 0) {
			u1.translate(-vx, vy);

			// Sets smash ability
			counter++;
			if (counter >= 180) {
				canSmash = true;
			} else {
				canSmash = false;
			}
		}
	}

	// Draws paddle elements
	public void draw(Graphics2D win) {
		// Sets ball paddle color
		win.setColor(Color.lightGray);
		//Set paddle to black if can smash
		if (canSmash) {
			win.setColor(Color.black);
		}
		win.fill(u1);
	}

	// Determines if can smash or not
	public boolean getCanSmash() {
		return canSmash;
	}
}
