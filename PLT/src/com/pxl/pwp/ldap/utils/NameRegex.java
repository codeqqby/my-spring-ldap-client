package com.pxl.pwp.ldap.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

import com.pxl.pwp.ldap.web.UserController;

public class NameRegex {

	// get log4j handler
	private static final Logger logger = Logger.getLogger(UserController.class);

	public String getFullName() {

		String firstName = null;
		String lastName = null;

		try {

			LdapUserDetailsImpl loggedUser = (LdapUserDetailsImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();

			String lastNameRegEx = Pattern.quote("cn=") + "(.*?)"
					+ Pattern.quote("\\,");
			Pattern pattern = Pattern.compile(lastNameRegEx);

			Matcher matcher = pattern.matcher(loggedUser.getDn());

			if (matcher.find()) {
				lastName = matcher.group(1).trim();
			}

			String firstNameRegEx = Pattern.quote("\\,") + "(.*?)"
					+ Pattern.quote(",");
			pattern = Pattern.compile(firstNameRegEx);

			matcher = pattern.matcher(loggedUser.getDn());

			if (matcher.find()) {
				firstName = matcher.group(1).trim();
			}
			return firstName.concat(" ").concat(lastName);
		} catch (Exception e) {
			logger.info("An exception has ocured in NameRegex class - "
					+ e.getMessage());
			return null;
		}

	}
}
