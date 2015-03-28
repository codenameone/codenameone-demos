/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chen
 */
public class ServerAccess {

    public static List getEntriesFromFlickrService(String tag) {
        
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://api.flickr.com/services/feeds/photos_public.gne");
            req.setPost(false);
            req.addArgument("tagmode", "any");
            req.addArgument("tags", tag);
            req.addArgument("format", "json");
            
            NetworkManager.getInstance().addToQueueAndWait(req);
            byte[] data = req.getResponseData();
            if (data == null) {
                throw new IOException("Network Err");
            }
            JSONParser parser = new JSONParser();
            Map response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
            System.out.println("res" + response);
            List items = (List)response.get("items");
            return items;
        } catch (Exception e) {
        }
        return null;
    }

}
