package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.Invoice;

@Repository
public interface invoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByBooking_Customer_Id(Long userId);

}
