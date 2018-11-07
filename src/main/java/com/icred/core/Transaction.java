package com.icred.core;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	
    private Date transactionDate;

    
    private Long initialCredit;
    
    
    private String merchantId;

    private String merchantName;

//    /**
//     * @var User
//     *
//     * @ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="transactions")
//     * @JoinColumn(nullable=true)
//     */
//    private User user;

    
    private String userId;
    

    private String qrId;
    
    
    private boolean lastTransactionPerMerchant;

    
    private boolean creditUsed;
    
    
    private boolean dummy;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}


	public Long getInitialCredit() {
		return initialCredit;
	}


	public void setInitialCredit(Long initialCredit) {
		this.initialCredit = initialCredit;
	}


	public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


//	public User getUser() {
//		return user;
//	}
//
//
//	public void setUser(User user) {
//		this.user = user;
//	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getQrId() {
		return qrId;
	}


	public void setQrId(String qrId) {
		this.qrId = qrId;
	}


	public boolean isLastTransactionPerMerchant() {
		return lastTransactionPerMerchant;
	}


	public void setLastTransactionPerMerchant(boolean lastTransactionPerMerchant) {
		this.lastTransactionPerMerchant = lastTransactionPerMerchant;
	}


	public boolean isCreditUsed() {
		return creditUsed;
	}


	public void setCreditUsed(boolean creditUsed) {
		this.creditUsed = creditUsed;
	}


	public boolean isDummy() {
		return dummy;
	}


	public void setDummy(boolean dummy) {
		this.dummy = dummy;
	}


	public String getMerchantName() {
		return merchantName;
	}


	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
    

}