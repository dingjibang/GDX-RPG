package com.rpsg.rpg.object.context;

import com.rpsg.rpg.object.item.Prop;

/**
 * GDX-RPG JavaScript 上下文<br>
 * <br>
 * 所谓“上下文”，即执行一段js代码的时候，所处的“变量上下文环境”。<br>
 * 每个“上下文”类，都是一个java类，他里面可能有一些自定义的变量，
 * 然后在需要的时候（如{@link Prop#get(Contextable)}），将把当前上下文编译成js运行时“原型（prototype）”，这样js就可以直接调用该上下文里的变量，然后搞点事情了。
 */
public interface Contextable {}
