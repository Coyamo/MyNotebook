package coyamo.notebook.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * ���Ʊ༭��ֻ�ܴӼ�����������
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
