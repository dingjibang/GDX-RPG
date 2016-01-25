eval(""+load('global.js'));
PostUtil.showMenu=false;

if(RPG.getFlag("1-1-shrine-gen") != null){
	setRenderAble(true);
	removeSelf();
	end();
}

var black = $(Res.getSync(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem();
black.loaded = function(){
	black.addAction(Actions.sequence(Actions.run(function(){setRenderAble(true)}),Actions.fadeOut(0.3),Actions.run(function(){CG.dispose(black)})));	
};
CG.push(black);


RPG.setFlag("1-1-shrine-gen",true);
removeSelf();
end();