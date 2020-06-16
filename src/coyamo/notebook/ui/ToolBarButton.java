package coyamo.notebook.ui;

import javax.swing.JButton;

import coyamo.notebook.control.Idable;
import coyamo.notebook.util.UiUtils;

public class ToolBarButton extends JButton implements Idable<Object> {
	private static final long serialVersionUID = -5099835997987760441L;
	private String id;

	public ToolBarButton(String icon, String tip) {
		super(UiUtils.getIcon(icon));
		setToolTipText(tip);
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
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}

}
