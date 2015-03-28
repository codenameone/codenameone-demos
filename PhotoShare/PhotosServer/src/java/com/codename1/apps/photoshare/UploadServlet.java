/*
 * This is generated code, copyright free and unrestricted
 */
package com.codename1.apps.photoshare;

import com.codename1.proxy.server.ProxyServerHelper;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * This is a generated servlet that maps calls to the Server class static methods,
 * your code should go in that class!
 *
 * @author Codename One
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 100, // 10 MB
        maxFileSize = 1024 * 1024 * 150, // 50 MB
        maxRequestSize = 1024 * 1024 * 200)      // 100 MB 
public class UploadServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Collection<Part> parts = req.getParts();
        Part image = parts.iterator().next();
        String[] args = req.getParameter("p").split(";");
        long key = ImageFile.createImageFile(args, readInputStream(image.getInputStream()));
        DataOutputStream out = new DataOutputStream(res.getOutputStream());
        try {
            out.writeLong(key);
        } finally {
            out.close();
        }
    }

    /**
     * Converts a small input stream to a byte array
     *
     * @param i the stream to convert
     * @return byte array of the content of the stream
     */
    public static byte[] readInputStream(InputStream i) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        copy(i, b);
        return b.toByteArray();
    }

    /**
     * Copy the input stream into the output stream, closes both streams when finishing or in
     * a case of an exception
     * 
     * @param i source
     * @param o destination
     */
    public static void copy(InputStream i, OutputStream o) throws IOException {
        copy(i, o, 8192);
    }

    /**
     * Copy the input stream into the output stream, closes both streams when finishing or in
     * a case of an exception
     *
     * @param i source
     * @param o destination
     * @param bufferSize the size of the buffer, which should be a power of 2 large enoguh
     */
    public static void copy(InputStream i, OutputStream o, int bufferSize) throws IOException {
        try {
            byte[] buffer = new byte[bufferSize];
            int size = i.read(buffer);
            while(size > -1) {
                o.write(buffer, 0, size);
                size = i.read(buffer);
            }
        } finally {
            cleanup(o);
            cleanup(i);
        }
    }

    public static void cleanup(Object o) {
        try {
            if(o != null) {
                if(o instanceof InputStream) {
                    ((InputStream) o).close();
                }
                if(o instanceof OutputStream) {
                    ((OutputStream) o).close();
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
