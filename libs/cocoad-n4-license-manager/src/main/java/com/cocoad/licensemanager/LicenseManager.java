package com.cocoad.licensemanager;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.Base64;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.DAYS;

public class LicenseManager {

    public static final int POINTS = 1;
    public static final int SCHEDULE = 2;
    public static final int HISTORY = 3;
    public static final int ALARM = 4;
    public static final int CLIENT = 5;
    public static final int POPUP = 6;
    public static final String ERROR_PRODUCT_KEY_NOT_VALID = "Product key not valid";
    public static final String ERROR_NO_LICENSE = "No license";
    public static final String ERROR_LICENSE_EXPIRED = "Expired! please renew product key";
    public static final String ERROR_TRIAL_EXPIRED = "Trial expired";
    public static final String LICENSE_STATUS_EXPIRED = "EXPIRED";
    public static final String LICENSE_STATUS_DEMO = "DEMO LICENSED";
    public static final String LICENSE_STATUS_LICENSE = "LICENSED";
    public static final String LICENSE_STATUS_PERPETUAL = "LICENSED (PERPETUAL)";
    public static final String DAYS_LEFT = " day(s) left";
    public static final int ONE_YEAR = 365;
    public static final int  FIVE_YEAR = 1825;
    public static final String ERROR_SUNJCE_NOT_FOUND = "Provider SunJCE not found or not support";
    public static final String SUNJCE = "SunJCE";

    private static final Logger LOGGER = Logger.getLogger("license-manager");

    public static int getSubServiceAllow(String publicKey, String limitKey, String hostId, int softwareId) {
        int limit = 0;
        try {
            LOGGER.info("Verifying sub service allow");
            String[] decryptMessage = verify(publicKey,limitKey).split(":");

            //3 elements including hostid, softwareId, limit
            validateDecryptMessage(decryptMessage);
            String hostIdFromProductKey = decryptMessage[0];
            int softwareIdFromProductKey = Integer.parseInt(decryptMessage[1]);
            limit = Integer.parseInt(decryptMessage[2]);
            if(!hostIdFromProductKey.equals(hostId))
            {
                //LogProductKeyVerifyFailure();
                return limit;
            }

            if(softwareIdFromProductKey != softwareId)
            {
                //LogProductKeyVerifyFailure();
                return limit;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        LOGGER.info("Verify sub service complete limit " + limit);
        return limit;
    }

    private void LogProductKeyVerifyFailure()
    {
        LOGGER.severe(ERROR_PRODUCT_KEY_NOT_VALID);
        //setLicense(false);
        //setLicenseStatus(ERROR_NO_LICENSE);
    }

    public static long verifyLicense(String publicKey, String productKey, String hostId, int softwareId)
    {
        long dayLeft = 0;
        try {
            LOGGER.info("Verifying license");
            String[] decryptMessage = verify(publicKey,productKey).split(":");

            //3 elements including hostid, softwareId, limit
            validateDecryptMessage(decryptMessage);
            String hostIdFromProductKey = decryptMessage[0];
            int softwareIdFromProductKey = Integer.parseInt(decryptMessage[1]);
            LocalDate exp = LocalDate.parse(decryptMessage[2]);
            if(!hostIdFromProductKey.equals(hostId))
            {
                //LogProductKeyVerifyFailure();
                return dayLeft;
            }

            if(softwareIdFromProductKey != softwareId)
            {
                //LogProductKeyVerifyFailure();
                return dayLeft;
            }

            dayLeft = DAYS.between(LocalDate.now(),exp);
            if(dayLeft < 0)
            {
                LOGGER.severe(ERROR_LICENSE_EXPIRED);
                //setLicense(false);
                //setLicenseStatus(LICENSE_STATUS_EXPIRED);
                return 0;
            }
            LOGGER.info("Verify complete");
            return dayLeft;

        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
            //LogProductKeyVerifyFailure();
            LOGGER.severe(e.getMessage());
        } catch (NoSuchPaddingException e) {
            //e.printStackTrace();
            //LogProductKeyVerifyFailure();
            LOGGER.severe(e.getMessage());
        } catch (InvalidKeyException e) {
            //e.printStackTrace();
            //LogProductKeyVerifyFailure();
            LOGGER.severe(e.getMessage());
        } catch (InvalidKeySpecException e) {
            //e.printStackTrace();
            //LogProductKeyVerifyFailure();
            LOGGER.severe(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            //e.printStackTrace();
            //LogProductKeyVerifyFailure();
            LOGGER.severe(e.getMessage());
        } catch (BadPaddingException e) {
            //e.printStackTrace();
            //LogProductKeyVerifyFailure();
            LOGGER.severe(e.getMessage());
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            //LogProductKeyVerifyFailure();
            LOGGER.severe(e.getMessage());
        }
        return 0;
    }

    private static String verify(String publicKey,String secretMessageBase64) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Base64 decoding to byte array
        byte[] publicKeyByteServer = Base64.getDecoder().decode(publicKey);

        PublicKey publicKeyServer = (PublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByteServer));

        Cipher decryptCipher = null;
        try {
            decryptCipher = Cipher.getInstance("RSA",SUNJCE);
        } catch (NoSuchProviderException e) {
            LOGGER.severe(ERROR_SUNJCE_NOT_FOUND);
        }
        decryptCipher.init(Cipher.DECRYPT_MODE, publicKeyServer);

        byte[] secretMessageBytes = Base64.getDecoder().decode(secretMessageBase64);
        byte[] encryptedMessageBytes = decryptCipher.doFinal(secretMessageBytes);
        String encodedMessage = new String(encryptedMessageBytes, StandardCharsets.UTF_8);
        return encodedMessage;
    }

    private static void validateDecryptMessage(String[] decryptMessage) throws InvalidKeyException {
        //TODO
        //validate format of hostid pattern xxxx-xxxx-xxxx-xxxx
        //validate softwareId
        //validate date
        if(decryptMessage.length != 3)
        {
            //LogProductKeyVerifyFailure();
            throw new InvalidKeyException();
        }
    }
}
