/*
 * Title: UserDao.java
 * @author Prasad Sriramoju
 * @Created Date 25-APR-2013
 * @version 0.1
 */
package com.pxl.pwp.ldap.dao;

import org.springframework.ldap.AuthenticationException;

import org.springframework.ldap.NameNotFoundException;

import com.pxl.pwp.ldap.domain.User;

/**
 * 
 * @author Prasad Sriramoju
 * 
 */
public interface UserDao {

	User findByMail(String mail, String context)
			throws AuthenticationException, NameNotFoundException;

	public User enableUser(String userName, String context)
			throws NameNotFoundException, AuthenticationException;

	public User unLockUser(String userName, String context)
			throws NameNotFoundException, AuthenticationException;


}
