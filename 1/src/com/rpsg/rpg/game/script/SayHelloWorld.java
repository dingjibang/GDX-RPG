package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.Script;

public class SayHelloWorld extends Script{
	int a;
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("hello world"+a++);
	}

}
