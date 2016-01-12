package game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	public static final int PAUSE = 3;
	public static final int END = 4;
	
	public Game(String name, GameServer gameServer, ClientData clientData, ServerData serverData) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY, gameServer, clientData, serverData));
		this.addState(new Pause(PAUSE));
		this.addState(new End(END));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(PLAY).init(gc, this);
		this.getState(PAUSE).init(gc, this);
		this.getState(END).init(gc, this);
		this.enterState(MENU);
		gc.setAlwaysRender(true);
	}
	
	public static void main(String[] args) {
		PrintStream out, err;
		try {
			out = new PrintStream(new FileOutputStream("server.log"));
			err = new PrintStream(new FileOutputStream("error.log"));
			//System.setOut(out);
			//System.setErr(err);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		AppGameContainer gameContainer;
		ClientData clientData = ClientData.createClientData(150, 200);
		ServerData serverData = ServerData.createServerData(400, 200);
		GameServer gameServer = new GameServer(clientData);
		String IPAddress = null;
		try {
			IPAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		System.out.println("IP_SERVER: " + IPAddress);
		try {
			gameServer.start(PORT);
			System.out.println("Server started");
			
			Game game = new Game(GAMENAME, gameServer, clientData, serverData); 
			gameContainer = new AppGameContainer(game);
			int displayX = gameContainer.getScreenWidth();
			int displayY = gameContainer.getScreenHeight();
			gameContainer.setDisplayMode(640, 480, false);
			//gameContainer.setDisplayMode(displayX, displayY, true);
			gameContainer.start();

			
		} catch(SlickException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
