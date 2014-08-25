/*
 * This is generated code, copyright free and unrestricted
 */
package com.codename1.apps.photoshare;

import com.codename1.io.Externalizable;
import com.codename1.proxy.server.ProxyServerHelper;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a generated servlet that maps calls to the Server class static methods,
 * your code should go in that class!
 *
 * @author Codename One
 */
@WebServlet(name = "CN1WebServiceServlet", urlPatterns = {"/cn1proxy"})
public class CN1WebServiceServlet extends HttpServlet {
    private static final ProxyServerHelper.WSDefinition def_listPhotos = ProxyServerHelper.createServiceDefinition("listPhotos", ProxyServerHelper.TYPE_LONG_ARRAY);
    private static final ProxyServerHelper.WSDefinition def_getPhotoLikes = ProxyServerHelper.createServiceDefinition("getPhotoLikes", ProxyServerHelper.TYPE_INT, ProxyServerHelper.TYPE_LONG);
    private static final ProxyServerHelper.WSDefinition def_likePhoto = ProxyServerHelper.createServiceDefinition("likePhoto", ProxyServerHelper.TYPE_VOID, ProxyServerHelper.TYPE_LONG, ProxyServerHelper.TYPE_STRING);
    private static final ProxyServerHelper.WSDefinition def_login = ProxyServerHelper.createServiceDefinition("login", ProxyServerHelper.TYPE_VOID, ProxyServerHelper.TYPE_STRING, ProxyServerHelper.TYPE_STRING, ProxyServerHelper.TYPE_STRING);
    private static final ProxyServerHelper.WSDefinition def_createAccount = ProxyServerHelper.createServiceDefinition("createAccount", ProxyServerHelper.TYPE_STRING, ProxyServerHelper.TYPE_STRING);
    private static final ProxyServerHelper.WSDefinition def_getPhotoDetails = ProxyServerHelper.createServiceDefinition("getPhotoDetails", ProxyServerHelper.TYPE_STRING_ARRAY, ProxyServerHelper.TYPE_LONG);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Webservice access only!</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DataInputStream di = new DataInputStream(request.getInputStream());
        String methodName = di.readUTF();
        
        if(methodName.equals("listPhotos")) {
            Object[] args = ProxyServerHelper.readMethodArguments(di, def_listPhotos);
            ProxyServerHelper.writeResponse(response, def_listPhotos, WebServiceProxyServer.listPhotos());
            return;
        }
        if(methodName.equals("getPhotoLikes")) {
            Object[] args = ProxyServerHelper.readMethodArguments(di, def_getPhotoLikes);
            ProxyServerHelper.writeResponse(response, def_getPhotoLikes, WebServiceProxyServer.getPhotoLikes((Long)args[0]));
            return;
        }
        if(methodName.equals("likePhoto")) {
            Object[] args = ProxyServerHelper.readMethodArguments(di, def_likePhoto);
            WebServiceProxyServer.likePhoto((Long)args[0], (String)args[1]);
            ProxyServerHelper.writeResponse(response, def_likePhoto);
            return;
        }
        if(methodName.equals("login")) {
            Object[] args = ProxyServerHelper.readMethodArguments(di, def_login);
            WebServiceProxyServer.login((String)args[0], (String)args[1], (String)args[2]);
            ProxyServerHelper.writeResponse(response, def_login);
            return;
        }
        if(methodName.equals("createAccount")) {
            Object[] args = ProxyServerHelper.readMethodArguments(di, def_createAccount);
            ProxyServerHelper.writeResponse(response, def_createAccount, WebServiceProxyServer.createAccount((String)args[0]));
            return;
        }
        if(methodName.equals("getPhotoDetails")) {
            Object[] args = ProxyServerHelper.readMethodArguments(di, def_getPhotoDetails);
            ProxyServerHelper.writeResponse(response, def_getPhotoDetails, WebServiceProxyServer.getPhotoDetails((Long)args[0]));
            return;
        }
    }
}
