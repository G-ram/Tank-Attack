import java.awt.*;
import javax.swing.*;
import java.util.*;
public class Scene extends JComponent{
	public Scene(){
		shapes = new ArrayList<MoveableShape>();
	}
	public void addObject(MoveableShape shape){
		shapes.add(shape);
	}
	public void resetScene(){
		shapes.clear();
	}
	public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	for (MoveableShape s : shapes){
        	s.draw(g);
    	}
   	}
	private ArrayList<MoveableShape> shapes;
}