/*
 * Title: UserController.java
 * Description: Class handles all the User requests
 * @author Prasad Sriramoju
 * @Created Date 25-APR-2013
 * @version 0.1
 */
package com.pxl.pwp.ldap.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pxl.pwp.ldap.dao.UserDao;
import com.pxl.pwp.ldap.domain.ErrorDtls;
import com.pxl.pwp.ldap.domain.LoggedUser;
import com.pxl.pwp.ldap.domain.User;
import com.pxl.pwp.ldap.utils.ActiveDirectoryUtils;
import com.pxl.pwp.ldap.utils.DateConverter;
import com.pxl.pwp.ldap.utils.NameRegex;

/**
 * This class handles all the User requests and propagates to appropriate DAO
 * method.
 */
@Controller
public class UserController {

	// get log4j handler
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;

	DateConverter dateConverter = new DateConverter();
	NameRegex nameRegex = new NameRegex();

	@RequestMapping(value = "/welcome.do", method = RequestMethod.GET)
	public ModelAndView welcomeHandler() {
		logger.info("IN - UserController - welcomeHandler");

		ModelAndView model = new ModelAndView();

		// Setting the Logged in as information Starts
		LoggedUser loggedUser = new LoggedUser();

		loggedUser.setLoggedUser(nameRegex.getFullName());

		model.addObject("loggedUser", loggedUser);
		// Setting the Logged in as information Ends

		model.setViewName("welcome");
		model.addObject("loggedUser", loggedUser);

		logger.info("OUT - UserController - welcomeHandler");
		return model;
	}

	@RequestMapping(value = "/getUserByMail.do", method = RequestMethod.POST)
	public ModelAndView getUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws NameNotFoundException,
			Exception {

		logger.info("IN - UserController - getUserInfo");

		ActiveDirectoryUtils utils = new ActiveDirectoryUtils();
		ErrorDtls error = new ErrorDtls();

		ModelAndView model = new ModelAndView();
		User user = new User();

		if (request == null) {
			logger.info("Request object is NULL");
		}

		if (request.getParameter("envList") == null) {
			logger.info("request.getParameter(envList) is NULL");
		}

		if (request.getParameter("emailid") == null) {
			logger.info("request.getParameter(emailid) is NULL");
		}

		// Fetching User Details from DAO Layer
		try {
			user = userDao.findByMail(request.getParameter("emailid")
					.toString().trim(), request.getParameter("envList")
					.toString());

			logger.info("#####User Information#########");
			logger.info("Environment - " + user.getEnvironment());
			logger.info("Email - " + user.getEmailAddress());
			logger.info("Account is " + user.getIsAccountLocked() + " and "
					+ user.getIsUserDisabled());
			logger.info("##############################");

			model.setViewName("getUserByMail");
			model.addObject("user", user);

			// Setting the Logged in as information Starts
			LoggedUser loggedUser = new LoggedUser();

			loggedUser.setLoggedUser(nameRegex.getFullName());

			model.addObject("loggedUser", loggedUser);
			// Setting the Logged in as information Ends

			logger.info("OUT - UserController - getUserInfo");
			return model;
		} catch (NameNotFoundException e) {
			logger.info("Inside NameNotFoundException Catch block");
			logger.info(e.getMessage());
			e.printStackTrace();

			error.setErrorMsg("The User with Emailaddress - <b>"
					+ request.getParameter("emailid").toString()
					+ "</b> does not exist in the environment - <b>"
					+ utils.getContextFromEnv(request.getParameter("envList")
							.toString()) + "</b>");

			model.setViewName("error");
			model.addObject("error", error);

			// Setting the Logged in as information Starts
			LoggedUser loggedUser = new LoggedUser();

			loggedUser.setLoggedUser(nameRegex.getFullName());

			model.addObject("loggedUser", loggedUser);
			// Setting the Logged in as information Ends

			logger.info("OUT - UserController - getUserInfo");

			return model;
		} catch (AuthenticationException e) {
			logger.info("Inside AuthenticationException Catch block");
			logger.info(e.getMessage());
			e.printStackTrace();
			error
					.setErrorMsg("System is unable to establish the Connetion with LDAP server.<br/>"
							+ " Please verify the Connection details for the environment - <b>"
							+ utils.getContextFromEnv(request.getParameter(
									"envList").toString()) + "</b>");

			model.setViewName("error");
			model.addObject("error", error);

			// Setting the Logged in as information Starts
			LoggedUser loggedUser = new LoggedUser();

			loggedUser.setLoggedUser(nameRegex.getFullName());

			model.addObject("loggedUser", loggedUser);
			// Setting the Logged in as information Ends

			logger.info("OUT - UserController - getUserInfo");

			return model;
		} catch (Exception e) {
			logger.info("Inside Exception Catch block");
			logger.info(e.getMessage());
			e.printStackTrace();
			error.setErrorMsg("An Error has Occured. Please verify the logs");

			model.setViewName("error");
			model.addObject("error", error);

			// Setting the Logged in as information Starts
			LoggedUser loggedUser = new LoggedUser();

			loggedUser.setLoggedUser(nameRegex.getFullName());

			model.addObject("loggedUser", loggedUser);
			// Setting the Logged in as information Ends

			logger.info("OUT - UserController - getUserInfo");

			return model;
		}

	}

