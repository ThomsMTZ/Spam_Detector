
import java.awt.*;
import java.awt.event.*;

public class Non implements ActionListener {
	private Frame fenetre;

	public Non(Frame fenetre) {
		this.fenetre = fenetre;
	}
	
	public void actionPerformed(ActionEvent e) {
		fenetre.dispose();

	}

}