package com.iad4.iadlab4.area;

import com.iad4.iadlab4.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HitDataRepository extends JpaRepository<HitData, Long> {
    List<HitData> getByUser(@Param("User") User user);
}