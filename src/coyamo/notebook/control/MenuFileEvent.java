package coyamo.notebook.control;

import java.io.File;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

import coyamo.notebook.ui.BaseMenuBar;
import coyamo.notebook.ui.editor.TextEditor;
import coyamo.notebook.ui.editor.TextEditorPager;
import coyamo.notebook.util.FileUtils;
import coyamo.notebook.util.UiUtils;

/**
 * 
 * “文件”菜单的相关处理
 * 
 */
public class MenuFileEvent {
	private TextEditorPager pager;
	private JFrame host;

	public MenuFileEvent(BaseMenuBar bar) {
		pager = bar.getPager();
		host = bar.getHost();
	}

	/**
	 * 新建文件
	 */
	public void doNewFile() {
		pager.addDefaultTab();
	}

	/**
	 * 打开文件
	 */
	public void doOpen() {
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.addChoosableFileFilter(new FileFilter() {
			// 期望支持的文件类型
			String accpetFileType[] = { ".txt", ".html", ".htm", ".c", ".cpp", ".h", ".java", ".js" };

			@Override
			public String getDescription() {
				String des = "";
				for (String type : accpetFileType) {
					des += "*" + type + ";";
				}
				return des;
			}

			@Override
			public boolean accept(File f) {
				if (f.isFile()) {
					for (String type : accpetFileType) {
						if (f.getName().toLowerCase().endsWith(type))
							return true;
					}
				}
				return false;
			}
		});
		jfc.setFileView(new FileView() {
			public Icon getIcon(File f) {
				return jfc.getFileSystemView().getSystemIcon(f);
			}
		});
		int flag = jfc.showDialog(host, "打开");
		if (flag == JFileChooser.APPROVE_OPTION) {
			pager.addTabFromFile(jfc.getSelectedFile().getPath());
		}
	}

	/**
	 * 关闭文件
	 */
	public void doClose() {
		int index = pager.getSelectedIndex();
		if (index != -1) {
			TextEditor editor = pager.getSelectedEditor();
			if (editor.isTextEdit()) {
				int result = UiUtils.alertC(host, "提示", "文件已修改，是否保存？");
				switch (result) {
				case 0:
					doSave(editor);
					break;
				case 1:
					pager.remove(index);
					break;
				default:
					break;
				}
			} else {
				pager.remove(index);
			}
		}

	}

	/**
	 * 关闭所有抽屉
	 */
	public void doCloseAll() {

		for (int i = 0; i < pager.getTabCount(); i++) {
			TextEditor editor = (TextEditor) pager.getComponentAt(i);
			if (editor.isTextEdit()) {
				int result = UiUtils.alertC(host, "提示", "您还有未保存的文件，是否继续？");
				switch (result) {
				case 0:
					pager.removeAll();
					return;
				default:
					return;
				}
			}
		}
		pager.removeAll();
	}

	/**
	 * 保存所有文件
	 */
	public void doSaveAll() {
		for (int i = 0; i < pager.getTabCount(); i++) {
			TextEditor editor = (TextEditor) pager.getComponentAt(i);
			doSave(editor);
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param editor 当前要处理编辑器对象
	 * @return 是否成功保存
	 */
	public boolean doSave(TextEditor editor) {
		if (editor != null) {
			if (editor.isTempFile()) {
				return doSaveAs(editor);
			} else {
				FileUtils.write(editor.getEditor().getText(), editor.getFilePath());
				editor.setTextEdit(false);
				return true;
			}
		}
		return false;
	}

	/**
	 * 另存为
	 * 
	 * @param editor 当前要处理编辑器对象
	 * @return 是否成功保存
	 */
	public boolean doSaveAs(TextEditor editor) {
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileView(new FileView() {
			public Icon getIcon(File f) {
				return jfc.getFileSystemView().getSystemIcon(f);
			}
		});
		String defaultName = pager.getName().contains(".") ? pager.getName() : pager.getName() + ".txt";
		jfc.setSelectedFile(new File(defaultName));// 设置默认文件名
		int flag = jfc.showDialog(host, "另存为");
		if (flag == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();

			if (f.exists()) {
				int result = UiUtils.alertC(host, "警告", "文件已经存在，是否覆盖？");
				if (result == 0) {
					FileUtils.write(editor.getEditor().getText(), f.getAbsolutePath());
					if (editor.isTempFile())
						editor.setFilePath(f.getAbsolutePath());
					editor.setTextEdit(false);
					return true;
				}
			} else {
				FileUtils.write(editor.getEditor().getText(), f.getAbsolutePath());
				if (editor.isTempFile())
					editor.setFilePath(f.getAbsolutePath());
				editor.setTextEdit(false);
				return true;
			}

		}
		return false;
	}

}
