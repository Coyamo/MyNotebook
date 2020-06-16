package coyamo.notebook.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Highlighter.Highlight;

import coyamo.notebook.ui.BaseMenuBar;
import coyamo.notebook.ui.NumberInputLimiter;
import coyamo.notebook.ui.TextFieldHintUI;
import coyamo.notebook.ui.editor.SearchHighlighter;
import coyamo.notebook.ui.editor.SearchHighlighter.SearchingListener;
import coyamo.notebook.ui.editor.TextEditor;
import coyamo.notebook.util.UiUtils;

/**
 * 
 * ���������˵�����ش���
 * 
 */
public class MenuSeachEvent {
	private JFrame host;
	private int screenW = (int) UiUtils.getScreenWidth();
	private int screenH = (int) UiUtils.getScreenHeight();

	public MenuSeachEvent(BaseMenuBar bar) {
		host = bar.getHost();
	}

	/**
	 * ���������滻����
	 * 
	 * @param editor ��ǰ�༭��
	 * @param which  true ���� false �滻
	 */
	private void doFindOrReplace(final TextEditor editor, boolean which) {
		if (host == null)
			return;
		final JDialog dialog = new JDialog(host, which ? "����" : "�滻", true);

		int dialogW = screenW / 4, dialogH = screenH / 6;
		dialog.setPreferredSize(new Dimension(dialogW, dialogH));
		dialog.pack();
		dialog.setLocation(3 * screenW / 8, 2 * screenH / 5);
		dialog.setResizable(false);
		int paddingW = dialogW / 3 / 5, paddingH = dialogH / 3 / 8;
		int titleBarH = dialog.getInsets().top;
		int averageW = (dialogW - 5 * paddingW) / 4, averageH = (dialogH - titleBarH - 36 - 4 * paddingH) / 3;

		final JTabbedPane pager = new JTabbedPane();

		JPanel findPanel = new JPanel();
		findPanel.setLayout(null);
		JLabel l1 = new JLabel("����Ŀ�꣺");
		final JTextField t1 = new JTextField();
		t1.setUI(new TextFieldHintUI("������ؼ���..."));
		JButton b1 = new JButton("����");
		final JTextArea l2 = new JTextArea();
		l2.setOpaque(false);
		l2.setEditable(false);
		l2.setLineWrap(true);
		l2.setWrapStyleWord(true);
		l2.setFocusable(false);
		JButton b2 = new JButton("�ر�");
		l1.setBounds(paddingW, paddingH, averageW, averageH);
		t1.setBounds(paddingW + averageW, paddingH, 2 * averageW + paddingW, averageH);
		l2.setBounds(paddingW, 2 * paddingH + averageH, 3 * averageW + paddingW, 2 * averageH + paddingH);
		b1.setBounds(3 * paddingW + 3 * averageW, paddingH, averageW + averageH / 2, averageH);
		b2.setBounds(3 * paddingW + 3 * averageW, 2 * paddingH + averageH, averageW + averageH / 2, averageH);
		findPanel.add(l1);
		findPanel.add(t1);
		findPanel.add(b1);
		findPanel.add(l2);
		findPanel.add(b2);

		// �����õ��ĸ���������
		final SearchHighlighter highLighter = editor.getEditor().getHighlighter();

		// �������
		final SearchingListener listener1 = new SearchHighlighter.SearchingListener() {

			@Override
			public void onResult(SearchHighlighter.RESULT result, int start, int end) {
				switch (result) {
				case NOT_FOUND:
					l2.setForeground(Color.decode("#2E7D32"));
					l2.setText("�Ѳ������ĵ���ײ���");
					break;
				case FOUND:
					l2.setForeground(Color.black);
					l2.setText(
							"�У� " + (editor.getEditor().getLineOfOffset(start) + 1) + " ��ʼ�� " + start + " ������ " + end);
					break;
				case ERROR:
					l2.setForeground(Color.red);
					l2.setText("����ʧ�ܣ�����δ֪�Ĵ���");
					break;
				}

			}
		};

		b1.addActionListener(new ActionListener() {
			String lastWords = "";

			@Override
			public void actionPerformed(ActionEvent e) {
				String words = t1.getText();
				if (!words.equals("")) {
					highLighter.setListener(listener1);
					if (!words.equals(lastWords)) {
						lastWords = words;
						highLighter.setSearchWords(words);
					}
					highLighter.findNext();
				} else {
					l2.setForeground(Color.red);
					l2.setText("����������Ŀ�꣡��");
				}
			}
		});
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				highLighter.removeAllHighlights();
				dialog.dispose();
			}
		});

		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				highLighter.removeAllHighlights();
			}
		});

		JPanel replacePanel = new JPanel();
		replacePanel.setLayout(null);
		JLabel l1_replacePanel = new JLabel("�滻Ŀ�꣺");
		l1_replacePanel.setHorizontalAlignment(JTextField.RIGHT);
		final JTextField t1_replacePanel = new JTextField();
		t1_replacePanel.setUI(new TextFieldHintUI("������ؼ���..."));
		JButton b1_replacePanel = new JButton("����");
		JLabel l2_replacePanel = new JLabel("�滻Ϊ��");
		l2_replacePanel.setHorizontalAlignment(JTextField.RIGHT);
		final JTextField t2_replacePanel = new JTextField();
		t2_replacePanel.setUI(new TextFieldHintUI("���滻Ϊ..."));
		JButton b2_replacePanel = new JButton("�滻");
		final JTextArea t3_replacePanel = new JTextArea();
		t3_replacePanel.setOpaque(false);
		t3_replacePanel.setEditable(false);
		t3_replacePanel.setLineWrap(true);
		t3_replacePanel.setWrapStyleWord(true);
		t3_replacePanel.setFocusable(false);

		l1_replacePanel.setBounds(paddingW, paddingH, averageW, averageH);
		t1_replacePanel.setBounds(paddingW + averageW, paddingH, 2 * averageW + paddingW, averageH);
		l2_replacePanel.setBounds(paddingW, 2 * paddingH + averageH, averageW, averageH);
		t2_replacePanel.setBounds(paddingW + averageW, 2 * paddingH + averageH, 2 * averageW + paddingW, averageH);
		t3_replacePanel.setBounds(paddingW, 3 * paddingH + 2 * averageH, 3 * averageW + paddingW, averageH);
		b1_replacePanel.setBounds(3 * paddingW + 3 * averageW, paddingH, averageW + averageH / 2, averageH);
		b2_replacePanel.setBounds(3 * paddingW + 3 * averageW, 2 * paddingH + averageH, averageW + averageH / 2,
				averageH);
		replacePanel.add(l1_replacePanel);
		replacePanel.add(t1_replacePanel);
		replacePanel.add(b1_replacePanel);
		replacePanel.add(l2_replacePanel);
		replacePanel.add(b2_replacePanel);
		replacePanel.add(t2_replacePanel);
		replacePanel.add(t3_replacePanel);

		final SearchingListener listener2 = new SearchHighlighter.SearchingListener() {

			@Override
			public void onResult(SearchHighlighter.RESULT result, int start, int end) {
				switch (result) {
				case NOT_FOUND:
					t3_replacePanel.setForeground(Color.decode("#2E7D32"));
					t3_replacePanel.setText("�Ѳ������ĵ���ײ���");
					break;
				case FOUND:
					t3_replacePanel.setForeground(Color.black);
					t3_replacePanel.setText(
							"�У� " + (editor.getEditor().getLineOfOffset(start) + 1) + "  ��ʼ�� " + start + " ������ " + end);
					break;
				case ERROR:
					t3_replacePanel.setForeground(Color.red);
					t3_replacePanel.setText("����ʧ�ܣ�����δ֪�Ĵ���");
					break;
				}

			}
		};

		b1_replacePanel.addActionListener(new ActionListener() {
			String lastWords = "";

			@Override
			public void actionPerformed(ActionEvent e) {
				String words = t1_replacePanel.getText();
				if (!words.equals("")) {
					highLighter.setListener(listener2);
					if (!words.equals(lastWords)) {
						lastWords = words;
						highLighter.setSearchWords(words);
					}
					highLighter.findNext();
				} else {
					t3_replacePanel.setForeground(Color.red);
					t3_replacePanel.setText("�������滻Ŀ�꣡");
				}
			}
		});
		b2_replacePanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Highlight highlight = highLighter.getCurrentHighlight();
				String text = t2_replacePanel.getText();
				highLighter.setListener(listener2);
				if (highlight == null) {
					t3_replacePanel.setForeground(Color.red);
					t3_replacePanel.setText("�滻ʧ�ܣ���ǰ��ѡ���");
				} else {
					editor.getEditor().setText(highlight.getStartOffset(), highlight.getEndOffset(), text);
					highLighter.refreshFromEditor();
					t3_replacePanel.setForeground(Color.black);
					t3_replacePanel.setText("�滻�ɹ���");
				}
			}
		});

		pager.addTab("����", findPanel);
		pager.addTab("�滻", replacePanel);
		if (!which)
			pager.setSelectedIndex(1);
		pager.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				dialog.setTitle(pager.getTitleAt(pager.getSelectedIndex()));
				highLighter.setListener(null);
			}
		});
		dialog.getContentPane().add(pager);
		dialog.setVisible(true);
	}

	public void dofind(TextEditor editor) {
		doFindOrReplace(editor, true);
	}

	public void doReplace(TextEditor editor) {
		doFindOrReplace(editor, false);
	}

	public void doGotoLine(final TextEditor editor) {

		final JDialog dialog = new JDialog(host, "��ת", true);
		int dialogW = screenW / 4, dialogH = screenH / 5;

		dialog.setPreferredSize(new Dimension(dialogW, dialogH));
		dialog.pack();// �����ܻ�ȡ�������߶�
		dialog.setLocation(3 * screenW / 8, 2 * screenH / 5);
		dialog.getContentPane().setLayout(null);
		dialog.setResizable(false);
		// ����������
		int paddingW = dialogW / 3 / 5, paddingH = dialogH / 4 / 5;
		int titleBarH = dialog.getInsets().top;// �������߶�
		int averageW = (dialogW - 4 * paddingW) / 3, averageH = (dialogH - titleBarH - 5 * paddingH) / 4;

		// line1
		ButtonGroup group = new ButtonGroup();
		final JRadioButton r1 = new JRadioButton("����", true);
		final JRadioButton r2 = new JRadioButton("ƫ��ֵ");
		r1.setBounds(new Rectangle(paddingW, paddingH, averageW, averageH));
		r2.setBounds(new Rectangle(2 * paddingW + averageW, paddingH, averageW, averageH));
		group.add(r1);
		group.add(r2);
		// line2
		JLabel l1 = new JLabel("��ǰλ�ã�");
		final JLabel l2 = new JLabel((editor.getEditor().getSelectLine() + 1) + "");
		l1.setBounds(paddingW, 2 * paddingH + averageH, averageW, averageH);
		l2.setBounds(2 * paddingW + averageW, 2 * paddingH + averageH, averageW, averageH);
		// line3
		JLabel l3 = new JLabel("Ŀ��λ�ã�");
		final JTextField t1 = new JTextField();
		t1.setUI(new TextFieldHintUI("������..."));
		t1.addKeyListener(new NumberInputLimiter());
		JButton b1 = new JButton("��λ");
		l3.setBounds(paddingW, 3 * paddingH + 2 * averageH, averageW, averageH);
		t1.setBounds(2 * paddingW + averageW, 3 * paddingH + 2 * averageH, averageW, averageH);
		b1.setBounds(3 * paddingW + 2 * averageW, 3 * paddingH + 2 * averageH, averageW, averageH);
		// line4
		JLabel l4 = new JLabel("��ֹλ�ã�");
		final JLabel l5 = new JLabel(editor.getEditor().getLineCount() + "");
		JButton b2 = new JButton("ȡ��");
		l4.setBounds(paddingW, 4 * paddingH + 3 * averageH, averageW, averageH);
		l5.setBounds(2 * paddingW + averageW, 4 * paddingH + 3 * averageH, averageW, averageH);
		b2.setBounds(3 * paddingW + 2 * averageW, 4 * paddingH + 3 * averageH, averageW, averageH);
		dialog.getContentPane().add(r1);
		dialog.getContentPane().add(r2);
		dialog.getContentPane().add(l1);
		dialog.getContentPane().add(l2);
		dialog.getContentPane().add(l3);
		dialog.getContentPane().add(t1);
		dialog.getContentPane().add(b1);
		dialog.getContentPane().add(l4);
		dialog.getContentPane().add(l5);
		dialog.getContentPane().add(b2);

		r2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (r2.isSelected()) {
					l2.setText(editor.getEditor().getCaretPosition() + "");
					l5.setText(editor.getEditor().getLength() + "");

				} else {
					l2.setText((editor.getEditor().getSelectLine() + 1) + "");
					l5.setText(editor.getEditor().getLineCount() + "");
				}
				t1.setText("");
			}
		});
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = t1.getText();
				if (str.equals(""))
					return;
				try {
					int intV = Integer.valueOf(str);
					if (r1.isSelected()) {
						editor.gotoLine(intV);
						l2.setText((editor.getEditor().getSelectLine() + 1) + "");
					} else {
						if (intV > editor.getEditor().getLength())
							intV = editor.getEditor().getLength();
						editor.getEditor().setCaretPosition(intV);
						l2.setText(editor.getEditor().getCaretPosition() + "");
					}
				} catch (Exception e) {
					UiUtils.alertW(host, "��ʾ", "Ŀ��λ�ñ�����һ����ֵ��");
				}
			}
		});

		dialog.setVisible(true);
	}

}
