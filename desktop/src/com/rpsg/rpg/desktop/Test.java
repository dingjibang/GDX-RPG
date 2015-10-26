package com.rpsg.rpg.desktop;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

public class Test {
	public static void main(String[] args) {
		System.out.println(0f / 0f);
		System.out.println(233f / 0);
		System.out.println(12f / 450f);
	}
}
