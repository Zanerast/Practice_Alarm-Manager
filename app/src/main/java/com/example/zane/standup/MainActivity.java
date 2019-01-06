package com.example.zane.standup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

	private static final int NOTIFICATION_ID = 1;
	private static final String PRIMARY_CHANNEL_ID = "primary_channel_id";


	private ToggleButton toggleButton;
	private NotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toggleButton = findViewById(R.id.tog_alarm);


		Intent notifyIntent = new Intent(this, AlarmReceiver.class);
		boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
						PendingIntent.FLAG_NO_CREATE) != null);
		toggleButton.setChecked(alarmUp);

		final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
						(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


		toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				String toast;
				long time = 1 * 30 * 1000;//AlarmManager.INTERVAL_FIFTEEN_MINUTES;

				if (isChecked) {
					toast = "Stand Up Alarm On!";
					if (alarmManager != null) {
						alarmManager.setInexactRepeating(
										AlarmManager.ELAPSED_REALTIME_WAKEUP,
										SystemClock.elapsedRealtime(),
										time,
										notifyPendingIntent);
					}
				} else {
					if (alarmManager != null) {
						alarmManager.cancel(notifyPendingIntent);
					}
					notificationManager.cancelAll();
					toast = "Stand Up Alarm Off!";
				}

				Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
			}
		});

		createNotificationChannel();
	}


	private void createNotificationChannel() {
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel notificationChannel = new NotificationChannel(
							PRIMARY_CHANNEL_ID, "Stand Up notification", NotificationManager.IMPORTANCE_HIGH);

			notificationChannel.enableLights(true);
			notificationChannel.setLightColor(Color.RED);
			notificationChannel.enableVibration(true);
			notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
			notificationManager.createNotificationChannel(notificationChannel);
		}
	}


}


