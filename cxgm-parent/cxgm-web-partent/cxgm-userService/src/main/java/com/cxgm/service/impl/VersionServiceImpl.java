package com.cxgm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.VersionMapper;
import com.cxgm.domain.Version;
import com.cxgm.domain.VersionExample;
import com.cxgm.service.VersionService;

@Primary
@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	private VersionMapper versionMapper;

	@Override
	public Version getVersion() {
		
		VersionExample example = new VersionExample();
		
		example.setOrderByClause("id desc");
		
		List<Version> list  = versionMapper.selectByExample(example);
		
		return list.get(0);
	}

}
