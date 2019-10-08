
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Oui implements ActionListener {
private File f;
	public Oui(File f) {
		this.f=f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		f.delete();
		
	}

	
	}
