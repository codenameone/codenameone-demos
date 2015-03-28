/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */

package com.codename1.proxy.server;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shai Almog
 */
public class ProxyServerHelper {
    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_VOID = 0;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BYTE = 1;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_CHAR = 2;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_SHORT = 3;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_INT = 4;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_LONG = 5;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_DOUBLE = 6;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_FLOAT = 7;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BOOLEAN = 8;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BYTE_OBJECT = 9; 

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_CHARACTER_OBJECT = 10;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_SHORT_OBJECT = 11;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_INTEGER_OBJECT = 12;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_LONG_OBJECT = 13;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_DOUBLE_OBJECT = 14;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_FLOAT_OBJECT = 15;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BOOLEAN_OBJECT = 16;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_STRING = 17;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BYTE_ARRAY = 18;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_CHAR_ARRAY = 19;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_SHORT_ARRAY = 20;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_INT_ARRAY = 21;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_LONG_ARRAY = 22;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_DOUBLE_ARRAY = 23;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_FLOAT_ARRAY = 24;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BOOLEAN_ARRAY = 25;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_STRING_ARRAY = 26;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_EXTERNALIABLE = 1000;

    public static WSDefinition createServiceDefinition(String name, int returnType, int... argumentTypes) {
        WSDefinition wd = new WSDefinition();
        wd.arguments = argumentTypes;
        wd.returnType = returnType;
        wd.name = name;
        return wd;
    }
    
    /**
     * Parses the arguments to a method 
     * @param dis the stream from the client
     * @param def the method definition
     * @return arguments for the method
     */
    public static Object[] readMethodArguments(DataInputStream dis, WSDefinition def) throws IOException {
        Object[] result = new Object[def.arguments.length];
        for(int iter = 0 ; iter < result.length ; iter++) {
            switch(def.arguments[iter]) {
                case TYPE_BYTE:
                    result[iter] = new Byte(dis.readByte());
                    break;

                case TYPE_CHAR:
                    result[iter] = new Character(dis.readChar());
                    break;

                case TYPE_SHORT:
                    result[iter] = new Short(dis.readShort());
                    break;

                case TYPE_INT:
                    result[iter] = new Integer(dis.readInt());
                    break;

                case TYPE_LONG:
                    result[iter] = new Long(dis.readLong());
                    break;

                case TYPE_DOUBLE:
                    result[iter] = new Double(dis.readDouble());
                    break;

                case TYPE_FLOAT:
                    result[iter] = new Float(dis.readFloat());
                    break;

                case TYPE_BOOLEAN:
                    result[iter] = new Boolean(dis.readBoolean());
                    break;

                case TYPE_BYTE_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Byte(dis.readByte());
                    }
                    break;

                case TYPE_CHARACTER_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Character(dis.readChar());
                    }
                    break;

                case TYPE_SHORT_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Short(dis.readShort());
                    }
                    break;

                case TYPE_INTEGER_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Integer(dis.readInt());
                    }
                    break;

                case TYPE_LONG_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Long(dis.readLong());
                    }
                    break;

                case TYPE_DOUBLE_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Double(dis.readDouble());
                    }
                    break;

                case TYPE_FLOAT_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Float(dis.readFloat());
                    }
                    break;

                case TYPE_BOOLEAN_OBJECT:
                    if(dis.readBoolean()) {
                        result[iter] = new Boolean(dis.readBoolean());
                    }
                    break;

                case TYPE_STRING:
                    if(dis.readBoolean()) {
                        result[iter] = dis.readUTF();
                    }
                    break;

                case TYPE_BYTE_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        byte[] b = new byte[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readByte();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_CHAR_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        char[] b = new char[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readChar();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_SHORT_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        short[] b = new short[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readShort();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_INT_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        int[] b = new int[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readInt();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_LONG_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        long[] b = new long[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readLong();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_DOUBLE_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        double[] b = new double[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readDouble();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_FLOAT_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        float[] b = new float[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readFloat();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_BOOLEAN_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        boolean[] b = new boolean[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readBoolean();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_STRING_ARRAY: {
                    int size = dis.readInt();
                    if(size > -1) {
                        String[] b = new String[size];
                        for(int i = 0 ; i < b.length ; i++) {
                            b[i] = dis.readUTF();
                        }
                        result[iter] = b;
                    }
                    break;
                }

                case TYPE_EXTERNALIABLE:
                    result[iter] = Util.readObject(dis);
                    break;

                default:
                    throw new RuntimeException("Unrecognized type: " + def.arguments[iter]);
            }
        }
        return result;
    }
     
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        OutputStream o = resp.getOutputStream();
        o.write(0);
        o.close();
    }
    
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, byte response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeByte(response);
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, char response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeChar(response);
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, short response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeShort(response);
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, int response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeInt(response);
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, long response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeLong(response);
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, float response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeFloat(response);
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, double response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeDouble(response);
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, boolean response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        dos.writeBoolean(response);
        dos.close();
    }

    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Byte response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeByte(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Character response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeChar(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Short response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeShort(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Integer response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeInt(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Long response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeLong(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Float response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeFloat(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Double response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeDouble(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Boolean response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeBoolean(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, String response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeUTF(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, byte[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(byte b : response) {
                dos.writeByte(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, char[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(char b : response) {
                dos.writeChar(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, short[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(short b : response) {
                dos.writeShort(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, int[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(int b : response) {
                dos.writeInt(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, long[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(long b : response) {
                dos.writeLong(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, float[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(float b : response) {
                dos.writeFloat(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, double[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(double b : response) {
                dos.writeDouble(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, boolean[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(boolean b : response) {
                dos.writeBoolean(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, String[] response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeInt(response.length);
            for(String b : response) {
                dos.writeUTF(b);
            }
        } else {
            dos.writeInt(-1);
        }
        dos.close();
    }

    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, Externalizable response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        Util.writeObject(response, dos);
        dos.close();
    }
    
    /**
     * Webservice definition type, allows defining the argument values for a specific WS call
     */
    public static class WSDefinition {
        String name;
        int returnType;
        int[] arguments;
    }
}
