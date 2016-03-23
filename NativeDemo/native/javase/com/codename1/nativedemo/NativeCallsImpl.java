package com.codename1.nativedemo;

import javax.swing.SwingUtilities;

public class NativeCallsImpl implements com.codename1.nativedemo.NativeCalls{
    
    public void testVoid() {
    }

    public void testMultipleArgs(int param, byte param1, char param2, short param3, double param4, float param5, boolean param6, String param7, int[] param8) {
    }

    public com.codename1.ui.PeerComponent createNativeButton(final String param) {
        final java.awt.Component [] cmp = new java.awt.Component[1];
        try {
            //swing Components needs to be created on the swing EDT
            SwingUtilities.invokeAndWait(new Runnable() {
                
                public void run() {
                    cmp[0] = new javax.swing.JButton(param);
                }
            });
        } catch (Exception ex) {
        }
        
        return com.codename1.ui.PeerComponent.create(cmp[0]);
    }

    public byte[] getArrayData() {
        return null;
    }

    public boolean isSupported() {
        return true;
    }

}
