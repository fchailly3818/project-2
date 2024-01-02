/*
 * ActivityDiary
 *
 * Copyright (C) 2023 Raphael Mack http://www.raphael-mack.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.rampro.activitydiary.ui.main;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.zip.Inflater;

import de.rampro.activitydiary.R;
import de.rampro.activitydiary.db.ActivityDiaryContract;
import de.rampro.activitydiary.ui.history.HistoryDetailActivity;

public class AlarmActivity extends AppCompatActivity {

         View.OnClickListener startBtnListner;
         View.OnClickListener endBtnListener;
         Button startBtn;
         Button endBtn;
        private Calendar start, storedStart;

        private Calendar end, storedEnd;
        private static final long dayInMils = 24 * 60 * 60 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_history_detail_content, null, false);
        setContentView(R.layout.activity_alarm);

        startBtn = contentView.findViewById(R.id.time_start);
        endBtn = contentView.findViewById(R.id.time_end);


        start = Calendar.getInstance();
        startBtnListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartPickerDialog(v);
            }
        };
        startBtn.setOnClickListener(startBtnListner);


        end = Calendar.getInstance();
        endBtnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndPickerDialog(v);
            }
        };
        endBtn.setOnClickListener(endBtnListener);







        // 设置布局文件
    }
    public static class TimePickerFragment extends DialogFragment {
        private int hour, minute;
        private TimePickerDialog.OnTimeSetListener listener;

        public void setData(TimePickerDialog.OnTimeSetListener listener,
                            int hour, int minute){
            this.hour = hour;
            this.minute = minute;
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), listener, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }
    public void showStartPickerDialog(View v) {
        AlarmActivity.TimePickerFragment newFragment = new AlarmActivity.TimePickerFragment();
        newFragment.setData(new TimePickerDialog.OnTimeSetListener (){
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    start.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    start.set(Calendar.MINUTE, minute);
                                    start.set(Calendar.SECOND, 0);
                                    start.set(Calendar.MILLISECOND, 0);

                                    Long newStart = Long.valueOf(start.getTimeInMillis());
                                    setAlarm(newStart,StartActivity.class);
                                }
                            }
                , start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE));
        newFragment.show(getSupportFragmentManager(), "startTimePicker");
    }


   // private void setAlarm(Long l){
   //     Intent alarm = new Intent(this, AlarmReceiver.class);
   //     PendingIntent pend = PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
   //     AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
   //     long triggertime = ( l - SystemClock.elapsedRealtime() + dayInMils) % dayInMils;
   //     alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggertime, pend);
   // }

   // private void setAlarm(Long l, Class<?> destinationActivity) {
        // 创建一个新的 Intent 用于启动目标 Activity
   //     Intent startActivityIntent = new Intent(this, destinationActivity);

        // 使用 PendingIntent 包含启动目标 Activity 的 Intent
   //     PendingIntent pendingIntent = PendingIntent.getActivity(
   //             this,
   //             0,
   //             startActivityIntent,
   //             PendingIntent.FLAG_UPDATE_CURRENT
   //     );

   //     AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
   //     long triggerTime = l;  // 使用 System.currentTimeMillis() 代替 SystemClock.elapsedRealtime()
   //     alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
   // }

    private void setAlarm(Long l, Class<?> destinationActivity) {
        // 创建一个新的 Intent 用于启动目标 Activity
        Intent startActivityIntent = new Intent(this, destinationActivity);

        // 使用 PendingIntent 包含启动目标 Activity 的 Intent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // 获取系统默认提示音
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        // 创建一个 AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // 使用 set 方法设置闹钟，其中 setSound 设置提示音
        alarmManager.set(AlarmManager.RTC_WAKEUP, l, pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(l, pendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
        }
    }


    public void showEndPickerDialog(View v) {
        AlarmActivity.TimePickerFragment newFragment = new AlarmActivity.TimePickerFragment();
        newFragment.setData(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                end.set(Calendar.HOUR_OF_DAY, hourOfDay);
                end.set(Calendar.MINUTE, minute);
                end.set(Calendar.SECOND, 0);
                end.set(Calendar.MILLISECOND, 0);

                Long newEnd = Long.valueOf(end.getTimeInMillis());
                setAlarm(newEnd,EndActivity.class);  // 或者进行其他结束时间的处理
            }
        }, end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
        newFragment.show(getSupportFragmentManager(), "endTimePicker");
    }







}




