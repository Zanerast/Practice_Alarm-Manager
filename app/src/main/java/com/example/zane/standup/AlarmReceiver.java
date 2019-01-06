package com.example.zane.standup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

	private static final int NOTIFICATION_ID = 1;
	private static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
	private NotificationManager notificationManager;


	@Override
	public void onReceive(Context context, Intent intent) {
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		deliverNotification(context);
	}

	private void deliverNotification(Context context){
		Intent intent = new Intent(context, MainActivity.class);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
						.setSmallIcon(R.drawable.ic_stat_name)
						.setContentTitle("Stand Up Alert")
						.setContentText("You should stand up and walk around now!")
						.setContentIntent(pendingIntent)
						.setPriority(NotificationCompat.PRIORITY_HIGH)
						.setAutoCancel(true)
						.setDefaults(NotificationCompat.DEFAULT_ALL);

		notificationManager.notify(NOTIFICATION_ID, builder.build());

	}
}
