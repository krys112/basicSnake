import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter { 
	
	private Game game;
	private Handler handler;
	private HUD hud;
	private Color[] colors = {Color.green, Color.red, Color.cyan, Color.yellow, Color.magenta};
	private String title = "SNAKE";
	private Random r = new Random();
	private int timer = 0;
	private Color col = new Color(100, 0, 0);
	private boolean redFull, greenFull, blueFull;
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	/**
	 * Provide rectangle functionality by allowing user to select with a mouse click,
	 * checks position of mouse over the rectangle boundries using mouseOver.
	 * 
	 * @param e Mouse event, in this case, a press.
	 */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (game.gameState == STATE.Menu) {
			// 'PLAY' button.
			if (mouseOver(mx, my, 30, 320, 175, 100)) {
				game.gameState = STATE.Select;
			}
			
			// 'HELP' button.
			if (mouseOver(mx, my, 235, 320, 175, 100)) {
				game.gameState = STATE.Help;
			}
			
			// 'QUIT' button.
			if (mouseOver(mx, my, 440, 320, 175, 100)) {
				System.exit(1);
			}
		}
		
		else if (game.gameState == STATE.Select) {
			// 'NORMAL' button.
			if (mouseOver(mx, my, 30, 320, 175, 100)) {
				game.gameState = STATE.Game;
				game.isDead = false;
				game.hardDiff = false;
				handler.addObject(new SnakeHead(30, 30, ID.SnakeHead, handler));
				handler.addObject(new Food(Game.getLocX(), Game.getLocY(), ID.Food));
			}
			
			// 'HARD' button.
			if (mouseOver(mx, my, 235, 320, 175, 100)) {
				game.gameState = STATE.Game;
				game.isDead = false;
				game.hardDiff = true;
				handler.addObject(new SnakeHead(30, 30, ID.SnakeHead, handler));
				handler.addObject(new Food(Game.getLocX(), Game.getLocY(), ID.Food));
			}
			
			// 'BACK' button.
			if (mouseOver(mx, my, 440, 320, 175, 100)) {
				game.gameState = STATE.Menu;
			}
		}
		
		else if (game.gameState == STATE.Help) {
			// 'BACK' button.
			if (mouseOver(mx, my, 235, 320, 175, 100)) {
				game.gameState = STATE.Menu;
			}
		}
		
		else if (game.gameState == STATE.End) {
			
			// 'CONTINUE' button.
			if (mouseOver(mx, my, 250, 230, 145, 30)) {
				System.out.println("size is "+handler.object.size());
				game.gameState = STATE.Menu;
			}
		}
	}
	
	/**
	 * Check if mouse coordinates are within a given rectangle's area.
	 * 
	 * @param mx Mouse position's x coordinate.
	 * @param my Mouse position's y coordinate.
	 * @param x, y, width, height Rectangle's dimensions.
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void tick() {
	}
	
	/**
	 * Render multiple buttons in the form of rectangles with strings for difference states.
	 * 
	 * @param g The Graphics instance.
	 */
	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 30);
		Font fnt2 = new Font("arial", 1, 20);
		Font fnt3 = new Font("arial", 1, 15);
		Font fnt4 = new Font("arial", 1, 10);
		drawTitle(g, fnt);
		if (game.gameState == STATE.Menu) {
			g.drawRect(30, 320, 175, 100);
			drawCenteredString(g, "PLAY", new Rectangle(30, 320, 175, 100), fnt);
			g.drawRect(235, 320, 175, 100);
			drawCenteredString(g, "HELP", new Rectangle(235, 320, 175, 100), fnt);
			g.drawRect(440, 320, 175, 100);
			drawCenteredString(g, "QUIT", new Rectangle(440, 320, 175, 100), fnt);
		}
		
		else if (game.gameState == STATE.Select) {
			g.drawRect(30, 320, 175, 100);
			drawCenteredString(g, "NORMAL", new Rectangle(30, 320, 175, 100), fnt);
			g.drawRect(235, 320, 175, 100);
			drawCenteredString(g, "HARD", new Rectangle(235, 320, 175, 100), fnt);
			g.drawRect(440, 320, 175, 100);
			drawCenteredString(g, "BACK", new Rectangle(440, 320, 175, 100), fnt);
		}
		
		else if (game.gameState == STATE.Help) {
			g.setColor(Color.white);
			drawCenteredString(g, "Use WASD to move the snake around, collect food to increase your snake and score.", new Rectangle(0, 120, Game.WIDTH, 100), fnt4);
			drawCenteredString(g, "Hard mode features death upon passing the borders.", new Rectangle(0, 150, Game.WIDTH, 100), fnt4);
			g.setColor(col);
			g.drawRect(235, 320, 175, 100);
			drawCenteredString(g, "BACK", new Rectangle(235, 320, 175, 100), fnt);
		}
		
		else if (game.gameState == STATE.End) {
			g.drawRect(235, 175, 175, 100);
			drawCenteredString(g, "GAME OVER", new Rectangle(235, 175, 175, 50), fnt2);
			g.setColor(Color.red);
			drawCenteredString(g, "Score: " +hud.getScore(), new Rectangle(235, 195, 175, 50), fnt4);
			g.setColor(col);
			g.drawRect(250, 230, 145, 30);
			drawCenteredString(g, "CONTINUE", new Rectangle(250, 230, 145, 30), fnt3);
		}
	}
	
	/**
	 * Draw the title with a gradually changing colour.
	 *
	 * @param g The Graphics instance.
	 * @param fnt The font used to draw the title.
	 */
	public void drawTitle(Graphics g, Font fnt) {
		int rectX = 185;
		if (timer % 500 == 0) {
			int no = r.nextInt(3);
			switch (no) {
				case 0:
					if (col.getRed() > 249) {
						redFull = true;
					} else if (col.getRed() < 1) {
						redFull = false;
					}
					if (!redFull) {
						col = new Color(col.getRed() + 10, col.getGreen(), col.getBlue());
					} else {
						col = new Color(col.getRed() - 10, col.getGreen(), col.getBlue());
					}
				 
				case 1:
					if (col.getGreen() > 249) {
						greenFull = true;
					} else if (col.getGreen() < 1) {
						greenFull = false;
					}
					if (!greenFull) {
						col = new Color(col.getRed(), col.getGreen() + 10, col.getBlue());
					} else {
						col = new Color(col.getRed(), col.getGreen() - 10, col.getBlue());
					}
				
				
				case 2:
					if (col.getBlue() > 249) {
						blueFull = true;
					} else if (col.getBlue() < 1) {
						blueFull = false;
					}
					if (!blueFull) {
						col = new Color(col.getRed(), col.getGreen(), col.getBlue() + 10);
					} else {
						col = new Color(col.getRed(), col.getGreen(), col.getBlue() - 10);
					}
			}
		}
		
		for (int i = 0; i < title.length(); i++) {
			rectX += 40;
			g.setColor(col);
			drawCenteredString(g, title.substring(i, i+1), new Rectangle(rectX, 50, 30, 100), fnt);
			}
		timer += 1;
	}
	
	/**
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g The Graphics instance.
	 * @param text The String to draw.
	 * @param rect The Rectangle to center the text in.
	 */
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}

}
