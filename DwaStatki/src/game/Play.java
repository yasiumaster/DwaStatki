package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import model.Bullet;
import model.Rock;
import model.RockData;
import network.GameServer;

import network.Packet;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import control.ClientData;
import control.CollisionDetector;
import control.ServerData;

public class Play extends BasicGameState {

    private final int POINTS_TO_WIN = 1;

    private boolean showInfo = false;
    private static String winner = "NONE";
    private int timer = 0;

    int shooted = 0;

    Image serverShip, clientShip, bg, bullet, rock, screenshot;

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

    //for reset game
    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        winner = "NONE";
        clientData.reset();
        serverData.reset();
    }

    @Override
    public void init(GameContainer arg0, StateBasedGame arg1)
            throws SlickException {
        arg0.setShowFPS(false);
        serverShip = new Image("res/ship.png");
        clientShip = new Image("res/ship2.png");
        bg = new Image("res/bg.png");
        bullet = new Image("res/bullet.png");
        rock = new Image("res/rock.png");
        detector = new CollisionDetector(serverData, serverShip, clientData, clientShip);
        screenshot = new Image(arg0.getWidth(), arg0.getHeight());
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        pause(gc);
        if (!gc.isPaused()) {
            gc.setShowFPS(showInfo);
            gameServer.send(serverData);
            g.drawImage(bg, 0, 0);
            g.drawImage(serverShip, serverData.getShipX(), serverData.getShipY());
            g.drawImage(clientShip, clientData.getShipX(), clientData.getShipY());

            winnerDetection(sbg, g);

            generateRocks(gc.getScreenWidth());
            //gameServer.send(new ArrayList<Rock>(rocks));
            renderRocks(g);
            handleRocks(gc.getScreenHeight());

            g.drawString("SERVER: " + serverData.getPoints(), 10, 20);
            g.drawString("CLIENT: " + clientData.getPoints(), 10, 50);
            g.drawString("HP: " + serverData.getHP(), 10, 80);

            chceckClientShoot();
            renderShoots(g);
            handleShoots();

            targetDetection();
            targetCollisionDetection();

            if (showInfo) {
                g.drawString("SERVER", 100, 10);
                g.drawString("timer " + timer, 100, 20);
                g.drawString("CLIENT POS:", 10, 120);
                g.drawString("X:" + clientData.getShipX() + "\nY:" + clientData.getShipY() + "\nPoints:" + clientData.getPoints(), 10, 150);

                g.drawString("Use ARROWS to naviagate. SPACE to shoot.", 10, 50);
                g.drawString("Statek X:" + serverData.getShipX() + "\nStatek Y:" + serverData.getShipY() + "\nPoints:" + serverData.getPoints(), 400, 80);
                //g.drawString("BG X:"+bgX + "\nBG Y:"+ bgY,400,150);
            }
            g.copyArea(screenshot, 0, 0);
        } else {
            g.drawImage(screenshot, 0, 0);
        }
    }
    
    public static String getWinner() {
    	return winner;
    }

    private void pause(GameContainer gc) {
        if (clientData.isGamePaused()) {
            gc.setPaused(true);
        } else {
            gc.setPaused(false);
        }
    }

    private void winnerDetection(StateBasedGame sbg, Graphics g) {
        if (clientData.getPoints() >= POINTS_TO_WIN || serverData.getHP() == 0) {
            winner = "CLIENT";
        }
        if (serverData.getPoints() >= POINTS_TO_WIN || clientData.getHP() == 0) {
            winner = "SERVER";
        }
        if (!winner.equals("NONE")) {
            g.drawString("WINNER IS: " + winner, 100, 100);
            gameServer.send(winner);
            sbg.enterState(4);
        }
    }

    private void targetCollisionDetection() {
        for (Iterator<Rock> rockIterator = rocks.iterator(); rockIterator.hasNext(); ) {
            Rock currentRock = rockIterator.next();
            if (serverData.getShipX() + serverShip.getWidth() >= currentRock.getX()) {
                if (currentRock.getX() < serverData.getShipX() + serverShip.getWidth()) {
                    if (serverData.getShipX() < currentRock.getWidth() + currentRock.getX()) {
                        if (currentRock.getY() + currentRock.getHeight() >= serverData.getShipY() && currentRock.getY() <= serverData.getShipY() + serverShip.getHeight()) {
                            serverData.hurt();
                            rockIterator.remove();
                        }
                    }
                }
            }

            if (clientData.getShipX() + clientShip.getWidth() >= currentRock.getX()) {
                if (currentRock.getX() < clientData.getShipX() + clientShip.getWidth()) {
                    if (clientData.getShipX() < currentRock.getWidth() + currentRock.getX()) {
                        if (currentRock.getY() + currentRock.getHeight() >= clientData.getShipY() && currentRock.getY() <= clientData.getShipY() + clientShip.getHeight()) {
                            clientData.hurt();
                            //sendClientHPToClient as info
                            //serverData.send(clientData.getHP())
                            rockIterator.remove();
                        }
                    }
                }
            }
        }
    }

    private void targetDetection() {
        for (Iterator<Rock> rockIterator = rocks.iterator(); rockIterator.hasNext(); ) {
            Rock currentRock = rockIterator.next();
            for (Iterator<Bullet> bulletIterator = bullets.iterator(); bulletIterator.hasNext(); ) {
                Bullet currentBullet = bulletIterator.next();
                int currentShipY = 0;
                String author = "NONE";
                if (currentBullet.getAuthor().equals("CLIENT")) {
                    currentShipY = clientData.getShipY();
                    author = "CLIENT";
                } else if (currentBullet.getAuthor().equals("SERVER")) {
                    currentShipY = serverData.getShipY();
                    author = "SERVER";
                }
                if (currentRock.getY() < currentShipY &&
                        currentBullet.getY() <= currentRock.getY() + currentRock.getHeight() &&
                        currentBullet.getX() + currentBullet.getWidth() <= currentRock.getX() + currentRock.getWidth() &&
                        currentBullet.getX() >= currentRock.getX()) {
                    System.out.println("Autor: " + author);
                    gameServer.send(currentRock.getId());
                    rockIterator.remove();
                    bulletIterator.remove();
                    if (author.equals("SERVER")) {
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
        Rock r = new Rock(rock.copy(), x, y, id);
        int rotation = random.nextInt(359);
        r.getRock().rotate(rotation);
        if (rocks.size() < 5) {
            rocks.add(r);
            rockData.add(new RockData(r.getX(), r.getY(), rotation, id));
            gameServer.send(rockData);
        }
    }

    private void handleRocks(int gameContainerHeight) {
        for (Iterator<Rock> iterator = rocks.iterator(); iterator.hasNext(); ) {
            Rock current = iterator.next();
            current.incrementY(0.05f);
            if (current.getY() >= gameContainerHeight) {
                iterator.remove();
            }
        }
    }

    private void renderRocks(Graphics g) throws SlickException {
        for (Rock r : rocks) {
            g.drawImage(r.getRock(), r.getX(), r.getY());
        }
    }

    public void newServerShoot() {
        Bullet b = new Bullet(bullet, serverData.getShipX() + 45, serverData.getShipY(), "SERVER");
        bullets.add(b);
    }

    public void chceckClientShoot() {
        //TODO: czy to jest poprawnie?
        if (clientData.getIfNewShootAndReset()) {
            Bullet b = new Bullet(bullet, clientData.getShipX() + 45, clientData.getShipY(), "CLIENT");
            bullets.add(b);
        }
    }

    private void renderShoots(Graphics g) throws SlickException {
        for (Bullet b : bullets) {
            g.drawImage(b.getBullet(), b.getX(), b.getY());

        }
    }

    private void handleShoots() {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet current = iterator.next();
            current.incrementY(1);
            if (current.getY() == 0) {
                iterator.remove();
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int g)
            throws SlickException {
        //gameServer.send(serverData);
        timer += 1;
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_DOWN)) {
            if (serverData.getShipY() < gc.getHeight() - 10 && detector.noDownCollisionDetected()) {
                serverData.incrementShipY(1);
            }
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            if (serverData.getShipY() > 0 + 10 && detector.noUpCollisionDetected()) {
                serverData.incrementShipY(-1);
            }
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            if (serverData.getShipX() < gc.getWidth() - 10 && detector.noRightCollisionDetected()) {
                serverData.incrementShipX(1);
            }
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            if (serverData.getShipX() > 0 + 10 && detector.noLeftCollisionDetected()) {
                serverData.incrementShipX(-1);
            }
        }
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            newServerShoot();
            serverData.shoot();
        }
        if (input.isKeyPressed(Input.KEY_F2)) {
            if (showInfo)
                showInfo = false;
            else
                showInfo = true;
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            //sbg.enterState(0);
            sbg.enterState(3);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_P)) {
            boolean pauseState = !gc.isPaused();
            gc.setPaused(pauseState);

            clientData.setGamePaused(gc.isPaused());
            serverData.setGamePaused(gc.isPaused());
            gameServer.sendPauseState(gc.isPaused());
        }
    }

    @Override
    public int getID() {
        return 1;
    }

}
