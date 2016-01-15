package control;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class JTextFiledTest extends JFrame {
	  public JTextFiledTest() {
	    super("JTextField Test");

	    getContentPane().setLayout(new FlowLayout());

	    JTextField textField1 = new JTextField("1", 1);

	    getContentPane().add(textField1);

	    setSize(300, 170);
	    setVisible(true);
	  }
}
