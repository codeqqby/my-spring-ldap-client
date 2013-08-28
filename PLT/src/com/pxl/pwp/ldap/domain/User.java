/*
 * Copyright 2005-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pxl.pwp.ldap.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * User Class.
 * 
 * @author Prasad Sriramoju
 */
public class User {

	private String environment;

	private String displayName;

	private String fullName;

	private String lastName;

	private String description;

	private String country;

	private String company;

	private String phone;

	private String badPwdCount;

	private String lastLogon;

	private String emailAddress;

	private String createTimeStamp;

	private String badPasswordTime;

	private String isUserDisabled;

	private String isAccountLocked;

	private String lockOutTime;

	private String[] memberOfLst;

	private String pwdLastSet;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBadPwdCount() {
		return badPwdCount;
	}

	public void setBadPwdCount(String badPwdCount) {
		this.badPwdCount = badPwdCount;
	}

	public String getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(String lastLogon) {
		this.lastLogon = lastLogon;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(String createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public String getBadPasswordTime() {
		return badPasswordTime;
	}

	public void setBadPasswordTime(String badPasswordTime) {
		this.badPasswordTime = badPasswordTime;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getIsUserDisabled() {
		return isUserDisabled;
	}

	public void setIsUserDisabled(String isUserDisabled) {
		this.isUserDisabled = isUserDisabled;
	}

	public String getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(String isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public String getLockOutTime() {
		return lockOutTime;
	}

	public void setLockOutTime(String lockOutTime) {
		this.lockOutTime = lockOutTime;
	}

	public String[] getMemberOfLst() {
		return memberOfLst;
	}

	public void setMemberOfLst(String[] memberOfLst) {
		this.memberOfLst = memberOfLst;
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getPwdLastSet() {
		return pwdLastSet;
	}

	public void setPwdLastSet(String pwdLastSet) {
		this.pwdLastSet = pwdLastSet;
	}

}
