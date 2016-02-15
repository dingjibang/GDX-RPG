package com.rpsg.rpg.object.rpg;


public class PublicNPC extends DefaultNPC {

	private static final long serialVersionUID = 1L;
	private String id = "";
	
	
	public PublicNPC(String id,String path, Integer width, Integer height) {
		super(path, width, height);
		if(id!=null)
			this.id=id;
	}
	
	@Override
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
