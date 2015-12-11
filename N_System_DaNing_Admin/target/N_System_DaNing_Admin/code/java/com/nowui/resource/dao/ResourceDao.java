package com.nowui.module.dao;

import java.util.List;

import com.nowui.module.model.Resource;

public interface ResourceDao {

	public Integer count(Resource resource);

	public List<Resource> findList(Resource resource, Integer m, Integer n);

	public Resource find(Resource resource);

	public void save(Resource resource);

	public void update(Resource resource);

	public void delete(Resource resource);

}