package coyamo.notebook.ui;

import java.awt.Component;

import javax.swing.JMenu;

import coyamo.notebook.control.Idable;

/**
 * 
 * JMenu�ķ�װ
 * 
 */
public class BaseMenu extends JMenu implements Idable<BaseMenuItem> {

	private static final long serialVersionUID = -9134752524325536266L;
	private String id;
	// ��������߼�
	private Object event;

	public BaseMenu(String text) {
		super(text);
	}

	/**
	 * ��ȡ�����߼�
	 * 
	 * @return ��Ӧ�Ŀ�����
	 */
	public Object getEvent() {
		return event;
	}

	/**
	 * ���ÿ����߼�
	 * 
	 * @param event ��Ӧ�Ŀ�����
	 */
	public void setEvent(Object event) {
		this.event = event;
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
	public BaseMenuItem findChildById(String id) {
		BaseMenuItem item;
		for (Component c : getMenuComponents()) {
			// ��һ�������������Ҫ������
			if (c instanceof BaseMenuItem) {
				item = (BaseMenuItem) c;
				if (item.getId().equals(id)) {
					return item;
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
