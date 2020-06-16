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
 * JMenuBar的封装
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
	 * 获取依赖的JFrame
	 * 
	 * @return JFrame
	 */
	public JFrame getHost() {
		return host;
	}

	/**
	 * 设置依赖的JFrame
	 * 
	 * @param host JFrame
	 * 
	 */
	public void setHost(JFrame host) {
		this.host = host;
	}

	private void initSettingMenu() {
		BaseMenu SettingMenu = new BaseMenu("设置 (E)");
		SettingMenu.setMnemonic('E');
		SettingMenu.setId("setting");
		BaseMenuItem themeSetting = new BaseMenuItem("主题", "theme_setting.png");

		BaseMenuItem otherSetting = new BaseMenuItem("其他", "other_setting.png");

		themeSetting.addActionListener(this);
		themeSetting.setId("themeSetting");

		otherSetting.addActionListener(this);
		otherSetting.setId("otherSetting");

		SettingMenu.add(themeSetting);
		SettingMenu.add(otherSetting);

		add(SettingMenu);
	}

	private void initHelpMenu() {
		BaseMenu helpMenu = new BaseMenu("帮助 (H)");
		helpMenu.setMnemonic('H');
		helpMenu.setId("help");
		BaseMenuItem about = new BaseMenuItem("关于", "about.gif");
		BaseMenuItem tip = new BaseMenuItem("每日一言", "tip.png");

		about.addActionListener(this);
		about.setId("about");

		tip.addActionListener(this);
		tip.setId("tip");

		helpMenu.add(about);
		helpMenu.add(tip);
		add(helpMenu);
	}

	/**
	 * 初始化“编辑”菜单
	 */
	private void initEditMenu() {
		editMenu = new BaseMenu("编辑 (E)");
		editMenu.setMnemonic('E');
		editMenu.setId("edit");
		BaseMenuItem undo = new BaseMenuItem("撤消", "undo.gif");
		BaseMenuItem redo = new BaseMenuItem("恢复", "redo.gif");
		BaseMenuItem cut = new BaseMenuItem("剪切", "cut.gif");
		BaseMenuItem copy = new BaseMenuItem("复制", "copy.gif");
		BaseMenuItem paste = new BaseMenuItem("粘贴", "paste.gif");
		BaseMenuItem delete = new BaseMenuItem("删除", "delete.gif");
		BaseMenuItem selectAll = new BaseMenuItem("全选", "select_all.png");

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
	 * 初始化“搜索”菜单
	 */
	private void initSearchMenu() {
		BaseMenu searchMenu = new BaseMenu("搜索 (S)");
		searchMenu.setMnemonic('S');

		BaseMenuItem find = new BaseMenuItem("查找...", "find.gif");
		BaseMenuItem replace = new BaseMenuItem("替换...", "replace.png");
		BaseMenuItem gotoLine = new BaseMenuItem("跳转", "goto.gif");

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
	 * 初始化文件菜单
	 */
	private void initFileMenu() {
		fileMenu = new BaseMenu("文件 (F)");
		fileMenu.setId("file");
		fileMenu.setMnemonic('F');
		BaseMenuItem newFile = new BaseMenuItem("新建", "new_file.gif");
		BaseMenuItem open = new BaseMenuItem("打开", "open_file.png");
		BaseMenuItem close = new BaseMenuItem("关闭", "close.png");
		BaseMenuItem closeAll = new BaseMenuItem("关闭所有", "close_all.png");
		BaseMenuItem save = new BaseMenuItem("保存", "save.gif");
		BaseMenuItem saveAll = new BaseMenuItem("保存所有", "save_all.gif");
		BaseMenuItem saveAs = new BaseMenuItem("另存为...", "save_as.gif");
		BaseMenuItem exit = new BaseMenuItem("退出", "exit.png");

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
		// 搜索菜单项
		case "find":
			searchEvent.dofind(editor);
			break;
		case "replace":
			searchEvent.doReplace(editor);
			break;
		case "gotoLine":
			searchEvent.doGotoLine(editor);
			break;
		// 編輯菜单栏
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
	 * 获取关联的 TextEditorPager
	 * 
	 * @return TextEditorPager
	 */
	public TextEditorPager getPager() {
		return pager;
	}

	/**
	 * 设置关联的 TextEditorPager
	 * 
	 * @param pager TextEditorPager
	 */
	public void setPager(TextEditorPager pager) {
		this.pager = pager;
		// 初始化和绑定控制逻辑
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
