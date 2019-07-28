package com.eslam.whatsappsendmessage;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsappAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("eslamfaisal", "onServiceConnected: ");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (getRootInActiveWindow() == null) {
            Log.d("eslamfaisal", "onAccessibilityEvent: ");
            return;
        }

        AccessibilityNodeInfoCompat rootInActiveWindow = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

        // Whatsapp Message EditText id
        List<AccessibilityNodeInfoCompat> messageNodeList = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
        if (messageNodeList == null || messageNodeList.isEmpty()) {
            Log.d("eslamfaisal", "messageNodeList: ");
            return;
        }

//        // check if the whatsapp message EditText field is filled with text and ending with your suffix (explanation above)
//        AccessibilityNodeInfoCompat messageField = messageNodeList.get(0);
//        if (messageField.getText() == null || messageField.getText().length() == 0
//                || !messageField.getText().toString().endsWith(getApplicationContext().getString(R.string.whatsapp_suffix))) { // So your service doesn't process any message, but the ones ending your apps suffix
//            Log.d("eslamfaisal", "messageField: ");
//            return;
//        }

        // Whatsapp send button id
        List<AccessibilityNodeInfoCompat> sendMessageNodeInfoList = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        if (sendMessageNodeInfoList == null || sendMessageNodeInfoList.isEmpty()) {
            Log.d("eslamfaisal", "sendMessageNodeInfoList: ");
            return;
        }

        AccessibilityNodeInfoCompat sendMessageButton = sendMessageNodeInfoList.get(0);
        if (!sendMessageButton.isVisibleToUser()) {
            Log.d("eslamfaisal", "sendMessageButton: ");
            return;
        }

        // Now fire a click on the send button
        sendMessageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        Log.d("eslamfaisal", "performAction: ");
        // Now go back to your app by clicking on the Android back button twice:
        // First one to leave the conversation screen
        // Second one to leave whatsapp
//        try {
//            Thread.sleep(500); // hack for certain devices in which the immediate back click is too fast to handle
//            performGlobalAction(GLOBAL_ACTION_BACK);
//            Thread.sleep(500);  // same hack as above
//        } catch (InterruptedException ignored) {
//            Log.d("eslamfaisal", "InterruptedException:"+ignored.getMessage());
//        }
//        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    @Override
    public void onInterrupt() {
        Log.d("eslamfaisal", "onInterrupt");
    }
}
