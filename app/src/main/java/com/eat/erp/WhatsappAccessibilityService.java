package com.eat.erp;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsappAccessibilityService extends AccessibilityService {
    String tag="whatsapp_service";
    @Override
    public void onInterrupt(){
        //Log.e(tag, "called3");
    }

    @Override
    public void onAccessibilityEvent (AccessibilityEvent event) {
        //Log.e(tag, "called");
        if (getRootInActiveWindow () == null) {
            //Log.e(tag, "called4");
            return;
        }
        //Log.e(tag, "called1");
        AccessibilityNodeInfoCompat rootInActiveWindow = AccessibilityNodeInfoCompat.wrap (getRootInActiveWindow ());

        // Whatsapp Message EditText id
        List<AccessibilityNodeInfoCompat> messageNodeList = rootInActiveWindow.findAccessibilityNodeInfosByViewId ("com.whatsapp:id/entry");
        if (messageNodeList == null || messageNodeList.isEmpty ()) {
            //Log.e(tag, "called2");
            return;
        }

        // check if the whatsapp message EditText field is filled with text and ending with your suffix (explanation above)
        AccessibilityNodeInfoCompat messageField = messageNodeList.get (0);
        if (messageField.getText () == null || messageField.getText ().length () == 0
                || !messageField.getText ().toString ().endsWith (getApplicationContext ().getString (R.string.whatsapp_suffix))) {
            //Log.e(tag, "called true");// So your service doesn't process any message, but the ones ending your apps suffix
            return;
        }

        // Whatsapp send button id
        List<AccessibilityNodeInfoCompat> sendMessageNodeInfoList = rootInActiveWindow.findAccessibilityNodeInfosByViewId ("com.whatsapp:id/send");
        if (sendMessageNodeInfoList == null || sendMessageNodeInfoList.isEmpty ()) {
            //Log.e(tag, "called send true");
            return;
        }

        AccessibilityNodeInfoCompat sendMessageButton = sendMessageNodeInfoList.get (0);
        if (!sendMessageButton.isVisibleToUser ()) {
            //Log.e(tag, "called visible");
            return;
        }

        // Now fire a click on the send button
        sendMessageButton.performAction (AccessibilityNodeInfo.ACTION_CLICK);
        //Log.e(tag, "called clicked");
        // Now go back to your app by clicking on the Android back button twice:
        // First one to leave the conversation screen
        // Second one to leave whatsapp
        try {
            Thread.sleep (500); // hack for certain devices in which the immediate back click is too fast to handle
            //performGlobalAction (GLOBAL_ACTION_BACK);
        } catch (InterruptedException ignored) {}
        //performGlobalAction (GLOBAL_ACTION_BACK);
    }

    @Override
    public void onServiceConnected()
    {
        Log.e(tag, "started");
        AccessibilityServiceInfo config = getServiceInfo();//This IS A REALLY IMPORTANT CHANGE!!!
        //By instantiating a new "AccessibilityServiceInfo" you have overwritten all of your XML Config changes, as well as any default settings internal to Android!
        //DON'T instantiate your own ServiceConfig objects!!!! Just don't. In fact, do everything in XML instead!

        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
        super.onServiceConnected();
    }
}

