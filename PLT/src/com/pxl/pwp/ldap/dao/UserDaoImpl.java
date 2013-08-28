/*
 * Title: UserDao.java
 * Description: Default implementation of UserDao
 * @author Prasad Sriramoju
 * @Created Date 25-APR-2013
 * @version 0.1
 */

package com.pxl.pwp.ldap.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

import com.pxl.pwp.ldap.domain.User;
import com.pxl.pwp.ldap.utils.ActiveDirectoryUtils;
import com.pxl.pwp.ldap.utils.DateConverter;

/**
 * Default implementation of UserDao.
 */

@RemoteProxy(name = "UserDtls")
public class UserDaoImpl implements UserDao {

	// get log4j handler
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	private Map<String, LdapTemplate> listOfLdapTemplates;

	private static final int FLAG_TO_DISABLE_USER = 0x2;
	private static final String USER_ACCOUNT_CONTROL_ATTR_NAME = "userAccountControl";
	private static final String USER_LOCKOUT_TIME = "lockoutTime";

	@RemoteMethod
	public User findByMail(String fullname, String context)
			throws AuthenticationException, NameNotFoundException {
		logger.info("IN - UserDaoImpl - findByMail");

		ActiveDirectoryUtils utils = new ActiveDirectoryUtils();

		DistinguishedName dn = buildDn(fullname);

		User user = null;

		user = (User) listOfLdapTemplates.get(context).lookup(dn,
				getContextMapper());
		user.setEnvironment(utils.getContextFromEnv(context.toString()));

		logger.info("OUT - UserDaoImpl - findByMail");

		return user;
	}

	@RemoteMethod
	public String isUserExist(String fullname, String context) {

		String email;
		logger.info("IN - UserDaoImpl - isUserExist");
		DistinguishedName dn = buildDn(fullname);
		try {
			User user = (User) listOfLdapTemplates.get(context).lookup(dn,
					getContextMapper());
			email = user.getEmailAddress();

			logger.info("OUT - UserDaoImpl - isUserExist");
			return email;
		} catch (NameNotFoundException e) {
			logger.info("User -" + fullname + " doesnot exist in " + context);
			logger.info("OUT - UserDaoImpl - isUserExist - Excep");
			return null;
		}
	}

	private ContextMapper getContextMapper() {
		return new UserContextMapper();
	}

	private DistinguishedName buildDn(String fullname) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("cn", fullname);
		return dn;
	}

	private static class UserContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			DistinguishedName dn = new DistinguishedName(context.getDn());
			User user = new User();

			ActiveDirectoryUtils adUtils = new ActiveDirectoryUtils();
			DateConverter dateConverter = new DateConverter();

			String userAccountControl = context
					.getStringAttribute("userAccountControl");

			user.setCountry(dn.getLdapRdn(0).getComponent().getValue());
			// person.setCompany(dn.getLdapRdn(1).getComponent().getValue());
			user.setDisplayName(context.getStringAttribute("displayName"));
			user.setFullName(context.getStringAttribute("cn"));
			user.setLastName(context.getStringAttribute("sn"));
			user.setDescription(context.getStringAttribute("description"));
			user.setPhone(context.getStringAttribute("telephoneNumber"));
			user.setBadPwdCount(context.getStringAttribute("badPwdCount"));
			user.setEmailAddress(context.getStringAttribute("mail"));
			user.setMemberOfLst(context.getStringAttributes("memberOf"));

			// Convert AD Timestamp to readable format

			String adWhenCreated = context.getStringAttribute("whenCreated");
			String adBadPasswordTime = context
					.getStringAttribute("badPasswordTime");
			String adLastLogonTime = context
					.getStringAttribute("lastLogonTimestamp");

			String passwordLastSet = context.getStringAttribute("pwdLastSet");

			String acLockOutTime = context.getStringAttribute("lockoutTime");

			if (adWhenCreated != null) {
				user.setCreateTimeStamp(dateConverter
						.generalisedTimetoDate(adWhenCreated));
			}

			if (adBadPasswordTime != null) {
				user.setBadPasswordTime(dateConverter
						.odiToDateConvert(adBadPasswordTime));
			}

			if (adLastLogonTime != null) {
				user.setLastLogon(dateConverter
						.odiToDateConvert(adLastLogonTime));
			}

			if (passwordLastSet != null) {
				user.setPwdLastSet(dateConverter
						.odiToDateConvert(passwordLastSet));
			}

			if (acLockOutTime != null) {
				if (acLockOutTime.equals("0")) {
					user.setIsAccountLocked("ACTIVE");
				} else {
					user.setIsAccountLocked("LOCKED");
					user.setLockOutTime(dateConverter
							.odiToDateConvert(acLockOutTime));
				}
			} else {
				user.setIsAccountLocked("ACTIVE");
			}

			user.setIsUserDisabled(adUtils.isUserDisabled(userAccountControl));

			return user;
		}
	}

	private DistinguishedName getDnFrom(String userName) {
		return new DistinguishedName("CN=" + userName);
	}

	public User enableUser(String userName, String context)
			throws NameNotFoundException, AuthenticationException {
		logger.info("IN - UserDaoImpl - enableUser");

		LdapTemplate ldapTemplate = listOfLdapTemplates.get(context);

		DirContextOperations userContextOperations = ldapTemplate
				.lookupContext(getDnFrom(userName));
		String userAccountControlStr = userContextOperations
				.getStringAttribute(USER_ACCOUNT_CONTROL_ATTR_NAME);
		int newUserAccountControl = Integer.parseInt(userAccountControlStr)
				& ~FLAG_TO_DISABLE_USER;
		userContextOperations.setAttributeValue(USER_ACCOUNT_CONTROL_ATTR_NAME,
				"" + newUserAccountControl);
		ldapTemplate.modifyAttributes(userContextOperations);

		logger.info("OUT - UserDaoImpl - enableUser");

		return findByMail(userName, context);
	}

	public void disableUser(String userName, String context) {
		LdapTemplate ldapTemplate = listOfLdapTemplates.get(context);
		DirContextOperations userContextOperations = ldapTemplate
				.lookupContext(getDnFrom(userName));
		String userAccountControlStr = userContextOperations
				.getStringAttribute(USER_ACCOUNT_CONTROL_ATTR_NAME);
		int newUserAccountControl = Integer.parseInt(userAccountControlStr)
				| FLAG_TO_DISABLE_USER;
		userContextOperations.setAttributeValue(USER_ACCOUNT_CONTROL_ATTR_NAME,
				"" + newUserAccountControl);
		ldapTemplate.modifyAttributes(userContextOperations);
	}

	public User unLockUser(String userName, String context)
			throws NameNotFoundException, AuthenticationException {
		logger.info("IN - UserDaoImpl - unLockUser");

		LdapTemplate ldapTemplate = listOfLdapTemplates.get(context);

		DirContextOperations userContextOperations = ldapTemplate
				.lookupContext(getDnFrom(userName));

		// Set lockoutTime to ZERO
		userContextOperations.setAttributeValue(USER_LOCKOUT_TIME, "0");

		ldapTemplate.modifyAttributes(userContextOperations);

		logger.info("OUT - UserDaoImpl - unLockUser");

		return findByMail(userName, context);
	}

	public void setListOfLdapTemplates(
			Map<String, LdapTemplate> listOfLdapTemplates) {
		this.listOfLdapTemplates = listOfLdapTemplates;
	}

}
