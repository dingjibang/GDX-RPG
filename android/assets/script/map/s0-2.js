eval(""+load('global.js'));
Hero.setVisible(false);
PostUtil.showMenu=false;
setKeyLocker(true);

var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
black.addAction(Actions.sequence(Actions.fadeOut(0.3),Actions.run(function(){CG.dispose(black)})));	
CG.push(black);

setRenderAble(true);

var yuki=getNPC("1");
setBalloon(yuki, BalloonType.沉默);
showMSG(MsgType.梅莉);
say("果然你是出于这种考虑啊","梅莉");
say("莲子，你认为，有栖突然变成灵能力者这件事，跟最近那件事有关吗？","梅莉");
showMSG(MsgType.莲子);
say("嗯。不论如何，非灵能力者一下子变成灵能力者，而且还是这么强力的能力，是非常少见的事情。还偏偏是在这种时候","莲子");
showMSG(MsgType.梅莉);
say("是这样没错……但就这样也不能断定有关系啊","梅莉");
say("就好像我们还没有确定连环杀人案还有都市传说跟那件事有关系一样","梅莉");
showMSG(MsgType.莲子);
say("这倒是……啊","莲子");
hideMSG();
pause(60);
showMSG(MsgType.莲子);
say("有栖同学看来一头雾水呢","莲子");
say("春假期间你或多或少也听说了吧，新闻上天天在说呢，让警方一头雾水，找不到凶手的连环杀人案","莲子");//没听说过
say("有人被杀害的同时也有人失踪，杀人的手法不尽相同，但各个都十分老练","莲子");
say("有人说是集团犯罪，却又完全看不出有什么作案规律，更没有发现有据点什么的。所以也有都市传说说，凶手不是人类，或者是来自另一个世界的人类呢","莲子");
showMSG(MsgType.梅莉);
say("嗯，而且春假期间京都各个地方都有灵异事件的报告，这在以前是很少有的","梅莉");
say("当然，体现出来就是各式各样的都市传说啦，比如哪条小街早上会看到两条尾巴的猫又，又或者那个寺庙前的路灯到了时候就会不明原因地熄灭什么的","梅莉");
showMSG(MsgType.莲子);
say("所以我和梅莉就觉得，说不定杀人案也是灵异事件的一环。而这都与京都周围的结界变稀薄有关……","莲子");
say("啊，有栖还不知道京都周围结界的事情吧？","莲子");
showMSG(MsgType.梅莉);
say("（喂，莲子，说漏啦，结界的事可是我们两人的……）","梅莉");
showMSG(MsgType.莲子);
say("（哈哈……不小心就……）","莲子");
say("（不过现在还是说明白了比较好吧，我觉得以后可能会需要结成同学的能力，而且结成同学能接触到我们应该也花费了不少力气，某种意义上或许不算外人了）","莲子");
showMSG(MsgType.梅莉);
say("（好吧……）","梅莉");
hideMSG();
setBalloon(yuki,BalloonType.疑惑);
pause(60);
showMSG(MsgType.莲子);
say("啊……实际上是这样的","莲子");
say("我们秘封俱乐部的目的，是为了研究京都周围的结界，找到其中的缝隙并进入其中","莲子");
say("说白了，就是寻找另一个世界的存在","莲子");
say("如今结界突然变得稀薄，正是我们进入开展行动的大好机会","莲子");
say("而且，我相信，有栖同学的能力也与结界的变化存在关系。如果有栖想要了解自己能力的来龙去脉，跟我们俩一起行动最好了","莲子");
showMSG(MsgType.梅莉);
say("莲子又自作主张了！","梅莉");
showMSG(MsgType.莲子);
say("啊哈哈……","莲子");
showMSG(MsgType.梅莉);
say("不过说到行动的话，本来预定今晚就有活动的吧","梅莉");
showMSG(MsgType.莲子);
say("是这样没错，有都市传说称，下雨的博丽神社会打开通往异世界的大门呢。众多带有暗示色彩的都市传说里就数这条最露骨了","莲子");
showMSG(MsgType.梅莉);
say("所以……真的要带上结成同学吗？","梅莉");
showMSG(MsgType.莲子);
say("那要看结成同学自己的意愿吧","莲子");
say("如果来的话对了解自己的能力可能会有意向不到的收获呢，当然怕危险的话留在家里也好，珍惜生命可是美好的品质哦","莲子");
hideMSG();
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
CG.push($(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(0,0,0,0).addAction(Actions.color(new Color(0,0,0,1),1)).getItem());

pause(70);

teleport("inner",7,18,3);
CG.disposeAll();
removeSelf();
end();
