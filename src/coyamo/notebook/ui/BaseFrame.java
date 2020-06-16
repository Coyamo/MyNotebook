package coyamo.notebook.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import coyamo.notebook.util.UiUtils;

/**
 * JFrameµÄ·â×°
 */
public class BaseFrame extends JFrame {
	private static final long serialVersionUID = -5902967276195332088L;
	private int width = (int) UiUtils.getScreenWidth(), height = (int) UiUtils.getScreenHeight();

	public BaseFrame() {
		this("MyNotebook");
	}

	public BaseFrame(String title) {
		super(title);
		setMinimumSize(new Dimension(width / 3, height / 5));
		setPreferredSize(new Dimension(2 * width / 3, 2 * height / 3));
		// ¾ÓÖÐ
		setLocation(width / 6, height / 6);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

}
