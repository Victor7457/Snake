// Imports:
import javax.swing.JFrame;

//extends the JFrame class
public class GameFrame extends JFrame{
	GameFrame() {
		// old:
		//	GamePanel panel = new GamePanel();		
		//	this.add(panel);
		
		// single use of the code (works the same)
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}
