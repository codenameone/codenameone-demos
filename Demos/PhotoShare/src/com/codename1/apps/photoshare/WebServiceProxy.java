/*
 * This is generated code, copyright free and unrestricted
 */
package com.codename1.apps.photoshare;

import com.codename1.io.WebServiceProxyCall;
import com.codename1.io.Externalizable;
import com.codename1.util.Callback;
import java.io.IOException;


public class WebServiceProxy {
    private static final String DESTINATION_URL = PhotoShare.SERVER_URL + "cn1proxy";

    private static final WebServiceProxyCall.WSDefinition def_listPhotos = WebServiceProxyCall.defineWebService(DESTINATION_URL, "listPhotos", WebServiceProxyCall.TYPE_LONG_ARRAY);
    private static final WebServiceProxyCall.WSDefinition def_getPhotoLikes = WebServiceProxyCall.defineWebService(DESTINATION_URL, "getPhotoLikes", WebServiceProxyCall.TYPE_INT, WebServiceProxyCall.TYPE_LONG);
    private static final WebServiceProxyCall.WSDefinition def_likePhoto = WebServiceProxyCall.defineWebService(DESTINATION_URL, "likePhoto", WebServiceProxyCall.TYPE_VOID, WebServiceProxyCall.TYPE_LONG, WebServiceProxyCall.TYPE_STRING);
    private static final WebServiceProxyCall.WSDefinition def_login = WebServiceProxyCall.defineWebService(DESTINATION_URL, "login", WebServiceProxyCall.TYPE_VOID, WebServiceProxyCall.TYPE_STRING, WebServiceProxyCall.TYPE_STRING, WebServiceProxyCall.TYPE_STRING);
    private static final WebServiceProxyCall.WSDefinition def_createAccount = WebServiceProxyCall.defineWebService(DESTINATION_URL, "createAccount", WebServiceProxyCall.TYPE_STRING, WebServiceProxyCall.TYPE_STRING);
    private static final WebServiceProxyCall.WSDefinition def_getPhotoDetails = WebServiceProxyCall.defineWebService(DESTINATION_URL, "getPhotoDetails", WebServiceProxyCall.TYPE_STRING_ARRAY, WebServiceProxyCall.TYPE_LONG);
    public static long[] listPhotos() throws IOException {
        return ((long[])WebServiceProxyCall.invokeWebserviceSync(def_listPhotos));
    }

    public static void listPhotosAsync(Callback<long[]> returnValueFromMethod) {
        WebServiceProxyCall.invokeWebserviceASync(def_listPhotos, returnValueFromMethod);
    }

    public static int getPhotoLikes(long photoId) throws IOException {
        return ((Integer)WebServiceProxyCall.invokeWebserviceSync(def_getPhotoLikes, photoId)).intValue();
    }

    public static void getPhotoLikesAsync(long photoId, Callback<Integer> returnValueFromMethod) {
        WebServiceProxyCall.invokeWebserviceASync(def_getPhotoLikes, returnValueFromMethod, photoId);
    }

    public static void likePhoto(long photoId, String myId) throws IOException {
        WebServiceProxyCall.invokeWebserviceSync(def_likePhoto, photoId, myId);
    }

    public static void likePhotoAsync(long photoId, String myId, Callback<Void> returnValueFromMethod) {
        WebServiceProxyCall.invokeWebserviceASync(def_likePhoto, returnValueFromMethod, photoId, myId);
    }

    public static void login(String name, String email, String key) throws IOException {
        WebServiceProxyCall.invokeWebserviceSync(def_login, name, email, key);
    }

    public static void loginAsync(String name, String email, String key, Callback<Void> returnValueFromMethod) {
        WebServiceProxyCall.invokeWebserviceASync(def_login, returnValueFromMethod, name, email, key);
    }

    public static String createAccount(String email) throws IOException {
        return ((String)WebServiceProxyCall.invokeWebserviceSync(def_createAccount, email));
    }

    public static void createAccountAsync(String email, Callback<String> returnValueFromMethod) {
        WebServiceProxyCall.invokeWebserviceASync(def_createAccount, returnValueFromMethod, email);
    }

    public static String[] getPhotoDetails(long photoId) throws IOException {
        return ((String[])WebServiceProxyCall.invokeWebserviceSync(def_getPhotoDetails, photoId));
    }

    public static void getPhotoDetailsAsync(long photoId, Callback<String[]> returnValueFromMethod) {
        WebServiceProxyCall.invokeWebserviceASync(def_getPhotoDetails, returnValueFromMethod, photoId);
    }

}
