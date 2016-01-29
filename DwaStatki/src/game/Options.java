package game;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Options extends BasicGameState {

	class Frame extends JFrame {
		private JTextField textField = new JTextField("10");
		private JButton button = new JButton("Potwierdz");
		private JLabel label = new JLabel("Niepoprawna wartosc!");
		JPanel panel = new JPanel();
		public Frame() {
			super("Do ilu punktów:");
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			setVisible(true);
			setSize(200,100);
			label.setVisible(false);
			textField.setBounds(0, 0, 10, 10);
			button.setBounds(10, 10, 10, 10);
			panel.add(label);
			panel.add(button);
			panel.add(textField);

			
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					boolean started = false;
					if(!textField.getText().isEmpty()) {
						String points=textField.getText();
						Play.pointsToWin = (Integer.parseInt(points));
						closeWindow();
						entered = true;
						if(!started) {
							label.setVisible(true);
						}
					}

				}

			});
			this.add(panel);
		}
		
		private void closeWindow() {
			this.dispose();
		}
		
	}

	private Image startButton;
	private int startImgX;
	private int startImgY;
	boolean entered = false;

	public Options(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Frame();
				}
			});
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		startButton = new Image("res/start_button_small.png");
		startImgX = arg0.getScreenWidth() / 2 - startButton.getWidth() / 2;
		startImgY = arg0.getScreenHeight() / 2 - startButton.getHeight() / 2;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {

		arg2.drawImage(new Image("res/bg.png"), 0, 0);
		// arg2.drawImage(startButton, startImgX, startImgY);
		arg2.drawString("Do ilu punktow gramy?",
				arg0.getScreenWidth() / 2, arg0.getScreenHeight() / 2);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

		Input input = arg0.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();

		if (entered) {
			arg1.enterState(1);
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			arg1.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 5;
	}

}
