package be.mygod.vpnhotspot

import android.bluetooth.BluetoothManager
import android.content.*
import android.graphics.drawable.Icon
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import be.mygod.vpnhotspot.R
import be.mygod.vpnhotspot.TetheringService
import be.mygod.vpnhotspot.manage.BluetoothTethering
import be.mygod.vpnhotspot.net.TetherType
import be.mygod.vpnhotspot.net.TetheringManager
import be.mygod.vpnhotspot.net.TetheringManager.tetheredIfaces
import be.mygod.vpnhotspot.net.wifi.WifiApManager
import be.mygod.vpnhotspot.util.broadcastReceiver
import be.mygod.vpnhotspot.util.readableMessage
import be.mygod.vpnhotspot.util.stopAndUnbind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MyBroadcastReceiver : BroadcastReceiver(), TetheringManager.StartTetheringCallback {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.toString().contains("BT_TETHER_START")) {
            TetheringManager.startTethering(TetheringManager.TETHERING_BLUETOOTH, false, this)
        }
        if (intent.action.toString().contains("BT_TETHER_STOP")) {
            TetheringManager.stopTethering(TetheringManager.TETHERING_BLUETOOTH)
            onTetheringStarted()  // force flush state
        }
    }
}
