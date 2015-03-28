package com.codename1.nativedemo;

public class NativeCallsImpl {
    public void testVoid() {
    }

    public void testMultipleArgs(int param, byte param1, char param2, short param3, double param4, float param5, boolean param6, String param7, int[] param8) {
    }

    public android.view.View createNativeButton(String param) {
        android.widget.Button b = new android.widget.Button((android.content.Context)NativeDemo.getContext());
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
