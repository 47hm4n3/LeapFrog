public class LeapFrog {

	//public static Stone[] stones;
	public static int num;
	public static int length;
	public static int empty;
	public static int step;
	//public static Stone[] endStones;

	public static void main(String[] args) {

		num = Integer.parseInt(args[0]);
		length = 2 * num + 1;
		step = 0;

		StringBuilder sb = new StringBuilder("");
		sb.append(initialize(step));
		sb.append(" & \n");
		for (step = 1; step <= 2; step++) {
			sb.append(oneStep(step));
			sb.append(" & \n");
			sb.append(oneStoneTwoFrogs(step));
			sb.append(" & ");
		}
		sb.append(end(step-1));
		System.out.println(sb.toString());
	}

	public static String oneStep(int s) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			sb.append("(");
			sb.append(moveOneBox(s, i));
			sb.append(")");
			sb.append(" | \n");
		}
		sb.setLength(sb.length() - 4);
		return sb.toString();
	}

	public static String moveOneBox(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		String str1 = jumpToRight(s, i);
		sb.append(str1);
		if (!str1.isEmpty()) {
			sb.append(" & ");
			sb.append("\n!(");
			String str = jumpToLeft(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpOverRight(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpOverLeft(s, i);
			if (str.isEmpty()) 
				sb.setLength(sb.length() - 4);
			else 
				sb.append(str);
			sb.append(")");
			sb.append(" | \n");
		}
		//sb.setLength(sb.length() - 4);
		str1 = jumpToLeft(s, i);
		sb.append(str1);
		if (!str1.isEmpty()) {
			sb.append(" & ");
			sb.append("\n!(");
			String str = jumpToRight(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpOverRight(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpOverLeft(s, i);
			if (str.isEmpty()) 
				sb.setLength(sb.length() - 4);
			else 
				sb.append(str);
			sb.append(")");
			sb.append(" | \n");
		}
		//sb.setLength(sb.length() - 4);
		str1 = jumpOverRight(s, i);
		sb.append(str1);
		if (!str1.isEmpty()) {
			sb.append(" & ");
			sb.append("\n!(");
			String str = jumpToRight(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpToLeft(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpOverLeft(s, i);
			if (str.isEmpty()) 
				sb.setLength(sb.length() - 4);
			else 
				sb.append(str);
			sb.append(")");
			sb.append(" | \n");
		}
		sb.setLength(sb.length() - 4);
		str1 = jumpOverLeft(s, i);
		if (str1.isEmpty()) {
			sb.setLength(sb.length() - 4);
		} else {
			sb.append(" & ");
			sb.append("\n!(");
			String str = jumpToLeft(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpOverRight(s, i);
			sb.append(str);
			if (!str.isEmpty()) 
				sb.append(" | \n");
			str = jumpOverLeft(s, i);
			if (str.isEmpty()) 
				sb.setLength(sb.length() - 4);
			else 
				sb.append(str);
			sb.append(")");
			sb.append(" & ");

			sb.append(str1);
		}
		return sb.toString();
	}

	public static String jumpToRight(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i + 1) < length) {
			sb.append("(x" + i + "_" + (s - 1) + " & " + "!y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i + 1) + "_" + (s - 1) + " & " + "!y" + (i + 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i, i + 1));
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String jumpToLeft(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i - 1) >= 0) {
			sb.append("(!x" + i + "_" + (s - 1) + " & " + "y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i - 1) + "_" + (s - 1) + " & " + "!y" + (i - 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i - 1, i));
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String jumpOverRight(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i + 2) < length) {
			sb.append("(x" + i + "_" + (s - 1) + " & " + "!y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i + 1) + "_" + (s - 1) + " & " + "y" + (i + 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i + 2) + "_" + (s - 1) + " & " + "!y" + (i + 2) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i, i + 2));
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String jumpOverLeft(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i - 2) >= 0) {
			sb.append("(!x" + i + "_" + (s - 1) + " & " + "y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(x" + (i - 1) + "_" + (s - 1) + " & " + "!y" + (i - 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i - 2) + "_" + (s - 1) + " & " + "!y" + (i - 2) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i - 2, i));
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String initialize(int s) {
		StringBuilder sb = new StringBuilder("");
		sb.append("(");
		for (int j = 0; j < length / 2; j++) {
			sb.append("(x" + j + "_" + s + " & " + "!y" + j + "_" + s + ")");
			sb.append(" & ");
		}
		sb.append("(!x" + (length / 2) + "_" + s + " & " + "!y" + (length / 2) + "_" + s + ")");
		sb.append(" & ");
		for (int j = (length / 2 + 1); j < length; j++) {
			sb.append("(!x" + j + "_" + s + " & " + "y" + j + "_" + s + ")");
			sb.append(" & ");
		}
		sb.setLength(sb.length() - 3);
		sb.append(")");
		return sb.toString();
	}

	public static String end(int s) {
		StringBuilder sb = new StringBuilder("");
		sb.append("(");
		for (int j = 0; j < length / 2; j++) {
			sb.append("(!x" + j + "_" + s + " & " + "y" + j + "_" + s + ")");
			sb.append(" & ");
		}
		sb.append("(!x" + (length / 2) + "_" + s + " & " + "!y" + (length / 2) + "_" + s + ")");
		sb.append(" & ");
		for (int j = (length / 2 + 1); j < length; j++) {
			sb.append("(x" + j + "_" + s + " & " + "!y" + j + "_" + s + ")");
			sb.append(" & ");
		}
		sb.setLength(sb.length() - 3);
		sb.append(")");
		return sb.toString();
	}

	public static String oneStoneTwoFrogs(int s) {
		StringBuilder sb = new StringBuilder("");
		sb.append("(");
		for (int j = 0; j < length; j++) {
			sb.append("!(x" + j + "_" + s + " & " + "y" + j + "_" + s + ")");
			sb.append(" & ");
		}
		sb.setLength(sb.length() - 3);
		sb.append(")");
		return sb.toString();
	}

	/*public static void change(Stone[] tab, int i, int j) {
		Stone tmp = new Stone();
		tmp = tab[i];
		tab[i] = tab[j];
		tab[j] = tmp;
	}*/

	public static String toBool(int s, int min, int max) {
		StringBuilder sb = new StringBuilder("");
		sb.append("(");
		switch (max - min) {
		case 1:
			for (int j = 0; j < min; j++) {
				sb.append("(x" + j + "_" + s + " = x" + j + "_" + (s - 1) + ") & (y" + j + "_" + s + " = y" + j + "_"
						+ (s - 1) + ")");
				sb.append(" & ");
			}
			sb.append("(x" + min + "_" + s + " = x" + max + "_" + (s - 1) + ") & (y" + min + "_" + s + " = y" + max
					+ "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(x" + max + "_" + s + " = x" + min + "_" + (s - 1) + ") & (y" + max + "_" + s + " = y" + min
					+ "_" + (s - 1) + ")");
			sb.append(" & ");
			for (int j = max + 1; j < length; j++) {
				sb.append("(x" + j + "_" + s + " = x" + j + "_" + (s - 1) + ") & (y" + j + "_" + s + " = y" + j + "_"
						+ (s - 1) + ")");
				sb.append(" & ");
			}
			break;
		case 2:
			for (int j = 0; j < min; j++) {
				sb.append("(x" + j + "_" + s + " = x" + j + "_" + (s - 1) + ") & (y" + j + "_" + s + " = y" + j + "_"
						+ (s - 1) + ")");
				sb.append(" & ");
			}
			sb.append("(x" + min + "_" + s + " = x" + max + "_" + (s - 1) + ") & (y" + min + "_" + s + " = y" + max
					+ "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(x" + (min + 1) + "_" + s + " = x" + (min + 1) + "_" + (s - 1) + ") & (y" + (min + 1) + "_" + s
					+ " = y" + (min + 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(x" + max + "_" + s + " = x" + min + "_" + (s - 1) + ") & (y" + max + "_" + s + " = y" + min
					+ "_" + (s - 1) + ")");
			sb.append(" & ");
			for (int j = max + 1; j < length; j++) {
				sb.append("(x" + j + "_" + s + " = x" + j + "_" + (s - 1) + ") & (y" + j + "_" + s + " = y" + j + "_"
						+ (s - 1) + ")");
				sb.append(" & ");
			}
			break;
		default:
			break;
		}
		sb.setLength(sb.length() - 3);
		sb.append(")");
		return sb.toString();
	}

	/*public static String arrayToString(Stone[] tab) {
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
	}*/

}