	@RequestMapping(value = "/enableUser.do", method = RequestMethod.POST)
	public ModelAndView enableUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("IN - DefaultController - enableUser");

		ActiveDirectoryUtils utils = new ActiveDirectoryUtils();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm zz");

		if (request.getParameter("env") == null) {
			logger.info("request.getParameter(env) is NULL");
		}

		if (request.getParameter("emailid") == null) {
			logger.info("request.getParameter(emailid) is NULL");
		}

		// Enable User here
		User user = userDao.enableUser(request.getParameter("emailid")
				.toString(), utils.getEnvFromContex(request.getParameter("env")
				.toString()));

		ModelAndView model = new ModelAndView("getUserByMail");
		model.addObject("user", user);

		// Setting the Logged in as information Starts
		LoggedUser loggedUser = new LoggedUser();

		loggedUser.setLoggedUser(nameRegex.getFullName());

		model.addObject("loggedUser", loggedUser);
		// Setting the Logged in as information Ends

		logger.info("User - " + user.getEmailAddress() + " is Enabled by "
				+ nameRegex.getFullName() + " on "
				+ dateFormat.format(new Date()));

		logger.info("OUT - DefaultController - enableUser");
		return model;

	}

	@RequestMapping(value = "/unLockUser.do", method = RequestMethod.POST)
	public ModelAndView unLockUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("IN - DefaultController - unLockUser");

		ActiveDirectoryUtils utils = new ActiveDirectoryUtils();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm zz");

		if (request.getParameter("env") == null) {
			logger.info("request.getParameter(env) is NULL");
		}

		if (request.getParameter("emailid") == null) {
			logger.info("request.getParameter(emailid) is NULL");
		}

		// Unlock User here
		User user = userDao.unLockUser(request.getParameter("emailid")
				.toString(), utils.getEnvFromContex(request.getParameter("env")
				.toString()));

		logger.info("User - " + user.getEmailAddress() + " is Unlocked");

		ModelAndView model = new ModelAndView("getUserByMail");
		model.addObject("user", user);

		// Setting the Logged in as information Starts
		LoggedUser loggedUser = new LoggedUser();

		loggedUser.setLoggedUser(nameRegex.getFullName());

		model.addObject("loggedUser", loggedUser);
		// Setting the Logged in as information Ends

		logger.info("User - " + user.getEmailAddress() + " is Unlocked by "
				+ nameRegex.getFullName() + " on "
				+ dateFormat.format(new Date()));

		logger.info("OUT - DefaultController - unLockUser");
		return model;

	}

}
