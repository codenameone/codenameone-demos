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
package com.codename1.demos.kitchen;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.tree.Tree;
import com.codename1.ui.tree.TreeModel;
import com.codename1.xml.Element;
import com.codename1.xml.XMLParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Shai Almog
 */
public class WebServices extends Demo {
    private WebServiceRequest requestElement;
    public String getDisplayName() {
        return "Web Services";
    }

    public Image getDemoIcon() {
        return getResources().getImage("network-wireless.png");
    }

    private void addArgument(final Container arguments, final Container request, final Button addArgument, boolean noAnimation, String name, String val) {
        final ComponentGroup args = new ComponentGroup();
        TextField argName = new TextField(name);
        argName.setHint("Argument Name");
        TextField value = new TextField(val);
        value.setHint("Argument Value");
        Button remove = new Button("Remove Argument");
        args.addComponent(argName);
        args.addComponent(value);
        args.addComponent(remove);
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                args.setX(Display.getInstance().getDisplayWidth());
                arguments.animateUnlayoutAndWait(500, 255);
                arguments.removeComponent(args);
                arguments.getParent().animateHierarchy(500);
            }
        });
        arguments.addComponent(args);
        if(noAnimation) {
            return;
        }
        request.revalidate();
        request.morphAndWait(addArgument, args, 1000);
        request.setShouldCalcPreferredSize(true);
        addArgument.getUnselectedStyle().setOpacity(255);
        request.animateHierarchy(400);        
    }
    
    public Container createDemo() {
        Tabs webservices = new Tabs();
        final TextArea responseText = new TextArea(20, 80);
        responseText.setEditable(false);
        final Tree responseTree = new Tree() {
            protected String childToDisplayLabel(Object child) {
                if(child instanceof Element) {
                    Element e = (Element)child;
                    return e.getTagName();
                }
                if(child instanceof Hashtable) {
                    return "Map";
                }
                if(child instanceof Vector) {
                    return "Array";
                }
                return child.toString();
            }
        };
        
        requestElement = new WebServiceRequest(responseTree, webservices, responseText);
        final Container arguments = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        final Container request = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        request.setScrollableY(true);
        
        final ComponentGroup cnt = new ComponentGroup();
        final TextField url = new TextField("http://search.twitter.com/search.json");
        url.setHint("Webservice URL");
        cnt.addComponent(url);
        
        final CheckBox postOrGet = new CheckBox("Post/Get method");
        postOrGet.setSelected(false);
        cnt.addComponent(postOrGet);

        final CheckBox responseType = new CheckBox("JSON/XML");
        responseType.setSelected(true);
        cnt.addComponent(responseType);

        final Button addArgument = new Button("Add Argument");
        addArgument(arguments, request, addArgument, true, "q", "@codename-one");
        cnt.addComponent(addArgument);
        addArgument.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addArgument(arguments, request, addArgument, false, "Argument", "Value");
            }
        });
        
        Button send = new Button("Send Request");
        cnt.addComponent(send);
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                InfiniteProgress ip = new InfiniteProgress();
                Dialog dlg = ip.showInifiniteBlocking();
                requestElement.setDisposeOnCompletion(dlg);
                requestElement.setUrl(url.getText());
                requestElement.removeAllArguments();
                requestElement.setPost(postOrGet.isSelected());
                if(responseType.isSelected()) {
                    requestElement.setContentType("application/json");
                    requestElement.addRequestHeader("Accept", "application/json");
                } else {
                    requestElement.setContentType("application/xml");
                    requestElement.addRequestHeader("Accept", "application/xml");
                }
                for(int iter = 0 ; iter < arguments.getComponentCount() ; iter++) {
                    Container currentArg = (Container)arguments.getComponentAt(iter);
                    String p = ((TextField)currentArg.getComponentAt(0)).getText();
                    String v = ((TextField)currentArg.getComponentAt(1)).getText();
                    requestElement.addArgument(p, v);
                }
                NetworkManager.getInstance().addToQueue(requestElement);
            }
        });
        
        request.addComponent(cnt);
        request.addComponent(arguments);
        webservices.addTab("Request", request);
        webservices.addTab("Response", responseTree);
        webservices.addTab("Raw", responseText);
        
        return webservices;
    }
    
    
    class WebServiceRequest extends ConnectionRequest {
        boolean json = true;
        private Tree t;
        private Tabs tab;
        private TextArea responseText;
        public WebServiceRequest(Tree t, Tabs tab, TextArea responseText) {
            this.t = t;
            this.tab = tab;
            this.responseText = responseText;
            setDuplicateSupported(true);
        }
        
        protected void readResponse(InputStream input) throws IOException  {
            byte[] bi = Util.readInputStream(input);
            responseText.setText(new String(bi));
            input = new ByteArrayInputStream(bi);
            if(json) {
                JSONParser jp = new JSONParser();
                Hashtable h = jp.parse(new InputStreamReader(input));
                t.setModel(new JSONTreeModel(h));
            } else {
                XMLParser xp = new XMLParser();
                Element response = xp.parse(new InputStreamReader(input));
                t.setModel(new XMLTreeModel(response));
            }
        }
        
        protected void postResponse() {
            tab.setSelectedIndex(1);            
        }
    }
    
    class JSONTreeModel implements TreeModel {
        private Hashtable root;
        public JSONTreeModel(Hashtable h) {
            root = h;
        }
        
        public Vector getChildren(Object parent) {
            if(parent == null) {
                Vector c = new Vector();
                c.addElement(root);
                return c;
            }
            if(parent instanceof Hashtable) {
                Hashtable h = (Hashtable)parent;
                Vector response = new Vector();
                Enumeration elements = h.keys();
                while(elements.hasMoreElements()) {
                    Object key = elements.nextElement();
                    response.addElement("Key: " + key);
                    response.addElement(h.get(key));
                }
                return response;
            } else {
                //????
                return (Vector)parent;
                /*if(parent instanceof Vector) {
                    return (Vecparent;
                }*/
            }
        }

        public boolean isLeaf(Object node) {
            return !(node instanceof Vector || node instanceof Hashtable);
        }
        
    }

    class XMLTreeModel implements TreeModel {
        private Element root;
        public XMLTreeModel(Element e) {
            root = e;
        }
        
        public Vector getChildren(Object parent) {
            if(parent == null) {
                Vector c = new Vector();
                c.addElement(root);
                return c;
            }
            Vector result = new Vector();
            Element e = (Element)parent;
            for(int iter = 0 ; iter < e.getNumChildren() ; iter++) {
                result.addElement(e.getChildAt(iter));
            }
            return result;
        }

        public boolean isLeaf(Object node) {
            Element e = (Element)node;
            return e.getNumChildren() == 0;
        }
        
    }
}
