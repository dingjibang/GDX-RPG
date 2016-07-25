eval(""+load('global.js'));

var task = RPG.ctrl.task;

task.add(1);
task.add(2);

RPG.ctrl.item.put(13);
RPG.ctrl.item.put(14);

removeSelf();
end();
