eval(""+load('global.js'));

Move.teleportAnotherMap(Teleporter.this, npc.params.get("TELEPORT") + ".tmx",
						Integer.parseInt((String) npc.params.get("TELEPORTX")),
						Integer.parseInt((String) npc.params.get("TELEPORTY")),
						Integer.parseInt((String) npc.params.get("TELEPORTZ")));