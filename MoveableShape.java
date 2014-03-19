import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
public interface MoveableShape{
	public Point2D.Double getXY();
	public void setMagAngle(int aMag,double aAngle);
	public int getMag();
	public double getAngle();
	public void translate();
	public void translatePolar(int mag,double angle);
	public void translateRect(int dx,int dy);
	public void draw(Graphics g);
}