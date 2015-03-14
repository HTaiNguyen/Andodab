package fr.upem.andodab.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import fr.upem.andodab.R;

public class NotificationService extends Service {
	private NotificationServiceReceiver notificationServiceReceiver;

	public final static String ACTION = "NotificationServiceAction";
	public final static String STOP_SERVICE_BROADCAST_KEY = "StopServiceBroadcastKey";
	public final static int RQS_STOP_SERVICE = 1;


	public NotificationService() {
	}

	@Override
	public void onCreate() {
		notificationServiceReceiver = new NotificationServiceReceiver();
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION);
		registerReceiver(notificationServiceReceiver, intentFilter);

		// Send Notification
		String notificationTitle = "Andodab's Notification";
		String notificationText = "Please get the lastest update";
		
		PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		Notification notification = new Notification.Builder(this)
			.setContentTitle(notificationTitle)
			.setContentText(notificationText)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pendingIntent)
			.getNotification(); //rather use getNotification() than build() for API 14
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, notification); 
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		this.unregisterReceiver(notificationServiceReceiver);
		super.onDestroy();
	}

	public class NotificationServiceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int rqs = intent.getIntExtra(STOP_SERVICE_BROADCAST_KEY, 0);

			if(rqs == RQS_STOP_SERVICE) {
				stopSelf();
				((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancelAll();
			}
		}
	}
}
