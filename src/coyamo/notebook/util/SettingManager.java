package coyamo.notebook.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class SettingManager {
	private static Map<String, String> setting = new HashMap<>();
	private static String path = System.getProperty("user.dir") + "\\MyNotebook\\setting.txt";
	static {
		File f = new File(path);
		if (f.exists()) {

			try (Reader reader = new FileReader(path); BufferedReader breader = new BufferedReader(reader);) {
				String line;
				while ((line = breader.readLine()) != null) {
					line = line.trim();
					if (line.length() != 0) {
						String keypair[] = line.split("=");
						setting.put(keypair[0].trim(), keypair[1].trim());
					}
				}
			} catch (Exception e) {
				UiUtils.alertW(null, "提示", "配置文件错误，已恢复默认设置。");
				if (f.exists()) {
					f.delete();
				}
			}
		}
	}

	public static String get(String key, String defValue) {
		if (setting.containsKey(key))
			return setting.get(key);
		setting.put(key, defValue);
		return defValue;
	}

	public static boolean getBoolean(String key, boolean defValue) {
		if (setting.containsKey(key))
			return Boolean.parseBoolean(setting.get(key));
		setting.put(key, defValue + "");
		return defValue;
	}

	public static void set(String key, String defValue) {
		setting.put(key, defValue);
	}

	public static void setBoolean(String key, boolean defValue) {
		setting.put(key, defValue + "");
	}

	public static void save() {
		File f = new File(path);
		if (!f.exists())
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} catch (Exception e1) {
				System.out.println(f);
				System.out.println(e1);
			}
		try (Writer writer = new FileWriter(path); BufferedWriter bwriter = new BufferedWriter(writer);) {
			for (String key : setting.keySet()) {
				bwriter.write(key + "=" + setting.get(key));
				bwriter.newLine();
				bwriter.flush();
			}
		} catch (Exception e) {
		}
	}
}
