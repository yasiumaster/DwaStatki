package game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import model.RockData;
import network.GameClient;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import control.ClientData;
import control.ServerData;

public class Game extends StateBasedGame{

	public static final String GAMENAME = "2dGame";
	public static int PORT = 54555;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int OPTIONS = 2;
	
	public Game(String name, GameClient gameClient, ClientData clientData, ServerData serverData, List<RockData> rockData, List<Integer> toRemoveRocks) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY, gameClient, clientData, serverData, rockData, toRemoveRocks));
		this.addState(new Options(OPTIONS));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(PLAY).init(gc, this);
		this.getState(OPTIONS).init(gc, this);
		this.enterState(MENU);
		gc.setAlwaysRender(true);
	}
	
	public static void main(String[] args) {
		PrintStream out, err;
		try {
			out = new PrintStream(new FileOutputStream("client.log"));
			err = new PrintStream(new FileOutputStream("error.log"));
			System.setOut(out);
			System.setErr(err);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		AppGameContainer gameContainer;
		ClientData clientData = ClientData.createClientData(50, 150);
		ServerData serverData = ServerData.createServerData(300, 150);
		List<RockData> rockData = new ArrayList<>();
		List<Integer> toRemoveRocks = new ArrayList<>();
		GameClient gameClient = new GameClient(serverData, rockData, toRemoveRocks);
		String IPAddress = null;
		try {
			IPAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		try {
			gameClient.start(IPAddress, PORT);
			System.out.println("Client started");
			
			Game game = new Game(GAMENAME, gameClient, clientData, serverData, rockData, toRemoveRocks); 
			gameContainer = new AppGameContainer(game);
			int displayX = gameContainer.getScreenWidth();
			int displayY = gameContainer.getScreenHeight();
			gameContainer.setDisplayMode(640, 480, false);
			//gameContainer.setDisplayMode(displayX, displayY, true);
			gameContainer.start();
			
		} catch(SlickException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
