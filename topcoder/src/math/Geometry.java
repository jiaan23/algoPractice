package math;

public class Geometry {

	public static void main(String[] args) {
		int[][] rect1 = {{2,3},{9,10}}, rect2 = {{4,4},{11,11}};

		System.out.println(rectToString(rect1) + " ^ " + rectToString(rect2) + " = " +
				rectToString(intersect(rect1, rect2)));
	}

	public static int[][] intersect(int[][] rect1, int[][] rect2) {

		int[][] interRect = {{-1,-1},{-1,-1}};

		int x1 = Math.max(rect1[0][0], rect2[0][0]);
		int y1 = Math.max(rect1[0][1], rect2[0][1]);

		int x2 = Math.min(rect1[1][0], rect2[1][0]);
		int y2 = Math.min(rect1[1][1], rect2[1][1]);

		if (x1 > x2 || y1 > y2) return interRect;

		interRect[0][0] = x1;
		interRect[0][1] = y1;
		interRect[1][0] = x2;
		interRect[1][1] = y2;

		return interRect;
	}

	public static String rectToString(int[][] rect) {
		return "((" + rect[0][0] + "," + rect[0][1] + "),("
				+ rect[1][0] + "," + rect[1][1] + "))";
	}
}
