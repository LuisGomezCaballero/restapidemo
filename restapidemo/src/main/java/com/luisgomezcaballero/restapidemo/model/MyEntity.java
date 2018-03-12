package com.luisgomezcaballero.restapidemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MyEntity {
	private long myLong;
	private String myString;

	public MyEntity() {
		super();
	}

	public MyEntity(long myLong, String myString) {
		super();
		this.myLong = myLong;
		this.myString = myString;
	}

	@ApiModelProperty(notes = "Long as a property of the entity", required = true)
	public long getMyLong() {
		return myLong;
	}

	public void setMyLong(long myLong) {
		this.myLong = myLong;
	}

	@ApiModelProperty(notes = "String as a property of the entity", required = true)
	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}

	@Override
	public String toString() {
		return "MyEntity [myLong=" + myLong + ", myString=" + myString + "]";
	}

}
