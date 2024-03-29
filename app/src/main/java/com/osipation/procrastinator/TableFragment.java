package com.osipation.procrastinator;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.osipation.procrastinator.adapters.RecyclerAdapter;


import java.util.ArrayList;
import java.util.List;


public class TableFragment extends Fragment {

    public long fromTime;
    public long toTime;

    private RecyclerView timeResultRV;
    private RecyclerAdapter recyclerAdapter;
    private NotificationManagerCompat managerCompat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromTime = getArguments().getLong("FT", 0);
        toTime = getArguments().getLong("TT", 86400000);
        if (toTime < fromTime) {
            toTime += 86400000;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_table, container, false);
//        view.setBackground(new ColorDrawable(ContextCompat.getColor(getActivity(), android.R.color.white)));
        timeResultRV = view.findViewById(R.id.rv_table);
        timeResultRV.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        timeResultRV.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter();

        timeResultRV.setAdapter(recyclerAdapter);

        new Thread(initTimerRunnable(createDataForAdapter(fromTime, toTime))).start();

        return view;
    }


    private Runnable initTimerRunnable(final List<StatsItem> statsItemList) {
        TimerRunnable timerRunnable = new TimerRunnable(statsItemList, new StatsManager() {
            @Override
            public void onManageStat(StatsItem statsItem) {
                recyclerAdapter.getItemList().add(statsItem);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerAdapter.notifyDataSetChanged();
                    }
                });
                createNotification(statsItem);
            }
        });
        return timerRunnable;
    }

    private List<StatsItem> createDataForAdapter(long fromTime, long toTime) {
        double dif = (double) (toTime - fromTime) / 1800000;
        int countOfItems = (int) Math.round(dif);

        List<StatsItem> resultList = new ArrayList<>();

        for (int i = 0; i < countOfItems; i++) {
            StatsItem statsItem = new StatsItem(fromTime);
            resultList.add(statsItem);
            fromTime = statsItem.getToTime();
        }


        return resultList;
    }

    public void createNotification(StatsItem statsItem) {
        String fTime = RecyclerAdapter.timeToString(statsItem.getFromTime());
        String tTime = RecyclerAdapter.timeToString(statsItem.getToTime());

//        String GROUP_KEY_STAT = "key of stat";
        String title = "Procrastinator";
        String message = "Интервал " + fTime + " - " + tTime + " пройден, отметь свой результат!";
        Intent fragmentIntent = new Intent(getActivity(), MainActivity.class);
        fragmentIntent.putExtra("fragment", "notification");
        fragmentIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, fragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT);


//        Intent broadcastIntent = new Intent(getActivity(), NotificationReceiver.class);
//        broadcastIntent.putExtra("toastMessage", message);
//        PendingIntent actionIntent = PendingIntent.getBroadcast(getActivity(),
//                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int notificationId = (int) System.currentTimeMillis();
        Notification notification = new NotificationCompat.Builder(getActivity(), App.CHANNEL)
                .setSmallIcon(R.drawable.ic_infinity)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle(title)
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setColor(Color.BLUE)
//                .setGroup(GROUP_KEY_STAT)
//                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();
        managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(notificationId, notification);
    }

//    public void setSummaryNotification(StatsItem statsItem) {
//        String fTime = RecyclerAdapter.timeToString(statsItem.getFromTime());
//        String tTime = RecyclerAdapter.timeToString(statsItem.getToTime());
//        String countOfMessages = Integer.toString(recyclerAdapter.getItemCount());
//        String GROUP_KEY_STAT = "key of stat";
//        String title = "Procrastinator";
//        String message = "Интервал " + fTime + " - " + tTime + " пройден, отметь свой результат!";
//        Intent fragmentIntent = new Intent(getActivity(), MainActivity.class);
//        fragmentIntent.putExtra("fragment", "notification");
//        fragmentIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, fragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        int notificationId = 1;
//
//
//        Notification summaryNotification = new NotificationCompat.Builder(getActivity(), App.CHANNEL)
//                .setSmallIcon(R.drawable.ic_infinity)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setStyle(new NotificationCompat.InboxStyle()
//                        .addLine(title + " " + message)
//                        .setBigContentTitle(countOfMessages + " интервалов пройдено")
//                        .setSummaryText("Отметь свой результат!"))
//                .setCategory(NotificationCompat.CATEGORY_ALARM)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(contentIntent)
//                .setAutoCancel(true)
//                .setColor(Color.BLUE)
//                .setGroup(GROUP_KEY_STAT)
//                .setGroupSummary(true)
//                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
////                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
//                .build();
//        managerCompat = NotificationManagerCompat.from(getActivity());
//        managerCompat.notify(notificationId, summaryNotification);
//
//    }
}
