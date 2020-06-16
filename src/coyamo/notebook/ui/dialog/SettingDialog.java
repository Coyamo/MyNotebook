package coyamo.notebook.ui.dialog;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import coyamo.notebook.util.SettingManager;
import coyamo.notebook.util.UiUtils;

public class SettingDialog extends JDialog {
	private static final long serialVersionUID = -3565923137774217420L;
	private JTabbedPane pager;
	private JFrame host;

	public SettingDialog(JFrame host, int which) {
		super(host, "设置", true);
		this.host = host;
		setIconImage(UiUtils.getIcon("setting.png").getImage());

		pager = new JTabbedPane();
		pager.setTabPlacement(JTabbedPane.LEFT);

		initThemeSettingPanel();
		initOtherSettingPanel();
		getContentPane().add(pager);

		pager.setSelectedIndex(which);
		pack();

		setLocation((int) (UiUtils.getScreenWidth() - getWidth()) / 2,
				(int) (UiUtils.getScreenHeight() - getHeight()) / 2);
	}

	private void initThemeSettingPanel() {
		ThemeItem item[] = {
				new ThemeItem("theme_pre_windows.jpg", "Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),
				new ThemeItem("theme_pre_classic.jpg", "Windows Classic",
						"com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"),
				new ThemeItem("theme_pre_motif.jpg", "Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"),
				new ThemeItem("theme_pre_metal.jpg", "Metal", "javax.swing.plaf.metal.MetalLookAndFeel") };
		final JList<ThemeItem> list = new JList<>(item);
		list.setCellRenderer(new ThemeCellRenderer());
		list.setVisibleRowCount(2);
		JScrollPane jsp = new JScrollPane(list);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							try {
								UIManager.setLookAndFeel(list.getSelectedValue().theme);
							} catch (Exception e) {
								UiUtils.alertW(host, "提示", "您的计算机可能不支持所选主题！");
							}
							SwingUtilities.updateComponentTreeUI(host);
							SwingUtilities.updateComponentTreeUI(SettingDialog.this.getContentPane());
						}
					});

					SettingManager.set("theme", list.getSelectedValue().theme);
					SettingManager.save();

				}

			}
		});
		pager.addTab("主题", jsp);
	}

	private void initOtherSettingPanel() {
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label1 = new JLabel("每日一言");
		label1.setHorizontalAlignment(JLabel.LEFT);
		final JCheckBox jcb = new JCheckBox();
		jcb.setSelected(SettingManager.getBoolean("dailyTip", false));
		jcb.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				SettingManager.setBoolean("dailyTip", jcb.isSelected());
				SettingManager.save();
			}
		});
		jp.add(label1);
		jp.add(jcb);
		pager.addTab("其他", jp);
	}

	class ThemeCellRenderer extends JLabel implements ListCellRenderer<ThemeItem> {

		private static final long serialVersionUID = -8278617578373114411L;

		public ThemeCellRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends ThemeItem> list, ThemeItem value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setIcon(UiUtils.getIcon(value.themePreImgPath));

			setBorder(new TitledBorder(value.name));
			return this;
		}

	}

	class ThemeItem {
		String themePreImgPath, name, theme;

		ThemeItem(String themePreImgPath, String name, String theme) {
			this.themePreImgPath = themePreImgPath;
			this.name = name;
			this.theme = theme;
		}
	}
}
