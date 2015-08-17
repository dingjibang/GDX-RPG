eval(""+load('global.js'));

if(RPG.getFlag("1-1-inner")==null){
	RPG.setFlag("1-1-inner",true);
	CG.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem()).addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(function(){CG.dispose(black)});
	PostUtil.showMenu=false;
	setKeyLocker(true);
	Hero.turn(RPGObject.FACE_U);
	wait(50);
	showMSG(莲子);
	say("这里就是我家啦，当然今天开始也是结成同学家","莲子");
	say("到晚上还有一点时间，结成同学可以随意转转\n二楼最里面是你的房间，已经给你准备了一些道具和食物，自己去拿就好了","莲子");
	say("准备好了就来找我出发吧，当然有什么问题也是可以问我的哦！","莲子");
	hideMSG();
	wait(20);
	CG.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(0,0,0,0).getItem()).addAction(Actions.color(new Color(0,0,0,0.5f),0.3f));	
	var tip1=Res.getNP(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc1.png":"tip_mobile1.png"));
	var tip2=Res.getNP(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc2.png":"tip_mobile2.png"));
	CG.push(tip1.color(1,1,1,0).oranCenter().position((int)(GameUtil.screen_width/2-tip1.getWidth()/2), (int)(GameUtil.screen_height/2-+tip1.getHeight()/2))).addAction(Actions.color(new Color(1,1,1,1),0.3f));
	say("   ");			
	CG.dispose(tip1);
	CG.push(tip2.oranCenter().position((int)(GameUtil.screen_width/2-tip2.getWidth()/2), (int)(GameUtil.screen_height/2-+tip2.getHeight()/2)));
	say("   ");
	tip2.addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(function(){CG.dispose(tip2)})));
	black.addAction(Actions.sequence(Actions.color(new Color(0,0,0,0),0.3f),Actions.run(function(){CG.dispose(black)});
	setKeyLocker(false);				
}			
removeSelf();
end();
