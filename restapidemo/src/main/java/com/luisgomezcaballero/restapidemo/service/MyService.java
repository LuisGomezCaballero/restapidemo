package com.luisgomezcaballero.restapidemo.service;

import java.util.List;

import com.luisgomezcaballero.restapidemo.model.MyEntity;

public interface MyService {

	public List<MyEntity> readAll();

	public MyEntity readById(long id);

	public MyEntity create(MyEntity myEntity);

	public MyEntity update(long id, MyEntity myEntity);

	public void deleteById(long id);

	public void deleteAll();

}
