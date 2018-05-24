package com.wadimkazak.locker;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Wadim on 13.05.2018.
 */

public class WorkText {
    public static WorkText workText;
    private final String TAG = "WorkText";
    private String text, key;
    private SecretKeySpec secretKeySpec = null;


    public static WorkText get(String text, String key) {
        if (workText == null) {
            workText = new WorkText(text, key);
        }
        return workText;
    }

    private WorkText(String text, String key) {
        this.text = text;
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void lockText() {
        try {
            createSpecKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encodedArray = cipher.doFinal(text.getBytes());
            text = Base64.encodeToString(encodedArray, Base64.DEFAULT);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "No such Algo");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Log.e(TAG, "No such padding exception");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Log.e(TAG, "Invalid key");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Log.e(TAG, "Bad padding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            Log.e(TAG, "Illegal block size");
        }

    }

    public void unlockText() {
        try {
            createSpecKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] arr = cipher.doFinal(Base64.decode(text, Base64.CRLF));
            text = new String(arr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "No such Algo");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Log.e(TAG, "No such padding exception");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Log.e(TAG, "Invalid key");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Log.e(TAG, "Bad padding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            Log.e(TAG, "Illegal block size");
        }
    }

    private void createSpecKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, secureRandom);
        secretKeySpec = new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES");
    }


}
