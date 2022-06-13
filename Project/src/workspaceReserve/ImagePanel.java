package workspaceReserve;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private Image img;
	
	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}
	
	public ImagePanel(Image img) {
		this.img = img;
        Dimension size = new Dimension(600, 400);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        //setLayout(null);
        this.setOpaque(true);
        setVisible(true);
	}
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        g.drawImage(img, 0, 0,600,400, null);
       

    }
}
