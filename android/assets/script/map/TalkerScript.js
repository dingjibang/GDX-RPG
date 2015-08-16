eval(""+load('global.js'));

var pre=(String) npc.params.get("SAYING");
setKeyLocker(true);
showMSG();
say(prepareSaying(pre.replaceAll("\\\\n", "\n");));
hideMSG();
setKeyLocker(false);