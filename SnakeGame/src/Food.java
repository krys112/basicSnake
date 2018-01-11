import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Food extends GameObject{
	
	private int cols, rows;
	private Random r = new Random();

	public Food(int x, int y, ID id) {
		super(x, y, id);
	}

	public void tick() {
		// TODO Auto-generated method stub
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 15, 15);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 15, 15);
	}
}
