package coyamo.notebook.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import coyamo.notebook.control.Idable;
import coyamo.notebook.control.MenuEditEvent;
import coyamo.notebook.control.MenuFileEvent;
import coyamo.notebook.control.MenuSeachEvent;
import coyamo.notebook.ui.dialog.AboutDialog;
import coyamo.notebook.ui.dialog.DailyTipDialog;
import coyamo.notebook.ui.dialog.SettingDialog;
import coyamo.notebook.ui.editor.TextEditor;
import coyamo.notebook.ui.editor.TextEditorPager;

/**
 * JMenuBar�ķ�װ
 */
public class BaseMenuBar extends JMenuBar implements ActionListener, Idable<BaseMenu> {
	private static final long serialVersionUID = -6673109486418374846L;

	private TextEditorPager pager;
	private JFrame host;
	private MenuSeachEvent searchEvent;
	private MenuFileEvent filehEvent;
	private MenuEditEvent editEvent;
	private String id;

	private BaseMenu fileMenu, editMenu;

	public BaseMenuBar(JFrame host) {
		super();
		this.host = host;
		initFileMenu();
		initEditMenu();
		initSearchMenu();
		initSettingMenu();
		initHelpMenu();
	}

	/**
	 * ��ȡ������JFrame
	 * 
	 * @return JFrame
	 */
	public JFrame getHost() {
		return host;
	}

	/**
	 * ����������JFrame
	 * 
	 * @param host JFrame
	 * 
	 */
	public void setHost(JFrame host) {
		this.host = host;
	}

	private void initSettingMenu() {
		BaseMenu SettingMenu = new BaseMenu("���� (E)");
		SettingMenu.setMnemonic('E');
		SettingMenu.setId("setting");
		BaseMenuItem themeSetting = new BaseMenuItem("����", "theme_setting.png");

		BaseMenuItem otherSetting = new BaseMenuItem("����", "other_setting.png");

		themeSetting.addActionListener(this);
		themeSetting.setId("themeSetting");

		otherSetting.addActionListener(this);
		otherSetting.setId("otherSetting");

		SettingMenu.add(themeSetting);
		SettingMenu.add(otherSetting);

		add(SettingMenu);
	}

	private void initHelpMenu() {
		BaseMenu helpMenu = new BaseMenu("���� (H)");
		helpMenu.setMnemonic('H');
		helpMenu.setId("help");
		BaseMenuItem about = new BaseMenuItem("����", "about.gif");
		BaseMenuItem tip = new BaseMenuItem("ÿ��һ��", "tip.png");

		about.addActionListener(this);
		about.setId("about");

		tip.addActionListener(this);
		tip.setId("tip");

		helpMenu.add(about);
		helpMenu.add(tip);
		add(helpMenu);
	}

	/**
	 * ��ʼ�����༭���˵�
	 */
	private void initEditMenu() {
		editMenu = new BaseMenu("�༭ (E)");
		editMenu.setMnemonic('E');
		editMenu.setId("edit");
		BaseMenuItem undo = new BaseMenuItem("����", "undo.gif");
		BaseMenuItem redo = new BaseMenuItem("�ָ�", "redo.gif");
		BaseMenuItem cut = new BaseMenuItem("����", "cut.gif");
		BaseMenuItem copy = new BaseMenuItem("����", "copy.gif");
		BaseMenuItem paste = new BaseMenuItem("ճ��", "paste.gif");
		BaseMenuItem delete = new BaseMenuItem("ɾ��", "delete.gif");
		BaseMenuItem selectAll = new BaseMenuItem("ȫѡ", "select_all.png");

		undo.addActionListener(this);
		undo.setId("undo");

		redo.addActionListener(this);
		redo.setId("redo");

		cut.addActionListener(this);
		cut.setId("cut");

		copy.addActionListener(this);
		copy.setId("copy");

		paste.addActionListener(this);
		paste.setId("paste");

		delete.addActionListener(this);
		delete.setId("delete");

		selectAll.addActionListener(this);
		selectAll.setId("selectAll");

		editMenu.add(undo);
		editMenu.add(redo);
		editMenu.addSeparator();
		editMenu.add(cut);
		editMenu.add(copy);
		editMenu.add(paste);
		editMenu.add(delete);
		editMenu.add(selectAll);
		add(editMenu);
	}

	/**
	 * ��ʼ�����������˵�
	 */
	private void initSearchMenu() {
		BaseMenu searchMenu = new BaseMenu("���� (S)");
		searchMenu.setMnemonic('S');

		BaseMenuItem find = new BaseMenuItem("����...", "find.gif");
		BaseMenuItem replace = new BaseMenuItem("�滻...", "replace.png");
		BaseMenuItem gotoLine = new BaseMenuItem("��ת", "goto.gif");

		find.addActionListener(this);
		find.setId("find");

		replace.addActionListener(this);
		replace.setId("replace");

		gotoLine.addActionListener(this);
		gotoLine.setId("gotoLine");

		searchMenu.add(find);
		searchMenu.add(replace);
		searchMenu.addSeparator();
		searchMenu.add(gotoLine);

		add(searchMenu);
	}

