eval(""+load('global.js'));
if(RPG.getFlag("1-1-shrine") == null){
	var black = $(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.stage_width, GameUtil.stage_height).setColor(Color.BLACK).setAlpha(0).getItem();
	CG.push(black);
	black.addAction(Actions.fadeIn(.5));
	pause(60);
	RPG.setFlag("1-1-shrine",true);
	teleport("shrine.tmx",11,13,3);
	CG.disposeAll();
	end();
}
