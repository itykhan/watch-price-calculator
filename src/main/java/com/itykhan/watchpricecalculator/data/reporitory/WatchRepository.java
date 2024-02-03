package com.itykhan.watchpricecalculator.data.reporitory;

import com.itykhan.watchpricecalculator.data.entity.Watch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchRepository extends JpaRepository<Watch, String> {
}
