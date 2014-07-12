package easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

// http://www.codechef.com/problems/RHOUSE
/*
Input:
3
3 5
HHR
1 2 3
1 2 5
1 3 10
3 2 -1
3 1 7
2 2
RR
1 2 1
2 1 2
3 3
HRR
1 2 1
1 3 2
2 3 3
*/

/*
1
6 7
HHHHRR
4 5 2
1 2 -1
1 4 5
1 3 6
3 4 1
2 4 3
2 6 2
*/

public class HousesAndRestaurants {
	private static int cost = 0;
	private static DisjointSet ds;
	private static Line[] lines;

	public static void main(String[] args) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int numOfTests = Integer.parseInt(br.readLine());
		Line maxLine = new Line(-1, -1, Integer.MAX_VALUE);

		for (int i = 0; i < numOfTests; i++) {
			int[] nm = readInts(br, 2);
			int n = nm[0];
			int m = nm[1];

			cost = 0;
			ds = new DisjointSet(n);
			lines = new Line[m];

			String buildings = br.readLine();

			for (int j = 0; j < n; j++) {
				char b = buildings.charAt(j);

				if (b == 'R') {
					ds.makeSet(j, true);
				} else {
					ds.makeSet(j, false);
				}
			}

			for (int j = 0; j < m; j++) {
				int[] line = readInts(br, 3);
				line[0]--;
				line[1]--;

				if (line[2] <= 0) {
					cost += line[2];
					ds.union(line[0], line[1]);
					lines[j] = maxLine;
				} else {
					lines[j] = new Line(line[0], line[1], line[2]);
				}
			}

			calculateCost();

			System.out.println(cost);
		}

		return;
	}


	private static void calculateCost() {
		Arrays.sort(lines);

		for (int i = 0; i < lines.length; i++) {
			Line line = lines[i];

			if (line.cost == Integer.MAX_VALUE) break;

			int root1 = ds.findRoot(line.from);
			int root2 = ds.findRoot(line.to);

			if (root1 == root2 || (ds.isDone[root1] && ds.isDone[root2])) {
				continue;
			}

			ds.union(root1, root2);
			cost += line.cost;
		}
	}

	public static int[] readInts(BufferedReader br, int expectedCount) throws IOException {
		int[] results = new int[expectedCount];

		String line = br.readLine();
		StringTokenizer st = new StringTokenizer(line, " ");

		for (int i = 0; i < expectedCount; i++) {
			results[i] = Integer.parseInt(st.nextToken());
		}

		return results;
	}

	public static class Line implements Comparable<Line> {
		int from;
		int to;
		int cost;

		public Line(int from, int to, int cost) {
			super();
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Line other) {

			return this.cost - other.cost;
		}
	}

	public static class DisjointSet {
		int[] parent;
		boolean[] isDone;
		int[] rank;
		int size;

		public DisjointSet(int m) {
			parent = new int[m];
			Arrays.fill(parent, -1);

			isDone = new boolean[m];
			Arrays.fill(isDone, false);

			rank = new int[m];
			Arrays.fill(rank, -1);

			size = m;
		}

		public void makeSet(int i, boolean done) {
			parent[i] = i;
			rank[i] = 0;
			isDone[i] = done;
		}

		public int findRoot(int i) {
			if (parent[i] == i) {
				return i;
			}

			return findRoot(parent[i]);
		}

		public void union(int i1, int i2) {
			int root1 = findRoot(i1);
			int root2 = findRoot(i2);
			int newRoot;

			if (root1 != root2) {
				newRoot = link(root1, root2);
				if (isDone[root1] || isDone[root2])
					isDone[newRoot] = true;
			}
		}

		private int link(int x, int y) {
			if (rank[x] < rank[y]) {
				parent[x] = y;
				return y;
			}

			else if (rank[x] > rank[y]) {
				parent[y] = x;
				return x;
			}

			else {
				parent[x] = y;
				rank[y]++;
				return y;
			}
		}
	}
}
