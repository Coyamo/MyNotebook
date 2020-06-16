package coyamo.notebook.control;

/**
 * ����ʵ��GUI����Ŀ��ٻ�ȡ���������
 * 
 * @param <T> ����findChildById���Ӷ�������
 */
public interface Idable<T> {
	public String getId();

	public void setId(String id);

	public T findChildById(String id);

	public boolean hasChild();
}
