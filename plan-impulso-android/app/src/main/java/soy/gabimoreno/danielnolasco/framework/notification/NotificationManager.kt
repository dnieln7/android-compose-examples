package soy.gabimoreno.danielnolasco.framework.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import soy.gabimoreno.danielnolasco.domain.extensions.isPlayful
import soy.gabimoreno.danielnolasco.domain.model.Cat
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.ui.navigation.CAT_DETAIL_DEEPLINK_ROUTE
import soy.gabimoreno.danielnolasco.ui.navigation.DOG_DETAIL_DEEPLINK_ROUTE
import xyz.dnieln7.core.res.R

@Singleton
class NotificationManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val manager get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createGeneralNotificationsChannel()
    }

    private fun createGeneralNotificationsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                GENERAL_CHANNEL_ID,
                GENERAL_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                enableVibration(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

            manager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun launchNotification(id: Int, notification: Notification) {
        NotificationManagerCompat.from(context).notify(id, notification)
    }

    fun clearNotifications() {
        NotificationManagerCompat.from(context).cancelAll()
    }

    fun buildCatNotification(cat: Cat): Notification {
        val uri = CAT_DETAIL_DEEPLINK_ROUTE
            .plus("/")
            .plus(cat.name)
            .toUri()

        val intent = Intent(Intent.ACTION_VIEW, uri)

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        return NotificationCompat.Builder(context, GENERAL_CHANNEL_ID)
            .setSmallIcon(soy.gabimoreno.danielnolasco.R.drawable.ic_cat)
            .setContentTitle(cat.name)
            .setContentText(cat.origin)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun buildDogNotification(dog: Dog): Notification {
        val uri = DOG_DETAIL_DEEPLINK_ROUTE
            .plus("/")
            .plus(dog.name)
            .toUri()

        val intent = Intent(Intent.ACTION_VIEW, uri)

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val contentText = if (dog.isPlayful) {
            context.getString(R.string.playful)
        } else {
            null
        }

        return NotificationCompat.Builder(context, GENERAL_CHANNEL_ID)
            .setSmallIcon(soy.gabimoreno.danielnolasco.R.drawable.ic_dog)
            .setContentTitle(dog.name)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun buildLocationServiceNotification(): Notification {
        val title = context.getString(R.string.walking_app)
        val content = context.getString(R.string.walking_session_progress)

        return NotificationCompat.Builder(context, GENERAL_CHANNEL_ID)
            .setSmallIcon(soy.gabimoreno.danielnolasco.R.drawable.ic_walking)
            .setContentTitle(title)
            .setOngoing(true)
            .setContentText(content)
            .setTicker(content)
            .build()
    }
}

private const val GENERAL_CHANNEL_ID = "soy.gabimoreno.danielnolasco.GENERAL"
private const val GENERAL_CHANNEL_NAME = "General notifications"

const val CAT_NOTIFICATION_ID = 1
const val DOG_NOTIFICATION_ID = 2
const val LOCATION_SERVICE_NOTIFICATION_ID = 3
