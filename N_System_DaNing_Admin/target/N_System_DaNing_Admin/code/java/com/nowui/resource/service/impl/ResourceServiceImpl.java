package com.nowui.module.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowui.base.utility.Helper;
import com.nowui.module.dao.ResourceDao;
import com.nowui.module.model.Resource;
import com.nowui.module.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	public Integer count(Resource resource) throws Exception {
		return resourceDao.count(resource);
	}

	public List<Resource> findList(Resource resource, Integer page, Integer limit) throws Exception {
		return resourceDao.findList(resource, Helper.checkStar(page, limit), Helper.checkEnd(limit));
	}

	public Resource find(Resource resource) throws Exception {
		return resourceDao.find(resource);
	}

	public void save(Resource resource) throws Exception {
		resourceDao.save(resource);
	}

	public void update(Resource resource) throws Exception {
		resourceDao.update(resource);
	}

	public void delete(Resource resource) throws Exception {
		resourceDao.delete(resource);
	}

}