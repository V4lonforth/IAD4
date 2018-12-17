package com.iad4.iadlab4.area;

import com.iad4.iadlab4.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HitDataService {
    private final HitDataRepository hitDataRepository;

    @Autowired
    public HitDataService(HitDataRepository hitDataRepository) {
        this.hitDataRepository = hitDataRepository;
    }

    public List<HitData> findAll() {
        return (List<HitData>)hitDataRepository.findAll();
    }

    public void addHitData(HitData hitData) {
        hitDataRepository.saveAndFlush(hitData);
    }
    public List<HitData> findHitDataByUser(User user) {
        return hitDataRepository.getByUser(user);
    }
}
