package com.iad4.iadlab4.area;

import java.util.Date;

public class HitChecker {

    public HitChecker() {
    }
    public HitData checkHit(InputData inputData) {
        return checkHit(inputData.getX(), inputData.getY(), inputData.getR());
    }
    public HitData checkHit(double x, double y, double r) {
        long currentTime = System.nanoTime();
        boolean result;

        if (x >= 0) {
            if (y >= 0) {
                result = x * x + y * y <= r * r;
            }
            else {
                result = x <= r && y >= -r / 2d;
            }
        } else {
            if (y >= 0) {
                result = y <= x + r;
            } else {
                result = false;
            }
        }
        return new HitData(x, y, r, (System.nanoTime() - currentTime) / 1000000000d, new Date(System.currentTimeMillis()), result);
    }
}
