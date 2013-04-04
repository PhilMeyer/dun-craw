package org.pnm.dun.util;


public class GeometryUtil {

	public static double distance(Location l1, Location l2) {
		if(l1 == null || l2 == null){
			throw new IllegalArgumentException();
		}
		return Math.sqrt(Math.pow(l1.x - l2.x, 2) + Math.pow(l1.y - l2.y, 2));
	}
	
	public static int getAngle(double x1, double y1, double x2, double y2){
		double angle = Math.atan2(x2 - x1, y2 - y1) * 180 / Math.PI;
		if(angle < 0){
			angle = 360 + angle;
		}
		return (int)Math.round(angle);
	}
	
	public static int getAngle(Location l1, Location l2){
		int angle = getAngle(l1.x, l1.y, l2.x, l2.y);
		//System.out.println("The angle between "+l1+" and "+l2+" is "+angle);
		return angle;
	}

	public static Location getPointWithAngleAndRadius(Location c, int a, double r) {
		double x = c.x + r * Math.sin(a * Math.PI / 180);
		double y = c.y + r * Math.cos(a * Math.PI / 180);
		return new Location(x,y);
	}
	
	
	public static void main(String[] args) {
		//Location a1 = new Location(5, 0);
		//Location a2 = new Location(0, 0);
		//for(int a = 0; a < 360; a+=5){
		//	test1(c, a);
		//}
	}

	protected static void test1(Location c, int a) {
		Location point = GeometryUtil.getPointWithAngleAndRadius(c, a, 10);
		System.out.println(a);
		System.out.println(point);
		System.out.println(getAngle(c, point));
		System.out.println(distance(c, point));
		System.out.println("---------------------------------------------------------------");
	}

	protected static void testAngles() {
		System.out.println(getAngle(0, 0, 0, 10));
		System.out.println(getAngle(0, 0, 10, 10));
		System.out.println(getAngle(0, 0, 10, 0));
		System.out.println(getAngle(0, 0, 10, -10));
		System.out.println(getAngle(0, 0, 0, -10));
		System.out.println(getAngle(0, 0, -10, -10));
		System.out.println(getAngle(0, 0, -10, 0));
		System.out.println(getAngle(0, 0, -10, 10));
		System.out.println(getAngle(0, 0, 0, 10));
	}

}
