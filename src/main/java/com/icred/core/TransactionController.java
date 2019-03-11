package com.icred.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TransactionController {

		/**
		 * GET /create --> Create a new user and save it in the database.
		 */
		@RequestMapping(path="/transaction", method=RequestMethod.GET)
		@ResponseBody
		public String create(String email, String name) {
			
			return "User succesfully created with id = ";
		}
}
