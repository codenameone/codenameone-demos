package com.codename1.demos.sbaitso;

import android.speech.tts.TextToSpeech;
import com.codename1.impl.android.AndroidNativeUtil;
import java.util.Locale;

public class TTSImpl {
    private TextToSpeech ttobj;
    public void say(final String param) {
        if(ttobj == null) {
            ttobj = new TextToSpeech(AndroidNativeUtil.getActivity(),
                    new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                ttobj.setLanguage(Locale.UK);
                                ttobj.speak(param, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    });
            return;
        }
        ttobj.speak(param, TextToSpeech.QUEUE_FLUSH, null);
    }

    public boolean isSupported() {
        return true;
    }

}
