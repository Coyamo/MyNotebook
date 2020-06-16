package coyamo.notebook.ui;

import javax.swing.JMenuItem;

import coyamo.notebook.control.Idable;
import coyamo.notebook.util.UiUtils;

/**
 * JMenuItemµÄ·â×°
 */
public class BaseMenuItem extends JMenuItem implements Idable<Object> {

	private static final long serialVersionUID = 6168297025726740903L;
	private String id;

	public BaseMenuItem(String text, String icon) {
		super(text, UiUtils.getIcon(icon));
	}

	public BaseMenuItem(String text) {
		super(text);
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
	public Object findChildById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChild() {
		// TODO Auto-generated method stub
		return false;
	}

}
