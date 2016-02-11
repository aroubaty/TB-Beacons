package tb.heigvd.tb_userapp.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.kontakt.sdk.android.ble.broadcast.BluetoothStateChangeReceiver;
import com.kontakt.sdk.android.ble.broadcast.OnBluetoothStateChangeListener;
import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.IBeaconScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.discovery.BluetoothDeviceEvent;
import com.kontakt.sdk.android.ble.discovery.DistanceSort;
import com.kontakt.sdk.android.ble.discovery.ibeacon.IBeaconDeviceEvent;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.rssi.RssiCalculators;
import com.kontakt.sdk.android.ble.util.BluetoothUtils;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.Proximity;
import com.kontakt.sdk.android.common.log.LogLevel;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import tb.heigvd.tb_userapp.MainActivity;

/**
 * Created by anthony on 11.12.2015.
 */
public class BluetoothManager implements ProximityManager.ProximityListener, OnBluetoothStateChangeListener {
    private static final int REQUEST_CODE_ENABLE_BLUETOOTH = 1;
    protected static final int REQUEST_CODE_CONNECT_TO_DEVICE = 2;

    private ProximityManager deviceManager;
    private MainActivity activity;
    private ScanContext scanContext;

    private BluetoothStateChangeReceiver bluetoothStateChangeReceiver;

    public BluetoothManager(MainActivity activity){
        this.activity = activity;

        //proximity manager thing
        KontaktSDK.initialize(activity.getApplicationContext())
                .setDebugLoggingEnabled(true)
                .setLogLevelEnabled(LogLevel.DEBUG, true)
                .setCrashlyticsLoggingEnabled(true);

        deviceManager = new ProximityManager(activity.getApplicationContext());
    }

    public void onStart() {
        bluetoothStateChangeReceiver = new BluetoothStateChangeReceiver(this);
        activity.getApplicationContext()
                .registerReceiver(bluetoothStateChangeReceiver, new IntentFilter(BluetoothStateChangeReceiver.ACTION));
    }

    public void onStop() {
        activity.getApplicationContext().unregisterReceiver(bluetoothStateChangeReceiver);
    }

    public void onPause() {
        deviceManager.finishScan();
    }

    public void onDestroy() {
        deviceManager.disconnect();
        deviceManager = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ENABLE_BLUETOOTH) {
            if (resultCode != Activity.RESULT_OK)
                Toast.makeText(activity.getApplicationContext(), "Bluetooth not enabled", Toast.LENGTH_LONG).show();


            return;
        } else if (requestCode == REQUEST_CODE_CONNECT_TO_DEVICE) {
            if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(activity.getApplicationContext(), "Beacon authentication failure", Toast.LENGTH_SHORT).show();

            return;
        }
    }

    public void onResume() {
        if (!BluetoothUtils.isBluetoothEnabled()) {
            Toast.makeText(activity.getApplicationContext(), "Please enable bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            startScan();
        }
    }

    private void startScan() {
        deviceManager.initializeScan(getOrCreateScanContext(), new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                deviceManager.attachListener(BluetoothManager.this);
            }

            @Override
            public void onConnectionFailure() {
                Toast.makeText(activity.getApplicationContext(),
                        "Unexpected error occurred during establishing connection to Beacon Service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ScanContext getOrCreateScanContext() {
        if (scanContext == null) {
            scanContext = new ScanContext.Builder()
                    .setScanMode(ProximityManager.SCAN_MODE_BALANCED)
                    .setIBeaconScanContext(new IBeaconScanContext.Builder()
                                    .setRssiCalculator(RssiCalculators.newLimitedMeanRssiCalculator(5))
                                    .build()
                    )
                    .setActivityCheckConfiguration(ActivityCheckConfiguration.DEFAULT)
                    .setForceScanConfiguration(ForceScanConfiguration.DEFAULT)
                    .setScanPeriod(new ScanPeriod(TimeUnit.SECONDS.toMillis(5), 0))
                    .build();
        }
        return scanContext;
    }

    @Override
    public void onBluetoothConnected() {
        startScan();
    }

    @Override
    public void onBluetoothDisconnected() {
        deviceManager.finishScan();
    }

    @Override
    public void onEvent(BluetoothDeviceEvent event) {
        switch (event.getEventType()) {
            case DEVICES_UPDATE:
                onDevicesUpdateEvent(event);
                break;
        }
    }

    private void onDevicesUpdateEvent(final BluetoothDeviceEvent event) {
        final IBeaconDeviceEvent beaconDeviceEvent = (IBeaconDeviceEvent) event;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinkedList<String> idBeacons = new LinkedList<String>();

                for (IBeaconDevice ib : beaconDeviceEvent.getDeviceList()) {
                    if(CustomProximity.fromDistance(ib.getDistance()) == CustomProximity.IMMEDIATE)
                        idBeacons.add(ib.getUniqueId());
                }

                activity.map.update(idBeacons.toArray(new String[idBeacons.size()]));
            }
        });
    }

    @Override
    public void onScanStart() {

    }

    @Override
    public void onScanStop() {

    }

    @Override
    public void onBluetoothDisconnecting() {

    }

    @Override
    public void onBluetoothConnecting() {

    }
}
