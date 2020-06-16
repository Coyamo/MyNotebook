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

				// ��ʼ��ȫ��UI��ʽ
				UiUtils.initUI();

				// ����������
				final BaseFrame baseFrame = new BaseFrame("MyNotebook");
				baseFrame.setIconImage(UiUtils.getIcon("icon.png").getImage());

				// ���ò���
				BorderLayout layout = new BorderLayout();
				baseFrame.setLayout(layout);

				// ����˵���
				final BaseMenuBar menuBar = new BaseMenuBar(baseFrame);

				BaseToolBar toolBar = new BaseToolBar();
				toolBar.setMenuBar(menuBar);

				// ����༭������
				final TextEditorPager pager = new TextEditorPager(baseFrame);

				// �˵���������
				menuBar.setPager(pager);

				// ����ײ��༭��״̬��
				EditorStateBar stateBar = new EditorStateBar();

				// �༭���������״̬��
				pager.setEditorStateBar(stateBar);

				// ���ó���ر�ҳ��ļ���
				pager.setCloseListener(new OnCloseListener() {
					@Override
					public void onClose(TextEditor content) {
						// ������رյı༭�������иı�
						if (content.isTextEdit()) {
							// �õ�menu����
							// ���ò˵�����Ӧ�߼�
							BaseMenu menu = menuBar.findChildById("file");
							if (menu != null) {
								int result = UiUtils.alertC(baseFrame, "��ʾ", "�ļ����޸ģ��Ƿ񱣴棿");
								switch (result) {
								case 0:
									// �ļ��˵��¼��߼�
									MenuFileEvent event = (MenuFileEvent) menu.getEvent();
									boolean isSaved = event.doSave(content);
									if (isSaved) {
										pager.remove(content);
									} else {
										UiUtils.alertW(baseFrame, "��ʾ", "��ȡ���˱��棡");
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

				// �������ӵ�������
				baseFrame.getContentPane().add(pager, BorderLayout.CENTER);
				baseFrame.getContentPane().add(stateBar, BorderLayout.SOUTH);
				baseFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
				baseFrame.setJMenuBar(menuBar);
				baseFrame.pack();

				// ���Ĭ�ϱ༭�����
				// ������pack()�󣬷����ܻ�ȡ����
				pager.addDefaultTab();

				baseFrame.setVisible(true);
				if (SettingManager.getBoolean("dailyTip", true))
					new DailyTipDialog(baseFrame, false).showDailyTip();

				
			}
		});
	}

}
