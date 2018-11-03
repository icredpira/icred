package com.icred.core;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface TransactionDao extends CrudRepository<Transaction, Long> {

	List<Transaction> findAllByUserIdAndLastTransactionPerMerchantAndDummy(String userId, boolean lastTransactionPerMerchant,boolean dummy);

	
	Transaction findByQrIdAndMerchantIdAndLastTransactionPerMerchant(String qrId, String merchantId, boolean lastTransactionPerMerchant);


}