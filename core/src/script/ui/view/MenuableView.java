package script.ui.view;

/**
 * 只有系统菜单才可继承这个接口，用来做为辨识
 */
public abstract class MenuableView extends UIView {
	public MenuView parent;
	
	public MenuableView() {
		buuleable(true);
	}
}
