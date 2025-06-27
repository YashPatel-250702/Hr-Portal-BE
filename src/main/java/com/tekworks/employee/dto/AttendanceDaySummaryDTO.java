package com.tekworks.employee.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDaySummaryDTO {
    private LocalDate date;
    private List<AttendanceEventDTO> events;
    private String workedHours;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttendanceEventDTO {
        private String type;
        private LocalDateTime timestamp;
    }

  

}
