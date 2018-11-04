package com.icred.core;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javassist.NotFoundException;

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TransactionDao transactionDao;

	
	@RequestMapping(path = "/tri", method = RequestMethod.GET)
	@ResponseBody
	public String tri() {
		return "hello";
	}

	

	/**
	 * POST /create --> Create a new user and save it in the database.
	 */
	@RequestMapping(path = "/oauth/token", method = RequestMethod.POST)
	@ResponseBody
	public AuthObject login(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
		
		User foundUser = userDao.findByEmailAndPassword(username, password);
		AuthObject auth = null; 
		if (foundUser != null) {
			auth = new  AuthObject("MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3", "bearer", 3600, "IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk", "create");
				
		}
		return auth;
	}
	
	
	
	/**
	 * POST /create --> Create a new user and save it in the database.
	 */
	@RequestMapping(path = "/user", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestParam(required = true) String email, @RequestParam(required = true) String password) {
		String userId = "";
		try {
			User user = new User(email, "", password);
			userDao.save(user);
			userId = String.valueOf(user.getId());
			user.setQrCode(generateQrCode("" + user.getId()));
			userDao.save(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created with id = " + userId;
	}

	private String generateQrCode(String userId) {
		return "IC" + userId + java.util.UUID.randomUUID().toString();
	}

	/**
	 * GET /get-by-email --> Return the id for the user having the passed email.
	 * 
	 * @throws NotFoundException
	 */
	@RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public User getByEmail(@PathVariable(required = true) String userId) throws NotFoundException {
		User user = null;
		try {
			user = userDao.findByEmail(userId);

		} catch (Exception ex) {
			throw new NotFoundException("User not found");
		}
		return user;
	}

	/**
	 * GET /get-by-email --> Return the id for the user having the passed email.
	 * 
	 * @throws NotFoundException
	 */
	@RequestMapping(path = "/user/{userId}/credits", method = RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getLastCreditListPerMerchant(@PathVariable(required = true) String userId)
			throws NotFoundException {
		List<Transaction> list = null;
		try {
			list = transactionDao.findAllByUserIdAndLastTransactionPerMerchantAndDummy(userId, true, false);

		} catch (Exception ex) {
			throw new NotFoundException("User not found");
		}
		return list;
	}

	/**
	 * GET /get-by-email --> Return the id for the user having the passed email.
	 * 
	 * @throws NotFoundException
	 */
	@RequestMapping(path = "/user/{userId}/credit/{creditId}", method = RequestMethod.GET)
	@ResponseBody
	public Transaction getCreditDetail(@PathVariable(required = true) String userId,
			@PathVariable(required = true) Long creditId) throws NotFoundException {
		Transaction transaction = null;
		try {
			Optional<Transaction> findById = transactionDao.findById(creditId);
			transaction = findById.get();

		} catch (Exception ex) {
			throw new NotFoundException("User not found");
		}
		return transaction;
	}

}
