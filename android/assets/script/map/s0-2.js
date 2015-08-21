eval(""+load('global.js'));
Hero.setVisible(false);
PostUtil.showMenu=false;
setKeyLocker(true);
var yuki=getNPC("1");
setBalloon(yuki, BalloonType.沉默);

select("不知道怎么决定","跟莲子与梅丽一同前往博丽神社");

if(currentSelect("不知道怎么决定")){
	showMSG(MsgType.莲子);
	say("诶，自己不能决定吗","莲子");
	say("那就抛硬币试试吧，命运的抉择交给命运决定！\n正面就一起来，背面的话结成同学就在家等我们好了","莲子");
	hideMSG();
	playSE("coinfalling.mp3");
	pause(250);
	showMSG(MsgType.莲子);
	say("唔，是正面呢","莲子");
	say("那么结成同学就一起来吧","莲子");
	showMSG(MsgType.梅莉);
	say("到时候可能会出现无法预料的情况，我们也无法顾及结成同学的安全，请你照顾好自己哦！ ","梅莉");
}else{
	showMSG(MsgType.莲子);
	say("不愧是自己找到密封俱乐部的人啊，果然要一起来吗","莲子");
	showMSG(MsgType.梅莉);
	say("不过出了危险的话，我们也不能保证保护你的安全","梅莉");
	say("到时候不要后悔哦","梅莉");
}

showMSG(MsgType.莲子);
say("那么，距离晚上还有一段时间，考虑到可能发生的危险，我们还是回家准备一下吧","莲子");
showMSG(MsgType.梅莉);
say("嗯，防身的武器要准备好，真的进入异世界的话，求生工具和一定的应急食品也需要准备，还是先回家吧","梅莉");
showMSG(MsgType.莲子);
say("那就出发回家吧！","莲子");
showMSG(MsgType.梅莉);
say("在那之前还有一件事莲子不要忘了哦~","梅莉");
showMSG(MsgType.莲子);
say("诶，什么事情……","莲子");
showMSG(MsgType.梅莉);
say("付款啦……","梅莉");
//TODO STOP MUSIC
hideMSG();
CG.push($(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(0,0,0,0).addAction(Actions.color(new Color(0,0,0,1),1).getItem()));

pause(60);
CG.disposeAll();
teleport("inner",7,18,3);

removeSelf();
end();
