package com.epay.repository;

import com.epay.entity.UserServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServicesRepository extends JpaRepository<UserServices, Long> {
    List<UserServices> findByUserUserId(Long userId);
    List<UserServices> findByServiceServiceId(Long serviceId);
}
