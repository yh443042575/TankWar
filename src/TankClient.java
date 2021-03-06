import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

	public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
	Tank myTank = new Tank(50, 500, true,Tank.Direction.STOP, this);
	
	List<Explode> explodes=new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> enemyTanks=new ArrayList<Tank>();
	Image offScreenImage = null;

	@Override
	public void paint(Graphics g) {
		g.drawString("Missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("enemyTanks count:" + enemyTanks.size(), 10, 90);

		if (missiles.size() != 0)
			for (int i=0;i<missiles.size();i++) {
				/*
				 * if(!missile.isAlive()) missiles.remove(missile); else
				 */
				 
				Missile missile=missiles.get(i);
				missile.hitTanks(enemyTanks);
				missile.draw(g);
			}
		for(int i=0;i<explodes.size();i++){
			Explode e=explodes.get(i);
			e.draw(g);
		}
		for(int i=0;i<enemyTanks.size();i++)
		{
			Tank tank=enemyTanks.get(i);
			tank.draw(g);
		}
		myTank.draw(g);
		
		
		

	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}

		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);

	}

	public void lauchFrame() {
		for(int i=0;i<10;i++)
		{
			enemyTanks.add(new Tank(40*(i+1),50,false,Tank.Direction.D,this));
		}
		this.setLocation(200, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.GREEN);
		setTitle("TankVar");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		setResizable(false);

		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.lauchFrame();

	}

	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
	}

}
