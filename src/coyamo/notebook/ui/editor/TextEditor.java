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
 * ��BaseEditor�ķ�װ
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
	 * ���µײ�״̬������Ϣ
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
	 * ��ȡ��װ��BaseEditor
	 * 
	 * @return BaseEditor
	 */
	public BaseEditor getEditor() {
		return editor;
	}

	/**
	 * �Ƿ�����ʱ�ļ�����û�б�������ļ���
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
	 * ������·���Ļص���������������·������±�������tab������
	 * 
	 */
	public interface OnNewPathListener {
		void onNewPath(String newpath);
	}

}
