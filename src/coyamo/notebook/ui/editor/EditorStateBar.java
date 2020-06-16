package coyamo.notebook.ui.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

/**
 * 编辑框底部状态栏
 */
public class EditorStateBar extends JPanel {

	private static final long serialVersionUID = -8706525557193592895L;

	private JLabel length_tag, lines_tag, ln_tag, col_tag;
	private JLabel length, lines, ln, col;

	public void setLength(String length) {
		this.length.setText(length);
	}

	public void setLines(String lines) {
		this.lines.setText(lines);
	}

	public void setLn(String ln) {
		this.ln.setText(ln);
	}

	public void setCol(String col) {
		this.col.setText(col);
	}

	public EditorStateBar() {
		// setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setLayout(new FlowLayout(FlowLayout.RIGHT, 50, 0));
		setBorder(new EmptyBorder(-5, 0, -5, 0));
		length_tag = new JLabel("长度: ");
		lines_tag = new JLabel("总行: ");
		ln_tag = new JLabel("行: ");
		col_tag = new JLabel("列: ");
		length = new JLabel();
		lines = new JLabel();
		ln = new JLabel();
		col = new JLabel();

		JPanel wrap1 = new JPanel();
		wrap1.add(length_tag);
		wrap1.add(length);
		wrap1.setBorder(new Separator());
		add(wrap1);

		JPanel wrap2 = new JPanel();
		wrap2.add(lines_tag);
		wrap2.add(lines);
		wrap2.setBorder(new Separator());
		add(wrap2);

		JPanel wrap3 = new JPanel();
		wrap3.add(ln_tag);
		wrap3.add(ln);
		wrap3.setBorder(new Separator());
		add(wrap3);

		JPanel wrap4 = new JPanel();
		wrap4.add(col_tag);
		wrap4.add(col);
		wrap4.setBorder(new Separator());
		add(wrap4);
	}

	/**
	 * 
	 * 自定义一个边框
	 * 
	 */
	class Separator extends AbstractBorder {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2369836372225747296L;

		@Override
		public Insets getBorderInsets(Component c) {
			return getBorderInsets(c, new Insets(0, 0, 0, 0));
		}

		@Override
		public Insets getBorderInsets(Component c, Insets insets) {
			insets.left = 1;
			insets.right = 1;
			return insets;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.setColor(new Color(220, 220, 220));
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(1.5f));
			g.drawLine(x + 1, y, x + 1, height);
			g.drawLine(width - 1, y, width - 1, height);
		}
	}
}
