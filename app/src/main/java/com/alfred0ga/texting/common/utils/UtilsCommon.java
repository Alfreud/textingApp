package com.alfred0ga.texting.common.utils;

public class UtilsCommon {
    /*
    *  Codificar un correo electrónico
    * */
    public static String getEmailEncoded(String email){
        String preKey = email.replace("_", "__");
        return preKey.replace(".", "_");
    }


}
