package coyamo.notebook.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * 限制编辑框只能从键盘输入数字
 * 
 */
public class NumberInputLimiter extends KeyAdapter {
	@Override
	public void keyTyped(KeyEvent e) {
		char keyChar = e.getKeyChar();
		if (!(keyChar >= '0' && keyChar <= '9')) {
			e.consume();
		}
	}
}
