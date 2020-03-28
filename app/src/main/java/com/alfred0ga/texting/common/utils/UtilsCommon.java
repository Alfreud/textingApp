package com.alfred0ga.texting.common.utils;

import android.content.Context;
import android.os.Build;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatEditText;

import com.alfred0ga.texting.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

public class UtilsCommon {
    /*
    *  Codificar un correo electrónico
    * */
    public static String getEmailEncoded(String email){
        String preKey = email.replace("_", "__");
        return preKey.replace(".", "_");
    }

    /*
    *   Cargar Imagenes Básicas
    * */

    public static void loadImage(Context context, String url, ImageView target) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }

    public static boolean validateEmail(Context context, EditText etEmail) {
        boolean isValid = true;

        String email = etEmail.getText().toString().trim();
        if(email.isEmpty()){
            etEmail.setError(context.getString(R.string.common_validate_field_required));
            etEmail.requestFocus();
            isValid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError(context.getString(R.string.common_validate_email_invalid));
            etEmail.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    /*
    *   Verificacion de versiones
    * */

    public static boolean hasMaterialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /*
    *   Mostrar mensajes con SnackBar
    * */

    public static void showSnackBar(View contentMain, int resMsg) {
        showSnackBar(contentMain, resMsg, Snackbar.LENGTH_SHORT);
    }

    public static void showSnackBar(View contentMain, int resMsg, int duration) {
        Snackbar.make(contentMain, resMsg, duration).show();
    }

    public static boolean validateMessage(EditText etMessage) {
        return etMessage.getText() != null &&
                !etMessage.getText().toString().trim().isEmpty();
    }
}
