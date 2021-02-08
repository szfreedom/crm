package com.sz.crm.utils;


import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.Enumeration;

public class reflectutils {
    public static void makeRequestToObject(HttpServletRequest request,Object obj) {
        Class c=obj.getClass();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements())
        {
            String filename=parameterNames.nextElement();
            String methodname="set"+filename.toUpperCase().charAt(0)+filename.substring(1);
            try {
                Method declaredMethod = c.getDeclaredMethod(methodname, String.class);
                declaredMethod.invoke(obj,request.getParameter(filename));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
