package com.iad4.iadlab4.area;

import com.iad4.iadlab4.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class HitData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double x;
    private double y;
    private double r;
    private double executionTime;
    private Date dateTime;
    private boolean isPointInArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    HitData(double x, double y, double r, double executionTime, Date currentTime, boolean isPointInArea) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.executionTime = executionTime;
        this.dateTime = currentTime;
        this.isPointInArea = isPointInArea;
    }

    public HitData() {
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }
    public void setR(double r) {
        this.r = r;
    }

    public double getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public Date getCurrentTime() {
        return dateTime;
    }
    public void setCurrentTime(Date currentTime) {
        this.dateTime = currentTime;
    }

    public boolean isPointInArea() {
        return isPointInArea;
    }
    public void setPointInArea(boolean pointInArea) {
        isPointInArea = pointInArea;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
