package math;

public class GCD {

	public static void main(String[] args) {
		long a = 34842, b = 587034;

		System.out.println("The GCD of " + a + " and " + b + " is " + gcd(a, b));
	}

	public static long gcd(long a, long b) {

		if (b == 0) return a;

		return gcd(b, a%b);
	}

}
