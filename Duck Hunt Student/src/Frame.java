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
	
	Font bigFont = new Font("Serif", Font.PLAIN, 50);
	
	Font medFont = new Font("Serif", Font.PLAIN, 40);
	
	Font smallFont = new Font("Serif", Font.PLAIN, 20);
	
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
	int currRound = 1;
	
	//initialize any variables, objects, etc for the "start" of the game
	public void init() {
		//init the roundTimer and score
		roundTimer = 30;
		score = 0;
		time = 0;
		
		tr.setWidthHeight(150, 200);
		tr.setScale(0.25, 0.25);
		tr.setVx(3);
		
		bush.setScale(0.45, 0.45);
		bush.setXY(500, 300);
		
		dog.setScale(0.15, 0.15);
		dog.setXY(-160, 380);
	}
	
	
	//resetting for multiple rounds etc
	public void reset() {
		
	}
	
	
	//initialize object and vars for the next round
	public void nextRound() {
		//reset the roundCounter
		roundTimer = 30;
		currRound++;
		
		//recalibrate objects
		tr.setVx(3*currRound);
	}
	
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		//add 16 to time since paint is called every 20 ms
		time += 20; //time elapse updates
		
		if(time%100 == 0) { //has it been 1 second?
			roundTimer -= 1;
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
		
		//scoring
		g.setColor(Color.BLACK);
		g.drawString("Points: "+this.score, 50, 40);
		
		g.setFont(medFont);
		g.drawString("Round "+this.currRound, 350, 40);
		
		//draw the round String
		g.setFont(bigFont);
		g.setColor(Color.white);
		if(roundTimer >= 10) {
			g.drawString("Time: "+this.roundTimer, 300, 540);
		}
		else if(roundTimer> 0 && roundTimer < 10){
			if(time%2000 >= 1000) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.white);
			}
			g.drawString("Time: "+this.roundTimer, 310, 540);
			
		}
		else {
			g.setColor(Color.RED);
			g.drawString("Time: 0", 310, 540);
			g.drawString("ROUND OVER!", 230, 300);
			if(time==0) {
				StdAudio.playInBackground("roundOver.wav");//fix this
			}
			//tr.setVx(0);
			nextRound();
			t.stop(); //stops the timer after this round is over
		}
		
		//Text for mobing to next round
		if(!t.isRunning()) {
			g.setFont(smallFont);
			g.setColor(Color.darkGray);
			g.drawString("Press space for the next round", 275, 400);
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
		
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	//Make the timer visible to the other methods
	Timer t = new Timer(16, this);
	
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
			if(roundTimer>0) {
				score += 1;
				tr.setVy(-10);
				dog.setXY(-160, 380);
				dog.setVx(40);
				StdAudio.playInBackground("we_reBlastingOffAgain.wav");
			}
			if(dog.getX()>500) {
				dog.setXY(-160, 380);
				dog.setVx(0);
			}
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
		
		//space bar continue the round
		//if the timer is stopped we can start it agin
		if(arg0.getKeyCode()==32) {
			//start the timer again
			if(!t.isRunning()) {
				t.start();
			}
		}
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
