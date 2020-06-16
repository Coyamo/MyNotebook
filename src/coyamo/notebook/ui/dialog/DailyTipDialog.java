package coyamo.notebook.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;

import coyamo.notebook.util.UiUtils;
import coyamo.notebook.util.Utils;

public class DailyTipDialog extends JDialog {
	private static final long serialVersionUID = 487467952111108133L;
	private JFrame host;
	private boolean showErr;
	private JPanel jpanel;
	private JLabel text;

	public DailyTipDialog(JFrame host, boolean showErr) {
		super(host, "每日一言", true);
		this.host = host;
		this.showErr = showErr;
		setResizable(false);
		setIconImage(UiUtils.getIcon("love.png").getImage());
		setPreferredSize(new Dimension(400, 255));
		text = new JLabel("", JLabel.CENTER) {
			private static final long serialVersionUID = -3261226716080969124L;

			@Override
			public void setText(String text) {
				super.setText("<html>" + text + "</html>");
			}

		};
		jpanel = new JPanel() {
			private static final long serialVersionUID = 3285716690941725132L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(UiUtils.getIcon("pic1.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		BorderLayout bb = new BorderLayout();
		jpanel.setLayout(bb);
		text.setVerticalAlignment(SwingConstants.CENTER);
		text.setFont(new Font("宋体", 0, 18));
		text.setBorder(new TipBorder());
		jpanel.add(text, BorderLayout.CENTER);
		getContentPane().add(jpanel);
		pack();
		setLocation((int) (UiUtils.getScreenWidth() - getWidth()) / 2,
				(int) (UiUtils.getScreenHeight() - getHeight()) / 2);
	}

	public void showDailyTip() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String str = getDailyWords();
					if (str.trim().length() == 0) {
						if (showErr)
							UiUtils.alertW(host, "一言", "无法加载每日一言！");
						return;
					}

					text.setText(str);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							DailyTipDialog.super.setVisible(true);
						}
					});
				} catch (Exception e1) {
					if (showErr)
						UiUtils.alertW(host, "一言", "无法加载每日一言！");
				}

			}
		}).start();

	}

	private String getDailyWords() {
		// api好像出问题了
		return Utils.get("https://v1.hitokoto.cn/?encode=text");
	}

	class TipBorder extends AbstractBorder {
		private static final long serialVersionUID = -5666745103536967682L;

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(50, 50, 50, 50);
		}

		@Override
		public Insets getBorderInsets(Component c, Insets insets) {
			return getBorderInsets(c);
		}

		@Override
		public boolean isBorderOpaque() {
			return false;
		}
	}
}
