package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Bullet;
import network.GameClient;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import control.ClientData;
import control.CollisionDetector;
import control.ServerData;

public class Play extends BasicGameState{

	Image serverShip, clientShip, bg, bullet; 

	float bgX = -200;
	float bgY = -800;

	private GameClient gameClient; 
	private ClientData clientData;
	private ServerData serverData;
	private CollisionDetector detector;
	
	private List<Bullet> bullets = new ArrayList<Bullet>();
	
	public Play(int state, GameClient gameClient, ClientData clientData, ServerData serverData) {
		this.serverData = serverData;
		this.clientData = clientData;
		this.gameClient = gameClient;
	}
	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		serverShip = new Image("res/ship.png");
		clientShip = new Image("res/ship.png");
		bg = new Image("res/bg.png");
		bullet = new Image("res/bullet.png");
		detector = new CollisionDetector(serverData, serverShip, clientData, clientShip);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gameClient.send(clientData);
		g.drawImage(bg, bgX, bgY);
		g.drawImage(serverShip, serverData.getShipX(), serverData.getShipY());
		g.drawImage(clientShip, clientData.getShipX(), clientData.getShipY());

		chceckServerShoot();
		renderShoots(g);
		handleShoots();
		
		g.drawString("CLIENT", 100, 10);
		g.drawString("SERVER POS:", 10, 120);
		g.drawString("X:"+serverData.getShipX() + "\nY:"+ serverData.getShipY(),10,150);
		
		g.drawString("Use 'Q', 'E' and ARROWS to naviagate. SPACE to shoot.", 10, 50);
		g.drawString("Statek X:"+clientData.getShipX() + "\nStatek Y:"+ clientData.getShipY(),400,80);
		g.drawString("BG X:"+bgX + "\nBG Y:"+ bgY,400,130);
	}
	
	public void newClientShoot() {
		Bullet b = new Bullet(bullet, clientData.getShipX()+45, clientData.getShipY());
		bullets.add(b);
	}
	
	public void chceckServerShoot() {
		//TODO: czy to jest poprawnie?
		if(serverData.getIfNewShootAndReset()) {
			Bullet b = new Bullet(bullet, serverData.getShipX()+45, serverData.getShipY());
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
		//gameClient.send(clientData);
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_DOWN)){
			if(clientData.getShipY()<210 && detector.noUpCollisionDetected() ) {
				clientData.incrementShipY(1);
			}
		}
		if(input.isKeyDown(Input.KEY_UP) && detector.noDownCollisionDetected() ){
			if(clientData.getShipY()>0) {
				clientData.incrementShipY(-1);
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT) && detector.noRightCollisionDetected() ){
			if(clientData.getShipX()<530) {
				clientData.incrementShipX(1);
			}
		}
		if(input.isKeyDown(Input.KEY_LEFT) && detector.noLeftCollisionDetected() ){
			if(clientData.getShipX()>10) {
				clientData.incrementShipX(-1);
			}
		}
		if(input.isKeyDown(Input.KEY_E)) {
			clientShip.rotate(1);
		}
		else if(input.isKeyDown(Input.KEY_Q)) {
			clientShip.rotate(-1);
		}
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			newClientShoot();
			clientData.shoot();
			
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
	
}
