package com.rpsg.rpg.object.rpg;

import java.util.UUID;

public class PublicNPC extends DefaultNPC {

	private static final long serialVersionUID = 1L;
	private String id = UUID.randomUUID().toString();
	
	
	public PublicNPC(String id,String path) {
		super(path);
		if(id!=null)
			this.id=id;
	}
	
	public void init() {
		super.init();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
