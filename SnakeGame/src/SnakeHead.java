import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class SnakeHead extends GameObject{
	
	private Handler handler;
	private int total = 1;
	private ArrayList<SnakeHead> tail = new ArrayList<SnakeHead>();

	public SnakeHead(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}

	/**
	 * Move the snake with the corresponding velocity for x and y.
	 * Check difficulty, if hard then death on impact with border, otherwise loop around.
	 * Shift all elements in tail array with most recent location last.
	 * Update last element in tail array with latest location if total is equal to size of tail,
	 * otherwise add new SnakeHead into tail array.
	 * 
	 */
	public void tick() {
		x += velX;
		y += velY;
		
		if (!Game.hardDiff) {
			if (x < 0) {
				x = Game.WIDTH;
			} else if (x > Game.WIDTH - 15) {
				x = 0;
			} else if (y < 0) {
				y = Game.HEIGHT;
			} else if (y > Game.HEIGHT - 15) {
				y = 0;
			}
		} else {
			if (x < 0 || x > Game.WIDTH - 15 || y < 0 || y > Game.HEIGHT - 15) {
				Game.isDead = true;
			}
		}
		collision();
		
		for (int i = 0; i < tail.size() - 1; i++) {
			tail.set(i, tail.get(i + 1));
		}
		if (total >= 1) {
			if (total > tail.size()) {
				tail.add(new SnakeHead(x, y, ID.SnakeHead, handler));
			} else { tail.set(total - 1, new SnakeHead(x, y, ID.SnakeHead, handler)); }
		}
	}

	/**
	 * Draw snake head and its tail.
	 * 
	 */
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, 15, 15);
		
		for (int i = 0; i < tail.size(); i++) {
			g.setColor(Color.green);
			GameObject tempObject = tail.get(i);
			g.fillRect(tempObject.getX(), tempObject.getY(), 15, 15);
			g.setColor(Color.black);
			g.drawRect(tempObject.getX(), tempObject.getY(), 15, 15);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 15, 15);
	}
	
	/**
	 * Check if snake's coordinates intersect with food object or its tail.
	 * 
	 */
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Food) {
				if (getBounds().intersects(tempObject.getBounds())) {
					Game.isEaten = true;
					total++;
					HUD.score++;
				}
			}
			
		}
		for (int i = 0; i < tail.size(); i++) {
			GameObject tempObject = tail.get(i);
			
			if (getBounds().intersects(tempObject.getBounds())) {
				if (total > 1) {
					Game.isDead = true;
				}
			}
		}
	}

}
