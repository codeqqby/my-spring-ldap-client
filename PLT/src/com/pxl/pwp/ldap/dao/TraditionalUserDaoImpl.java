/*
 * Title: TraditionalUserDaoImpl.java
 * Description: Traditional implementation of UserDAO
 * @author Prasad Sriramoju
 * @Created Date 25-APR-2013
 * @version 0.1
 */
package com.pxl.pwp.ldap.dao;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang.StringUtils;

import com.pxl.pwp.ldap.domain.User;

/**
 * Traditional implementation of UserDAO. This implementation uses the basic
 * JNDI interfaces and classes {@link DirContext}, {@link Attributes},
 * {@link Attribute}, and {@link NamingEnumeration}. The purpose is to contrast
 * this implementation with that of {@link PersonDaoImpl}.
 * 
 */
public class TraditionalUserDaoImpl implements UserDao {

	private String userName;

	private String password;

	private String url;

	private String base;

	/*
	 * This method will return User object based on email address
	 * 
	 * @param String, String
	 * 
	 * @return User
	 */
	public User findByMail(String mail, String context) {

		DirContext ctx = createAuthenticatedContext();
		String dn = buildDn(mail);
		try {
			Attributes attributes = ctx.getAttributes(dn);
			return mapToUser(dn, attributes);
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Did not find entry with mail '" + dn
					+ "'", e);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					// Never mind this.
				}
			}
		}
	}

	public User enableUser(String mail, String context) {

		DirContext ctx = createAuthenticatedContext();
		String dn = buildDn(mail);
		try {
			Attributes attributes = ctx.getAttributes(dn);
			return mapToUser(dn, attributes);
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Did not find entry with mail '" + dn
					+ "'", e);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					// Never mind this.
				}
			}
		}
	}

	public User unLockUser(String mail, String context) {

		DirContext ctx = createAuthenticatedContext();
		String dn = buildDn(mail);
		try {
			Attributes attributes = ctx.getAttributes(dn);
			return mapToUser(dn, attributes);
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Did not find entry with mail '" + dn
					+ "'", e);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					// Never mind this.
				}
			}
		}
	}

	private String buildDn(String mail) {
		StringBuffer sb = new StringBuffer();
		sb.append("cn=");
		sb.append(mail);
		String dn = sb.toString();
		return dn;
	}

	@SuppressWarnings("unchecked")
	private DirContext createContext(Hashtable env) {
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		String tempUrl = createUrl();
		env.put(Context.PROVIDER_URL, tempUrl);
		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		return ctx;
	}

	@SuppressWarnings("unchecked")
	private DirContext createAuthenticatedContext() {
		Hashtable env = new Hashtable();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userName);
		env.put(Context.SECURITY_CREDENTIALS, password);
		return createContext(env);
	}

	private User mapToUser(String dn, Attributes attributes)
			throws NamingException {
		User user = new User();
		user.setFullName((String) attributes.get("cn").get());
		user.setLastName((String) attributes.get("sn").get());
		user.setDescription((String) attributes.get("description").get());
		user.setPhone((String) attributes.get("telephoneNumber").get());
		user.setDisplayName((String) attributes.get("dispalyName").get());

		// Remove any trailing spaces after comma
		String cleanedDn = dn.replaceAll(", *", ",");

		String countryMarker = ",c=";
		int countryIndex = cleanedDn.lastIndexOf(countryMarker);

		String companyMarker = ",ou=";
		int companyIndex = cleanedDn.lastIndexOf(companyMarker);

		String country = cleanedDn.substring(countryIndex
				+ countryMarker.length());
		user.setCountry(country);
		String company = cleanedDn.substring(companyIndex
				+ companyMarker.length(), countryIndex);
		user.setCompany(company);
		return user;
	}

	private String createUrl() {
		String tempUrl = url;
		if (!tempUrl.endsWith("/")) {
			tempUrl += "/";
		}
		if (StringUtils.isNotEmpty(base)) {
			tempUrl += base;
		}
		return tempUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public void setPassword(String credentials) {
		this.password = credentials;
	}

	public void setUserDn(String principal) {
		this.userName = principal;
	}

}
