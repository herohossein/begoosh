package com.act.voicecommand;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class VoiceWidget extends AppWidgetProvider {

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId) {

    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.voice_widget);


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);

    Intent intent = new Intent(context, VoiceDialog.class);
    // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
    // Here the basic operations the remote view can do.
    views.setOnClickPendingIntent(R.id.button, pendingIntent);
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

