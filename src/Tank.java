import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {

	private final static int XSPEED = 5;
	private final static int YSPEED = 5;

	private final static int WIDTH = 30;
	private final static int HEIGHT = 30;
	private boolean bL = false, bU = false, bD = false, bR = false;

	enum Direction {
		L, R, U, D, LU, LD, RU, RD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.L;
	int x, y;
	TankClient tc;

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Tank(int x, int y, TankClient tc) {
		this(x, y);
		this.tc = tc;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		switch (ptDir) {
		case L:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
			break;
		case R:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT/2);
			break;
		case U:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y);
			break;
		case D:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT);
			break;
		case LU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
			break;
		case LD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
			break;
		case RU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
			break;
		case RD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.HEIGHT, y+Tank.HEIGHT);
			break;

		default:
			break;
	}
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

		default:
			break;
		}
		
		if(dir!=Direction.STOP)
		{
			this.ptDir=dir;
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
		move();

	}

	private void locateDirection() {
		if (!bL & !bR & !bU & !bD)
			dir = Direction.STOP;
		else if (bL & !bR & !bU & !bD)
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

	}

	public Missile fire() {

		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile missile = new Missile(x, y, ptDir);
		tc.missiles.add(missile);
		return missile;
	}
}
