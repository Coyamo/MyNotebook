package coyamo.notebook.control;

/**
 * 用于实现GUI组件的快速获取子组件对象
 * 
 * @param <T> 用于findChildById的子对象类型
 */
public interface Idable<T> {
	public String getId();

	public void setId(String id);

	public T findChildById(String id);

	public boolean hasChild();
}
