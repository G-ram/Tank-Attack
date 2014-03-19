import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class LevelLogic{
	public LevelLogic(){
		frame = new JFrame();
		scene = new Scene();
		player = new Tank(100,300,40,60,Color.GREEN.darker());
		computer = new BadTank(500,300,40,60,Color.RED);
		scene.addObject(player);
		scene.addObject(computer);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_WIDTH);
		frame.setVisible(true);
	}
	public void update(double[] xyStick,double[] zrzStick,boolean[] buttons){
		if(state == 0 && buttons[9]){
			state = 1;
			createLevel();
		}else if(state == 1){
			updateLevel(xyStick,zrzStick,buttons);
		}else if(state == 2 && buttons[9]){
			state = 1;
			createLevel();
		}else if(state == 3 && buttons[9]){
			state = 1;
			createLevel();
		}
	}
	public void updateLevel(double[] xyStick,double[] zrzStick,boolean[] buttons){
		if(buttons[5] && !trigger){
			trigger = true;
			player.fireBullet();
		}else if(!buttons[5]){trigger = false;}
		if(player.contains(computer.getBullets())){
			state = 3;
			createLoseScreen();
		}
		if(computer.contains(player.getBullets())){
			state = 2;
			level++;
			createWinScreen();
		}
		player.setMagAngle((int)((xyStick[1] + zrzStick[1])*-3),-1*(xyStick[1] - zrzStick[1])*0.05);
		moveDelay++;
		if(moveDelay > 1){
			moveDelay = 0;
			double xDiff = player.getXY().getX()-computer.getXY().getX();
			double yDiff = player.getXY().getY()-computer.getXY().getY();
			angleRotation = Math.abs(oldAngleRotation)-Math.abs((double)Math.round(Math.atan(yDiff/xDiff)*1000)/1000);
			if(computer.getXY().getY()<player.getXY().getY() &&
				computer.getXY().getX()>player.getXY().getX()){angleRotation*=-1;}
			if(computer.getXY().getY()>player.getXY().getY() &&
				computer.getXY().getX()<player.getXY().getX()){angleRotation*=-1;}
			computer.setMagAngle(computer.getSpeed(),angleRotation);
			oldAngleRotation = Math.abs((double)Math.round(Math.atan(yDiff/xDiff)*1000)/1000);
			shotTimer++;
			if(computer.checkRadius(player.getXY()) && shotTimer > computer.getShotDelay()){
				computer.fireBullet();
				shotTimer = 0;
			}else if(!computer.checkRadius(player.getXY())){
				computer.translate();
			}
		}
		player.translate();
		int correction = 5;
		if(player.getXY().getY()<20){
			player.translateRect(0,correction);
		}else if(player.getXY().getY()>FRAME_HEIGHT-20){
			player.translateRect(0,-correction);
		}else if(player.getXY().getX()<20){
			player.translateRect(correction,0);
		}else if(player.getXY().getX()>FRAME_WIDTH-20){
			player.translateRect(-correction,0);
		}else{}
		scene.repaint();
	}
	public void createLevel(){
		shotTimer = 0;
		moveDelay = 0;
		angleRotation = 0;
		oldAngleRotation = 0;
		scene.resetScene();
		frame.getContentPane().removeAll();    
		frame.setVisible(true);
		player = new Tank(100,200,40,60,Color.GREEN.darker());
		computer = new BadTank(300,400,40,60,Color.RED);
		int radiusDifficulty = 0;
		if(80+level*10 >= 200){radiusDifficulty = 200;}
		else{radiusDifficulty = 80+level*10;}
		int shotDelayDifficulty = 0;
		if(50-level*2 <= 10){shotDelayDifficulty = 10;}
		else{shotDelayDifficulty = 50-level*2;}
		computer.setRadius(radiusDifficulty);
		computer.setSpeed(level+1);
		computer.setShotDelay(shotDelayDifficulty);
		computer.translatePolar(0,Math.PI/2);
		scene.addObject(player);
		scene.addObject(computer);
		frame.add(scene);
		frame.revalidate();
		frame.repaint();
	}
	public void createWinScreen(){
		frame.getContentPane().removeAll();    
		frame.setVisible(true);
		contentPanel = new JPanel();
		JLabel winlabel = createLabel("You Win!",90);
		JLabel nextLabel = createLabel("Press Start for Level #"+level,40);
		contentPanel.add(winlabel);
		contentPanel.add(nextLabel);
		frame.add(contentPanel);
		frame.revalidate();
		frame.repaint();
	}
	public void createLoseScreen(){
		level = 1;
		frame.getContentPane().removeAll();    
		frame.setVisible(true);
		contentPanel = new JPanel();
		JLabel loselabel = createLabel("You Lose.",90);
		JLabel startLabel = createLabel("Press Start for Level 1",40);
		contentPanel.add(loselabel);
		contentPanel.add(startLabel);
		frame.add(contentPanel);
		frame.revalidate();
		frame.repaint();
	}
	public void createNewGameScreen(){
		state = 0;
		level = 1;
		frame.getContentPane().removeAll();    
		frame.setVisible(true);
		contentPanel = new JPanel();
		JLabel newLabel = createLabel("New Game.",90);
		JLabel startLabel = createLabel("Press Start for Level 1",40);
		contentPanel.add(newLabel);
		contentPanel.add(startLabel);
		frame.add(contentPanel);
		frame.revalidate();
		frame.repaint();
	}
	public static JLabel createLabel(String value,int fontSize){
		JLabel label = new JLabel(value);
		label.setFont(new Font("Helvetica",Font.PLAIN,fontSize));
		return label;
	}
	private static final int FRAME_WIDTH = 750;
	private static final int FRAME_HEIGHT = 750;
	private static int state = 0;
	private static int level = 1;
	private static int shotTimer = 0;
	private static int moveDelay = 0;
	private static double angleRotation;
	private static double oldAngleRotation;
	private static boolean trigger = false;
	private static JFrame frame;
	private static JPanel contentPanel;
	private static Scene scene;
	private static Tank player;
	private static BadTank computer;
	private static Bullet bullet;
}