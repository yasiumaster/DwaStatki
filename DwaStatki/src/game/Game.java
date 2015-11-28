package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{

	public static final String GAMENAME = "2dGame";
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	public Game(String name) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(PLAY).init(gc, this);
		this.enterState(MENU);
	}
	
	public static void main(String[] args) {
		AppGameContainer gameContainer;
		try {
			gameContainer = new AppGameContainer(new Game(GAMENAME));
			gameContainer.setDisplayMode(640, 360, false);
			gameContainer.start();
		} catch(SlickException e) {
			
		}

	}

}
