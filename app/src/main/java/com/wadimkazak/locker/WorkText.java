package com.wadimkazak.locker;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Wadim on 13.05.2018.
 */

public class WorkText {
    public static WorkText workText;
    private final String TAG = "WorkText";
    private String text, key;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static WorkText get() {
        if (workText == null) {
            workText = new WorkText();
        }

        return workText;
    }

    private WorkText() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void lockText() {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptArr = cipher.doFinal(text.getBytes());
            text = "";
            for (byte a : encryptArr) {
                text += a + " ";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Toast.makeText(context, "No such Algorithm", Toast.LENGTH_SHORT).show();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Toast.makeText(context, "No such Padding", Toast.LENGTH_SHORT).show();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Toast.makeText(context, "Invalid key. Key must be at least 16 characters", Toast.LENGTH_SHORT).show();
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Toast.makeText(context, "Bad padding", Toast.LENGTH_SHORT).show();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            Toast.makeText(context, "Illegal block size", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error. You do something wrong", Toast.LENGTH_SHORT).show();
        }


    }

    public void unlockText() {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            String[] encryptArrOfStrings = text.split(" ");
            byte[] decodedArrayOfBytes = new byte[encryptArrOfStrings.length];
            for (int i = 0; i < encryptArrOfStrings.length; i++) {
                decodedArrayOfBytes[i] = Byte.valueOf(encryptArrOfStrings[i]);
            }
            text = new String(cipher.doFinal(decodedArrayOfBytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Toast.makeText(context, "No such Algorithm", Toast.LENGTH_SHORT).show();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Toast.makeText(context, "No such Padding", Toast.LENGTH_SHORT).show();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Toast.makeText(context, "Invalid key. Key must be at least 16 characters", Toast.LENGTH_SHORT).show();
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Toast.makeText(context, "Bad padding", Toast.LENGTH_SHORT).show();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            Toast.makeText(context, "Illegal block size", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error. You do something wrong", Toast.LENGTH_SHORT).show();
        }
    }


}
