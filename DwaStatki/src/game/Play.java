package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Bullet;
import model.Rock;
import network.GameServer;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import control.ClientData;
import control.ServerData;

public class Play extends BasicGameState{
	
	int shooted = 0;
	
	Image serverShip, clientShip, bg, bullet; 
	
	float bgX = -200;
	float bgY = -800;
	
	private GameServer gameServer;
	private ClientData clientData;
	private ServerData serverData;
	
	private List<Bullet> bullets = new ArrayList<Bullet>();
	
	public Play(int state, GameServer gameServer, ClientData clientData, ServerData serverData) {
		this.gameServer = gameServer;
		this.clientData = clientData;
		this.serverData = serverData;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		serverShip = new Image("res/ship.png");
		clientShip = new Image("res/ship.png");
		bg = new Image("res/bg.png");
		bullet = new Image("res/bullet.png");

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gameServer.send(serverData);
		g.drawImage(bg, bgX, bgY);
		g.drawImage(serverShip, serverData.getShipX(), serverData.getShipY());
		g.drawImage(clientShip, clientData.getShipX(), clientData.getShipY());

		//targets
		//generateRocks(g);
		Rock rock = new Rock();
		//g.drawImage(rock.getRock(), rock.getX(), rock.getY());
		
		chceckClientShoot();
		renderShoots(g);
		handleShoots();
		
		g.drawString("SERVER", 100, 10);
		g.drawString("CLIENT POS:", 10, 120);
		g.drawString("X:"+clientData.getShipX() + "\nY:"+ clientData.getShipY(),10,150);
		
		g.drawString("Use 'Q', 'E' and ARROWS to naviagate. SPACE to shoot.", 10, 50);
		g.drawString("Statek X:"+serverData.getShipX() + "\nStatek Y:"+ serverData.getShipY(),400,80);
		g.drawString("BG X:"+bgX + "\nBG Y:"+ bgY,400,130);
	}
	
	public void newServerShoot() {
		Bullet b = new Bullet(bullet, serverData.getShipX()+45, serverData.getShipY());
		bullets.add(b);
	}
	
	public void chceckClientShoot() {
		//TODO: czy to jest poprawnie?
		if(clientData.getIfNewShootAndReset()) {
			Bullet b = new Bullet(bullet, clientData.getShipX()+45, clientData.getShipY());
			bullets.add(b);
		}
	}
	
	private void renderShoots(Graphics g) throws SlickException {	
		for(Bullet b : bullets) {
			g.drawImage(new Image("res/bullet.png"), b.getX(), b.getY());
		}
	}
	
	private void handleShoots() {
		for(Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
			Bullet current = iterator.next();
			current.incrementY(1);
			if(current.getY() == 0) {
				iterator.remove();
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		//gameServer.send(serverData);
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_DOWN)){
			if(serverData.getShipY()<210) {
				serverData.incrementShipY(1);
			}
		}
		if(input.isKeyDown(Input.KEY_UP)){
			if(serverData.getShipY()>0) {
				serverData.incrementShipY(-1);
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			if(serverData.getShipX()<530) {
				serverData.incrementShipX(1);
			}
		}
		if(input.isKeyDown(Input.KEY_LEFT)){
			if(serverData.getShipX()>10) {
				serverData.incrementShipX(-1);
			}
		}
		if(input.isKeyDown(Input.KEY_E)) {
			serverShip.rotate(1);
		}
		else if(input.isKeyDown(Input.KEY_Q)) {
			serverShip.rotate(-1);
		}
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			newServerShoot();
			//serverData.shoot();
			
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
	
}
