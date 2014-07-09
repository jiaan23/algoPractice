package easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

// http://www.codechef.com/problems/RHOUSE
public class HousesAndRestaurants {
	public static void main(String[] args) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int numOfTests = Integer.parseInt(br.readLine());

		int[] costs = new int[numOfTests];

		for (int i = 0; i < numOfTests; i++) {
			int[] nm = readInts(br, 2);
			int n = nm[0];
			int m = nm[1];

			String line = br.readLine();

			char[] buildings = new char[n+1];
			buildings[0] = '-';
			for (int j = 1; j <= n; j++) {
				buildings[j] = line.charAt(j-1);
			}

			int[][] lines = new int[m][];
			for (int j = 0; j < m; j++) {
				lines[j] = readInts(br, 3);
			}

			costs[i] = calculateCost(buildings, lines);
			System.out.println(costs[i]);
		}

		return;
	}

	public static int calculateCost(char[] buildings, int[][] lines) {

		Set<Integer> houseSet = new HashSet<Integer>();
		for (int i = 1; i < buildings.length; i++) {
			if (buildings[i] == 'H') {
				houseSet.add(i);
			}
		}

		List<Line> lineList = new ArrayList<Line>();
		for (int i = 0; i < lines.length; i++) {
			lineList.add(new Line(lines[i][0], lines[i][1], lines[i][2]));
		}

		Collections.sort(lineList);

		Iterator<Line> lineIter = lineList.iterator();

		List<Set<Integer>> connectedHousesList = new ArrayList<Set<Integer>>();
		List<Set<Line>> connectedLinesList = new ArrayList<Set<Line>>();
		List<Boolean> visitedConnectedHousesList = new ArrayList<Boolean>();

		while (!houseSet.isEmpty() && lineIter.hasNext()) {
			Line line = lineIter.next();

			if (buildings[line.getFrom()] == 'R' && buildings[line.getTo()] == 'R') {
				continue;
			}

			int setIndex1 = -1;
			int setIndex2 = -1;

			for (int i = 0; i < connectedHousesList.size(); i++) {
				if (connectedHousesList.get(i).contains(line.getFrom())) {
					setIndex1 = i;
				}

				if (connectedHousesList.get(i).contains(line.getTo())) {
					setIndex2 = i;
				}
			}

			if (setIndex1 == -1 && setIndex2 == -1) {
				Set<Integer> s = new HashSet<Integer>();
				s.add(line.getFrom());
				s.add(line.getTo());
				connectedHousesList.add(s);

				Set<Line> sLine = new HashSet<Line>();
				sLine.add(line);
				connectedLinesList.add(sLine);

				if (buildings[line.getFrom()] == 'R' || buildings[line.getTo()] == 'R') {
					visitedConnectedHousesList.add(new Boolean(true));
					houseSet.remove(line.getFrom());
					houseSet.remove(line.getTo());

				} else {
					visitedConnectedHousesList.add(new Boolean(false));
				}


			} else if (setIndex1 != setIndex2) {
				if (setIndex1 == -1) {
					if (buildings[line.getFrom()] == 'R') {
						if (!visitedConnectedHousesList.get(setIndex2)) {
							connectedHousesList.get(setIndex2).add(line.getFrom());
							
							visitedConnectedHousesList.set(setIndex2, new Boolean(true));
							houseSet.removeAll(connectedHousesList.get(setIndex2));

							connectedLinesList.get(setIndex2).add(line);
						}
					} else {
						connectedHousesList.get(setIndex2).add(line.getFrom());
						connectedLinesList.get(setIndex2).add(line);

						if (visitedConnectedHousesList.get(setIndex2)) {
							houseSet.remove(line.getFrom());
						}
					}

				} else if (setIndex2 == -1) {
					if (buildings[line.getTo()] == 'R') {
						if (!visitedConnectedHousesList.get(setIndex1)) {
							connectedHousesList.get(setIndex1).add(line.getTo());

							visitedConnectedHousesList.set(setIndex1, new Boolean(true));
							houseSet.removeAll(connectedHousesList.get(setIndex1));

							connectedLinesList.get(setIndex1).add(line);
						}
					} else {
						connectedHousesList.get(setIndex1).add(line.getTo());
						connectedLinesList.get(setIndex1).add(line);

						if (visitedConnectedHousesList.get(setIndex1)) {
							houseSet.remove(line.getTo());
						}
					}

				} else {
					int min = Math.min(setIndex1, setIndex2);
					int max = Math.max(setIndex1, setIndex2);

					connectedHousesList.get(min).addAll(connectedHousesList.get(max));
					connectedHousesList.remove(max);

					connectedLinesList.get(min).addAll(connectedLinesList.get(max));
					connectedLinesList.remove(max);

					if (visitedConnectedHousesList.get(setIndex1) || visitedConnectedHousesList.get(setIndex2)) {
						visitedConnectedHousesList.set(min, new Boolean(true));

						houseSet.removeAll(connectedHousesList.get(setIndex1));
						houseSet.removeAll(connectedHousesList.get(setIndex2));
					}

					visitedConnectedHousesList.remove(max);
				}
			}
		}

		int cost = 0;
		//Set<Line> resultLineSet = new HashSet<Line>();

		for (int i = 0; i < connectedLinesList.size(); i++) {
			if (visitedConnectedHousesList.get(i)) {
				for (Line line : connectedLinesList.get(i)) {
					cost += line.getCost();
					//resultLineSet.add(line);
				}
			}
		}
		
		return cost;
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
		private Integer from;
		private Integer to;
		private Integer cost;

		public Line(Integer from, Integer to, Integer cost) {
			super();
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		public Integer getFrom() {
			return from;
		}

		public void setFrom(Integer from) {
			this.from = from;
		}

		public Integer getTo() {
			return to;
		}

		public void setTo(Integer to) {
			this.to = to;
		}

		public Integer getCost() {
			return cost;
		}

		public void setCost(Integer cost) {
			this.cost = cost;
		}

		@Override
		public int compareTo(Line other) {

			return this.getCost().compareTo(other.getCost());
		}
	}

	public static class House {
		private int index;
		private boolean visited;
		public House(int index, boolean visited) {
			super();
			this.index = index;
			this.visited = visited;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public boolean isVisited() {
			return visited;
		}
		public void setVisited(boolean visited) {
			this.visited = visited;
		}
	}
}
