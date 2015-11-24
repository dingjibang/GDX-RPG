eval(""+load('global.js'));
faceTo(1);
var renko = getNPC("19");
var mari = getNPC("17");
var monster = getNPC("21");

renko.setVisible(false);
mari.setVisible(false);
monster.setVisible(false);
monster.enableCollide = false;

if(RPG.getFlag("1-1-shrine")!=null){
	RPG.ctrl.hero.walk(2);
	renko.setVisible(true);
	mari.setVisible(true);
	
	renko.turn(RPGObject.FACE_U).walk(1).testWalk();
	mari.turn(RPGObject.FACE_U).walk(1).testWalk();
	
	var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
	CG.push(black);
	black.addAction(Actions.sequence(Actions.fadeOut(.5),Actions.run(function(){
		CG.dispose(black);
	})));
	RPG.ctrl.hero.show = false;
	setKeyLocker(true);
	setRenderAble(true);
	
	
	
	pause(110);
	showMSG(MsgType.莲子);
	say("嗯……就是这里了么","莲子");
	hideMSG();
	setCameraPositionWithHero(0, -80, true);
	mari.turn(RPGObject.FACE_U);
	pause(60);
	showMSG(MsgType.梅莉);
	mari.setBalloon(BalloonType.惊讶);
	say("等等，门上……似乎有什么东西？","梅莉");
	hideMSG();
	setCameraPositionWithHero(0, -60, false);
	MoveController.offsetActor.addAction(Actions.scaleTo(0.8,0.8,1,Interpolation.pow4Out));
	pause(30);
	showMSG(MsgType.莲子);
	say("是……一张符咒？","莲子");
	renko.turn(RPGObject.FACE_L);
	say("这张符咒，看起来很旧了，这个神社到底荒废了多久呢","莲子");
	mari.turn(RPGObject.FACE_R);
	renko.turn(RPGObject.FACE_U);
	showMSG(MsgType.梅莉);
	say("贴在门上感觉像是要封印什么东西一样……","梅莉");
	say("想要调查神社里面的话，难道说就要撕掉这个符咒么……\n感觉很不祥啊……","梅莉");
	hideMSG();
	mari.turn(RPGObject.FACE_U);
	RPG.ctrl.hero.walk(1);
	setCameraPositionWithHero(0, 0, true);
	pause(20);
	playSE("tear");
	pause(40);
	faceTo(getNPC("6"),4);
	pause(70);
	Hero.turn(RPGObject.FACE_D);
	RPG.ctrl.hero.walk(1);
	pause(30);
	
	showMSG(MsgType.莲子);
	renko.setBalloon(BalloonType.惊讶);
	say("诶诶诶诶诶！！","莲子");
	setCameraPositionWithHero(0, -90, false);
	renko.setBalloon(BalloonType.惊讶);
	say("有栖你下手太快了吧！万一真的发生了什么怎么办！","莲子");
	
	mari.turn(RPGObject.FACE_R);
	showMSG(MsgType.莲子);
	
	showMSG(MsgType.梅莉);
	mari.setBalloon(BalloonType.混乱);
	say("嘛，那种科学无法解释的事情不会","梅莉");
	
	
	RPG.ctrl.hero.walk(-1);
	renko.turn(RPGObject.FACE_U).setBalloon(BalloonType.惊讶);
	mari.turn(RPGObject.FACE_U).setBalloon(BalloonType.惊讶);
	Hero.turn(RPGObject.FACE_U).setBalloon(BalloonType.惊讶);
	playSE("opendoor");
	faceTo(10);
	monster.setColor(0,0,0,1);
	monster.addAction(Actions.color(new Color(1,1,1,1),2));
	monster.setVisible(true);
	//TODO let the monster hide
	setCameraPositionWithHero(0, 30, false);
	monster.walk(1).testWalk();
	
	RPG.maps.loader.getLight(50).light.setDistance(0);
	RPG.maps.loader.getLight(51).light.setDistance(0);
	pause(40);
	say("……发生的","梅莉");
	hideMSG();
	setKeyLocker(false);
	
}else{	
	playSE("rain",true);
}
setRenderAble(true);

removeSelf();
end();
