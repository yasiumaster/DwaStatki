package game;

import java.util.List;

import network.GameServer;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState{
	
	class Bullet {
		Image img;
		int x = 0;
		int y = 0;
		
		public Bullet(Image img, int x, int y) throws SlickException {
			this.img = img;
			this.x = x;
			this.y = y;
		}
	}
	
	Image ship, bg, bullet; 
	List<Bullet> bullets;
	int shipX = 100;
	int shipY = 100;
	float bgX = -200;
	float bgY = -800;
	float bulletX = 0;
	float bulletY = 0;
	boolean shooted = false;
	boolean newShooted = false;
	public Play(int state, GameServer gameServer) {
		this.gameServer = gameServer;
	}
	
	private GameServer gameServer; 
	public Play(int state) {
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		ship = new Image("res/ship.png");
		bg = new Image("res/bg.png");
		bullet = new Image("res/bullet.png");

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gameServer.send(gc);
		g.drawImage(bg, bgX, bgY);
		g.drawString("Use 'Q', 'E' and ARROWS to naviagate. SPACE to shoot.", 10, 50);
		g.drawImage(ship, shipX, shipY);
		g.drawString("Statek X:"+shipX + "\nStatek Y:"+ shipY,400,80);
		g.drawString("BG X:"+bgX + "\nBG Y:"+ bgY,400,130);

		if(newShooted || shooted) {
			//render
			if(newShooted) {
				bulletX = shipX+(ship.getWidth()/2)-7;
				bulletY = shipY-15;
			}
			g.drawImage(bullet, bulletX, bulletY);
			newShooted=false;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		gameServer.send(gc);
		Input input = gc.getInput();
		bgY+=0.1;
		if(bgY>0) {
			bgY=-800;
		}
		
		if(shooted)
			bulletY-=0.5;
		
		if(input.isKeyDown(Input.KEY_DOWN)){
			if(shipY<210) {
				shipY += 1;
			}
		}
		if(input.isKeyDown(Input.KEY_UP)){
			if(shipY>0) {
				shipY -= 1;
				bgY += 0.2;
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			if(shipX<530) {
				shipX += 1;
				bgX+=0.4;
			}
		}
		if(input.isKeyDown(Input.KEY_LEFT)){
			if(shipX>10) {
				shipX -= 1;
				bgX-=0.4;
			}
		}
		if(input.isKeyDown(Input.KEY_E)) {
			ship.rotate(1);
		}
		else if(input.isKeyDown(Input.KEY_Q)) {
			ship.rotate(-1);
		}
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			
			newShooted = true;
			shooted = true;
			
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
	
}
