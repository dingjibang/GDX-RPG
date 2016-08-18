eval(""+load('global.js'));
var taskId = 1;
var task = RPG.ctrl.task;

setKeyLocker(true);
lock(true);
faceToHero();

showMSG(MsgType.魔理沙);
showFGRight(FGType.魔理沙, FGType.普通);
say("咳咳咳，任务测试（","魔理沙??");
hideMSG();
hideFG();

select("接任务","还任务");

showMSG(MsgType.魔理沙);

if(currentSelect("接任务")){
	if(task.has(taskId)){
		say("你的任务还没做完呢！","魔理沙??");
	}else{
		say("已经添加任务~","魔理沙??");
		task.add(1);
	}
}else{
	if(task.has(taskId)){
		say("我看看你的任务有没有做完……","魔理沙??");
		if(task.canBeDone(taskId)){
			task.endTask(taskId);
			say("任务完成www","魔理沙??");
		}else{
			say("还没有达成任务条件_(:3」∠)_","魔理沙??");
		}
	}else{
		say("你还没有任务~","魔理沙??");
	}
}
hideMSG();
setKeyLocker(false);
lock(false);
end();