package coyamo.notebook.ui;

import java.awt.Component;

import javax.swing.JMenu;

import coyamo.notebook.control.Idable;

/**
 * 
 * JMenu的封装
 * 
 */
public class BaseMenu extends JMenu implements Idable<BaseMenuItem> {

	private static final long serialVersionUID = -9134752524325536266L;
	private String id;
	// 保存控制逻辑
	private Object event;

	public BaseMenu(String text) {
		super(text);
	}

	/**
	 * 获取控制逻辑
	 * 
	 * @return 对应的控制类
	 */
	public Object getEvent() {
		return event;
	}

	/**
	 * 设置控制逻辑
	 * 
	 * @param event 对应的控制类
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
			// 不一定子组件都是需要的类型
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
