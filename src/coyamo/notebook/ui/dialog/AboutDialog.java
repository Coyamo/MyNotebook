package coyamo.notebook.ui.dialog;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import coyamo.notebook.util.UiUtils;

public class AboutDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4431308904525866584L;

	public AboutDialog(JFrame host) {
		super(host, "关于", true);
		JPanel jp = new JPanel();
		jp.setBorder(new EmptyBorder(10, 40, 2, 40));
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		JPanel jp1 = new JPanel();
		JLabel iconLabel = new JLabel(UiUtils.getIcon("icon.png"));
		iconLabel.setLocation((iconLabel.getWidth() + jp.getWidth()) / 2, iconLabel.getLocation().y);
		jp1.add(iconLabel);
		jp.add(jp1);

		JPanel jp2 = new JPanel();
		JLabel nameLabel = new JLabel("MyNotebook");
		nameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		jp2.add(nameLabel);
		jp.add(jp2);

		JPanel jp3 = new JPanel();
		JLabel verLabel = new JLabel("Version 1.0");
		jp3.add(verLabel);
		jp.add(jp3);

		JPanel jp4 = new JPanel();
		jp4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "说明",
				TitledBorder.CENTER, TitledBorder.TOP));
		JLabel thanksLabel = new JLabel(
				"<html>2019.11 JAVA程序设计<br>图标：Icontfont，Eclipse<br>翻译API：有道翻译<br>每日一言API：一言网</html>");

		jp4.add(thanksLabel);
		jp.add(jp4);

		JPanel jp5 = new JPanel();
		JLabel copyRightLabel = new JLabel("Copyright 2019  Coyamo. All Rights Reserved.");
		copyRightLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		jp5.add(copyRightLabel);
		jp.add(jp5);
		setIconImage(UiUtils.getIcon("about.gif").getImage());
		setContentPane(jp);
		pack();
		setLocation((int) (UiUtils.getScreenWidth() - getWidth()) / 2,
				(int) (UiUtils.getScreenHeight() - getHeight()) / 2);

	}
}
