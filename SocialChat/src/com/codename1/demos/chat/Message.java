package com.codename1.demos.chat;

import com.codename1.io.Externalizable;
import com.codename1.io.Log;
import com.codename1.io.Preferences;
import com.codename1.io.Util;
import com.codename1.push.Push;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An externalizable message object that we can store/load dynamically. It allows us to both encapsulate the 
 * JSON logic for transmission and to store local objects for which JSON is no longer necessary.
 *
 * @author Shai Almog
 */
public class Message implements Externalizable {

    private long time;
    private String senderId;
    private String recepientId;
    private String picture;
    private String name;
    private String message;
    
    /**
     * Required default constructor for externalizable to work...
     */
    public Message() {}
    

    public Message(String senderId, String recepientId, String picture, String name, String message) {
        this.senderId = senderId;
        this.recepientId = recepientId;
        this.picture = picture;
        this.name = name;
        this.message = message;
    }
    
    public Message(JSONObject obj) {
        try {
            time = Long.parseLong(obj.getString("time"));
            senderId = obj.getString("fromId");
            recepientId = obj.getString("toId");
            message = obj.getString("message");
            name = obj.getString("name");
            picture = obj.getString("pic");
            
            // update the push id for the given user
            if(obj.has("pushId")) {
                String pushId = obj.getString("pushId");
                if(pushId != null) {
                    Preferences.set("pid-" + senderId, pushId);
                }
            }
        } catch (JSONException ex) {
            // will this ever happen?
            Log.e(ex);
        }        
    }
    
    public JSONObject toJSON() {
        String pushId = Push.getPushKey();
        if(pushId != null) {
            JSONObject obj = createJSONObject("fromId", senderId,
                    "toId", recepientId,
                    "name", name,
                    "pic", picture,
                    "time", Long.toString(System.currentTimeMillis()),
                    "message", message, "pushId", pushId);
            return obj;
        }
        JSONObject obj = createJSONObject("fromId", senderId,
                "toId", recepientId,
                "name", name,
                "pic", picture,
                "time", Long.toString(System.currentTimeMillis()),
                "message", message);
        return obj;
    }
    
    /**
     * Helper method to create a JSONObject
     */
    JSONObject createJSONObject(String... keyValues) {        
        try {
            JSONObject o = new JSONObject();
            for(int iter = 0 ; iter < keyValues.length ; iter += 2) {
                o.put(keyValues[iter], keyValues[iter + 1]);
            }
            return o;
        } catch(JSONException err) {
            // will this ever happen?
            err.printStackTrace();
        }
        return null;
    }
    
    
    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        out.writeLong(time);
        Util.writeUTF(senderId, out);
        Util.writeUTF(recepientId, out);
        Util.writeUTF(picture, out);
        Util.writeUTF(name, out);
        Util.writeUTF(message, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        time = in.readLong();
        senderId = Util.readUTF(in);
        recepientId = Util.readUTF(in);
        picture = Util.readUTF(in);
        name = Util.readUTF(in);
        message = Util.readUTF(in);
    }

    @Override
    public String getObjectId() {
        return "Message";
    }

    public long getTime() {
        return time;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecepientId() {
        return recepientId;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
