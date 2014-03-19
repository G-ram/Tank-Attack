import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
public class Bullet implements MoveableShape{
	public Bullet(int aX, int aY, int aWidth, int aHeight, Color aColor){	
		x = aX;
		y = aY;
		width = aWidth;
		height = aHeight;
		color = aColor;
		path = new GeneralPath();
		Line2D.Double lineBottom = new Line2D.Double(x-width/2,y+height/2,x+width/2,y+height/2);
		Line2D.Double diagLeft = new Line2D.Double(x,y-height/2,x-width/2,y-height/4);
		Line2D.Double diagRight = new Line2D.Double(x,y-height/2,x+width/2,y-height/4);
		Line2D.Double lineLeft = new Line2D.Double(x-width/2,y-height/4,x-width/2,y+height/2);
		Line2D.Double lineRight = new Line2D.Double(x+width/2,y-height/4,x+width/2,y+height/2);
		path.append(diagLeft,false);
		path.append(diagRight,false);
		path.append(lineLeft,false);
		path.append(lineRight,false);
		path.append(lineBottom,false);
		path.closePath();
	}
	public Point2D.Double getXY(){
		return new Point2D.Double(x,y);
	}
	public void setMagAngle(int aMag,double aAngle){
		angle = aAngle;
		mag = aMag;
		translatePolar(mag,angle);
	}
	public int getMag(){return mag;}
	public double getAngle(){return angle;}
	public void translate(){
		translateRect((int)(mag*Math.cos(angle-Math.PI/2)),(int)(mag*Math.sin(angle-Math.PI/2)));
	}
	public void translatePolar(int mag,double angle){
		AffineTransform at = AffineTransform.getRotateInstance(angle,x,y);
      	path.transform(at);
      	translateRect((int)(mag*Math.cos(angle-Math.PI/2)),(int)(mag*Math.sin(angle-Math.PI/2)));
	}
	public void translateRect(int dx,int dy){
		x += dx;
     	y += dy;
      	AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);
      	path.transform(at);
	}
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(color);
		g2.setStroke(new BasicStroke(2));
		g2.draw(path);
	}
	private int x;
	private int y;
	private int width;
	private int height;
	private int mag;
	private double angle;
	private Color color;
	private GeneralPath path;
	private Point2D.Double velocityVector;
}