package com.javaCode.wallet.repository;

import com.javaCode.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, UUID> {

    @Modifying
    @Query("UPDATE Wallet SET amount = amount + :amount WHERE id = :id")
    void updatePlusAmount(@Param("amount") Long amount, @Param("id") UUID id);
    @Modifying
    @Query("UPDATE Wallet SET amount = amount - :amount WHERE id = :id")
    void updateMinusAmount(@Param("amount") Long amount, @Param("id") UUID id);

}
