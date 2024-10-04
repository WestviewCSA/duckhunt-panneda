import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	Font bigFont = new Font("Serif", Font.PLAIN, 80);
	
	//create a Rocket Object
	Rocket tr = new Rocket();
	
	//create a Ludicolo Object
	ludicolo bush = new ludicolo();
	
	//create a Yamper Object
	Yamper dog = new Yamper();
	
	//create an Exeggcutor Object
	Exeggcutor tree = new Exeggcutor();
	
	//create a background object
	Background ground = new Background();
	
	
	//score related vars and timer
	int roundTimer;
	int score;
	long time; //long is bigger int that can hold bigger whole numbers
	
	//initialize any variables, objects, etc for the "start" of the game
	public void init() {
		//init the oundTimer and score
		roundTimer = 30;
		score = 0;
		time = 0;
		
		tr.setWidthHeight(150, 200);
		tr.setScale(0.25, 0.25);
		tr.setVx(3);
		
		bush.setScale(0.45, 0.45);
		bush.setXY(500, 300);
		
		dog.setScale(0.15, 0.15);
		dog.setXY(0, 380);
	}
	
	
	//resetting for multiple rounds etc
	public void reset() {
		
	}
	
	
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		//add 16 to time since paint is called every 20 ms
		time += 20; //time elapse updates
		
		if(time%1000 == 0) { //has it been 1 second?
			roundTimer -= 1;
			if(roundTimer == 0) {
				//what do u do after one complete round?
				roundTimer += 30;
			}
		}
		
		g.setFont(bigFont);
		
		//draw the round String
		
		
		//LAYER your object as you want them to layer visually!
		ground.paint(g);
		
		tr.paint(g);
		
		bush.paint(g);
		
		tree.paint(g);
		
		dog.paint(g);
		
		//logic for resetting the dog position and or making it bounce around
		if(tr.getY() < -150) {
			tr.setScale(0.25, 0.25);
			tr.setX((int)(Math.random()*300));
			tr.setY((int)(Math.random()*300));
			tr.setVx(3);
			tr.setVy(0);
		}
		if(tr.getX()> 550||tr.getX()<-50) {
			tr.setVx(tr.getVx()*-1);
		}
		
		//draw time related Strings last so the are overlaid on top of everything else
		g.setFont(bigFont);
		
		//draw the round String
		g.setColor(Color.white);
		if(roundTimer >= 10) {
			g.drawString(""+this.roundTimer, 360, 540);
		}
		else if(roundTimer>= 0 && roundTimer < 10){
			g.drawString(""+this.roundTimer, 380, 540);
		}
		else {
			reset();
		}
		
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(800, 600));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		
		init(); //call your init method to give properties to the object and variables
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
		//perform a rectangle collision with the mouse and the object
		Rectangle rMouse = new Rectangle(mouse.getX(), mouse.getY(), 25, 25); //guess and check size for now
		
		//2nd rectangle will be for the object (the duck)
		Rectangle rMain = new Rectangle(
					tr.getX()+70, tr.getY()+30,
					tr.getWidth(), tr.getHeight()
				);
		
		//check if they're colliding
		if(rMouse.intersects(rMain)) { //do the 2 rect intersect?
			tr.setVy(-10);
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
