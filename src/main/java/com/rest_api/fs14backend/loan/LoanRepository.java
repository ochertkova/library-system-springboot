package com.rest_api.fs14backend.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
}
