package coyamo.notebook.ui.editor;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import coyamo.notebook.util.FileUtils;
import coyamo.notebook.util.UiUtils;

/**
 * JTabbedPane�ķ�װ����չʾ�༭��
 */
public class TextEditorPager extends JTabbedPane {

	private static final long serialVersionUID = 3171581575837898980L;
	private JFrame host;
	private EditorStateBar stateBar;
	private OnCloseListener closeListener;

	public void setCloseListener(OnCloseListener closeListener) {
		this.closeListener = closeListener;
	}

	public TextEditorPager(JFrame host) {
		super(TOP, SCROLL_TAB_LAYOUT);
		this.host = host;
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				TextEditor select = getSelectedEditor();
				if (select != null) {
					// ѡ��ı�����host��title
					setHostTitle(select.getFilePath() + " - MyNotebook");
					// �༭���ȡ����
					select.getEditor().requestFocus();
					// ˢ�µ�ǰ״̬��
					select.refreshStateBar();

				} else {
					// ���Ϊ���򴴽�Ĭ��tab����
					addDefaultTab();
				}
			}
		});

	}

	public void setEditorStateBar(EditorStateBar stateBar) {
		this.stateBar = stateBar;
	}

	public TextEditor addDefaultTab() {
		TextEditor editor = new TextEditor("δ����");
		editor.setStateBar(stateBar);
		insertTab("δ����233", null, editor, "δ����", 0);
		final TabComponent tab = newTabComponent("δ����", UiUtils.getIcon("file_blank.gif"), editor);
		setTabComponentAt(0, tab);
		setSelectedComponent(editor);
		editor.setOnNewPathListener(new TextEditor.OnNewPathListener() {

			@Override
			public void onNewPath(String newpath) {
				File f = new File(newpath);
				tab.setTitle(f.getName());
				tab.setIcon(getIconByFile(f));
				tab.setToolTipText(newpath);
				setHostTitle(newpath + " - MyNotebook");
			}
		});
		return editor;
	}

	public TextEditor getSelectedEditor() {
		return (TextEditor) getSelectedComponent();
	}

	/**
	 * ���ļ�����һ���༭��tab
	 * 
	 * @param filePath
	 * @return TextEditor
	 */
	public TextEditor addTabFromFile(String filePath) {
		TextEditor editor = findEditorByPath(filePath);

		if (editor != null) {
			setSelectedComponent(editor);
			return editor;
		}

		File file = new File(filePath);
		editor = new TextEditor(filePath);

		editor.setStateBar(stateBar);

		insertTab(file.getName(), null, editor, filePath, 0);
		final TabComponent tab = newTabComponent(file.getName(), getIconByFile(file), editor);
		setTabComponentAt(0, tab);
		editor.getEditor().setText(FileUtils.read(filePath));
		editor.setFilePath(filePath);
		setSelectedComponent(editor);

		editor.setOnNewPathListener(new TextEditor.OnNewPathListener() {

			@Override
			public void onNewPath(String newpath) {

				File f = new File(newpath);
				tab.setTitle(f.getName());
				tab.setIcon(getIconByFile(f));
				tab.setToolTipText(newpath);
				setHostTitle(newpath + " - MyNotebook");
			}
		});
		return editor;
	}

	/**
	 * �����Ѿ���ӵĳ��룬ͨ�����·�����ҳ���
	 * 
	 * @param filePath
	 * @return
	 */
	private TextEditor findEditorByPath(String filePath) {
		for (Component c : getComponents()) {
			if (c instanceof TextEditor) {
				TextEditor editor = (TextEditor) c;
				if (editor.getFilePath().equals(filePath))
					return editor;
			}
		}
		return null;

	}

	/**
	 * ͨ���ļ���չ��ѡ������չʾ���ļ�ͼ��
	 * 
	 * @param file
	 * @return ImageIcon
	 */
	private ImageIcon getIconByFile(File file) {
		if (file.isFile()) {
			String name = file.getName().toLowerCase();
			if (name.endsWith(".txt")) {
				return UiUtils.getIcon("file_txt.png");
			} else {
				return UiUtils.getIcon("file_blank.gif");
			}
		}
		return null;
	}

	public void setTitleHost(JFrame host) {
		this.host = host;
	}

	public void setHostTitle(String title) {
		if (host != null)
			host.setTitle(title);
	}

	public JFrame getTitleHost() {
		return host;
	}

	@Override
	public String getName() {
		return getCurTabComponent().getTitle();

	}

	@Override
	public void setName(String name) {
		getCurTabComponent().setTitle(name);
	}

	/**
	 * ��ȡ��ǰѡ��ĳ��������
	 * 
	 * @return TabComponent
	 */
	public TabComponent getCurTabComponent() {
		return ((TabComponent) getTabComponentAt(getSelectedIndex()));
	}

	private TabComponent newTabComponent(String name, ImageIcon img, TextEditor content) {
		return new TabComponent(name, img, content);

	}

	/**
	 * 
	 * �Զ��������
	 * 
	 */
	class TabComponent extends JPanel {

		private static final long serialVersionUID = -4385066981190036924L;
		JLabel icon, title, close, sign;

		TabComponent(String name, ImageIcon img, final TextEditor content) {
			setOpaque(false);
			setLayout(new FlowLayout());
			setBorder(new EmptyBorder(-5, 0, -5, 0));
			icon = new JLabel(img);
			icon.setOpaque(false);
			sign = new JLabel("*");
			sign.setOpaque(false);
			showSign(false);
			title = new JLabel(name);
			title.setOpaque(false);
			close = new JLabel(UiUtils.getIcon("close_tab.gif"));
			close.setOpaque(false);
			add(icon);
			add(sign);
			add(title);
			add(close);
			close.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (closeListener != null) {
						closeListener.onClose(content);
					} else {
						content.getParent().remove(content);
					}

				}
			});
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					((TextEditorPager) content.getParent()).setSelectedComponent(content);
				}
			});
		}

		public String getTitle() {
			return title.getText();
		}

		public void setTitle(String title) {
			this.title.setText(title);
		}

		public void setIcon(ImageIcon icon) {
			this.icon.setIcon(icon);
		}

		public void showSign(boolean b) {
			if (b)
				sign.setVisible(true);
			else
				sign.setVisible(false);

		}
	}

	/**
	 * 
	 * �ر��ļ��Ļص�
	 * 
	 */
	public interface OnCloseListener {
		void onClose(TextEditor content);
	}

}
