package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.MsgUtil;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	
	@Override
	public void init() {
		MsgUtil.MSG(GameViews.batch, "�Ҷ��ڽ��䣬����ȴ������һ��","����֮��", 22,this);
		MsgUtil.MSG(GameViews.batch, "����Ϊ��Ҫ�����˺��أ�\n���ѵ���Ҫ����ս��ô��","Java", 22,this);
	}
}
