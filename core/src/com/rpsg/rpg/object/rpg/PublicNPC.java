package com.rpsg.rpg.object.rpg;


public class PublicNPC extends DefaultNPC {

	private static final long serialVersionUID = 1L;
	String id;

	public void init() {
		if(this.params.get("ID")==null)
			return;
		this.id=(String) this.params.get("ID");
	}

}
