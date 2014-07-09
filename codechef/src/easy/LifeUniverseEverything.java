package easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LifeUniverseEverything {

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = 0;
		int i = 0;

		int[] array = new int[1000];

		while (n >= 0) {
			n = Integer.parseInt(br.readLine());

			if (n == 42) {
				break;
			}

			array[i] = n;
			i++;
		}

		for (int j = 0; j < i; j++) {
			System.out.println(array[j]);
		}
	}

}
