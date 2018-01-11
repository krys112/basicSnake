import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	public static int score = 0;
	
	public void tick() {
	}
	
	/**
	 * Display the score in top left corner.
	 * 
	 */
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Score: " + score, 15, 15);
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
}
