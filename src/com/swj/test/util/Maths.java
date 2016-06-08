package com.swj.test.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Maths {
    public Maths() {
    }
    public static final float atof(String value) {
        float result =0f;
        if(value == null || value.trim().equals(""))
            return result;
        try{
            result = Float.parseFloat(value.trim());
        }catch(Exception ex){

        }
        return result;
    }
    /**
     * atoi
     *
     * @param value String
     */
    public static final int atoi(String value) {
        int result =0;
        if(value == null || value.trim().equals(""))
            return 0;
        try{
            result = Integer.parseInt(value.trim());
        }catch(Exception ex){
        }
        return result;
    }

    public static final String itoa(int value) {
        String result="";
        try{
           result = String.valueOf(value);
       }catch(Exception ex){
       }
       return result;
   }


    public static final long atol(String value) {
        if(value == null || value.trim().equals(""))
            return 0;
        return Long.parseLong(value);
    }
}
