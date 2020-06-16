package coyamo.notebook.ui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.border.AbstractBorder;

/**
 * 通过自定义Border实现行数显示
 */
public class LineNumberBorder extends AbstractBorder {

	private static final long serialVersionUID = 3826797544639550364L;

	public LineNumberBorder() {

	}

	@Override
	public Insets getBorderInsets(Component c) {
		return getBorderInsets(c, new Insets(0, 0, 0, 0));
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = lineNumberWidth((BaseEditor) c) + 15;
		return insets;
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		// 获得当前剪贴区域的边界矩形。
		Rectangle clip = g.getClipBounds();
		FontMetrics fm = c
				.getFontMetrics(((BaseEditor) c).getStyledDocument().getFont(((BaseEditor) c).getInputAttributes()));
		int fontHeight = fm.getHeight();
		int ybaseline = y + fm.getAscent();
		int startingLineNumber = (clip.y / fontHeight) + 1;
		if (startingLineNumber != 1) {
			ybaseline = y + startingLineNumber * fontHeight - (fontHeight - fm.getAscent());
		}
		int yend = ybaseline + fm.getHeight() * ((BaseEditor) c).getLineCount();
		if (yend > (y + height)) {
			yend = y + height;
		}
		g.setColor(Color.decode("#696969"));
		((Graphics2D) g).setBackground(Color.decode("#f8f8ff"));
		((Graphics2D) g).clearRect(0, 0, getBorderInsets(c).left - 5, height);
		// 绘制行号
		while (ybaseline < yend) {
			String label = padLabel(startingLineNumber, 0, true);
			g.drawString(label, 10, ybaseline);
			ybaseline += fontHeight;
			startingLineNumber++;
		}
		g.setColor(Color.decode("#e6e6fa"));
		g.drawLine(getBorderInsets(c).left - 5, 0, getBorderInsets(c).left - 5, height);

	}

	// 数字宽度

	private int lineNumberWidth(BaseEditor editor) {
		int lineCount = editor.getLineCount();
		return editor.getFontMetrics(editor.getStyledDocument().getFont(editor.getInputAttributes()))
				.stringWidth(lineCount + " ");
	}

	private String padLabel(int lineNumber, int length, boolean addSpace) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(lineNumber);
		for (int count = (length - buffer.length()); count > 0; count--) {
			buffer.insert(0, ' ');
		}
		if (addSpace) {
			buffer.append(' ');
		}
		return buffer.toString();
	}
}
