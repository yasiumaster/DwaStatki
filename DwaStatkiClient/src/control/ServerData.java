package control;

public class ServerData implements ShipAction {

    private int shipX;
    private int shipY;
    private int hp = 100;
    private int points = 0;
    private boolean newShoot = false;
    private boolean isGamePaused = false;
    private static ServerData serverData = null;

    private ServerData() {
    }

    public static ServerData createServerData(int x, int y) {
        if (serverData == null) {
            serverData = new ServerData();
            serverData.shipX = x;
            serverData.shipY = y;
        }

        return serverData;
    }

    public boolean getIfNewShootAndReset() {
        if (newShoot) {
            System.out.println("New shoot from server!");
            newShoot = false;
            return true;
        }
        return false;
    }

    public void incrementShipY(int i) {
        shipY += i;
    }

    public void incrementShipX(int i) {
        shipX += i;
    }

    public int getShipX() {
        return shipX;
    }

    public int getShipY() {
        return shipY;
    }

    public void setShipX(int shipX) {
        this.shipX = shipX;
    }

    public void setShipY(int shipY) {
        this.shipY = shipY;
    }

    @Override
    public void shoot() {
        newShoot = true;

    }

    @Override
    public void reset() {
        hp = 100;
        points = 0;
    }

    @Override
    public void addPoints() {
        points++;
    }

    @Override
    public void hurt() {
        if (hp > 0)
            hp -= 10;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int i) {
        this.points = i;
    }

    public int getHP() {
        return hp;
    }

    public void setGamePaused(boolean gamePaused) {
        isGamePaused = gamePaused;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }
}
