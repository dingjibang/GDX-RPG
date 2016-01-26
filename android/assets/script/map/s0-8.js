eval(""+load('global.js'));
PostUtil.showMenu=false;

var black = $(Res.getSync(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
black.loaded = function(){
	black.addAction(Actions.sequence(Actions.run(function(){setRenderAble(true)}),Actions.fadeOut(0.5),Actions.run(function(){CG.disposeAll()})));	
};
CG.push(black);

var yuki = getNPC("16");
yuki.enableCollide = false;
npc.enableCollide = false;

if(RPG.getFlag("1-1-shrine-gen") == null){
	toggleAllHero(true);
	yuki.setVisible(false);
	npc.setVisible(false);
	RPG.ctrl.hero.remove(7);
	PostUtil.showMenu=true;
	removeSelf();
	end();
}else{
	setKeyLocker(true);
	toggleAllHero(false);
	
	MoveController.offsetActor.addAction(Actions.scaleTo(0.5,0.5,1,Interpolation.pow4Out));
	
	yuki.turn(RPGObject.FACE_R);
	pause(50);
	faceTo(RPGObject.FACE_U);
	pause(40);
	faceTo(RPGObject.FACE_D);
	pause(10);
	faceTo(RPGObject.FACE_U);
	pause(40);
	faceTo(RPGObject.FACE_D);
	pause(10);
	faceTo(RPGObject.FACE_U);
	pause(20);
	setBalloon(BalloonType.惊讶);
	showMSG(MsgType.莲子);
	say("……疼疼疼疼疼","莲子");
	faceTo(7);
	pause(15);
	faceTo(10);
	pause(20);
	faceTo(7);
	pause(15);
	faceTo(6);
	pause(15);
	faceTo(7);
	pause(15);
	faceTo(6);
	setBalloon(BalloonType.惊讶);
	pause(15);
	faceTo(7);
	say("这里是……","莲子");
	say("……","莲子");
	say("我记得我们是被怪物袭击了，然后有栖使用了那个符咒……","莲子");
	setBalloon(BalloonType.惊讶);
	say("大家都怎么样了！","莲子");
	yuki.walk(5).testWalk();
	pause(30);
	MoveController.offsetActor.addAction(Actions.scaleTo(1,1,1,Interpolation.pow4Out));
	pause(30);
	setBalloon(BalloonType.惊讶);
	say("有栖同学！","莲子");
	say("你没事吧","莲子");
	hideMSG();
	move(1);
	yuki.walk(2).testWalk();
	pause(60);
	showMSG(MsgType.莲子);
	say("看起没什么事，太好了……","莲子");
	say("这里到底是什么地方呢，看起来很荒芜，不像是住处的附近……而且梅莉也不见了","莲子");
	say("按照常识来讲在原地等待救援比较好，可是我放心不下梅莉","莲子");
	hideMSG();
	pause(40);
	showMSG(MsgType.莲子);
	say("有栖同学也是这么想的？那就在附近探索一下吧，沿途做好记号，顺便找找食物和水源","莲子");
	hideMSG();
	
	var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).setAlpha(0).getItem();
	CG.push(black);
	black.addAction(Actions.fadeIn(.5));
	pause(60);
	
	RPG.setFlag("1-1-shrine-gen",true);
	setKeyLocker(false);
	
	teleport("11/outside1.tmx",15,58,3);
	
	removeSelf();
	end();
}