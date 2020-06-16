package coyamo.notebook.control;

import coyamo.notebook.ui.BaseMenuBar;
import coyamo.notebook.ui.editor.BaseEditor;
import coyamo.notebook.ui.editor.TextEditorPager;

/**
 * “编辑”菜单的相关处理
 * 
 */
public class MenuEditEvent {
	private TextEditorPager pager;

	public MenuEditEvent(BaseMenuBar bar) {
		pager = bar.getPager();
	}

	/**
	 * 撤消
	 */
	public void doUndo() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.undo();
	}

	/**
	 * 恢复
	 */
	public void doRedo() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.redo();
	}

	/**
	 * 剪切
	 */
	public void doCut() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.cut();
	}

	/**
	 * 粘贴
	 */
	public void doPaste() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.paste();
	}

	/**
	 * 全选
	 */
	public void doSelectAll() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.selectAll();
	}

	/**
	 * 删除选择的文字
	 */
	public void doDelete() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.replaceSelection("");
	}

	/**
	 * 复制选择的文字
	 */
	public void doCopy() {
		BaseEditor editor = pager.getSelectedEditor().getEditor();
		editor.copy();
	}
}
