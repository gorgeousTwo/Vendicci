package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

@WebServlet(
        urlPatterns = {
                "/Controller",
                "*.do"
        },

        initParams = {
                @WebInitParam(name = "propertyConfig", value = "com.proper.mapping")
        }
)
public class Controller extends HttpServlet {
    private final long serialVersionUID = 1L;
    private HashMap<String, Object> commandMap = new HashMap<String, Object>();

    public Controller() {
        super();
    }

    public void loadProperties(ServletConfig config) throws ServletException {
        initProperties("com/proper/mapping");
    }

    /**
     *
     * @param path "com/proper/mapping"
     *
     * mapping.properties => rs. properties Keys => bundleKeys.
     *
     * While(.hasMoreElements())
     *             properties key => currentKey. (example : /list.do)
     *             properties String => className. (example : com.servlet.ListAction)
     *
     *             Class.forName(className) => commandClass.
     *             Class Instance => commandInstance.
     *
     *             finally, put(Mapped Url,Class Instance) => commandMap
     *
     */

    private void initProperties(String path) {
        ResourceBundle rs = ResourceBundle.getBundle(path);
        Enumeration<String> bundleKeys = rs.getKeys();

        while (bundleKeys.hasMoreElements()) {
            String currentKey = bundleKeys.nextElement();
            String className = rs.getString(currentKey);

            try {
                Class commandClass = Class.forName(className);
                Object commandInstance = commandClass.newInstance();
                commandMap.put(currentKey,commandInstance);
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (InstantiationException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestPro(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestPro(request, response);
    }


    /**
     * @see com.servlet requestPro(HttpServletRequest request, HttpServletResponse response)
     */

    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = null;
        CommandAction com = null;

        try {
            String command = request.getRequestURI();

            if (command.indexOf(request.getContextPath()) == 0) {
                command = command.substring(request.getContextPath().length());
            }
            com = (CommandAction) commandMap.get(command);
            view = com.requestPro(request,response);

        }catch (Throwable e) {
            e.printStackTrace();
        }finally {
            if (view == null || view.equals("")) {
                return;
            }
        }

        request.setAttribute("conf",view);
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request,response);
    }
}
