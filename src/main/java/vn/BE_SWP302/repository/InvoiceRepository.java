package vn.BE_SWP302.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByBooking_Customer_Id(Long userId);

    List<Invoice> findByBooking_BookingId(Long bookingId);

    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i WHERE DATE(i.issuedDate) = :date")
    Double sumTotalAmountByDate(LocalDate date);

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE MONTH(i.issuedDate) = :month AND YEAR(i.issuedDate) = :year")
    Double sumTotalAmountByMonth(@Param("month") int month, @Param("year") int year);
}
