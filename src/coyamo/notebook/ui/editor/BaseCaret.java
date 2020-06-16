package coyamo.notebook.ui.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.text.DefaultCaret;

/**
 * 
 * 自定义编辑框光标
 * 
 */
public class BaseCaret extends DefaultCaret {

	private static final long serialVersionUID = 6256544659106380948L;

	private BaseEditor editor;
	private float paintWidth = 2;
	private boolean tag = true;

	public BaseCaret(BaseEditor editor) {
		this.editor = editor;
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 0, 500);
	}

	@Override
	public void paint(Graphics g) {
		try {
			Rectangle r = editor.modelToView(getDot());
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(paintWidth));
			g2.setColor((tag = !tag) ? editor.getCaretColor() : new Color(0, 0, 0, 0));
			g2.drawLine(r.x, r.y, r.x, r.y + r.height);
		} catch (Exception e) {
		}
	}
}
