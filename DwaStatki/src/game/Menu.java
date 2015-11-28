package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState{

	public Menu(int state) {
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.drawImage(new Image("res/bg.png"), 0, 0);
		arg2.setColor(Color.blue);
		arg2.fillRect(300, 180, 90, 25);
		arg2.setColor(Color.white);
		arg2.drawString("Start!", 320, 185);
		
		/*int ypos = Mouse.getY();
		int xpos = Mouse.getX();
		arg2.drawString("Mouse Y " + ypos, 100, 100);
		arg2.drawString("Mouse X " + xpos, 250, 100);*/
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input input = arg0.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		if((xpos>300 && xpos<390)&&(ypos>155 && ypos<180)) {
			if(input.isMouseButtonDown(0)) {
				arg1.enterState(1);
			}
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
