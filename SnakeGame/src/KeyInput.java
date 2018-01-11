import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	private char dir;
	
	Game game;
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
	}
	
	/**
	 * Check for snake object (includes head and tail), set direction using x and y velocities.
	 * Prevent snake from accepting input to go backwards.
	 * Exit game when key ESC is pressed.
	 * Pause game when key P is pressed.
	 * 
	 * @param e Key event, in this case, a press.
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.SnakeHead) {
				if (key == KeyEvent.VK_W && dir != 'W') {
					tempObject.setVelX(0);
					tempObject.setVelY(-15);
					dir = 'S';
				}
				if (key == KeyEvent.VK_S && dir != 'S') {
					tempObject.setVelX(0);
					tempObject.setVelY(15);
					dir = 'W';
				}
				if (key == KeyEvent.VK_A && dir != 'A') {
					tempObject.setVelY(0);
					tempObject.setVelX(-15);
					dir = 'D';
				}
				if (key == KeyEvent.VK_D && dir != 'D') {
					tempObject.setVelY(0);
					tempObject.setVelX(15);
					dir = 'A';
				}
			}
		}
		
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (key == KeyEvent.VK_P) {
			if (game.gameState == STATE.Game) {
				if (game.paused) {
					game.paused = false;
				} else { game.paused = true; }
			}
		}
	}
	
	
}
