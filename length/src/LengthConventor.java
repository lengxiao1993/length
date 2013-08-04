

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LengthConventor {

	private static Map<String, Double> rulesMap = new HashMap<String, Double>();
	// 分析文件的过程中将需要的输出结果均存储于该ArrayList 中最后统一输出
	private static ArrayList<String> results = new ArrayList<String>();

	public static void main(String[] args) {
		parseTxtFile("input.txt");
	}

	public static void parseTxtFile(String filename) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filename));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);

			if (line.contains("=")) {
				// 存储转换规则
				storeConvertRule(line);

			} else if (line.contains("+") || line.contains("-")) {

				String[] splits = line.split("[+-]");
				ArrayList<String> signs = new ArrayList<String>();
				for (int i = 0; i < line.length(); i++) {
					String subStr = line.substring(i, i + 1);
					if (subStr.matches("[+-]"))
						signs.add(subStr);
				}

				Double[] values = new Double[splits.length];
				for (int i = 0; i < splits.length; i++) {
					values[i] = convertToMeters(splits[i]);
				}

				Double result = values[0];
				for (int i = 0; i < signs.size(); i++) {
					switch (signs.get(i)) {
					case "+":
						result = result + values[i + 1];
						break;
					case "-":
						result = result - values[i + 1];
						break;
					default:
						break;
					}
				}
				DecimalFormat df = new DecimalFormat("0.00");

				results.add(df.format(result));

			} else if (!line.trim().equals("")) {
				Double result = convertToMeters(line);
				DecimalFormat df = new DecimalFormat("0.00");
				results.add(df.format(result));
			}

		}

		try {
			PrintWriter pw = new PrintWriter(new File("output.txt"));
			pw.println("8112883@qq.com");
			pw.println();

			for (int i = 0; i < results.size(); i++) {
				pw.println(results.get(i) + " m");
			}
			
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		scanner.close();

	}

	private static Double convertToMeters(String line) {

		String[] splits = line.trim().split(" ");
		// 去除复数单位后的s
		if (splits[1].equals("inches")) {
			splits[1] = splits[1].substring(0, splits[1].length() - 2);
		} else if (splits[1].endsWith("s"))
			splits[1] = splits[1].substring(0, splits[1].length() - 1);
		if (splits[1].equals("feet"))
			splits[1] = "foot";

		// 根据单位判断转换值
		Double convertValue = rulesMap.get(splits[1]);
		// 计算结果
		Double result = Double.parseDouble(splits[0]) * convertValue;
		return result;
	}

	private static void storeConvertRule(String line) {
		String[] splits = line.split(" ");
		rulesMap.put(splits[1], Double.parseDouble(splits[3]));
	}
}
