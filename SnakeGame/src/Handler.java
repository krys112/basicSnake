import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	/**
	 * Call tick() method for all existing game objects.
	 * 
	 */
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	/**
	 * Call render(g) method for all existing game objects.
	 * 
	 */
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	/**
	 * Remove all existing game objects.
	 * 
	 */
	public void clearObjects() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			removeObject(tempObject);
			i--;
		}
	}
	
	/**
	 * Add an object to the game.
	 * 
	 */
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	/**
	 * Remove an object from the game.
	 * 
	 */
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
}
