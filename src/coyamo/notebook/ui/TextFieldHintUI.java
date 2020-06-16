package coyamo.notebook.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 * 
 * 设置编辑框背景提示文字 来自网络未知作者
 */
public class TextFieldHintUI extends BasicTextFieldUI implements FocusListener {
	private String hint;
	private Color hintColor;

	public TextFieldHintUI(String hint) {
		this(hint, Color.gray);
	}

	public TextFieldHintUI(String hint, Color hintColor) {
		this.hint = hint;
		this.hintColor = hintColor;
	}

	private void repaint() {
		if (getComponent() != null) {
			getComponent().repaint();
		}
	}

	@Override
	protected void paintSafely(Graphics g) {
		super.paintSafely(g);
		JTextComponent component = getComponent();
		if (component.getText().length() == 0 && !component.hasFocus()) {
			g.setColor(hintColor);
			int padding = (component.getHeight() - component.getFont().getSize()) / 2;
			int inset = 3;
			g.drawString(hint, inset, component.getHeight() - padding - inset);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		repaint();
	}

	@Override
	public void focusLost(FocusEvent e) {
		repaint();
	}

	@Override
	public void installListeners() {
		super.installListeners();
		getComponent().addFocusListener(this);
	}

	@Override
	public void uninstallListeners() {
		super.uninstallListeners();
		getComponent().removeFocusListener(this);
	}
}