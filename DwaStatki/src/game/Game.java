package game;

import java.io.IOException;

import network.GameServer;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.Log;

import control.ClientData;
import control.ServerData;

public class Game extends StateBasedGame{

	public static final String GAMENAME = "2dGame";
	public static final int PORT = 54555;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	public Game(String name, GameServer gameServer, ClientData clientData, ServerData serverData) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY, gameServer, clientData, serverData));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(PLAY).init(gc, this);
		this.enterState(MENU);
	}
	
	public static void main(String[] args) {
		AppGameContainer gameContainer;
		ClientData clientData = ClientData.createClientData(150, 200);
		ServerData serverData = ServerData.createServerData(400, 200);
		GameServer gameServer = new GameServer(clientData);
		
		try {
			gameServer.start(PORT);
			System.out.println("Server started");
			
			Game game = new Game(GAMENAME, gameServer, clientData, serverData); 
			gameContainer = new AppGameContainer(game);
			gameContainer.setDisplayMode(640, 360, false);
			//gameContainer.setDisplayMode(1366, 768, false);
			gameContainer.start();

			
		} catch(SlickException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
