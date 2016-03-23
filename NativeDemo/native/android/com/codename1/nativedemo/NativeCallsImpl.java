package com.codename1.nativedemo;

import android.app.Activity;
import com.codename1.impl.android.AndroidNativeUtil;

public class NativeCallsImpl {
    public void testVoid() {
    }

    public void testMultipleArgs(int param, byte param1, char param2, short param3, double param4, float param5, boolean param6, String param7, int[] param8) {
    }

    public android.view.View createNativeButton(String param) {
        Activity activity = AndroidNativeUtil.getActivity();
        android.widget.Button b = new android.widget.Button(activity);
        b.setText(param);
        return b;
    }

    public byte[] getArrayData() {
        return null;
    }

    public boolean isSupported() {
        return true;
    }

}
