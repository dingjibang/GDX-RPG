eval(""+load('global.js'));

var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
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


removeSelf();
end();