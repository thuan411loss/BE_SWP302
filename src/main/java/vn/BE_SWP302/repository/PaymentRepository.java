package vn.BE_SWP302.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.BE_SWP302.domain.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByInvoice_InvoiceId(Long invoiceId);
}
