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
 * ���ļ����˵�����ش���
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
	 * �½��ļ�
	 */
	public void doNewFile() {
		pager.addDefaultTab();
	}

	/**
	 * ���ļ�
	 */
	public void doOpen() {
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.addChoosableFileFilter(new FileFilter() {
			// ����֧�ֵ��ļ�����
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
		int flag = jfc.showDialog(host, "��");
		if (flag == JFileChooser.APPROVE_OPTION) {
			pager.addTabFromFile(jfc.getSelectedFile().getPath());
		}
	}

	/**
	 * �ر��ļ�
	 */
	public void doClose() {
		int index = pager.getSelectedIndex();
		if (index != -1) {
			TextEditor editor = pager.getSelectedEditor();
			if (editor.isTextEdit()) {
				int result = UiUtils.alertC(host, "��ʾ", "�ļ����޸ģ��Ƿ񱣴棿");
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
	 * �ر����г���
	 */
	public void doCloseAll() {

		for (int i = 0; i < pager.getTabCount(); i++) {
			TextEditor editor = (TextEditor) pager.getComponentAt(i);
			if (editor.isTextEdit()) {
				int result = UiUtils.alertC(host, "��ʾ", "������δ������ļ����Ƿ������");
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
	 * ���������ļ�
	 */
	public void doSaveAll() {
		for (int i = 0; i < pager.getTabCount(); i++) {
			TextEditor editor = (TextEditor) pager.getComponentAt(i);
			doSave(editor);
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param editor ��ǰҪ����༭������
	 * @return �Ƿ�ɹ�����
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
	 * ���Ϊ
	 * 
	 * @param editor ��ǰҪ����༭������
	 * @return �Ƿ�ɹ�����
	 */
	public boolean doSaveAs(TextEditor editor) {
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileView(new FileView() {
			public Icon getIcon(File f) {
				return jfc.getFileSystemView().getSystemIcon(f);
			}
		});
		String defaultName = pager.getName().contains(".") ? pager.getName() : pager.getName() + ".txt";
		jfc.setSelectedFile(new File(defaultName));// ����Ĭ���ļ���
		int flag = jfc.showDialog(host, "���Ϊ");
		if (flag == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();

			if (f.exists()) {
				int result = UiUtils.alertC(host, "����", "�ļ��Ѿ����ڣ��Ƿ񸲸ǣ�");
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
