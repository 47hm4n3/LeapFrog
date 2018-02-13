import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeapFrog {

	// public static Stone[] stones;
	public static int numFrog;
	public static int numStep;
	public static int length;
	public static int empty;
	public static int step;
	// public static Stone[] endStones;

	public static void main(String[] args) {
		if (args != null && args.length >= 2) {
				numFrog = Integer.parseInt(args[0]);
				numStep = Integer.parseInt(args[1]);
		} else {
			numFrog = 3;
			numStep = 8;
		}
		
		length = 2 * numFrog + 1;
		step = 0;

		StringBuilder sb = new StringBuilder("");
		sb.append(initialize(step));
		sb.append(" & ");
		for (step = 1; step <= numStep; step++) {
			sb.append("( ");
			sb.append(oneStep(step));
			sb.append(" )");
			sb.append(" & \n");
			sb.append(oneStoneTwoFrogs(step));
			sb.append(" & ");
		}
		sb.append(end(step - 1));
		System.out.println(sb.toString());
		
	}

	public static String oneStep(int s) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			sb.append("( ");
			sb.append(moveOneBox(s, i));
			sb.append(" )");
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
			sb.append(" | \n");
		}
		str1 = jumpToLeft(s, i);
		sb.append(str1);
		if (!str1.isEmpty()) {
			sb.append(" | \n");
		}
		str1 = jumpOverRight(s, i);
		sb.append(str1);
		if (!str1.isEmpty()) {
			sb.append(" | \n");
		}
		str1 = jumpOverLeft(s, i);
		if (str1.isEmpty()) {
			sb.setLength(sb.length() - 4);
		} else {
			sb.append(str1);
		}
		return sb.toString();
	}

	public static String jumpToRight(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i + 1) < length) {
			sb.append("( ");
			sb.append("(x" + i + "_" + (s - 1) + " & " + "!y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i + 1) + "_" + (s - 1) + " & " + "!y" + (i + 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i, i + 1));
			sb.append(" )");
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String jumpToLeft(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i - 1) >= 0) {
			sb.append("( ");
			sb.append("(!x" + i + "_" + (s - 1) + " & " + "y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i - 1) + "_" + (s - 1) + " & " + "!y" + (i - 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i - 1, i));
			sb.append(" )");
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String jumpOverRight(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i + 2) < length) {
			sb.append("( ");
			sb.append("(x" + i + "_" + (s - 1) + " & " + "!y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i + 1) + "_" + (s - 1) + " & " + "y" + (i + 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i + 2) + "_" + (s - 1) + " & " + "!y" + (i + 2) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i, i + 2));
			sb.append(" )");
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String jumpOverLeft(int s, int i) {
		StringBuilder sb = new StringBuilder("");
		if ((i - 2) >= 0) {
			sb.append("( ");
			sb.append("(!x" + i + "_" + (s - 1) + " & " + "y" + i + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(x" + (i - 1) + "_" + (s - 1) + " & " + "!y" + (i - 1) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append("(!x" + (i - 2) + "_" + (s - 1) + " & " + "!y" + (i - 2) + "_" + (s - 1) + ")");
			sb.append(" & ");
			sb.append(toBool(s, i - 2, i));
			sb.append(" )");
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public static String initialize(int s) {
		StringBuilder sb = new StringBuilder("");
		sb.append("( ");
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
		sb.append(" )");
		return sb.toString();
	}

	public static String end(int s) {
		StringBuilder sb = new StringBuilder("");
		sb.append("( ");
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
		sb.append(" )");
		return sb.toString();
	}

	public static String oneStoneTwoFrogs(int s) {
		StringBuilder sb = new StringBuilder("");
		sb.append("( ");
		for (int j = 0; j < length; j++) {
			sb.append("!(x" + j + "_" + s + " & " + "y" + j + "_" + s + ")");
			sb.append(" & ");
		}
		sb.setLength(sb.length() - 3);
		sb.append(" )");
		return sb.toString();
	}

	public static int softMinStepsNumber (int number) {
		return number * (number + 2);
	}
	
	public static String hardMinStepsNumber () {
		String str = "";
		String beforeLast = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("out/result.out"));

			str = br.readLine();
			while ((str = br.readLine()) != null) {
				beforeLast = str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return beforeLast;
	} 
	
	public static String toBool(int s, int min, int max) {
		StringBuilder sb = new StringBuilder("");
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
		return sb.toString();
	}

}
