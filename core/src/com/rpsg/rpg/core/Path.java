
package com.rpsg.rpg.core;


/**
 * GDX-RPG 资源路径
 */
public class Path {
	//基础路径
	public static final String BASE_PATH = "";
	//图片路径
	public static final String IMAGE_BASE_PATH = "images";

	public static final String IMAGE_BACKGROUND = BASE_PATH + IMAGE_BASE_PATH + "/background/";
	public static final String IMAGE_LOGO = BASE_PATH + IMAGE_BASE_PATH + "/logo/";
	public static final String IMAGE_LOAD = BASE_PATH + IMAGE_BASE_PATH + "/load/";
	public static final String IMAGE_TITLE = BASE_PATH + IMAGE_BASE_PATH + "/title/";
	public static final String IMAGE_FG = BASE_PATH + IMAGE_BASE_PATH + "/fg/";
	public static final String IMAGE_ANIMATION = BASE_PATH + IMAGE_BASE_PATH + "/animation/";
	public static final String IMAGE_GLOBAL = BASE_PATH + IMAGE_BASE_PATH + "/global/";

	public static final String MUSIC_BGM = BASE_PATH + "sound/bgm/";
	public static final String MUSIC_SE = BASE_PATH + "sound/se/";
	public static final String MAP = BASE_PATH + "maps/";

	public static final String IMAGE_MENU = BASE_PATH + IMAGE_BASE_PATH + "/menu/";
	public static final String IMAGE_CG = BASE_PATH + IMAGE_BASE_PATH + "/cg/";
	public static final String IMAGE_ENEMY = BASE_PATH + IMAGE_BASE_PATH + "/enemy/";
	public static final String IMAGE_BATTLE = BASE_PATH + IMAGE_BASE_PATH + "/battle/";

	// Menu images/menu/
	public static final String IMAGE_MENU_GLOBAL = IMAGE_MENU + "global/";
	public static final String IMAGE_MENU_MAP = IMAGE_MENU + "map/";
	public static final String IMAGE_MENU_EQUIP = IMAGE_MENU + "nequip/";
	public static final String IMAGE_MENU_SC = IMAGE_MENU + "sc/";
	public static final String IMAGE_MENU_ITEM = IMAGE_MENU + "nitem/";
	public static final String IMAGE_MENU_STATUS = IMAGE_MENU + "nstatus/";
	public static final String IMAGE_MENU_SYSTEM = IMAGE_MENU + "nsystem/";
	public static final String IMAGE_MENU_TACTIC = IMAGE_MENU + "tactic/";
	public static final String IMAGE_MENU_TASK = IMAGE_MENU + "task/";

	public static final String MESSAGE = BASE_PATH + IMAGE_BASE_PATH + "/message/";
	public static final String WALK = BASE_PATH + IMAGE_BASE_PATH + "/walk/";
	public static final String SHADER = BASE_PATH + "shader/";
	public static final String IMAGE_ICONS = BASE_PATH + IMAGE_BASE_PATH + "/icons/";
	public static final String PARTICLE = BASE_PATH + "particle/";
	
	//script
	public static final String SCRIPT = BASE_PATH + "script";
	public static final String SCRIPT_MAP = SCRIPT + "/map/";
	public static final String SCRIPT_DATA = SCRIPT +"/data/";
	public static final String SCRIPT_DATA_ITEM = SCRIPT_DATA + "/item/";
	public static final String SCRIPT_DATA_ENEMY = SCRIPT_DATA + "/enemy/";
	public static final String SCRIPT_DATA_ASSOCIATION = SCRIPT_DATA + "/association/";
	public static final String SCRIPT_DATA_ASSOCIATION_SKILL = SCRIPT_DATA_ASSOCIATION + "skill/";
	public static final String SCRIPT_DATA_HERO = SCRIPT_DATA + "/hero/";
	public static final String SCRIPT_DATA_BUFF = SCRIPT_DATA + "/buff/";
	public static final String SCRIPT_SYSTEM = SCRIPT + "/system/";
	public static final String SCRIPT_DATA_TASK = SCRIPT_DATA + "/task/";
	public static final String SCRIPT_DATA_ACHIEVEMENT = SCRIPT_DATA + "/achievement/";
	public static final String SCRIPT_DATA_INDEX = SCRIPT_DATA + "/index/";

	// 存档最大页数
	public static final int SAVE_FILE_MAX_PAGE = 20;

	//存储目录
	public static final String SAVE = "save/";

	// 系统菜单文字显示速度默认显示文字
	public static final String MENU_SYSTEM_TEST_MESSAGE = "人类为何要互相伤害呢？";

	//RPSG服务器
	public static final String NET_LOGIN_URL = "http://rpsgvote.sinaapp.com/lyric.php";
}