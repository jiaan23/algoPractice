package math;

public class LCM {

	public static void main(String[] args) {
		long a = 34842, b = 587034;

		System.out.println("The LCM of " + a + " and " + b + " is " + lcm(a, b));
	}

	public static long lcm(long a, long b) {
		return a*b/GCD.gcd(a, b);
	}

}
