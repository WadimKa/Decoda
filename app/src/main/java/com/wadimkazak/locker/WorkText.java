package com.wadimkazak.locker;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Wadim on 13.05.2018.
 */

public class WorkText {
    public static WorkText workText;
    private final String TAG = "WorkText";
    private String text, key;
    private Context context;
    public static int COUNT_OF_ENCODINGS = 0;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setKey(String key) {
        String hashOfKey = String.valueOf(key.hashCode());
        char[] arrayOfCharacter = new char[16];
        if (hashOfKey.length() < 16) {
            for (int i = hashOfKey.length(); i < arrayOfCharacter.length; i++) {
                arrayOfCharacter[i] = '.';
            }
        }
        if (hashOfKey.length() > 16) {

        }
        if (hashOfKey.length() == 0) {

        }
        this.key = new String(arrayOfCharacter);

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
        if (COUNT_OF_ENCODINGS < 5) {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            try {
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                byte[] encryptArr = cipher.doFinal(text.getBytes());
                text = "";
                for (byte a : encryptArr) {
                    text += (char) a;
                }
                COUNT_OF_ENCODINGS += 1;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Toast.makeText(context, "No such Algorithm", Toast.LENGTH_SHORT).show();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
                Toast.makeText(context, "No such Padding", Toast.LENGTH_SHORT).show();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                Toast.makeText(context, "Invalid key. Key must have 16 characters", Toast.LENGTH_SHORT).show();
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

        } else {
            Toast.makeText(context, R.string.count_of_encodings, Toast.LENGTH_SHORT).show();
        }
    }

    public void unlockText() {
        if (COUNT_OF_ENCODINGS > 0) {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            try {
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                char[] encryptArrOfStrings = text.toCharArray();
                byte[] decodedArrayOfBytes = new byte[encryptArrOfStrings.length];
                for (int i = 0; i < encryptArrOfStrings.length; i++) {
                    decodedArrayOfBytes[i] = (byte) encryptArrOfStrings[i];
                }
                text = new String(cipher.doFinal(decodedArrayOfBytes));
                COUNT_OF_ENCODINGS -= 1;

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
                //Toast.makeText(context, "Error. You do something wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.original_text, Toast.LENGTH_SHORT).show();
        }
    }


}
