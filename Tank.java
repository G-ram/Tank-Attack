import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
public class Tank implements MoveableShape{
	public Tank(int aX, int aY, int aWidth, int aHeight, Color aColor){
		x = aX;
		y = aY;
		width = aWidth;
		height = aHeight;
		color = aColor;
		bullets = new ArrayList<Bullet>();
		path = new GeneralPath();
		Rectangle2D.Double tankBody = new Rectangle2D.Double(x-width/2,y-height/2,width,height);
		Ellipse2D.Double turetBody = new Ellipse2D.Double(x-width/5,y-width/5,width/2.5,width/2.5);
		Rectangle2D.Double cannonBody = new Rectangle2D.Double(x-width/18,y-height/1.3,width/9,height/1.3);
		Line2D.Double diagLeft = new Line2D.Double(x-width/18,y-height/2,x-width/3,y-height/6);
		Line2D.Double diagRight = new Line2D.Double(x+width/18,y-height/2,x+width/3,y-height/6);
		Line2D.Double lineLeft = new Line2D.Double(x-width/3,y-height/2,x-width/3,y+height/2);
		Line2D.Double lineRight = new Line2D.Double(x+width/3,y-height/2,x+width/3,y+height/2);
		path.append(tankBody,false);
		path.append(turetBody,false);
		path.append(diagLeft,false);
		path.append(diagRight,false);
		path.append(lineLeft,false);
		path.append(lineRight,false);
		path.append(cannonBody,false);
		path.closePath();
	}
	public Point2D.Double getXY(){
		return new Point2D.Double(x,y);
	}
	public void setMagAngle(int aMag,double aAngle){
		mag = aMag;
		//if(aAngle != angle){
			angle = aAngle;
			angleTracked += aAngle;
			translatePolar(mag,angle);
		//}
	}
	public int getMag(){return mag;}
	public double getAngle(){return angle;}
	public void translate(){
		translateRect((int)(mag*Math.cos(angleTracked-Math.PI/2)),(int)(mag*Math.sin(angleTracked-Math.PI/2)));
	}
	public void translatePolar(int mag,double angle){
		AffineTransform at = AffineTransform.getRotateInstance(angle,x,y);
      	path.transform(at);
      	//translateRect((int)(mag*Math.cos(angle-Math.PI/2)),(int)(mag*Math.sin(angle-Math.PI/2)));
	}
	public void translateRect(int dx,int dy){
		x += dx;
     	y += dy;
      	AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);
      	path.transform(at);
	}
	public void translateRect(double dx,double dy){
		x += (int)dx;
     	y += (int)dy;
      	AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);
      	path.transform(at);
	}
	public boolean contains(ArrayList<Bullet> aBullets){
		for(Bullet bullet : aBullets){
			if(path.contains(bullet.getXY())){return true;}
		}
		return false;
	}
	public void fireBullet(){
		Bullet aBullet = new Bullet(x,y,4,12,color);
		aBullet.setMagAngle(5,angleTracked);
		bullets.add(aBullet);
	}
	public ArrayList<Bullet> getBullets(){return bullets;}
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(color);
		g2.setStroke(new BasicStroke(2));
		g2.draw(path);
		for(Bullet b : bullets){
			b.translate();
			b.draw(g);
		}
	}
	protected int x;
	protected int y;
	private int width;
	private int height;
	protected int mag;
	protected double angle;
	protected double angleTracked;
	protected Color color;
	protected GeneralPath path;
	protected ArrayList<Bullet> bullets;
}