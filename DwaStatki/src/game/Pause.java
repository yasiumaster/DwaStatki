package game;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Pause extends BasicGameState {
	
	private Image startButton;
	private int startImgX;
	private int startImgY;
	private TextField textField;

	public Pause(int state) {
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		arg2.drawImage(new Image("res/bg.png"), 0, 0);
		//arg2.drawImage(startButton, startImgX, startImgY);
		arg2.drawString("CZY NA PEWNO CHCESZ OPUSCIC GRE?", arg0.getScreenWidth()/2, arg0.getScreenHeight()/2);
		arg2.drawString("[ ENTER ] - Wyjscie | [ ESC ] - Powrot do rozgrywki", arg0.getScreenWidth()/2, 20 + arg0.getScreenHeight()/2);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		Input input = arg0.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		if(input.isKeyPressed(Input.KEY_ENTER)) {
				arg1.enterState(0);
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			arg1.enterState(1);
		}
	}
	@Override
	public int getID() {
		return 3;
	}


}
