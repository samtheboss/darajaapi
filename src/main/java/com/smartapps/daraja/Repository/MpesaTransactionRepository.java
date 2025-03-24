package com.smartapps.daraja.Repository;


import com.smartapps.daraja.model.MpesaTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MpesaTransactionRepository extends JpaRepository<MpesaTransaction, Long> {
}