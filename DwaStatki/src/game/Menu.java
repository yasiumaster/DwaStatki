package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState{

	private Image startButton;
	private int startImgX;
	private int startImgY;
	
	public Menu(int state) {
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		startButton = new Image("res/start_button_small.png");
		startImgX = arg0.getScreenWidth()/2 - startButton.getWidth()/2;
		startImgY = arg0.getScreenHeight()/2 - startButton.getHeight()/2;
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.drawImage(new Image("res/bg.png"), 0, 0);
		arg2.drawString("SERVER", 100, 100);
		
/*		int ypos = Mouse.getY();
		int xpos = Mouse.getX();
		arg2.drawString("Mouse Y " + ypos, 100, 100);
		arg2.drawString("Mouse X " + xpos, 250, 100);*/
		
		arg2.drawImage(startButton, startImgX, startImgY);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input input = arg0.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		if(input.isKeyPressed(Input.KEY_ENTER)) {
			arg1.enterState(5);
		}
		if(xpos>startImgX && xpos<startImgX+startButton.getWidth() 
				&& ypos>startImgY && ypos<startImgY+startButton.getHeight()) {
			if(input.isMouseButtonDown(0)) {
				arg1.enterState(5);
			}
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			arg0.exit();
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
