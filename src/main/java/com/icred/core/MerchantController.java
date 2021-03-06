package com.icred.core;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javassist.NotFoundException;

@Controller
public class MerchantController {

	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	
	@RequestMapping(path = "/merchant/{merchantId}/user/{qrId}/credits", method = RequestMethod.GET)
	@ResponseBody
	public Transaction getLastTransactionByMerchant(@PathVariable(required = true) String merchantId, @PathVariable(required = true) String qrId)
			throws NotFoundException {
	
		Transaction newTransaction = transactionDao.findByQrIdAndMerchantIdAndLastTransactionPerMerchant(qrId, merchantId, true);
		
		User merchant = userDao.findByEmail(merchantId);
		
		if(newTransaction == null) {
			newTransaction = new Transaction();
			
			newTransaction.setDummy(true);
			newTransaction.setInitialCredit(0L);
			newTransaction.setMerchantId(merchantId);
			newTransaction.setTransactionDate(new Date());
			
			
			User findByQrCode = userDao.findByQrCode(qrId);
			
			newTransaction.setQrId(qrId);
			newTransaction.setMerchantName(merchant.getName());
			newTransaction.setLastTransactionPerMerchant(true);
			newTransaction.setCreditUsed(false);
			newTransaction.setUserId(findByQrCode.getEmail());
			transactionDao.save(newTransaction);
			
		}
		
		return newTransaction;
	}
	
	

	@RequestMapping(path = "/merchant/{merchantId}/user/{qrId}/credits", method = RequestMethod.POST)
	@ResponseBody
	public Transaction updateLastTransaction(@PathVariable(required = true) String merchantId, @PathVariable(required = true) String qrId, HttpServletRequest request)
			throws NotFoundException,Exception {
	
		// TODO: check user maerchant validity
		// TODO: check parameters
		// TODO: check last transaction
		
		String newCredit = null; 
		Long newCreditLong = null; 
		String currentCreditId = null; 
		
		try {
			newCredit = request.getParameter("newCredit");
			newCreditLong = Long.parseLong(newCredit);
			currentCreditId = request.getParameter("currentCreditId");
		}
		catch (Exception e){
			throw new Exception("error in input parameters");
		}
	
		
		Transaction currentTransaction = transactionDao.findById(Long.parseLong(currentCreditId)).get(); 
		
        
        User user = userDao.findByQrCode(qrId);
        
        
        // update existing transaction
        currentTransaction.setLastTransactionPerMerchant(false);
        currentTransaction.setCreditUsed(true); 
        
        // create new transaction
        Transaction transaction = new Transaction();
//        transaction.setUser( user );
        transaction.setUserId( user.getEmail() );
        transaction.setQrId( qrId );
        transaction.setTransactionDate(new Date());
        transaction.setMerchantId(merchantId);
        transaction.setMerchantName(currentTransaction.getMerchantName());
        transaction.setInitialCredit( newCreditLong );
        transaction.setCreditUsed(false);
        transaction.setLastTransactionPerMerchant(true);
        transaction.setDummy(false);
        

        // tell Doctrine you want to (eventually) save the
        transactionDao.save(transaction);

        return transaction;
        
	}

      
}
	
