package minesweeper;

public class Spot {
	private int x;
	private int y;
	boolean mine;
	
	private int val;
	public Spot() {
		setX(0);
		setY(0);
	}
	public Spot(int a, int b) {
		setX(a);
		setY(b);
	}	
	public Spot(int a, int b, boolean c) {
		setX(a);
		setY(b);
		setMine(c);
	}
	public Spot(int a, int b, boolean c, int d) {
		setX(a);
		setY(b);
		setMine(c);
		setVal(d);
	}
	public void setX(int a) {
		x = a;
	}
	public int getX() {
		return x;
	}
	public void setY(int b) {
		y = b;
	}
	public int getY() {
		return y;
	}
	public void setMine(boolean in) {
		mine = in;
	}
	public boolean getMine() {
		return mine;
	}
	public void setVal(int in) {
		val = in;
	}
	public int getVal() {
		return val;
	}
	public String toString() {
		return x + ", " + y + " " + mine + " " + val;
	}
}
