package com.codename1.nativedemo;

public class NativeCallsImpl {
    public void testVoid() {
    }

    public void testMultipleArgs(int param, byte param1, char param2, short param3, double param4, float param5, boolean param6, String param7, int[] param8) {
    }

    public net.rim.device.api.ui.Field createNativeButton(String param) {
        synchronized(net.rim.device.api.ui.UiApplication.getEventLock()) {
            return new net.rim.device.api.ui.component.ButtonField(param);
        }
    }

    public byte[] getArrayData() {
        return null;
    }

    public boolean isSupported() {
        return true;
    }

}
