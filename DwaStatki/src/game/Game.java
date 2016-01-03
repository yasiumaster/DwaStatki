package game;

import java.io.IOException;

import network.GameServer;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.Log;

public class Game extends StateBasedGame{

	public static final String GAMENAME = "2dGame";
	public static final int PORT = 54555;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	public Game(String name, GameServer gameServer) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY, gameServer));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(PLAY).init(gc, this);
		this.enterState(MENU);
	}
	
	public static void main(String[] args) {
		AppGameContainer gameContainer;
		GameServer gameServer = new GameServer();
		
		try {
			gameServer.start(PORT);
			System.out.println("Server started");
			
			Game game = new Game(GAMENAME, gameServer); 
			gameContainer = new AppGameContainer(game);
			gameContainer.setDisplayMode(640, 360, false);
			gameContainer.start();

			
		} catch(SlickException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
