package com.icomm.mapper;

import java.util.List;

import com.icomm.pojo.SysUser;

public interface SysUserMapperCustom {
	
	List<SysUser> queryUserSimplyInfoById(String id);
}