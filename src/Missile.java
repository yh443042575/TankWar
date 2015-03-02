import java.awt.Color;
import java.awt.Graphics;




public class Missile {

	private final static int XSPEED = 10;
	private final static int YSPEED = 10;
	
	public final static int WIDTH = 10;
	public final static int HEIGHT = 10;
	
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	int x,y;
	Tank.Direction dir;
	
	public void draw(Graphics g){
		Color color=g.getColor();
		g.setColor(Color.black);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(color);
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

		default:
			break;
		}
	}
	
}
