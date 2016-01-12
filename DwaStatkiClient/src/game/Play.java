package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Bullet;
import model.Rock;
import model.RockData;
import network.GameClient;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import control.ClientData;
import control.CollisionDetector;
import control.GameHelper;
import control.ServerData;

public class Play extends BasicGameState {

    private boolean showInfo = false;
    private int timer = 0;

    Image serverShip, clientShip, bg, bullet, rock, screenshot;

    private GameClient gameClient;
    private ClientData clientData;
    private ServerData serverData;
    private CollisionDetector detector;

    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<Rock> rocks = new ArrayList<Rock>();
    private List<RockData> rockData;
    private List<Integer> toRemoveRocks;

    public Play(int state, GameClient gameClient, ClientData clientData, ServerData serverData, List<RockData> rockData, List<Integer> toRemoveRocks) {
        this.serverData = serverData;
        this.clientData = clientData;
        this.gameClient = gameClient;
        this.rockData = rockData;
        this.toRemoveRocks = toRemoveRocks;
    }

    //for reset game
    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        GameHelper.setWinner("NONE");
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
            gameClient.send(clientData);
            g.drawImage(bg, 0, 0);
            g.drawImage(serverShip, serverData.getShipX(), serverData.getShipY());
            g.drawImage(clientShip, clientData.getShipX(), clientData.getShipY());

            winnerDetection(sbg, g);

            generateRocks();
            renderRocks(g);
            handleRocks(gc.getScreenHeight());

            g.drawString("SERVER: " + serverData.getPoints(), 10, 20);
            g.drawString("CLIENT: " + clientData.getPoints(), 10, 50);
            g.drawString("HP: " + clientData.getHP(), 10, 80);

            chceckServerShoot();
            renderShoots(g);
            handleShoots();

            targetDetection();
            targetCollisionDetection();

            if (showInfo) {
                g.drawString("CLIENT", 100, 10);
                g.drawString("timer " + timer, 100, 20);
                g.drawString("SERVER POS:", 10, 120);
                g.drawString("X:" + serverData.getShipX() + "\nY:" + serverData.getShipY(), 10, 150);

                g.drawString("Use ARROWS to naviagate. SPACE to shoot.", 10, 50);
                g.drawString("Statek X:" + clientData.getShipX() + "\nStatek Y:" + clientData.getShipY(), 400, 80);
                //g.drawString("BG X:"+bgX + "\nBG Y:"+ bgY,400,130);
            }
            g.copyArea(screenshot, 0, 0);
        } else {
            g.drawImage(screenshot, 0, 0);
        }
    }

    private void pause(GameContainer gc) {
        if (serverData.isGamePaused()) {
            gc.setPaused(true);
        } else {
            gc.setPaused(false);
        }
    }

    private void winnerDetection(StateBasedGame sbg, Graphics g) {
        if (!GameHelper.getWinner().equals("NONE")) {
            timer = 0;
            g.drawString("WINNER IS: " + GameHelper.getWinner(), 100, 100);
            if (timer > 3000)
                sbg.enterState(0);
        }
    }

    private void generateRocks() {
        for (Iterator<RockData> iterator = rockData.iterator(); iterator.hasNext(); ) {
            RockData rockDatum = iterator.next();
            iterator.remove();
            Image rockImg = rock.copy();
            rockImg.rotate(rockDatum.getRotation());
            rocks.add(new Rock(rockImg, rockDatum.getX(), rockDatum.getY(), rockDatum.getId()));
        }
    }

    private void handleRocks(int gameContainerHeight) {
        for (Iterator<Rock> iterator = rocks.iterator(); iterator.hasNext(); ) {
            Rock current = iterator.next();
            boolean removed = false;
            current.incrementY(0.05f);
            for (Iterator<Integer> removingIt = toRemoveRocks.iterator(); removingIt.hasNext(); ) {
                Integer toRemoveRock = removingIt.next();
                if (current.getId() == toRemoveRock) {
                    iterator.remove();
                    removingIt.remove();
                    removed = true;
                }
            }
            if (removed)
                continue;
            if (current.getY() >= gameContainerHeight) {
                iterator.remove();
            }
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
                    gameClient.send(currentRock.getId());
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

    private void renderRocks(Graphics g) throws SlickException {
        for (Rock r : rocks) {
            g.drawImage(r.getRock(), r.getX(), r.getY());
        }
    }

    public void newClientShoot() {
        Bullet b = new Bullet(bullet, clientData.getShipX() + 45, clientData.getShipY(), "CLIENT");
        bullets.add(b);
    }

    public void chceckServerShoot() {
        //TODO: czy to jest poprawnie?
        if (serverData.getIfNewShootAndReset()) {
            Bullet b = new Bullet(bullet, serverData.getShipX() + 45, serverData.getShipY(), "SERVER");
            bullets.add(b);
        }
    }

    private void renderShoots(Graphics g) throws SlickException {
        for (Bullet b : bullets) {
            g.drawImage(new Image("res/bullet.png"), b.getX(), b.getY());
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
        //gameClient.send(clientData);
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_DOWN)) {
            if (clientData.getShipY() < gc.getHeight() - 10 && detector.noDownCollisionDetected()) {
                clientData.incrementShipY(1);
            }
        }
        if (input.isKeyDown(Input.KEY_UP) && detector.noUpCollisionDetected()) {
            if (clientData.getShipY() > 0 + 10) {
                clientData.incrementShipY(-1);
            }
        }
        if (input.isKeyDown(Input.KEY_RIGHT) && detector.noRightCollisionDetected()) {
            if (clientData.getShipX() < gc.getWidth() - 10) {
                clientData.incrementShipX(1);
            }
        }
        if (input.isKeyDown(Input.KEY_LEFT) && detector.noLeftCollisionDetected()) {
            if (clientData.getShipX() > 0 + 10) {
                clientData.incrementShipX(-1);
            }
        }
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            newClientShoot();
            clientData.shoot();
        }
        if (input.isKeyPressed(Input.KEY_F2)) {
            if (showInfo)
                showInfo = false;
            else
                showInfo = true;
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            sbg.enterState(0);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_P)) {
            boolean pauseState = !gc.isPaused();
            gc.setPaused(pauseState);

            clientData.setGamePaused(gc.isPaused());
            serverData.setGamePaused(gc.isPaused());
            gameClient.sendPauseState(gc.isPaused());
        }
    }

    @Override
    public int getID() {
        return 1;
    }

}
