package userclasses;

import android.content.Intent;
import com.codename1.demo.zxing.Zxing;
import com.codename1.impl.android.IntentResultListener;

public class ZXingNativeCallsImpl {
    private String contents;
    private String format;
    private int result;
    public void scan() {
        result = ZXingNativeCalls.PENDING;
        com.codename1.impl.android.CodenameOneActivity ctx = (com.codename1.impl.android.CodenameOneActivity)Zxing.getContext();
        android.content.Intent intent = new android.content.Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        ctx.setIntentResultListener(new IntentResultListener() {
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == 0) {
                    if (resultCode == com.codename1.impl.android.CodenameOneActivity.RESULT_OK) {
                        contents = data.getStringExtra("SCAN_RESULT");
                        format = data.getStringExtra("SCAN_RESULT_FORMAT");
                        result = ZXingNativeCalls.OK;
                    } else if (resultCode == com.codename1.impl.android.CodenameOneActivity.RESULT_CANCELED) {
                        // Handle cancel
                        result = ZXingNativeCalls.ERROR;
                    }
                }
            }
        });
        ctx.startActivityForResult(intent, 0);
    }

    public String getType() {
        return format;
    }

    public String getResult() {
        return contents;
    }

    public int getStatus() {
        return result;
    }

    public boolean isSupported() {
        return true;
    }

}
