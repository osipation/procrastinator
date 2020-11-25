package com.osipation.procrastinator;

import java.util.List;


public class TimerRunnable implements Runnable {

    private List<StatsItem> statsItemList;
    private StatsManager statsManager;

    TimerRunnable(List<StatsItem> statsItemList, StatsManager statsManager) {
        this.statsItemList = statsItemList;
        this.statsManager = statsManager;
    }

    @Override
    public void run() {
        for (int i = 0; i < statsItemList.size(); i++) {
            try {
                Thread.sleep(5000);
                statsManager.onManageStat(statsItemList.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
