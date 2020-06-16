package coyamo.notebook.ui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.undo.UndoManager;

import coyamo.notebook.ui.BaseMenuItem;
import coyamo.notebook.util.UiUtils;
import coyamo.notebook.util.Utils;

/**
 * 
 * 对JTextPane的封装
 * 
 */
public class BaseEditor extends JTextPane implements MouseListener, ActionListener, ChangeListener, FocusListener {

	private static final long serialVersionUID = 5789356699615602550L;
	// 右键菜单
	private JPopupMenu popMenu;
	// 撤消与恢复管理器
	private UndoManager undoManager = new UndoManager();
	// 搜索文字高亮管理
	private SearchHighlighter highlighter = new SearchHighlighter(this);
	// 自定义编辑框事件
	private EditorListener editorListener;

	public BaseEditor() {
		setHighlighter(highlighter);
		setSelectionColor(Color.decode("#ade1f6"));
		setSelectedTextColor(Color.BLACK);
		setCaretColor(Color.decode("#018577"));
		// 设置行数显示
		setBorder(new LineNumberBorder());

		addMouseListener(this);
		getDocument().addUndoableEditListener(undoManager);

		// 允许拖动编辑框文
		setDragEnabled(true);

		initPopMenu();

		setCaret(new BaseCaret(this));
		getCaret().addChangeListener(this);
		addFocusListener(this);
		// 添加撤消恢复快捷
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
					if (canUndo())
						undo();
					return;
				}
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
					if (canRedo())
						redo();
					return;
				}

				super.keyPressed(e);
			}
		});

		getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (editorListener != null)
					editorListener.onTextChange(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (editorListener != null)
					editorListener.onTextChange(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (editorListener != null)
					editorListener.onTextChange(e);
			}
		});
	}

	public void setEditorListener(EditorListener editorListener) {
		this.editorListener = editorListener;
	}

	public SearchHighlighter getHighlighter() {
		return highlighter;
	}

	/**
	 * 撤消
	 */
	public void undo() {
		if (canUndo())
			undoManager.undo();
	}

	/**
	 * 跳转到行
	 * 
	 * @param line
	 * @return 是否成功
	 */
	public boolean gotoLine(int line) {
		if (line > getLineCount())
			line = getLineCount();
		if (line <= 0)
			line = 1;
		int offset = getLineStartOffset(line - 1);
		if (offset != -1)
			setCaretPosition(offset);
		return offset != -1;
	}

	/**
	 * 能否撤消
	 * 
	 * @return
	 */
	public boolean canUndo() {
		return undoManager.canUndo();
	}

	/**
	 * 恢复
	 */
	public void redo() {
		if (canRedo())
			undoManager.redo();
	}

	/**
	 * 能否恢复
	 */
	public boolean canRedo() {
		return undoManager.canRedo();
	}

	/**
	 * 行是否在视野
	 * 
	 * @param line �?
	 * @return 是否可见
	 */
	public boolean isLineVisible(int line) {
		int topRange = viewToModel(new Point(0, getVisibleRect().y));
		int buttomRange = viewToModel(new Point(0, topRange + getVisibleRect().height));
		return line >= topRange && line <= buttomRange;
	}

	/**
	 * 获取光标�?在行
	 * 
	 * @return 光标�?在行
	 */
	public int getSelectLine() {
		return getLineOfOffset(getCaretPosition());
	}

	/**
	 * 获取某行的开始偏移�??
	 * 
	 * @param line �?
	 * @return 偏移�?
	 */
	public int getLineStartOffset(int line) {
		try {
			Element map = getDocument().getDefaultRootElement();
			Element lineElem = map.getElement(line);
			return lineElem.getStartOffset();
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取某行的结束偏移
	 * 
	 * @param line
	 * @return 结束偏移
	 */
	public int getLineEndOffset(int line) {
		try {
			int lineCount = getLineCount();
			Element map = getDocument().getDefaultRootElement();
			Element lineElem = map.getElement(line);
			int endOffset = lineElem.getEndOffset();
			return ((line == lineCount - 1) ? (endOffset - 1) : endOffset);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 通过偏移值计算行
	 * 
	 * @param offset 偏移
	 * @return
	 */
	public int getLineOfOffset(int offset) {
		try {
			Element map = getDocument().getDefaultRootElement();
			return map.getElementIndex(offset);
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 获取行数
	 * 
	 * @return 行数
	 */
	public int getLineCount() {
		Element map = getDocument().getDefaultRootElement();
		return map.getElementCount();
	}

	/**
	 * 获取内容长度
	 * 
	 * @return 内容长度
	 */
	public int getLength() {
		return getDocument().getLength();
	}

	/**
	 * 追加文字
	 * 
	 * @param str 文字
	 */
	public void append(String str) {
		insert(getLength(), str);
	}

	/**
	 * 设置指定位置文字
	 * 
	 * @param offs 开始偏移
	 * @param to   结束偏移
	 * @param str
	 */
	public void setText(int offs, int to, String str) {

		Document docs = getDocument();
		try {
			docs.remove(offs, to - offs);
			docs.insertString(offs, str, getInputAttributes());
		} catch (Exception e) {
		}
	}

	/**
	 * 指定位置插入文字
	 * 
	 * @param start �?始偏移�??
	 * @param str   文字
	 */
	public void insert(int start, String str) {
		Document docs = getDocument();
		try {
			docs.insertString(start, str, getInputAttributes());
		} catch (Exception e) {
		}
	}

	/**
	 * 是否选中文字
	 * 
	 * @return 是否选中文字
	 */
	public boolean hasSelectText() {
		return !(getSelectionEnd() == getSelectionStart());
	}

	private void initPopMenu() {
		popMenu = new JPopupMenu();
		BaseMenuItem undo = new BaseMenuItem("撤消", "undo.gif");
		BaseMenuItem redo = new BaseMenuItem("恢复", "redo.gif");
		BaseMenuItem copy = new BaseMenuItem("复制", "copy.gif");
		BaseMenuItem cut = new BaseMenuItem("剪切", "cut.gif");
		BaseMenuItem paste = new BaseMenuItem("粘贴", "paste.gif");
		BaseMenuItem toUcase = new BaseMenuItem("转成大写", "2Ucase.png");
		BaseMenuItem toLcase = new BaseMenuItem("转成小写", "2Lcase.png");
		BaseMenuItem trans = new BaseMenuItem("智能翻译", "trans.png");
		JMenu select = new JMenu("选择");
		select.setIcon(UiUtils.getIcon("select.png"));

		BaseMenuItem selectAll = new BaseMenuItem("选择全部", "select_all.png");
		BaseMenuItem selectLine = new BaseMenuItem("选择选择", "select_line.png");
		select.add(selectAll);
		select.add(selectLine);

		undo.setId("undo");
		undo.addActionListener(this);

		redo.setId("redo");
		redo.addActionListener(this);

		copy.setId("copy");
		copy.addActionListener(this);

		cut.setId("cut");
		cut.addActionListener(this);

		paste.setId("paste");
		paste.addActionListener(this);

		selectAll.setId("selectAll");
		selectAll.addActionListener(this);

		selectLine.setId("selectLine");
		selectLine.addActionListener(this);

		toUcase.setId("toUcase");
		toUcase.addActionListener(this);

		toLcase.setId("toLcase");
		toLcase.addActionListener(this);

		trans.setId("trans");
		trans.addActionListener(this);

		popMenu.add(undo);
		popMenu.add(redo);
		popMenu.add(copy);
		popMenu.add(cut);
		popMenu.add(paste);
		popMenu.add(select);
		popMenu.addSeparator();
		popMenu.add(toUcase);
		popMenu.add(toLcase);
		popMenu.addSeparator();
		popMenu.add(trans);
		setComponentPopupMenu(popMenu);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 绘制光标行高�?
		try {
			if (!hasSelectText()) {
				Rectangle rect = modelToView(getCaretPosition());
				if (rect != null) {
					g.setColor(new Color(176, 196, 222, 30));
					g.fillRect(getBorder().getBorderInsets(this).left, rect.y, getWidth(), rect.height);
				}
			}

		} catch (Exception ex) {
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			// 解决焦点丢失后不能高亮�?�取的问�?
			// 左键点击移除高亮
			getHighlighter().removeAllHighlights();
		}
		// 右键菜单
		else if (e.getSource() instanceof BaseEditor && e.getButton() == MouseEvent.BUTTON3) {
			for (Component it : popMenu.getComponents()) {
				if (it instanceof BaseMenuItem) {
					// 对支持的操作进行筛�??
					BaseMenuItem bit = (BaseMenuItem) it;
					switch (bit.getId()) {
					case "copy":
					case "cut":
					case "toUcase":
					case "toLcase":
					case "trans":
						if (hasSelectText()) {
							bit.setEnabled(true);
						} else {
							bit.setEnabled(false);
						}
						break;
					case "undo":
						if (canUndo()) {
							bit.setEnabled(true);
						} else {
							bit.setEnabled(false);
						}
						break;
					case "redo":
						if (canRedo()) {
							bit.setEnabled(true);
						} else {
							bit.setEnabled(false);
						}
						break;
					}
				}
			}

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (((BaseMenuItem) arg0.getSource()).getId()) {
		case "undo":
			undo();
			break;
		case "redo":
			redo();
			break;
		case "copy":
			copy();
			break;
		case "cut":
			cut();
			break;
		case "paste":
			paste();
			break;
		case "selectAll":
			selectAll();
			break;
		case "selectLine":
			int line = getLineOfOffset(getCaretPosition());
			int selectionStart = getLineStartOffset(line);
			int selectionEnd = getLineEndOffset(line);
			if (selectionStart != -1 && selectionEnd != -1) {
				select(selectionStart, selectionEnd);
			}
			break;
		case "toUcase":
			replaceSelection(getSelectedText().toUpperCase());
			break;
		case "toLcase":
			replaceSelection(getSelectedText().toLowerCase());
			break;
		case "trans":
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {

						String result = Utils.translate(getSelectedText());
						final JTextField input = new JTextField(result);
						Object[] message = { "翻译结果:", input };
						final JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
						pane.setIcon(UiUtils.getIcon("youdao.png"));
						JDialog dialog = pane.createDialog(BaseEditor.this, "有道翻译");
						dialog.setIconImage(UiUtils.getIcon("trans.png").getImage());
						dialog.setVisible(true);
					} catch (Exception e) {
						UiUtils.alertW(BaseEditor.this, "提示", "翻译失败�?" + e);
					}
				}
			}).start();

			break;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// 重绘行高亮，必须用这个监�?
		repaint();
		if (editorListener != null)
			editorListener.onCaretChange(e);

	}

	@Override
	public String getSelectedText() {
		String text = super.getSelectedText();
		return text == null ? "" : text;
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		// 焦点丢失 依然高亮选择的文�?
		// 默认会丢失高亮？不过选区域还�?
		getHighlighter().highlightSelection();

	}

	// 自定义回�?
	public interface EditorListener {
		void onCaretChange(ChangeEvent e);

		void onTextChange(DocumentEvent e);
	}

}
