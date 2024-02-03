package com.itykhan.watchpricecalculator.data.repository;

import com.itykhan.watchpricecalculator.data.entity.Watch;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class WatchRepositoryTest {

    @Autowired
    private WatchRepository watchRepository;

    @Test
    public void getWatchesNotNull() {
        Collection<Watch> watches = watchRepository.findAll();

        assertNotNull(watches);
    }

}
