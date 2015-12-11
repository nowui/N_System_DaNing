package com.nowui.module.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nowui.base.utility.Helper;
import com.nowui.module.dao.ResourceDao;
import com.nowui.module.model.Resource;

@Repository("resourceDao")
public class ResourceDaoImpl implements ResourceDao {

	private class ResourceRowMapper implements RowMapper<Resource> {
		public Resource mapRow(ResultSet rs, int number) throws SQLException {
			Resource resource = new Resource();
			resource.setId(rs.getInt("id"));
			resource.setName(rs.getString("name"));
			resource.setType(rs.getString("type"));
			resource.setValue(rs.getString("value"));
			resource.setSize(rs.getInt("size"));
			resource.setFolderId(rs.getInt("folderId"));
			resource.setCreateUserId(rs.getInt("createUserId"));
			resource.setCreateTime(rs.getTimestamp("createTime"));
			return resource;
		}
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	DataSource dataSource;

	public Integer count(Resource resource) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM Resource_Info ");
		Boolean isExit = false;

		if (!Helper.isNullOrEmpty(resource.getName())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append("name LIKE :name ");
			parameters.put("name", "%" + resource.getName() + "%");

			isExit = true;
		}


		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		return namedParameterJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
	}

	public List<Resource> findList(Resource resource, Integer m, Integer n) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("SELECT * FROM Resource_Info ");
		Boolean isExit = false;

		if (!Helper.isNullOrEmpty(resource.getName())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append("name LIKE :name ");
			parameters.put("name", "%" + resource.getName() + "%");

			isExit = true;
		}


		sql.append("ORDER BY id DESC ");
		if (n > 0) {
			sql.append("LIMIT :m, :n ");
			parameters.put("m", m);
			parameters.put("n", n);
		}

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		return namedParameterJdbcTemplate.query(sql.toString(), parameters, new RowMapperResultSetExtractor<Resource>(new ResourceRowMapper()));
	}

	public Resource find(Resource resource) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("SELECT * FROM Resource_Info ");
		Boolean isExit = false;

		if (!Helper.isNullOrEmpty(resource.getId())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append("id = :id ");
			parameters.put("id", resource.getId());

			isExit = true;
		}

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Resource> resourceList = namedParameterJdbcTemplate.query(sql.toString(), parameters, new RowMapperResultSetExtractor<Resource>(new ResourceRowMapper()));
		if(resourceList.size() == 0) {
			return null;
		} else {
			return resourceList.get(0);
		}
	}

	@Transactional
	public void save(Resource resource) {
		entityManager.persist(resource);
	}

	@Transactional
	public void update(Resource resource) {
		entityManager.merge(resource);
	}

	@Transactional
	public void delete(Resource resource) {
		StringBuffer sql = new StringBuffer("DELETE Resource ");

		Boolean isExit = false;

		if (!Helper.isNullOrEmpty(resource.getIdList())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append("id IN :idList ");

			isExit = true;
		}

		Query query = entityManager.createQuery(sql.toString());

		if (!Helper.isNullOrEmpty(resource.getIdList())) {
			query.setParameter("idList", resource.getIdList());
		}

		if(isExit) {
			query.executeUpdate();
		}
	}

}