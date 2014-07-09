package math;

public class Bases {

	public static void main(String[] args) {
		int n = 1111, b = 2;

		System.out.println(toDecimal(n, b));
	}

	public static int toDecimal(int n, int b) {

		int r = 0;
		int t = n;
		int m = 1;

		while (t > 0) {
			r += (t%b)*m;
			m *= b;
			t /= 10;
			
		}

		return r;
	}

}
