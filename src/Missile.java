import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile {

	private final static int XSPEED = 10;
	private final static int YSPEED = 10;

	public final static int WIDTH = 10;
	public final static int HEIGHT = 10;

	private boolean alive = true;
	private TankClient tc;

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x,int y,Tank.Direction dir ,TankClient tc){
		this(x, y, dir);
		this.tc=tc;
	}

	int x, y;
	Tank.Direction dir;

	public void draw(Graphics g) {
		if(!alive){
			tc.missiles.remove(this);
			return ;
		}
			
		
		Color color = g.getColor();
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

		if (x < 0 || y < 0 || y > TankClient.GAME_HEIGHT
				|| x > TankClient.GAME_WIDTH)
			{alive = false;
			}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if(this.getRect().intersects(t.getRect())&&t.isAlive()){
			t.setAlive(false);
			this.alive=false;
			return true;
		}
		else 
		return false;
			
			
		
	}

}
