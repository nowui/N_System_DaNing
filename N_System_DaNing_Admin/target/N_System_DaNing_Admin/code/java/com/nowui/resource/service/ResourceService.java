package com.nowui.module.service;

import java.util.List;

import com.nowui.module.model.Resource;

public interface ResourceService {

	public Integer count(Resource resource) throws Exception;

	public List<Resource> findList(Resource resource, Integer m, Integer n) throws Exception;

	public Resource find(Resource resource) throws Exception;

	public void save(Resource resource) throws Exception;

	public void update(Resource resource) throws Exception;

	public void delete(Resource resource) throws Exception;

}