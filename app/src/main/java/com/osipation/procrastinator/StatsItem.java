package com.osipation.procrastinator;


public class StatsItem {
    private long fromTime;
    private long toTime;
    private boolean doneOrNot;

    private static final long HALF_HOUR = 1800000;



    public StatsItem(long fromTime) {
        this.fromTime = fromTime;
        this.toTime = fromTime + HALF_HOUR;
        this.doneOrNot = false;

    }


    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getToTime() {
        return toTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }

    public boolean isDoneOrNot() {
        return doneOrNot;
    }

    public void setDoneOrNot(boolean doneOrNot) {
        this.doneOrNot = doneOrNot;
    }
}
