package coyamo.notebook.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * 
 * 涉及UI的工具类
 * 
 */
public class UiUtils {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static double getScreenWidth() {
		return screenSize.getWidth();
	}

	public static double getScreenHeight() {
		return screenSize.getHeight();
	}

	/**
	 * 获取指定图片
	 * 
	 * @param name 相对于src/res/的文件名
	 * @return ImageIcon
	 */
	public static ImageIcon getIcon(String name) {
		URL imgURL = UiUtils.class.getResource("/res/" + name);
		return new ImageIcon(imgURL);
	}

	public static void alertW(Component base, String title, String message) {
		JOptionPane.showMessageDialog(base, message, title, JOptionPane.WARNING_MESSAGE);
	}

	public static int alertC(Component base, String title, String message) {
		return JOptionPane.showConfirmDialog(base, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
	}

	public static void initUI() {

		try {
			// 设置UI风格
			UIManager.setLookAndFeel(SettingManager.get("theme", UIManager.getSystemLookAndFeelClassName()));
			// 设置全局默认字体样式
			Font vFont = new Font("Dialog", Font.PLAIN, 13);
			UIManager.getLookAndFeelDefaults().put("defaultFont", vFont);
		} catch (Exception e) {
		}
	}
}
