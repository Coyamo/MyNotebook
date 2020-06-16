package coyamo.notebook.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;

import coyamo.notebook.control.MenuEditEvent;
import coyamo.notebook.control.MenuFileEvent;
import coyamo.notebook.ui.editor.TextEditor;

public class BaseToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = -3158333861250398912L;
	private BaseMenuBar menuBar;

	public void setMenuBar(BaseMenuBar menuBar) {
		this.menuBar = menuBar;

	}

	public BaseToolBar() {
		super("������");

		ToolBarButton newFile = new ToolBarButton("new_file.gif", "�½�");
		newFile.setId("newFile");
		newFile.addActionListener(this);
		add(newFile);

		ToolBarButton open = new ToolBarButton("open_file.png", "��");
		open.setId("open");
		open.addActionListener(this);
		add(open);

		ToolBarButton save = new ToolBarButton("save.gif", "����");
		save.setId("save");
		save.addActionListener(this);
		add(save);

		ToolBarButton saveAll = new ToolBarButton("save_all.gif", "����ȫ��");
		saveAll.setId("saveAll");
		saveAll.addActionListener(this);
		add(saveAll);

		ToolBarButton close = new ToolBarButton("close.png", "�ر�");
		close.setId("close");
		close.addActionListener(this);
		add(close);

		ToolBarButton closeAll = new ToolBarButton("close_all.png", "ȫ���ر�");
		closeAll.setId("closeAll");
		closeAll.addActionListener(this);
		add(closeAll);

		addSeparator();

		ToolBarButton cut = new ToolBarButton("cut.gif", "����");
		cut.setId("cut");
		cut.addActionListener(this);
		add(cut);

		ToolBarButton copy = new ToolBarButton("copy.gif", "����");
		copy.setId("copy");
		copy.addActionListener(this);
		add(copy);

		ToolBarButton paste = new ToolBarButton("paste.gif", "ճ��");
		paste.setId("paste");
		paste.addActionListener(this);
		add(paste);

		addSeparator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuFileEvent fileEvent = (MenuFileEvent) menuBar.findChildById("file").getEvent();
		MenuEditEvent editEvent = (MenuEditEvent) menuBar.findChildById("edit").getEvent();
		TextEditor editor = menuBar.getPager().getSelectedEditor();
		switch (((ToolBarButton) e.getSource()).getId()) {
		case "newFile":
			fileEvent.doNewFile();
			break;
		case "open":
			fileEvent.doOpen();
			break;
		case "save":
			fileEvent.doSave(editor);
			break;
		case "saveAll":
			fileEvent.doSaveAll();
			break;
		case "close":
			fileEvent.doClose();
			break;
		case "closeAll":
			fileEvent.doCloseAll();
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

		}
	}
}
