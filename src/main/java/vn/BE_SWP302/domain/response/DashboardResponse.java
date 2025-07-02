package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardResponse {
    private Long totalUsers;
    private Long totalDoctors;
    private Long totalBookings;
    private Long todayBookings;
    private Double todayRevenue;
    private Double monthRevenue;
    private Long totalServices;
    private Long totalFeedback;
    private Long ongoingTreatmentCount;
}