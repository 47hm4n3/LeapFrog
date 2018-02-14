import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LeapFrog {

	public static int numFrog;
	public static int numStep;
	public static char[] configIn;
	public static char[] configOut;
	public static int length;
	public static boolean exe;
	public static boolean rand;
	private static BufferedReader br;

	public static void main(String[] args) {
		System.out.println(args.length);
		if (args != null && args.length == 2) {
			numFrog = Integer.parseInt(args[0]);
			numStep = Integer.parseInt(args[1]);
			configIn = "".toCharArray();
			configOut = "".toCharArray();
		} else if (args != null && args.length == 3) {
			configIn = args[1].toCharArray();
			configOut = args[2].toCharArray();
			if (!verifConfigs(configIn, configOut)) {
				configIn = "".toCharArray();
				configOut = "".toCharArray();
				numFrog = (new Random()).nextInt(5) + 1;
				numStep = (new Random()).nextInt((numFrog * (numFrog + 1) + 1)) + 1;
			} else {
				rand = true;
				length = configIn.length;
				numFrog = configOut.length / 2;
				numStep = Integer.parseInt(args[0]);
			}
		} else {
			numFrog = (new Random()).nextInt(5) + 1;
			numStep = (new Random()).nextInt((numFrog * (numFrog + 1) + 1)) + 1;
			configIn = "".toCharArray();
			configOut = "".toCharArray();
		}
		exe = true;
		length = 2 * numFrog + 1;
		try {
			Runtime.getRuntime().exec("rm out/result.out");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("===========Debut de l'execution");
		System.out.println("===========Grenouilles :" + numFrog + " - Etapes :" + numStep);
		run(numFrog, numStep);
		isSAT();
		exe = false;
		System.out.println("===========Fin de l'execution");
		System.out.println("===========Debut stats");
		if (!rand)
			System.out.println("Le nombre minimal d'etapes pour atteindre la solution est : "
					+ softMinStepsNumber(numFrog) + ", calcul par formule");
		System.out.println("Calcul en cours ...");
		// Commenter cette ligne pour avoir le formule booleenne de l'execution
		System.out.println("Le nombre minimal d'etapes pour atteindre la solution est : " + hardMinStepsNumber(numFrog)
				+ ", calcul par tatonnement");
		System.out.println("===========Fin stats");
	}

	public static void run(int n, int s) {
		StringBuilder sb = new StringBuilder("");
		int ss = 1;
		if (configIn.length == 0)
			sb.append(initialize(0));
		else
			sb.append(config(0, configIn));
		sb.append(" & \n");
		if (s >= 1) {
			for (ss = 1; ss <= s; ss++) {
				sb.append("( ");
				sb.append(oneStep(ss));
				sb.append(" )");
				sb.append(" & \n");
				sb.append(oneStoneTwoFrogs(ss));
				sb.append(" & \n");
			}
		}
		if (configOut.length == 0)
			sb.append(end(ss - 1));
		else
			sb.append(config(ss - 1, configOut));
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("out/formula.out"));
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			System.err.println("File out/formula.out doesn't exist !");
		}

		try {
			Process p = new ProcessBuilder().command("resources/bool2cnf/bool2cnf")
					.redirectInput(new File("out/formula.out")).redirectOutput(new File("out/formula.cnf")).start();

			boolean done = p.waitFor(15, TimeUnit.SECONDS);
			if (!done) {
				p.destroy();
				p.waitFor();
			}

			Process p1 = new ProcessBuilder().command("/usr/local/bin/minisat")
					.redirectInput(new File("out/formula.cnf")).redirectOutput(new File("out/result.out")).start();

			boolean done1 = p1.waitFor(15, TimeUnit.SECONDS);
			if (!done1) {
				p1.destroy();
				p1.waitFor();
			}
		} catch (IOException | InterruptedException e) {
			System.err.println(
					"Execution Error ! \n Bool2cnf or Minisat not installed try this command :\n make bool2cnf && make minisat");
			e.printStackTrace();
		}
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

	public static String config(int s, char[] tab) {
		StringBuilder sb = new StringBuilder("");
		for (int j = 0; j < tab.length; j++) {
			if (tab[j] == 'x')
				sb.append("(x" + j + "_" + s + " & " + "!y" + j + "_" + s + ")");
			else if (tab[j] == 'y')
				sb.append("(!x" + j + "_" + s + " & " + "y" + j + "_" + s + ")");
			else if (tab[j] == 'V')
				sb.append("(" + "!x" + j + "_" + s + " & " + "!y" + j + "_" + s + ")");
			sb.append(" & ");
		}
		sb.delete(sb.length() - 2, sb.length());
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

	public static int softMinStepsNumber(int n) {
		return n * (n + 2);
	}

	public static boolean isSAT() {
		String str = "";
		try {
			br = new BufferedReader(new FileReader("out/result.out"));
			str = br.readLine();
			while ((str = br.readLine()) != null) {
				if (exe)
					System.out.println(str);
				if (str.contains("SATISFIABLE") && !str.contains("UNSATISFIABLE"))
					return true;
				if (str.contains("UNSATISFIABLE"))
					return false;
			}
			br.close();
		} catch (IOException e) {
			System.err.println("File out/result.out doesn't exist !");
		}
		return false;
	}

	public static int hardMinStepsNumber(int n) {
		int nbSteps = 1;
		run(n, nbSteps);
		while (!isSAT() && nbSteps <= softMinStepsNumber(n) + 3) {
			System.out.println("***Etape : " + nbSteps);
			nbSteps++;
			run(n, nbSteps);
		}
		return nbSteps;
	}

	public static boolean verifConfigs(char[] in, char[] out) {

		if (in.length == out.length) {
			if (check(in) && check(out)) {
				int cix = count(in, 'x');
				int ciy = count(in, 'y');
				int cox = count(out, 'x');
				int coy = count(out, 'y');
				if ((cix == ciy) && (cox == coy) && (cix == cox) && (ciy == coy) && (count(in, 'V') == 1)
						&& (count(out, 'V') == 1)) {
					return true;
				} else
					System.err.println("The two configurations are incompatible !\n" + String.valueOf(in) + "\nand\n"
							+ String.valueOf(out));
			} else
				System.err.println("The two configurations are incompatible !\n" + String.valueOf(in) + "\nand\n"
						+ String.valueOf(out));
		} else
			System.err.println("The two configurations are incompatible !\n" + String.valueOf(in) + "\nand\n"
					+ String.valueOf(out));

		return false;
	}

	private static int count(char[] chars, char c) {
		int cpt = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == c)
				cpt++;
		}
		return cpt;
	}

	private static boolean check(char[] chars) {
		String str = "";
		for (int i = 0; i < chars.length; i++) {
			if (!str.contains(chars[i] + ""))
				str += chars[i];
		}
		if (str.length() == 3)
			return true;
		return false;
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
