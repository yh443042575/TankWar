import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {

	private final static int XSPEED = 5;
	private final static int YSPEED = 5;

	private final static int WIDTH = 30;
	private final static int HEIGHT = 30;
	private boolean bL = false, bU = false, bD = false, bR = false;

	private boolean good;
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	private boolean alive = true;

	private static Random r = new Random();

	private int step = 0;

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	enum Direction {
		L, R, U, D, LU, LD, RU, RD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.L;
	int x, y;
	TankClient tc;

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, TankClient tc) {
		this(x, y, good);
		this.tc = tc;
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good, tc);
		this.dir = dir;
	}

	public void draw(Graphics g) {
		if (!alive) {

			if (!good) {
				tc.enemyTanks.remove(this);
			}
			return;
		}

		Color c = g.getColor();
		if (this.good == true)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);

		switch (ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y
					+ Tank.HEIGHT / 2);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y + Tank.HEIGHT / 2);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH
					/ 2, y);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH
					/ 2, y + Tank.HEIGHT);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y
					+ Tank.HEIGHT);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2,
					x + Tank.HEIGHT, y + Tank.HEIGHT);
			break;

		default:
			break;
		}
		move();
	}

	private void move() {
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		default:
			break;

		}

		if (dir != Direction.STOP) {
			this.ptDir = dir;
		}

		/**
		 * 控制坦克不出边界
		 */
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

		if (!good) {
			if ((step+1)%10==0) {
				
				Direction[] dirs = Direction.values();
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];			
				if ((step+1)%20==0) {
					step=0;
					this.fire();
				}
			}
			step++;
			System.out.println(step);
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {

		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();

	}

	private void locateDirection() {

		if (bL & !bR & !bU & !bD)
			dir = Direction.L;
		else if (!bL & bR & !bU & !bD)
			dir = Direction.R;
		else if (!bL & !bR & bU & !bD)
			dir = Direction.U;
		else if (!bL & !bR & !bU & bD)
			dir = Direction.D;
		else if (bL & !bR & bU & !bD)
			dir = Direction.LU;
		else if (bL & !bR & !bU & bD)
			dir = Direction.LD;
		else if (!bL & bR & bU & !bD)
			dir = Direction.RU;
		else if (!bL & bR & !bU & bD)
			dir = Direction.RD;
		else if (!bL & !bR & !bU & !bD)
			dir = Direction.STOP;

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SPACE:
			this.fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();

	}

	public Missile fire() {

		if(this.isAlive()){
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile missile = new Missile(x, y, good,ptDir, tc);
		tc.missiles.add(missile);
		return missile;
		}
		else 
			return null;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