	/**
	 * ��ʼ���ļ��˵�
	 */
	private void initFileMenu() {
		fileMenu = new BaseMenu("�ļ� (F)");
		fileMenu.setId("file");
		fileMenu.setMnemonic('F');
		BaseMenuItem newFile = new BaseMenuItem("�½�", "new_file.gif");
		BaseMenuItem open = new BaseMenuItem("��", "open_file.png");
		BaseMenuItem close = new BaseMenuItem("�ر�", "close.png");
		BaseMenuItem closeAll = new BaseMenuItem("�ر�����", "close_all.png");
		BaseMenuItem save = new BaseMenuItem("����", "save.gif");
		BaseMenuItem saveAll = new BaseMenuItem("��������", "save_all.gif");
		BaseMenuItem saveAs = new BaseMenuItem("���Ϊ...", "save_as.gif");
		BaseMenuItem exit = new BaseMenuItem("�˳�", "exit.png");

		newFile.addActionListener(this);
		newFile.setId("newFile");

		open.addActionListener(this);
		open.setId("open");

		close.addActionListener(this);
		close.setId("close");

		closeAll.addActionListener(this);
		closeAll.setId("closeAll");

		save.addActionListener(this);
		save.setId("save");

		saveAll.addActionListener(this);
		saveAll.setId("saveAll");

		saveAs.addActionListener(this);
		saveAs.setId("saveAs");

		exit.addActionListener(this);
		exit.setId("exit");

		fileMenu.add(newFile);
		fileMenu.add(open);
		fileMenu.add(close);
		fileMenu.add(closeAll);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(saveAll);
		fileMenu.add(saveAs);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		add(fileMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (pager == null)
			return;
		TextEditor editor = pager.getSelectedEditor();
		switch (((BaseMenuItem) e.getSource()).getId()) {
		case "newFile":
			filehEvent.doNewFile();
			break;
		case "open":
			filehEvent.doOpen();
			break;
		case "close":
			filehEvent.doClose();
			break;
		case "closeAll":
			filehEvent.doCloseAll();
			break;
		case "save":
			filehEvent.doSave(editor);
			break;
		case "saveAll":
			filehEvent.doSaveAll();
			break;
		case "saveAs":
			filehEvent.doSaveAs(editor);
			break;
		case "exit":
			System.exit(0);
			break;
		// �����˵���
		case "find":
			searchEvent.dofind(editor);
			break;
		case "replace":
			searchEvent.doReplace(editor);
			break;
		case "gotoLine":
			searchEvent.doGotoLine(editor);
			break;
		// ��݋�˵���
		case "undo":
			editEvent.doUndo();
			break;
		case "redo":
			editEvent.doRedo();
			break;
		case "cut":
			editEvent.doCut();
			break;
		case "copy":
			editEvent.doCopy();
			break;
		case "paste":
			editEvent.doPaste();
			break;
		case "delete":
			editEvent.doDelete();
			break;
		case "selectAll":
			editEvent.doSelectAll();
			break;
		case "about":
			new AboutDialog(host).setVisible(true);
			break;
		case "tip":
			new DailyTipDialog(host, true).showDailyTip();
			break;
		case "themeSetting":
			new SettingDialog(host, 0).setVisible(true);
			break;
		case "otherSetting":
			new SettingDialog(host, 1).setVisible(true);
			break;
		}
	}

	/**
	 * ��ȡ������ TextEditorPager
	 * 
	 * @return TextEditorPager
	 */
	public TextEditorPager getPager() {
		return pager;
	}

	/**
	 * ���ù����� TextEditorPager
	 * 
	 * @param pager TextEditorPager
	 */
	public void setPager(TextEditorPager pager) {
		this.pager = pager;
		// ��ʼ���Ͱ󶨿����߼�
		searchEvent = new MenuSeachEvent(this);

		filehEvent = new MenuFileEvent(this);
		fileMenu.setEvent(filehEvent);

		editEvent = new MenuEditEvent(this);
		editMenu.setEvent(editEvent);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public BaseMenu findChildById(String id) {
		BaseMenu menu;
		for (Component c : getComponents()) {
			if (c instanceof BaseMenu) {
				menu = (BaseMenu) c;
				if (menu.getId().equals(id)) {
					return menu;
				}
			}
		}
		return null;
	}

	@Override
	public boolean hasChild() {
		return true;
	}

}
