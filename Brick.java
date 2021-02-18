package Breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Brick extends Rectangle {
	Color color;
	// Brick color
	static Color blue = new Color(50, 217, 207);
	// Other brick color
	static Color blue2 = new Color(83, 223, 215);
	// Other brick color
	static Color blue3 = new Color(100, 226, 219);
	// Other brick color
	static Color blue4 = new Color(133, 232, 226);
	// Other brick color
	static Color blue5 = new Color(166, 238, 234);
	// Other brick color
	static Color blue6 = new Color(199, 245, 242);

	boolean isVisible = true;

	// Gains access to private variable
	public Brick(int x, int y, Color color) {
		super(x, y, 78, 20);
		this.color = color;
		this.y = y;
	}

	// Called for collision detection
	public double height() {
		return this.y;
	}

	public void draw(Graphics2D win) {
		if (!isVisible)
			return;
		win.setColor(color);
		win.fill(this);
	}

	// Checks whether the ball is about to collide with this brick
	public boolean aboutToCollide(Ball b) {
		double nextX = b.getX() + b.getVX();
		double nextY = b.getY() + b.getVY();

		return this.x <= nextX + b.getWidth() && nextX <= this.x + this.width && this.y <= nextY + b.getHeight()
				&& nextY <= this.y + this.height;

	}

	public boolean collideBottom(Ball b) {
		// Ball must be moving up, about to collide with this brick, and below the brick
		return b.vy < 0 && aboutToCollide(b) && b.getY() > this.y + this.height && this.isVisible;
	}

	public boolean collideLeft(Ball b) {
		// Ball must be moving right, about to collide with this brick, and to the left
		// of the brick
		return b.vx > 0 && aboutToCollide(b) && b.getX() < this.x && this.isVisible;
	}

	public boolean collideTop(Ball b) {
		// Ball must be moving down, about to collide with this brick, and above the
		// brick
		return b.vy > 0 && aboutToCollide(b) && b.getY() < this.y && this.isVisible;
	}

	public boolean collideRight(Ball b) {
		// Ball must be moving left, about to collide with this brick, and to the right
		// of the brick
		return b.vx < 0 && aboutToCollide(b) && b.getX() > this.x + this.width && this.isVisible;
	}

	public void delete() {
		this.isVisible = false;
	}

}
