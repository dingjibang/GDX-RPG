eval("" + load('global.js'));
faceTo(1);
var renko = getNPC("19");
var mari = getNPC("17");
var monster = getNPC("21");


renko.setVisible(false);
renko.enableCollide = false;
mari.setVisible(false);
mari.enableCollide = false;
monster.setVisible(false);
monster.enableCollide = false;

if (RPG.getFlag("1-1-shrine") != null) {
	RPG.ctrl.hero.walk(2);
	renko.setVisible(true);
	mari.setVisible(true);

	renko.turn(RPGObject.FACE_U).walk(1).testWalk();
	mari.turn(RPGObject.FACE_U).walk(1).testWalk();

	var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
	CG.push(black);
	black.addAction(Actions.sequence(Actions.fadeOut(.5), Actions.run(function() {
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
	mari.turn(RPGObject.FACE_R);
	showMSG(MsgType.梅莉);
	say("总感觉……这里有种不好的气氛","梅莉");
	mari.turn(RPGObject.FACE_U);
	pause(60);
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
	RPG.toast.add("获得道具\n[SKY]破旧的符咒[]", Color.RED, 22, false, Res.fuckOPENGL(Setting.IMAGE_ICONS + "i1.png"));
	pause(70);
	Hero.turn(RPGObject.FACE_D);
	RPG.ctrl.hero.walk(1);
	pause(30);
	
	setCameraPositionWithHero(0, -90, false);
	showMSG(MsgType.梅莉);
	mari.setBalloon(BalloonType.惊讶);
	say("诶诶诶诶诶！！","梅莉");
	renko.setBalloon(BalloonType.惊讶);
	say("有栖你下手太快了吧！万一真的发生了什么怎么办！","梅莉");
	
	mari.turn(RPGObject.FACE_R);
	showMSG(MsgType.莲子);
	renko.setBalloon(BalloonType.混乱);
	say("嘛，那种科学无法解释的事情不会发生的","莲子");
	mari.turn(RPGObject.FACE_U).setBalloon(BalloonType.惊讶).walk(-1).testWalk();
	renko.turn(RPGObject.FACE_U).setBalloon(BalloonType.惊讶).walk(-1).testWalk();
	Hero.turn(RPGObject.FACE_U).setBalloon(BalloonType.惊讶).walk(-2).testWalk();
	playSE("opendoor");
	faceTo(10);
<<<<<<< HEAD
	monster.setColor(0,0,0,1);
	monster.setVisible(true);
	//TODO let the monster hide
	setCameraPositionWithHero(0, 30, false);
	monster.walk(1).testWalk();
=======
>>>>>>> e423046948d7df7f62ecd3c232687086fb226923
	
	setCameraPositionWithHero(0, 30, false);
	RPG.maps.loader.getLight(50).light.setDistance(0);
	RPG.maps.loader.getLight(51).light.setDistance(0);
	
	pause(40);
<<<<<<< HEAD
	say("……发生的","梅莉");
	//monster.addAction(Actions.color(new Color(1,1,1,1),2));
=======
	showMSG(MsgType.莲子);
	monster.setColor(0, 0, 0, 1);
	//monster.addAction(Actions.color(new Color(1,1,1,1),2));
	pause(30);
	monster.setVisible(true);
	monster.setWalkSpeed(1);
	monster.walk(1).testWalk();
	mari.setBalloon(BalloonType.惊讶);
	renko.setBalloon(BalloonType.惊讶);
	Hero.setBalloon(BalloonType.惊讶);
	pause(80);
	//TODO let the monster hide
	setCameraPositionWithHero(0, -50, false);
	mari.turn(RPGObject.FACE_R);
	showMSG(MsgType.莲子);
	say("喂喂喂喂喂……", "莲子");
	showMSG(MsgType.梅莉);
	say("莲子你个乌鸦嘴！", "梅莉");
	renko.turn(RPGObject.FACE_L);
	showMSG(MsgType.莲子);
	say("诶……嘿嘿", "莲子");
	mari.turn(RPGObject.FACE_U);
	renko.turn(RPGObject.FACE_U);
	showMSG(MsgType.梅莉);
	say("别傻笑了！不知道之前准备的那些武器对这些怪物有没有用……要是能争取到逃跑的时间就好了", "梅莉");
	showMSG(MsgType.莲子);
	say("精心准备的道具这么快就派上用场了，不知道是幸运还是不幸啊", "莲子");
	mari.turn(RPGObject.FACE_U);
	showMSG(MsgType.梅莉);
	say("别分神，准备战斗！", "梅莉");
	
	setCameraPositionWithHero(0, 0, false);
	monster.walk(1).testWalk();
	Hero.walk(-1).testWalk();
	mari.walk(-1).testWalk();
	renko.walk(-1).testWalk();
	pause(30);
	monster.walk(1).testWalk();
	Hero.walk(-1).testWalk();
	mari.walk(-1).testWalk();
	renko.walk(-1).testWalk();
	pause(30);
	showMSG(MsgType.梅莉);
	say("哈……哈……完全没有效果", "梅莉");
	showMSG(MsgType.莲子);
	say("怎么会……虽然都是些不难搞到的素材，但也是我精心改造调制的……威力已经可以媲美常规的军队武器了……", "莲子");
	say("束手无策了吗……", "莲子");
	showMSG(MsgType.梅莉);
	mari.setBalloon(BalloonType.惊讶);
	say("不……还有一个办法", "梅莉");
	
	mari.turn(RPGObject.FACE_R);
	showMSG(MsgType.梅莉);
	say("有栖，用符咒！既然符咒能封印住这些怪物，那应该会有效果才对！", "梅莉");
	renko.turn(RPGObject.FACE_L);
	showMSG(MsgType.莲子);
	say("对了，还有那个符咒，有栖快试试看！", "莲子");
>>>>>>> e423046948d7df7f62ecd3c232687086fb226923
	hideMSG();
	
	pause(30);
	
	MoveController.offsetActor.addAction(Actions.scaleTo(0.7,0.7,1,Interpolation.pow4Out));
	Hero.walk(1).testWalk();
	renko.turn(RPGObject.FACE_U);
	mari.turn(RPGObject.FACE_U);
	
	pause(30);
	
	
	var animation = RPG.ctrl.animation.add(1);
	animation.setPosition(455,1700);
	animation.layer = 3;
	animation.setColor(1,1,1,0);
	animation.addAction(Actions.fadeIn(1));
	
	var lightID = RPG.maps.loader.addLight(99,552,1786,0);
	var light = RPG.maps.loader.getLight(lightID);
	
	var white = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(1,1,1,0).getItem();
	
	var proxy = $(Res.get(Setting.UI_BASE_IMG)).setColor(1,1,1,0).setSize(50,50).getItem();
	
	CG.push(proxy);
	
	proxy.addAction(Actions.repeat(350,Actions.run(function(){
		light.light.setDistance(proxy.getWidth()*2+Math.random() * 50);	
	})));
	
	proxy.addAction(Actions.sizeTo(700,700,2,Interpolation.pow4In));
	playSE("mg1");
	pause(50);
	white.addAction(Actions.fadeIn(1,Interpolation.pow4));
	CG.push(white);
	
	pause(60);
	
	proxy.clear();
	RPG.maps.loader.removeLight(lightID);
	
	animation.remove();
	monster.setVisible(false);
	
	white.addAction(Actions.sequence(Actions.fadeOut(.5),Actions.run(function(){
		CG.dispose(white);
		CG.dispose(proxy);
	})));
	
	
	setKeyLocker(false);

} else {
	playSE("rain", true);
}
setRenderAble(true);

removeSelf();
end();
