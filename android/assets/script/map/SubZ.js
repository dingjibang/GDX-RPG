eval(""+load('global.js'));

if (npc.currentImageNo == RPGObject.FACE_D) {
	npc.layer--;
	npc.currentImageNo = NPC.FACE_U;
	npc.collideFaceAble = npc.collideFaceZAble = npc.collideFootAble = npc.collideNearAble = npc.collideZAble = true;
}