package coyamo.notebook;

import java.awt.BorderLayout;

import javax.swing.SwingUtilities;

import coyamo.notebook.control.MenuFileEvent;
import coyamo.notebook.ui.BaseFrame;
import coyamo.notebook.ui.BaseMenu;
import coyamo.notebook.ui.BaseMenuBar;
import coyamo.notebook.ui.BaseToolBar;
import coyamo.notebook.ui.dialog.DailyTipDialog;
import coyamo.notebook.ui.editor.EditorStateBar;
import coyamo.notebook.ui.editor.TextEditor;
import coyamo.notebook.ui.editor.TextEditorPager;
import coyamo.notebook.ui.editor.TextEditorPager.OnCloseListener;
import coyamo.notebook.util.SettingManager;
import coyamo.notebook.util.UiUtils;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// 初始化全局UI样式
				UiUtils.initUI();

				// 定义主窗口
				final BaseFrame baseFrame = new BaseFrame("MyNotebook");
				baseFrame.setIconImage(UiUtils.getIcon("icon.png").getImage());

				// 设置布局
				BorderLayout layout = new BorderLayout();
				baseFrame.setLayout(layout);

				// 定义菜单栏
				final BaseMenuBar menuBar = new BaseMenuBar(baseFrame);

				BaseToolBar toolBar = new BaseToolBar();
				toolBar.setMenuBar(menuBar);

				// 定义编辑器抽屉
				final TextEditorPager pager = new TextEditorPager(baseFrame);

				// 菜单关联抽屉
				menuBar.setPager(pager);

				// 定义底部编辑器状态栏
				EditorStateBar stateBar = new EditorStateBar();

				// 编辑器抽屉关联状态栏
				pager.setEditorStateBar(stateBar);

				// 设置抽屉关闭页面的监听
				pager.setCloseListener(new OnCloseListener() {
					@Override
					public void onClose(TextEditor content) {
						// 如果被关闭的编辑框内容有改变
						if (content.isTextEdit()) {
							// 得到menu对象
							// 调用菜单的相应逻辑
							BaseMenu menu = menuBar.findChildById("file");
							if (menu != null) {
								int result = UiUtils.alertC(baseFrame, "提示", "文件已修改，是否保存？");
								switch (result) {
								case 0:
									// 文件菜单事件逻辑
									MenuFileEvent event = (MenuFileEvent) menu.getEvent();
									boolean isSaved = event.doSave(content);
									if (isSaved) {
										pager.remove(content);
									} else {
										UiUtils.alertW(baseFrame, "提示", "您取消了保存！");
									}
									break;
								case 1:
									pager.remove(content);
									break;
								default:
									break;
								}
							}
						} else {
							pager.remove(content);
						}

					}
				});

				// 把组件添加到主窗口
				baseFrame.getContentPane().add(pager, BorderLayout.CENTER);
				baseFrame.getContentPane().add(stateBar, BorderLayout.SOUTH);
				baseFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
				baseFrame.setJMenuBar(menuBar);
				baseFrame.pack();

				// 添加默认编辑框抽屉
				// 必须在pack()后，否则不能获取焦点
				pager.addDefaultTab();

				baseFrame.setVisible(true);
				if (SettingManager.getBoolean("dailyTip", true))
					new DailyTipDialog(baseFrame, false).showDailyTip();

				
			}
		});
	}

}
