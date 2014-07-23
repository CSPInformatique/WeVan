package com.cspinformatique.wevan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.wevan.entity.ReservationNotification;
import com.cspinformatique.wevan.entity.ReservationNotification.Status;

public interface ReservationNotificationRepository extends JpaRepository<ReservationNotification, Long> {
	public List<ReservationNotification> findByStatusOrderByTimestampAsc(Status status);
}
