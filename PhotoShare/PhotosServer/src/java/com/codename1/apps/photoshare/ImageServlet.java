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
import javax.servlet.ServletOutputStream;
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
@WebServlet(name = "ImageServlet", urlPatterns = {"/image"})
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg");
        String w = request.getParameter("w");
        String h = request.getParameter("h");
        long i = Long.parseLong(request.getParameter("i"));
        ServletOutputStream out = response.getOutputStream();
        try {
            if(w != null) {
                int width = Integer.parseInt(w);
                int height = Integer.parseInt(h);    
                out.write(ImageFile.getThumb(i, width, height));
            } else {
                out.write(ImageFile.getImage(i));
            }
        } finally {
            out.close();
        }
    }
}
