package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import model.Bullet;
import model.Rock;
import model.RockData;
import network.GameServer;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import control.ClientData;
import control.CollisionDetector;
import control.ServerData;

public class Play extends BasicGameState{
	
	int shooted = 0;
	
	Image serverShip, clientShip, bg, bullet, rock; 
	
	float bgX = -200;
	float bgY = -800;
	
	private GameServer gameServer;
	private ClientData clientData;
	private ServerData serverData;
	private CollisionDetector detector;
	
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private List<Rock> rocks = new ArrayList<Rock>();
	private List<RockData> rockData = new ArrayList<>();
	
	public Play(int state, GameServer gameServer, ClientData clientData, ServerData serverData) {
		this.gameServer = gameServer;
		this.clientData = clientData;
		this.serverData = serverData;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		serverShip = new Image("res/ship.png");
		clientShip = new Image("res/ship2.png");
		bg = new Image("res/bg.png");
		bullet = new Image("res/bullet.png");
		rock = new Image("res/rock.png");
		detector = new CollisionDetector(serverData, serverShip, clientData, clientShip);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gameServer.send(serverData);
		g.drawImage(bg, bgX, bgY);
		g.drawImage(serverShip, serverData.getShipX(), serverData.getShipY());
		g.drawImage(clientShip, clientData.getShipX(), clientData.getShipY());

		//TODO: winnerDetection();
		
		generateRocks(gc.getScreenWidth());
		//gameServer.send(new ArrayList<Rock>(rocks));
		renderRocks(g);
		handleRocks(gc.getScreenHeight());
		
		chceckClientShoot();
		renderShoots(g);
		handleShoots();
		
		targetDetection();
		//TODO: targerCollisionDetection();
		
		g.drawString("SERVER", 100, 10);
		g.drawString("CLIENT POS:", 10, 120);
		g.drawString("X:"+clientData.getShipX() + "\nY:"+ clientData.getShipY() + "\nPoints:"+ clientData.getPoints(),10,150);
		
		g.drawString("Use 'Q', 'E' and ARROWS to naviagate. SPACE to shoot.", 10, 50);
		g.drawString("Statek X:"+serverData.getShipX() + "\nStatek Y:"+ serverData.getShipY() + "\nPoints:"+ serverData.getPoints(),400,80);
		g.drawString("BG X:"+bgX + "\nBG Y:"+ bgY,400,150);
	}
	
	private void targetDetection() {
		for(Iterator<Rock> rockIterator = rocks.iterator(); rockIterator.hasNext();) {
			Rock currentRock = rockIterator.next();
			for(Iterator<Bullet> bulletIterator = bullets.iterator(); bulletIterator.hasNext();) {
				Bullet currentBullet = bulletIterator.next();
				int currentShipY = 0;
				String author = "NONE";
				if(currentBullet.getAuthor().equals("CLIENT")) {
					currentShipY = clientData.getShipY();
					author = "CLIENT";
				} else if(currentBullet.getAuthor().equals("SERVER")) {
					currentShipY = serverData.getShipY();
					author = "SERVER";
				}
				if(currentRock.getY()<currentShipY &&
					currentBullet.getY()<=currentRock.getY()+currentRock.getHeight() &&
					currentBullet.getX()+currentBullet.getWidth()<=currentRock.getX()+currentRock.getWidth() &&
					currentBullet.getX()>=currentRock.getX()) {
						System.out.println("Kolizja!");
						gameServer.send(currentRock.getId());
						rockIterator.remove();
						bulletIterator.remove();
						if(author.equals("SERVER")) {
							serverData.addPoints();
						} else if (author.equals("CLIENT")) {
							clientData.addPoints();
						}
				}
			}
		}
	}
	
	private void generateRocks(int gameContainerWidht) {
		Random random = new Random();
		int x = random.nextInt(gameContainerWidht);
		int y = 0;
		int id = random.nextInt(10000);
		Rock r = new Rock(rock.copy(),x, y, id);
		int rotation = random.nextInt(359);
		r.getRock().rotate(rotation);
		if(rocks.size()<5) {
			rocks.add(r);
			rockData.add(new RockData(r.getX(), r.getY(), rotation, id));
			gameServer.send(rockData);
			System.out.println("New rock: " + x + " " + y + " size: " + rocks.size());
		}
	}
	
	private void handleRocks(int gameContainerHeight) {
		for(Iterator<Rock> iterator = rocks.iterator(); iterator.hasNext();) {
			Rock current = iterator.next();
			current.incrementY(0.05f);
			if(current.getY() >= gameContainerHeight) {
				iterator.remove();
			}
		}
	}
	
	private void renderRocks(Graphics g) throws SlickException {	
		for(Rock r : rocks) {
			g.drawImage(r.getRock(), r.getX(), r.getY());
		}
	}
	
	public void newServerShoot() {
		Bullet b = new Bullet(bullet, serverData.getShipX()+45, serverData.getShipY(), "SERVER");
		bullets.add(b);
	}
	
	public void chceckClientShoot() {
		//TODO: czy to jest poprawnie?
		if(clientData.getIfNewShootAndReset()) {
			Bullet b = new Bullet(bullet, clientData.getShipX()+45, clientData.getShipY(), "CLIENT");
			bullets.add(b);
		}
	}
	
	private void renderShoots(Graphics g) throws SlickException {	
		for(Bullet b : bullets) {
			g.drawImage(b.getBullet(), b.getX(), b.getY());
			
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
			if(serverData.getShipY()<210 && detector.noDownCollisionDetected() ) {
				serverData.incrementShipY(1);
			}
		}
		if(input.isKeyDown(Input.KEY_UP)){
			if(serverData.getShipY()>0 && detector.noUpCollisionDetected() ) {
				serverData.incrementShipY(-1);
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			if(serverData.getShipX()<530 && detector.noRightCollisionDetected() ) {
				serverData.incrementShipX(1);
			}
		}
		if(input.isKeyDown(Input.KEY_LEFT)){
			if(serverData.getShipX()>10 && detector.noLeftCollisionDetected() ) {
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
			serverData.shoot();
			//serverData.shoot();
			
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
	
}
