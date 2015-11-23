eval(""+load('global.js'));
faceTo(1);

if(RPG.getFlag("1-1-shrine")!=null ){
	RPG.ctrl.hero.walk(2);
	
	var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
	CG.push(black);
	black.addAction(Actions.sequence(Actions.fadeOut(.5),Actions.run(function(){
		CG.dispose(black);
	})));
	RPG.ctrl.hero.show = false;
	setKeyLocker(true);
	setRenderAble(true);
	showMSG(MsgType.莲子);
	say("嗯……就是这里了么","莲子");
	setCameraPositionWithHero(0, -80, true);
	showMSG(MsgType.梅莉);
	say("这里……总感觉……","梅莉");
	say("等等，门上……似乎有什么东西？","梅莉");
	setCameraPositionWithHero(0, -60, false);
	MoveController.offsetActor.addAction(Actions.scaleTo(0.8,0.8,1,Interpolation.pow4Out));
	waitCameraMove();
	showMSG(MsgType.莲子);
	say("是一张符咒？","莲子");
	say("这张符咒，看起来很旧了，这个神社到底荒废了多久呢","莲子");
	showMSG(MsgType.梅莉);
	say("贴在门上感觉像是要封印什么东西一样……","梅莉");
	say("想要调查神社里面的话，难道说就要撕掉这个符咒么……\n感觉很不祥啊……","梅莉");
	hideMSG();
	setCameraPositionWithHero(0, 0, true);
	pause(20);
	playSE("tear");
	pause(40);
	faceTo(getNPC("6"),4);
	pause(70);
	Hero.turn(RPGObject.FACE_D);
	pause(30);
	showMSG(MsgType.莲子);
	setBalloon(6,BalloonType.惊讶);
	say("诶诶诶诶诶！！","莲子");
	setCameraPositionWithHero(0, -90, true);
	setBalloon(6,BalloonType.惊讶);
	say("有栖你下手太快了吧！万一真的发生了什么怎么办！","莲子");
	MoveController.offsetActor.addAction(Actions.scaleTo(1,1,1,Interpolation.pow4Out));
	faceTo(getNPC("6"),4);
	setKeyLocker(false);
}else{	
	playSE("rain",true);
}
setRenderAble(true);

removeSelf();
end();
