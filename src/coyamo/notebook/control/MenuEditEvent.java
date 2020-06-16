package coyamo.notebook.control;

import coyamo.notebook.ui.BaseMenuBar;
import coyamo.notebook.ui.editor.BaseEditor;
import coyamo.notebook.ui.editor.TextEditorPager;

/**
 * ���༭���˵�����ش���
 * 
 */
public class MenuEditEvent {
	private TextEditorPager pager;

	public MenuEditEvent(BaseMenuBar bar) {
		pager = bar.getPager();
	}

	/**
	 * ����
	 */
	public void doUndo() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.undo();
	}

	/**
	 * �ָ�
	 */
	public void doRedo() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.redo();
	}

	/**
	 * ����
	 */
	public void doCut() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.cut();
	}

	/**
	 * ճ��
	 */
	public void doPaste() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.paste();
	}

	/**
	 * ȫѡ
	 */
	public void doSelectAll() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.selectAll();
	}

	/**
	 * ɾ��ѡ�������
	 */
	public void doDelete() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.replaceSelection("");
	}

	/**
	 * ����ѡ�������
	 */
	public void doCopy() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.copy();
	}
}
