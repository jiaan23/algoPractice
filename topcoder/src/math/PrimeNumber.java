package math;

public class PrimeNumber {

	public static void main(String[] args) {

		int p = 13;
		System.out.println(p + " is " + (isPrime(p) ? "" : "not ") + "a prime number");
	}

	public static boolean isPrime(int p) {
		if (p <= 1) return false;

		if (p == 2) return true;
		if (p % 2 == 0) return false;

		int m = (int)Math.sqrt(p);

		for (int i = 3; i <= m; i+=2) {
			if (p % i == 0) return false;
		}

		return true;
	}

	
}
