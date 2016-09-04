/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.math.BigInteger;
import java.security.SecureRandom;


/**
 *
 * @author Garcia Bosso
 */
public final class GenerateRandomPin {
    
    public static final java.security.SecureRandom SECURE_RANDOM= new SecureRandom();
    
    public static String generateRandomPin(){
       String out=  new BigInteger(130,SECURE_RANDOM).toString(32).substring(0, 4);
       return out;
       
    }
}
