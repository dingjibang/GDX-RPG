eval(""+load('global.js'));

if(RPG.getFlag("1-1-inner")==null){
	RPG.setFlag("1-1-inner",true);
	Hero.setVisible(true);
	Hero.turn(RPGObject.FACE_U);
	var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
	black.addAction(Actions.sequence(Actions.fadeOut(0.3),Actions.run(function(){CG.dispose(black)})));	
	CG.push(black);
	
	PostUtil.showMenu=false;
	setKeyLocker(true);
	faceTo(RPGObject.FACE_D);
	setRenderAble(true);
	pause(50);
	showMSG(MsgType.莲子);
	say("这里就是我家啦，当然今天开始也是结成同学家","莲子");
	say("到晚上还有一点时间，结成同学可以随意转转\n二楼最里面是你的房间，已经给你准备了一些道具和食物，自己去拿就好了","莲子");
	say("准备好了就来找我出发吧，当然有什么问题也是可以问我的哦！","莲子");
	hideMSG();
	pause(20);
	CG.push(black);	
	var tip1=$(Res.get(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc1.png":"tip_mobile1.png")));
	var tip2=$(Res.get(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc2.png":"tip_mobile2.png")));

	tip1 = tip1.setColor(new Color(1,1,1,0)).setPosition((GameUtil.screen_width/2-793/2),(GameUtil.screen_height/2-500/2)).getItem().oranCenter();
	tip1.addAction(Actions.color(new Color(1,1,1,1),0.3));
	CG.push(tip1);
	say("   ");
	tip1.addAction(Actions.sequence(Actions.fadeOut(0.3),Actions.run(function(){CG.dispose(tip1)})));
	pause(30);

	tip2 = tip2.setColor(new Color(1,1,1,0)).setPosition((GameUtil.screen_width/2-793/2), (GameUtil.screen_height/2-500/2)).getItem().oranCenter();
	tip2.addAction(Actions.color(new Color(1,1,1,1),0.3));
	CG.push(tip2);
	say("   ");
	tip2.addAction(Actions.sequence(Actions.fadeOut(0.3),Actions.run(function(){CG.dispose(tip2)})));
	
	setKeyLocker(false);				
}else{
	setRenderAble(true);
}
removeSelf();
end();
