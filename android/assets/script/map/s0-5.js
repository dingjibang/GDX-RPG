eval(""+load('global.js'));
if(RPG.getFlag("1-1-shrine") == null){
	var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).setAlpha(0).getItem();
	CG.push(black);
	black.addAction(Actions.sequence(Actions.fadeIn(.5),Actions.run(function(){
	})));
	pause(60);
	RPG.setFlag("1-1-shrine",true);
	teleport("shrine.tmx",11,13,3);
	CG.disposeAll();
	end();
}
