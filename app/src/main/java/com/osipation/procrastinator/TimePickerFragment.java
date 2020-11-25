package com.osipation.procrastinator;



import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import java.util.Calendar;



public class TimePickerFragment extends Fragment {
    private TimePicker fromTP;
    private TimePicker toTP;
    private Calendar now = Calendar.getInstance();
    private Button startButton;
    private long fromTime = now.getTimeInMillis();
    private long toTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);

        fromTP = view.findViewById(R.id.from_tp);
        toTP = view.findViewById(R.id.to_tp);
        startButton = view.findViewById(R.id.start_button);

        initListeners();
        fromTP.setIs24HourView(true);
        toTP.setIs24HourView(true);
        toTP.setHour(0);
        toTP.setMinute(0);

        return view;
    }

    private void initListeners() {
        fromTP.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                fromTime = calendar.getTimeInMillis();
            }
        });


        toTP.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                toTime = calendar.getTimeInMillis();
            }
        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putLong("FT", fromTime);
                args.putLong("TT", toTime);
                TableFragment tableFragment = new TableFragment();
                tableFragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .add(R.id.container, tableFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });
    }

}