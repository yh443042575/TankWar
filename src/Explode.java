import java.awt.Color;
import java.awt.Graphics;

public class Explode {

	private int x, y;
	private boolean alive = true;
	private TankClient tc;
	private int step=0;

	
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	int[] diameter = { 4, 7, 12, 18, 26, 32, 49, 30, 16, 8 };

	public void draw(Graphics g) {
		if (!alive){
			tc.explodes.remove(this);
			return;
		}
		if(step==diameter.length){
			alive=false;
			step=0;
			return;
		}
			
		Color color = g.getColor();
		g.setColor(Color.ORANGE);
		
			g.fillOval(x, y, diameter[step], diameter[step]);
			step+=1;
		g.setColor(color);
	}
}
