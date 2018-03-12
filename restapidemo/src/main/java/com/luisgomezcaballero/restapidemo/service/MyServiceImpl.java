package com.luisgomezcaballero.restapidemo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.luisgomezcaballero.restapidemo.model.MyEntity;

@Service
public class MyServiceImpl implements MyService {

	private static final boolean DELAY_ENABLED = true;
	private static final long DELAY_TIME = 3000L;

	private List<MyEntity> myEntityList;

	{
		myEntityList = new ArrayList<MyEntity>();
		myEntityList.add(new MyEntity(1, "Entity1"));
		myEntityList.add(new MyEntity(2, "Entity2"));
		myEntityList.add(new MyEntity(3, "Entity3"));
	}

	@Cacheable("mycache")
	@Override
	public List<MyEntity> readAll() {
		testCache();
		return myEntityList;
	}

	@Cacheable("mycache")
	@Override
	public MyEntity readById(long id) {
		testCache();
		for (MyEntity myEntity : myEntityList) {
			if (myEntity.getMyLong() == id) {
				return myEntity;
			}
		}
		return null;
	}

	@Override
	public MyEntity create(MyEntity myEntity) {
		myEntityList.add(myEntity);
		return myEntity;
	}

	@Override
	public MyEntity update(long id, MyEntity myEntity) {
		for (MyEntity myEntityToModify : myEntityList) {
			if (myEntityToModify.getMyLong() == id) {
				int index = myEntityList.indexOf(myEntityToModify);
				myEntityList.set(index, myEntity);
				break;
			}
		}
		return myEntity;
	}

	@Override
	public void deleteById(long id) {
		for (Iterator<MyEntity> iterator = myEntityList.iterator(); iterator.hasNext();) {
			MyEntity myEntity = iterator.next();
			if (myEntity.getMyLong() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public void deleteAll() {
		myEntityList.clear();
	}

	private void testCache() {
		if (DELAY_ENABLED) {
			try {
				long time = DELAY_TIME;
				Thread.sleep(time);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
