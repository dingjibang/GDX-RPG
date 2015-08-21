eval(""+load('global.js'));

var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
CG.push(black);
PostUtil.showMenu=false;
playSE("fire.mp3");
pause(230);
playSE("YS070523.wav");


var cg = $(Res.get(Setting.IMAGE_CG+"flash.png")).setColor(1,1,1,0).getItem();
cg.addAction(Actions.sequence(Actions.color(new Color(1,0.7,0,0.8),0.02),Actions.fadeOut(0.1),Actions.run(function(){
	CG.dispose(cg);
})));
CG.push(cg);

pause(79);

var cg = $(Res.get(Setting.IMAGE_CG+"flash.png")).setColor(1,1,1,0).setScale(3.8).setPosition(-800,-300).getItem();
cg.addAction(Actions.sequence(Actions.color(new Color(1,0.7,0,0.8),0.02),Actions.fadeOut(0.1),Actions.run(function(){
	CG.dispose(cg);
})));
CG.push(cg);

pause(83);
pause(130);

playSE("woodwave.wav");

pause(300);

showMenu(false);

showMSG(MsgType.电脑);
pause(10);
say("哈哈哈哈，是我赢了！","？？？");
say("八云紫！","？？？");
hideMSG();
pause(60);

showMSG(MsgType.电脑);
say("好好看看你自己人生最后的样子吧！","？？？");
hideMSG();
pause(80);

showMSG(MsgType.紫);
say("这句话，","八云紫");
say("我想原封不动地还给你才最恰当呢。","八云紫");
hideMSG();

var cg = $(Res.get(Setting.UI_BASE_IMG)).setColor(1,0,0,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
cg.addAction(Actions.sequence(Actions.color(new Color(1,0,0,1),0.02),Actions.fadeOut(0.15),Actions.run(function(){
	CG.dispose(cg);
})));
CG.push(cg);

stopAllSE(0.01);

playSE("attack.wav");
pause(65);
showMSG(MsgType.电脑);
say("什么！？","？？？",35);
hideMSG();
playSE("TornadoText3.mp3");
setSEVolume(0, 0.13);
pause(30);

var y11=$(Res.get(Setting.IMAGE_CG+"y11cg.jpg")).setColor(1,1,1,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
y11.addAction(Actions.sequence(Actions.color(new Color(1,1,1,1),0.5,Interpolation.pow4In)));
CG.push(y11);

pause(200);
showMSG(MsgType.电脑);
say("为什么！！！","？？？");
hideMSG();
pause(30);
showMSG(MsgType.电脑);
say("这不可能！！！","？？？");
hideMSG();
pause(140);
showMSG(MsgType.电脑);
say("难道说……","？？？");
hideMSG();
pause(120);
showMSG(MsgType.电脑);
say("原来如此……","？？？");
hideMSG();
pause(30);
showMSG(MsgType.电脑);
say("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈！！！","？？？");
hideMSG();


y11.setOrigin(Align.center);
y11.clearActions();
var del=0,flength=0;
y11.addAction(Actions.repeat(RepeatAction.FOREVER,Actions.addAction(new Action(){
	act:function(delta) {
		if(del++==850){
			del=0;
			flength++;
		}
		y11.addAction(Actions.moveTo(MathUtils.random(-flength,flength), MathUtils.random(-flength,flength),1));
		return false;
	}
})));
y11.addAction(Actions.scaleTo(2,2,8,Interpolation.pow4In));
var mask=$(Res.get(Setting.UI_BASE_IMG)).setColor(1,0,0,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
mask.addAction(Actions.color(new Color(1,0,0,1),6,Interpolation.pow5In));
CG.push(mask);
setSEVolume(4.1, 1);
playSE("fx1.wav");
pause(50);
CG.dispose(mask);
CG.dispose(y11);
y11.clearActions();

stopAllSE(0,"fx1.wav");
pause(180);
CG.disposeAll();
teleport("subway",18,1,1);

removeSelf();
end();