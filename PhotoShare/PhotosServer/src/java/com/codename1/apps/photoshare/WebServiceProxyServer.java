/*
 * This is generated code, copyright free and unrestricted
 */
package com.codename1.apps.photoshare;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class WebServiceProxyServer {
    
    public static long[] listPhotos() {
        return ImageFile.listPhotos();
    }

    public static int getPhotoLikes(long photoId) {
        return ImageFile.getLikeCount(photoId);
    }

    public static void likePhoto(long photoId, String myId) {
        ImageFile.like(photoId, myId);
    }

    public static void login(String name, String email, String key) {
        
    }

    public static String createAccount(String email) {
        return "1111";
    }

    public static String[] getPhotoDetails(long photoId) {
        return ImageFile.getPhotoDetails(photoId);
    }

}
