package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DashboardResponse {
    private Long totalUsers;
    private Long totalDoctors;
    private Long totalBookings;
    private Long todayBookings;
    private BigDecimal todayRevenue;
    private BigDecimal monthRevenue;
    private Long totalServices;
    private Long totalFeedback;
    private Long ongoingTreatmentCount;
}