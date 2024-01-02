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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    String text;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 在这里处理闹钟触发后的操作，例如显示通知或弹出提醒

        String message = intent.getStringExtra("ALARM_MESSAGE");
        String activityIdentifier = intent.getStringExtra("ACTIVITY_IDENTIFIER");
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            if ("START_ACTIVITY".equals(activityIdentifier)) {
                Intent startIntent = new Intent(context, StartActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startIntent);
            } else if ("END_ACTIVITY".equals(activityIdentifier)) {
                Intent endIntent = new Intent(context, EndActivity.class);
                endIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(endIntent);
            }
        }
    }







        //Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    public AlarmReceiver(){
        super();
        //text = "开始时间到";
    }
}
