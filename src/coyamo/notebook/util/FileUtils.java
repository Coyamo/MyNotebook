package coyamo.notebook.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * 
 * �ļ���д������
 * 
 */
public class FileUtils {
	/**
	 * ��ȡ�ļ�
	 * 
	 * @param path ·��
	 * @return
	 */
	public static String read(String path) {
		StringBuilder sb = new StringBuilder();
		try (Reader reader = new FileReader(path); BufferedReader breader = new BufferedReader(reader);) {
			String line;
			while ((line = breader.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		} catch (Exception e) {

		}
		return sb.toString();
	}

	/**
	 * ���Ǳ����ļ�
	 * 
	 * @param text ����
	 * @param to   ·��
	 */
	public static void write(String text, String to) {
		try (Writer writer = new FileWriter(to); BufferedWriter bwriter = new BufferedWriter(writer);) {
			for (String line : text.split(System.lineSeparator())) {
				bwriter.write(line);
				bwriter.newLine();
				bwriter.flush();
			}
		} catch (Exception e) {

		}
	}
}
