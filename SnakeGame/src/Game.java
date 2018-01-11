import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{
	
	public static final int JWIDTH = 651, JHEIGHT = 478;
	public static int WIDTH, HEIGHT;
	public static boolean isEaten;
	public static boolean isDead;
	public static boolean hardDiff;
	public static boolean paused = false;
	
	private Thread thread;
	private boolean running = false;
	private static Random r = new Random();
	private static int cols;
	private static int rows;
	
	private Handler handler;
	private HUD hud;
	private Menu menu;
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		new Window(JWIDTH, JHEIGHT, "Snake", this);
		this.addKeyListener(new KeyInput(handler, this));
		this.addMouseListener(menu);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 20.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
                    long now = System.nanoTime();
                    delta += (now - lastTime) / ns;
                    lastTime = now;
                    while(delta >=1)
                            {
                                tick();
                                delta--;
                            }
                            if(running)
                                render();
                            frames++;
                            
                            if(System.currentTimeMillis() - timer > 1000)
                            {
                                timer += 1000;
                                System.out.println("FPS: "+ frames);
                                frames = 0;
                            }
        }
                stop();
    }
	
	/**
	 * Call all relevant tick() methods, unless game is paused.
	 * Respawn food object if eaten.
	 * Progress game to end screen if snake is dead.
	 * 
	 */
	private void tick() {
		if (gameState == STATE.Game) {
			if (!paused) {
				handler.tick();
				hud.tick();
				
				if (isEaten) {
					for (int i = 0; i < handler.object.size(); i++) {
						GameObject tempObject = handler.object.get(i);
						
						if (tempObject.getId() == ID.Food) {
							handler.removeObject(tempObject);
							handler.addObject(new Food(getLocX(), getLocY(), ID.Food));
							isEaten = false;
						}
					}
				}
				
				if (isDead) {
					handler.clearObjects();
					gameState = STATE.End;
				}
			}
		}
		
		else if (gameState != STATE.Game) {
			menu.tick();
			handler.tick();
		}
	}
	
	/**
	 * Call all relevant render() methods.
	 * Render text to display paused status.
	 * 
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if (paused) {
			g.drawString("PAUSED", 15, 30);
		}
		
		if (gameState == STATE.Game) {
			hud.render(g);
		} else if (gameState != STATE.Game) {
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	/**
	 * Generate a random x coordinate for food object on the invisible grid (15).
	 * 
	 */
	public static int getLocX() {
		rows = Game.WIDTH / 15;
		return (r.nextInt(rows) * 15);
	}
	
	/**
	 * Generate a random y coordinate for food object on the invisible grid (15).
	 * 
	 */
	public static int getLocY() {
		cols = Game.HEIGHT / 15;
		return (r.nextInt(cols) * 15);
		
	}
	
	public static void main(String[] args) {
		new Game();
	}

	

}
