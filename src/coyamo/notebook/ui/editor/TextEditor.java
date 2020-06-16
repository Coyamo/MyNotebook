package coyamo.notebook.ui.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FontMetrics;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;

import coyamo.notebook.ui.editor.BaseEditor.EditorListener;

/**
 * 
 * 对BaseEditor的封装
 * 
 */
public class TextEditor extends JScrollPane {

	private String filePath = "";
	private static final long serialVersionUID = -1898256371317213363L;
	private JPanel wrapper = new JPanel(new BorderLayout());
	private BaseEditor editor = new BaseEditor();
	private OnNewPathListener listener;
	private EditorStateBar stateBar;
	private boolean isTextEdit = false;

	public TextEditor() {
		this("");
	}

	public TextEditor(String filePath) {
		setFilePath(filePath);
		wrapper.add(editor, BorderLayout.CENTER);
		setViewportView(wrapper);
		getVerticalScrollBar().setUnitIncrement(50);
		editor.setEditorListener(new EditorListener() {

			@Override
			public void onTextChange(DocumentEvent e) {
				refreshStateBar();
				setTextEdit(true);
			}

			@Override
			public void onCaretChange(ChangeEvent e) {
				refreshStateBar();

			}
		});
	}

	public boolean isTextEdit() {
		return isTextEdit;
	}

	public void setTextEdit(boolean b) {
		isTextEdit = b;
		Component c = getParent();
		if (c instanceof TextEditorPager) {
			((TextEditorPager) c).getCurTabComponent().showSign(isTextEdit);
		}
	}

	/**
	 * 更新底部状态栏的信息
	 */
	public void refreshStateBar() {
		if (stateBar != null) {
			stateBar.setLines(editor.getLineCount() + "");
			stateBar.setLength(editor.getLength() + "");
			stateBar.setLn(editor.getSelectLine() + 1 + "");
			stateBar.setCol(editor.getCaretPosition() - editor.getLineStartOffset(editor.getSelectLine()) + 1 + "");
		}
	}

	public EditorStateBar getStateBar() {
		return stateBar;
	}

	public void setStateBar(EditorStateBar stateBar) {
		this.stateBar = stateBar;
	}

	/**
	 * 获取包装的BaseEditor
	 * 
	 * @return BaseEditor
	 */
	public BaseEditor getEditor() {
		return editor;
	}

	/**
	 * 是否是临时文件，即没有保存过的文件。
	 * 
	 * @return
	 */
	public boolean isTempFile() {
		File cur = new File(filePath);
		return !(cur.exists() && cur.isFile());
	}

	public void gotoLine(int line) {
		if (editor.gotoLine(line)) {
			FontMetrics fm = editor.getFontMetrics(editor.getStyledDocument().getFont(editor.getInputAttributes()));
			int fontHeight = fm.getHeight();
			int selectLine = editor.getSelectLine() + 1;
			if (editor.isLineVisible(line - 1)) {
				getVerticalScrollBar().setValue(line * fontHeight);
			} else if (selectLine > line) {
				getVerticalScrollBar().setValue((line - 1) * fontHeight);
			} else {
				getVerticalScrollBar().setValue((line + 1) * fontHeight);
			}

		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		if (listener != null)
			listener.onNewPath(filePath);

	}

	public void setOnNewPathListener(OnNewPathListener listener) {
		this.listener = listener;
	}

	/**
	 * 
	 * 设置新路径的回调，用于设置了新路径后更新标题栏和tab栏文字
	 * 
	 */
	public interface OnNewPathListener {
		void onNewPath(String newpath);
	}

}
