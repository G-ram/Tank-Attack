import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class TankAttack{
	public static void main(String[] args){
		levelLogic = new LevelLogic();
		levelLogic.createNewGameScreen();
		gameController = new GamePadController();
		gameController.setRumbler(false);
    	startPolling(); 
	}
	private static void startPolling(){
		ActionListener pollPerformer = new ActionListener(){
			public void actionPerformed(ActionEvent e){
			gameController.poll();
			//int hatDxn = gameController.getHatDir();
			//int xyStickDxn = gameController.getXYStickDir();
			//int zrzStickDxn = gameController.getZRZStickDir();
			double[] xyStickDxn = gameController.getRawXYStickDir();
			double[] zrzStickDxn = gameController.getRawZRZStickDir();
			boolean[] buttons = gameController.getButtons();
			levelLogic.update(xyStickDxn,zrzStickDxn,buttons);
			}
		};
		pollTimer = new Timer(DELAY, pollPerformer);
		pollTimer.start();
	}
	private static final int DELAY = 20;
	private static LevelLogic levelLogic;
	private static GamePadController gameController;
	private static Timer pollTimer;
}