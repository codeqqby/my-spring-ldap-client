package com.pxl.pwp.ldap.utils;

import java.util.Hashtable;

public class ActiveDirectoryUtils {

	private static final int ACCOUNT_DISABLE = 0x0002;

	public String isUserDisabled(String userAccountControlStr) {
		int userAccountControl = Integer.parseInt(userAccountControlStr);
		if ((userAccountControl & ACCOUNT_DISABLE) == ACCOUNT_DISABLE)
			return "Disabled";
		else
			return "Enabled";
	}

	public String getContextFromEnv(String contextKey) {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put("mtdevContextSource", "MT DEV");
		ht.put("gskdevContextSource", "GSK DEV");
		ht.put("mtprdContextSource", "MT PROD");
		ht.put("gskprdContextSource", "GSK PROD");
		ht.put("mtstgContextSource", "MT STG");
		return ht.get(contextKey);
	}
	
	public String getEnvFromContex(String contextKey) {
		Hashtable<String, String> ht = new Hashtable<String, String>();

		ht.put("MT DEV", "mtdevContextSource");
		ht.put("GSK DEV", "gskdevContextSource");
		ht.put("MT PROD", "mtprdContextSource");
		ht.put("GSK PROD", "gskprdContextSource");
		ht.put("MT STG", "mtstgContextSource");

		return ht.get(contextKey);
	}
}
