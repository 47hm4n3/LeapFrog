public class Main {

	public static Stone[] stones;
	public static int num;
	public static int empty;
	public static int step;
	public static Stone[] endStones;

	public static void main(String[] args) {

		num = 3;
		empty = num;
		step = 0;

		stones = new Stone[2 * num + 1];
		endStones = new Stone[2 * num + 1];
		initialize(stones, 0);
		endStones = stones;
		System.out.println(initialize(stones, step));
		System.out.println(jumpToRight(stones, step));
		System.out.println(jumpToRight(stones, step));
		System.out.println(jumpToRight(stones, step));
		System.out.println(jumpToRight(stones, step));
		// System.out.println(end(endStones, step));

	}

	public static String jumpToRight(Stone[] tab, int i) {
		if ((empty > 0) && (tab[empty - 1].x && !tab[empty - 1].y)) {
			Stone tmp = new Stone();
			tmp = tab[empty];
			tab[empty] = tab[empty - 1];
			tab[empty - 1] = tmp;
			empty--;
			step++;
			System.out.println(arrayToString(tab));
		} else System.out.println("Impossible move !");
		return toBool(tab, step);
	}

	public static String jumpToLeft(Stone[] tab, int i) {
		if ((empty < tab.length) && (!tab[empty + 1].x && tab[empty + 1].y)) {
			Stone tmp = new Stone();
			tmp = tab[empty];
			tab[empty] = tab[empty + 1];
			tab[empty + 1] = tmp;
			empty++;
			step++;
			System.out.println(arrayToString(tab));
		} else System.out.println("Impossible move !");
		return toBool(tab, step);
	}

	public static String jumpOverRight(Stone[] tab, int i) {
		if ((empty > 1) && (tab[empty - 2].x && !tab[empty - 2].y) && (!tab[empty - 1].x && tab[empty - 1].y)) {
			StringBuilder sb = new StringBuilder(toBool(tab, step));
			sb.append(" && " + "");
			Stone tmp = new Stone();
			tmp = tab[empty];
			tab[empty] = tab[empty - 2];
			tab[empty - 2] = tmp;
			empty -= 2;
			step++;
			System.out.println(arrayToString(tab));
		} else System.out.println("Impossible move !");
		return toBool(tab, step);
	}

	public static String jumpOverLeft(Stone[] tab, int i) {
		if ((empty < tab.length - 1) && (!tab[empty + 2].x && tab[empty + 2].y) && (tab[empty + 1].x && !tab[empty + 1].y)) {
			Stone tmp = new Stone();
			tmp = tab[empty];
			tab[empty] = tab[empty + 2];
			tab[empty + 2] = tmp;
			empty += 2;
			step++;
			System.out.println(arrayToString(tab));
		} else System.out.println("Impossible move !");
		return toBool(tab, step);
	}

	public static String initialize(Stone[] tab, int i) {

		for (int j = 0; j < tab.length / 2; j++) {
			Stone s = new Stone();
			s.x = true;
			s.y = false;
			tab[j] = s;
		}
		Stone ss = new Stone();
		ss.x = false;
		ss.y = false;
		tab[tab.length / 2] = ss;
		for (int j = (tab.length / 2 + 1); j < tab.length; j++) {
			Stone s = new Stone();
			s.x = false;
			s.y = true;
			tab[j] = s;
		}
		System.out.println(arrayToString(tab));
		return toBool(tab, i);
	}

	public static String end(Stone[] tab, int end) {
		for (int i = 0; i < tab.length - 1; i++) {
			if (i % 2 == 0) {
				Stone s = new Stone();
				s.x = false;
				s.y = true;
				tab[i] = s;
			} else {
				Stone s = new Stone();
				s.x = true;
				s.y = false;
				tab[i] = s;
			}
			Stone s = new Stone();
			s.x = false;
			s.y = false;
			tab[tab.length - 1] = s;

		}
		System.out.println(arrayToString(tab));
		return toBool(tab, end);
	}

	public static boolean twoFrogsOneStone(Stone[] tab, int i) {
		for (int j = 0; j < tab.length; j++) {
			if (tab[j].x && tab[j].y)
				return false;
		}
		return true;
	}

	public static String toBool(Stone[] tab, int i) {
		StringBuilder sb = new StringBuilder("");
		for (int j = 0; j < tab.length; j++) {
			if (tab[j].x && tab[j].y)
				sb.append("(x" + j + "_" + i + " & " + "y" + j + "_" + i + ")");
			else if (tab[j].x)
				sb.append("(x" + j + "_" + i + " & " + "!y" + j + "_" + i + ")");
			else if (tab[j].y)
				sb.append("(" + "!x" + j + "_" + i + " & " + "y" + j + "_" + i + ")");
			else
				sb.append("(!x" + j + "_" + i + " & " + "!y" + j + "_" + i + ")");
			sb.append(" & ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}

	public static String arrayToString(Stone[] tab) {
		StringBuilder sb1 = new StringBuilder("");
		StringBuilder sb2 = new StringBuilder("");

		sb1.append("| ");
		sb2.append("| ");

		for (int i = 0; i < tab.length; i++) {
			if (tab[i].x)
				sb1.append(tab[i].x + " ");
			else
				sb1.append(tab[i].x);
			sb1.append(" | ");
			if (tab[i].y)
				sb2.append(tab[i].y + " ");
			else
				sb2.append(tab[i].y);
			sb2.append(" | ");
		}
		sb1.append("\n");
		sb1.append(sb2.toString());
		sb1.append("\n");
		return sb1.toString();
	}

}
