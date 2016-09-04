/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
//import org.postgresql.ds.PGSimpleDataSource;

/**
 * Clase para el Manejo básico de la seguridad
 *
 * @author extmchlfchacon
 */
public class Seguridad {

    private DataSource getJdbcAudInt() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/jdbcAudInt");
    }

    public static void main(String[] args) {
        
    }
//    public static String resetClaves() {
//        try {
//            PGSimpleDataSource dataSource = new PGSimpleDataSource();
//            dataSource.setUser("auditoria");
//            dataSource.setPassword("auditoria");
//            dataSource.setPortNumber(5432);
//            dataSource.setServerName("localhost");
//            dataSource.setDatabaseName("audinterna");
//            Connection con = dataSource.getConnection();
//            StringBuilder strBSql = new StringBuilder();
//            strBSql.append("UPDATE adm_colxemp SET cpe_clave = ?");
//
//            PreparedStatement ps = con.prepareStatement(strBSql.toString());
//            ps.setString(1, hashPasswordSha512("123456"));
//            ps.executeUpdate();
//            //       
//            //        for (Usuario usuario : amsfb.getLstUsr()) {
//            //            usuario.setUsrClave(Seguridad.hashPasswordSha512(usuario.getUsrId()));
//            //            amsfb.editaUsr(usuario);
//            //            System.out.println("Clave para " + usuario.getUsrId() + " = " + usuario.getUsrClave());
//            //        }
//            con.close();
//            return "";
//        } catch (SQLException ex) {
//            Logger.getLogger(Seguridad.class.getName()).log(Level.SEVERE, null, ex);
//            return "";
//        }
//    }
    /**
     * Constante para utilizar como base en la conversión de contraseñas
     */
    private static final char[] HEXADECIMAL = {'0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Función para encriptar contraseñas utilizando el algoritmo MD5
     *
     * @param password
     * @return Contraseña convertida en hexadecimal
     * @throws Exception
     */
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();

        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            int low = (bytes[i] & 0x0f);
            int high = ((bytes[i] & 0xf0) >> 4);
            sb.append(HEXADECIMAL[high]);
            sb.append(HEXADECIMAL[low]);
        }
        return sb.toString();
    }

    /**
     * Función para encriptar con el algoritmo sha2 con 512 bytes
     *
     * @param clave
     * @return
     */
    public static String hashPasswordSha512(String clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            String C6H5COONa = "C6H5COONa";
            StringBuilder strBClave = new StringBuilder(clave);
            strBClave.append(C6H5COONa);
            byte[] bytes = md.digest(strBClave.toString().getBytes());
            StringBuilder sb = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int low = (bytes[i] & 0x0f);
                int high = ((bytes[i] & 0xf0) >> 4);
                sb.append(HEXADECIMAL[high]);
                sb.append(HEXADECIMAL[low]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Seguridad.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }


    }

    public static String eliminarPuntosEspacios(String cadena) {
        String cadenatratada = cadena;
        cadenatratada = cadenatratada.replace(" ", "");
        cadenatratada = cadenatratada.replace(".", "");
        cadenatratada = cadenatratada.replace(",", "");
        return cadenatratada;
    }

    /**
     * Elimina símbolos especiales que se pueden utilizar para inyección SQL
     *
     * @param cadena Cadena a tratar
     * @return Cadena con símbolos especiales eliminados
     */
    public static String eliminarSE(String cadena) {
        String cadenatratada = cadena;
        cadenatratada = cadenatratada.replace("-", "");
        cadenatratada = cadenatratada.replace("<", "");
        cadenatratada = cadenatratada.replace(">", "");
        cadenatratada = cadenatratada.replace("=", "");
        cadenatratada = cadenatratada.replace("?", "");
        cadenatratada = cadenatratada.replace("/", "");
        cadenatratada = cadenatratada.replace("#", "");
        cadenatratada = cadenatratada.replace("$", "");
        cadenatratada = cadenatratada.replace("%", "");
        cadenatratada = cadenatratada.replace("(", "");
        cadenatratada = cadenatratada.replace(")", "");
        cadenatratada = cadenatratada.replace("\"", "");
        cadenatratada = cadenatratada.replace("'", "");
        cadenatratada = cadenatratada.replace("|", "");
        cadenatratada = cadenatratada.replace("*", "");
        cadenatratada = cadenatratada.replace("\\", "");


        return cadenatratada;
    }
}
