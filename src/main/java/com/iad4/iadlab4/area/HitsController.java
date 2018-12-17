package com.iad4.iadlab4.area;

import com.iad4.iadlab4.authentication.TokenController;
import com.iad4.iadlab4.user.User;
import com.iad4.iadlab4.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class HitsController {
    private HitChecker hitChecker;
    private TokenController tokenController;
    private final HitDataService hitDataService;
    private final UserService userService;

    @Autowired
    public HitsController(HitDataService hitDataService, UserService userService) {
        hitChecker = new HitChecker();
        tokenController = new TokenController();
        this.hitDataService = hitDataService;
        this.userService = userService;
    }

    @RequestMapping("/checkPoint")
    public HitData checkPoint(@RequestBody InputData input, HttpServletRequest request) {
        HitData hitData = hitChecker.checkHit(input);
        String token = tokenController.resolveToken(request);
        User user = userService.getOne(tokenController.getUserId(token));
        hitData.setUser(user);
        hitDataService.addHitData(hitData);
        return hitData;
    }
    @RequestMapping("/hitData")
    public List<HitData> getHitData(HttpServletRequest request) {
        String token = tokenController.resolveToken(request);
        User user = userService.getOne(tokenController.getUserId(token));
        return hitDataService.findHitDataByUser(user);
    }
}
