package com.example.accessibilityservicetest;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccessibilityServiceTest extends AccessibilityService {

    private static final String TAG = "myService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfoCompat textNodeInfoCompat = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());
        //Log.e(TAG, "text node info compat : "+textNodeInfoCompat);
        List<AccessibilityNodeInfoCompat>l = textNodeInfoCompat.findAccessibilityNodeInfosByText(ContactConfig.contact);
        if(l != null && !l.isEmpty()) {
            l.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            Log.e(TAG, "click 1 : " + l);
            l.clear();
            List<AccessibilityNodeInfoCompat>lDialBox2 = new ArrayList<>();
            for (int i = 0; i < textNodeInfoCompat.getChildCount(); i++) {

                AccessibilityNodeInfoCompat result = textNodeInfoCompat.getChild(i);
                if (result != null) {
                    Log.e(TAG, "result child l ------ : " + result.getClassName().toString() + " name l : "+result.getContentDescription());
                    lDialBox2.add(result);
                }
            }
            if(lDialBox2.size() == 10) {
                Log.e(TAG, "click send l");
                Log.e(TAG,"try send l = "+lDialBox2.size());
                lDialBox2.get(9).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            if(lDialBox2.size() == 11) {
                Log.e(TAG,"list size before send = "+lDialBox2.size());
                Log.e(TAG, "set text");
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, ContactConfig.msg);
                lDialBox2.get(7).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
            }
        }

        if(event.getEventType() == TYPE_WINDOW_STATE_CHANGED) {
            List<AccessibilityNodeInfoCompat>lDialBox = new ArrayList<>();
            for (int i = 0; i < textNodeInfoCompat.getChildCount(); i++) {

                AccessibilityNodeInfoCompat result = textNodeInfoCompat.getChild(i);
                if (result != null) {
                    Log.e(TAG, "result child ------ : " + result.getClassName().toString() + " name : " + result.getContentDescription());
                    lDialBox.add(result);
                }
            }

            if(lDialBox.size() == 9) {
                Log.e(TAG,"window all contacts elements size in window = " + lDialBox.size());
                lDialBox.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

            // click image button to send message in dialBox contact
            if(lDialBox.size() == 6) {
                Log.e(TAG,"size elements in dialbox = "+lDialBox.size());
                Log.e(TAG, "click in dial Box contact");
                lDialBox.get(2).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

            // set message input in edit text
            if(lDialBox.size() == 11) {
                Log.e(TAG,"size window before send = " + lDialBox.size());
                Log.e(TAG, "set text");
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, ContactConfig.msg);
                lDialBox.get(7).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
            }

            if(lDialBox.size() == 10) {
                Log.e(TAG, "click 3 send message");
                Log.e(TAG,"size window contact send message = " + lDialBox.size());
                lDialBox.get(9).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

        }

    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "interrupt...");
    }

    @Override
    public void onServiceConnected() {

        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.packageNames = new String[] {"com.whatsapp"};
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 1000;

        this.setServiceInfo(info);

        Log.e(TAG, "service connected..." + Arrays.toString(info.packageNames));

    }

}
