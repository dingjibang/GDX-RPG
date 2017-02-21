package script.ui.view;

/**
 * 只有系统菜单才可继承这个接口，用来做为辨识
 */
public abstract class MenuableView extends UIView {
	public MenuView parent;
	
	public MenuableView() {
		buuleable(true);
	}
	
	/**当窗口被创建、被还原时调用一次，可以不用强制覆盖此方法*/
	public void onResume(){}
}
