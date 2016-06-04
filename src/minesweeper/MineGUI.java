package minesweeper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


public class MineGUI extends JFrame implements ActionListener{

	private static final int SIZE = 32;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 600;
	
	private static ArrayList<Button> buts = new ArrayList<Button>();
	private static Button[][] stat;
	private static boolean[][] visited;
	private static int size, sizex;
	private static int flags;
	private static boolean start;
	private static int watch;
	
	private static Timer time;
	
	private JPanel toppane;
	private JPanel panel;
	
	private static JLabel timeh, timet, timeo;
	private static Image timehuns, timetens, timeones;
	
	private static JLabel flagh, flagt, flago;
	private static Image flaghuns, flagtens, flagones;

	private GridLayout grid;
	
	public static JButton newGame;
	private static Image face;
		
	public MineGUI() {
		super("Minesweeper");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		start = false;

		toppane = new JPanel();
		toppane.setLayout(new BoxLayout(toppane, BoxLayout.LINE_AXIS));
		newGame = new JButton("");
		newGame.setPreferredSize(new Dimension(52, 52));
		newGame.setMaximumSize(newGame.getPreferredSize());
		setFace(0);
		newGame.addActionListener(this);
		
		flagh = new JLabel("");
		flagt = new JLabel("");
		flago = new JLabel("");
		
		timeh = new JLabel("");
		timet = new JLabel("");
		timeo = new JLabel("");
		
		toppane.add(flagh);
		toppane.add(flagt);
		toppane.add(flago);
		toppane.add(Box.createHorizontalGlue());
		toppane.add(newGame);
		toppane.add(Box.createHorizontalGlue());
		toppane.add(timeh);
		toppane.add(timet);
		toppane.add(timeo);
		resetTime();
		panel = new JPanel();
		
		//start with beginner
		loadGame(9);
		
		getContentPane().add(toppane, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JOptionPane.showMessageDialog(this, "Minesweeper by Michael W.\nClick the buttons in the grid to reveal each square", "Information", JOptionPane.PLAIN_MESSAGE);
		}
	public static int getBombNum() {
		switch (size) {
		case 9:
			return 10;
		case 16:
			return 40;
		case 30:
			return 99;
		default:
			return 0;
		}
	}
	public static void setFace(int i) {
		String a;
		switch (i) {
		case 0:
			a = "smile";
			break;
		case 1:
			a = "ooh";
			break;
		case 2:
			a = "dead";
			break;
		case 3:
			a = "win";
			break;
		default:
			a = "smile";
		}
		try {
			face = ImageIO.read(new File("src/minesweeper/images/face" + a + ".gif"));
			newGame.setIcon(new ImageIcon(face));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static boolean hasStarted() {
		return start;
	}
	public static void setStarted(boolean a) {
		start = a;
	}
	private static void resetTime() {
		start = false;
		watch = 0;
		setTime();
	}
	public static void startTime() {
		watch = 0;
		time = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	watch++;
            	setTime();
            }
        });
		time.start();
	}
	private static void setTime() {
		int h = watch/100, t = watch/10, o = watch % 10;
		try {
			timehuns = ImageIO.read(new File("src/minesweeper/images/time"+ h + ".gif"));
			timetens = ImageIO.read(new File("src/minesweeper/images/time"+ t + ".gif"));
			timeones = ImageIO.read(new File("src/minesweeper/images/time"+ o + ".gif"));
			timeh.setIcon(new ImageIcon(timehuns));
			timet.setIcon(new ImageIcon(timetens));
			timeo.setIcon(new ImageIcon(timeones));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * Randomly assigns bombs to certain amount of squares after shuffling ArrayList
	 */
	private static void setLabel() {
		int hunids = getFlags()/100;
		int tens = getFlags()/10;
		int ones = getFlags() % 10;
		try {
			flaghuns = ImageIO.read(new File("src/minesweeper/images/time"+ hunids + ".gif"));
			flagtens = ImageIO.read(new File("src/minesweeper/images/time"+ tens + ".gif"));
			flagones = ImageIO.read(new File("src/minesweeper/images/time"+ ones + ".gif"));
			flagh.setIcon(new ImageIcon(flaghuns));
			flagt.setIcon(new ImageIcon(flagtens));
			flago.setIcon(new ImageIcon(flagones));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void bombAssign() {
		Collections.shuffle(buts);
		for (int i = 0; i < getBombNum(); i++) {
			buts.get(i).setMine(true);
		}
	}
	/*
	 * Calculates number of mines surrounding square
	 */
	private void setValues() {
		for (int i = 0; i < sizex; i++) { //col
			for (int j = 0; j < size; j++) { //row
				int ct = 0;
				//very unoptimized checking
				//left
				if (i > 0 && stat[i-1][j].getMine())
					ct++;
				//right
				if (i < sizex -1 && stat[i+1][j].getMine())
					ct++;
				//up
				if (j > 0 && stat[i][j-1].getMine())
					ct++;
				//down
				if (j < size -1 && stat[i][j+1].getMine())
					ct++;
				//top left
				if (i > 0 && j > 0 && stat[i-1][j-1].getMine())
					ct++;
				//top right
				if (i < sizex -1 && j > 0 && stat[i+1][j-1].getMine())
					ct++;
				//bottom left
				if (i > 0 && j < size -1 && stat[i-1][j+1].getMine())
					ct++;
				//bottom right
				if (i < sizex -1 && j < size -1 && stat[i+1][j+1].getMine())
					ct++;
				stat[i][j].setVal(ct);
			}
		}
	}
	public static void expandEmpty(int i, int j) {
		if(visited[i][j] == false && !stat[i][j].getMine()) {
			stat[i][j].setState(1);
			stat[i][j].setImage();
			visited[i][j] = true;
			if (stat[i][j].getVal() == 0) {
				if (i > 0)
					expandEmpty(i-1, j);
				if (i < sizex-1)
					expandEmpty(i+1, j);
				if (j > 0)
					expandEmpty(i, j-1);
				if (j < size-1)
					expandEmpty(i,j+1);
				if (i > 0 && j > 0)
					expandEmpty(i-1, j-1);
				if (i < sizex -1 && j > 0)
					expandEmpty(i+1, j-1);
				if (i > 0 && j < size -1)
					expandEmpty(i-1, j+1);
				if (i < sizex -1 && j < size - 1)
					expandEmpty(i+1,j+1);
			}
		}
		return;
	}
	public static int getFlags() {
		return getBombNum()-flags;
	}
	public static void decreaseFlags() {
		flags--;
		setLabel();
	}
	public static void increaseFlags() {
		flags++;
		setLabel();
		if (getFlags() == 0) {
			checkWin();
		}
	}
	public static void lost() {
		setFace(2);
		for (Button[] a: stat) {
			for (Button b: a) {
				b.getButton().removeActionListener(b);
				b.getButton().removeMouseListener(b);
			}
		}
		for (int i = 0; i < getBombNum(); i++) {
			if (buts.get(i).getState() != 2)
				buts.get(i).setState(4);
			buts.get(i).setImage();
			}
		for (int i = getBombNum(); i < sizex*size; i++) {
			if(buts.get(i).getState() == 3 && !buts.get(i).getMine()) {
				buts.get(i).setState(5);
				buts.get(i).setImage();
			}
		}
		time.stop();
	}
	public static void checkWin() {
		boolean won1 = true, won2 = true;
		for (int i = 0; i < getBombNum(); i++) { //if all mines are marked or all other squares are revealed
			if (buts.get(i).getState() != 3)
				won1 = false;
		}
		for (int i = getBombNum(); i < sizex*size; i++) {
			if (buts.get(i).getState() != 1)
				won2 = false;
		}
		if (won1 || won2)
			win();
	}
	public static void win() {
		setFace(3);
		time.stop();
		for (Button[] a: stat) {
			for (Button b: a) {
				b.getButton().removeActionListener(b);
				b.getButton().removeMouseListener(b);
			}
		}
	}
	private void loadGame(int n) {
		if (n == 30)
			sizex = 16;
		else
			sizex = n;
		int ct = 0;
		size = n;
		stat = new Button[sizex][size];
		panel.setPreferredSize(new Dimension(size*SIZE,sizex*SIZE));
		panel.setMaximumSize(panel.getPreferredSize());
		panel.setSize(size*SIZE, sizex*SIZE);

		grid = new GridLayout(0, size, 0, 0);
		panel.setLayout(grid);
		for (int i = 0; i < sizex; i++) { //col
			for (int j = 0; j < size; j++) { //row
				buts.add(new Button(i,j));
				stat[i][j] = buts.get(ct);
				panel.add(buts.get(ct).getButton());
				ct++;
			}
		}
		flags = 0;
		visited = new boolean[sizex][size];
		for(boolean[] a: visited)
			Arrays.fill(a, false);
		
		bombAssign();
		setValues();
		setLabel();
		pack();
		panel.revalidate();
		panel.repaint();
	}
	private void clearGame() {
		panel.removeAll();
		repaint();
		buts.clear();
		resetTime();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newGame)) {
			String[] buttons = {"Beginner", "Intermediate", "Expert"};
			int a = JOptionPane.showOptionDialog(this, "Difficulty:", "New Game", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[1]);
			clearGame();
			if (a == 0)
				loadGame(9);
			else if (a == 1)
				loadGame(16);
			else
				loadGame(30);
		}
		
	}
}
