import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
public class BadTank extends Tank{
	public BadTank(int aX, int aY, int aWidth, int aHeight, Color aColor){
		super(aX,aY, aWidth, aHeight, aColor);
	}
	public void setShotDelay(int aShotDelay){
		shotDelay = aShotDelay;
	}
	public int getShotDelay(){
		return shotDelay;
	}
	public void setSpeed(int aSpeed){
		speed = aSpeed;
	}
	public int getSpeed(){
		return speed;
	}
	public void setRadius(int aRadius){
		radius = aRadius;
	}
	public int getRadius(){
		return radius;
	}
	public boolean checkRadius(Point2D.Double other){
		int otherX = (int)other.getX();
		int otherY = (int)other.getY();
		int thisX = (int)super.getXY().getX();
		int thisY = (int)super.getXY().getY();
		int distance = (int)Math.pow(otherX-thisX,2)+(int)Math.pow(otherY-thisY,2);
		distance = (int)Math.sqrt(distance);
		if(distance <= radius){return true;}
		return false;
	}
	public void setMagAngle(int aMag,double aAngle){
		mag = aMag;
		angle = aAngle;
		angleTracked += aAngle;
		translatePolar(mag,angle);
	}
	public int getMag(){return mag;}
	public double getAngle(){return angle;}
	public double getTrackedAngle(){return angleTracked;}
	public void translate(){
		translateRect((int)(-1*mag*Math.cos(angleTracked)),(int)(mag*Math.sin(angleTracked)));
	}
	public void translatePolar(int mag,double angle){
		AffineTransform at = AffineTransform.getRotateInstance(-1*angle,x,y);
      	path.transform(at);
	}
	public void translateRect(int dx,int dy){
		x += dx;
     	y += dy;
      	AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);
      	path.transform(at);
	}
	public void fireBullet(){
		Bullet aBullet = new Bullet(x,y,4,12,color);
		aBullet.setMagAngle(5,-1*angleTracked-Math.PI/2);
		bullets.add(aBullet);
	}
	private int speed;
	private int shotDelay;
	private int radius;
}