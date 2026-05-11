package com.ticketlock.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String email;

    private String phone;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb") // "json" for MySQL, "jsonb" for PostgreSQL
    private List<HistoryRecord> bookingHistory;

    //Using simple Standard JPA
//    @ElementCollection
//    @CollectionTable(name = "user_booking_history", joinColumns = @JoinColumn(name = "user_id"))
//    private List<String> historyEntries;

}
