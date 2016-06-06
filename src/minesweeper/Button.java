package minesweeper;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Button extends Spot implements ActionListener, MouseListener{
	private static final int size = 32;
	private JButton butt;
	private Image image;
	private int state;
	
	public Button() {
		super();
		System.out.println("You used the wrong constructor!");
		butt = new JButton();
		setState(0);
		butt.setPreferredSize(new Dimension(size, size));
		butt.setMaximumSize(butt.getPreferredSize());
		System.out.println(butt.getSize());
		butt.addActionListener(this);
		butt.addMouseListener(this);
		setImage();

	}
	public Button(int a, int b) {
		super(a,b);
		butt = new JButton();
		setState(0);
		butt.setPreferredSize(new Dimension(size, size));
		butt.setMaximumSize(butt.getPreferredSize());
		//System.out.println(butt.getSize());
		butt.addActionListener(this);
		butt.addMouseListener(this);
		setImage();

	}
	public JButton getButton() {
		return butt;
	}
	public void setState(int in) {
		state = in;
	}
	public int getState() {
		return state;
	}
	public void setImage() {
		String name;
		switch (state) {
			case 0:
				name = "blank";
				break;
			case 1:
				name = "open" + getVal();
				break;
			case 2:
				name = "bombdeath";
				break;
			case 3:
				name = "bombflagged";
				break;
			case 4:
				name = "bombrevealed";
				break;
			case 5:
				name = "bombmisflagged";
				break;
			default:
				name = "blank";
				break;
		}
		//System.out.println(state + ", " + name);
		try {
			image = ImageIO.read(new File("src/minesweeper/images/"+name+".gif"));
			butt.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(!MineGUI.hasStarted()) {
			MineGUI.setStarted(true);
			MineGUI.startTime();
		}
		MineGUI.setFace(0);
		if(getMine()) {
			setState(2);
			MineGUI.lost();
		}
		else {
			if(getVal() == 0)
				MineGUI.expandEmpty(getX(), getY());
			setState(1);
		}
		setImage();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		MineGUI.setFace(1);
	}
	@Override
	/* Right clicked method to add/remove flags
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		 butt.getModel().setArmed(false);
         butt.getModel().setPressed(false);
         MineGUI.setFace(0);
             if (SwingUtilities.isRightMouseButton(e)) {
             	if(getState() == 3) {
             		setState(0);
             		MineGUI.decreaseFlags();
             	}
             	else if (getState() == 0 && MineGUI.getFlags() > 0) {
                 setState(3);
                 MineGUI.increaseFlags();
             	}
                 setImage();
         }
	}
}
