/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author breyner
 */
public class ManejoTexto {

    public ManejoTexto() {
    }

    /**
     * Este metodo reemplaza todos los campos de un String,
     * recibiendo de parametros un arrays de String a reemplazar y otro array de String reemplazado
     * los cuales deben ser de la misma longitud
     * @param ptxt : cadena a modificar
     * @param pVarAReemp :Array String con variables a reemplazar
     * @param pVarRemp Array String con variables por reemplazar
     * @return  ptxt cadena modificada
     */
    public static String getTxtLstVariableReemplazar(String ptxt, ArrayList<String> pVarAReemp, ArrayList<String> pVarRemp) {

        if (pVarAReemp == null || pVarAReemp.isEmpty() || ptxt == null) {
            return ptxt;
        } else {
            if (pVarAReemp.size() == pVarRemp.size()) {
                for (int i = 0; i < pVarAReemp.size(); i++) {
                    ptxt = ptxt.replace(pVarAReemp.get(i), pVarRemp.get(i));

                }
            }
        }
        return ptxt;
    }

    public static String getTxtSinEspacio(String ptxt) {
        String txtReturn = "";
        if (ptxt != null) {
            if (ptxt.isEmpty()) {
                return "";
            } else {
                if (ptxt.length() <= 0) {
                    return "";
                } else {
                    ptxt = ptxt.trim();
                    StringTokenizer stTexto = new StringTokenizer(ptxt);

                    while (stTexto.hasMoreElements()) {
                        txtReturn += stTexto.nextElement();
                    }
                    return txtReturn;
                }
            }
        }

        return "";

    }

    public static String getTxtSeparador(String ptxt, String pToken) {
        String txtReturn = "";
        if (ptxt != null) {
            if (ptxt.isEmpty()) {
                return "";
            } else {
                if (ptxt.length() <= 0) {
                    return "";
                } else {
                    StringTokenizer stTexto = new StringTokenizer(ptxt, pToken);

                    while (stTexto.hasMoreElements()) {
                        txtReturn += stTexto.nextElement() + "\n";
                    }
                    return txtReturn;
                }
            }
        }

        return "";

    }

    /**
     * Eliminar espacios, comas  y puntos de una cadena para ids o nombres de usuario
     * @return
     */
    public static String eliminarEspacios(String pCadena) {
        if (pCadena != null) {
            pCadena = pCadena.replace(" ", "");
            pCadena = pCadena.replace(",", "");
            pCadena = pCadena.replace(".", "");
            pCadena = pCadena.replace("'", "");
            pCadena = pCadena.replace("\"", "");

            return pCadena;
        } else {
            return "";
        }
    }

    public static String eliminarNoUtf8CompatibleCaracteres(final String inString) {
        if (null == inString) {
            return null;
        }
        byte[] byteArr = inString.getBytes();
        for (int i = 0; i < byteArr.length; i++) {
            byte ch = byteArr[i];
            // remove any characters outside the valid UTF-8 range as well as all control characters
            // except tabs and new lines
            if (!((ch > 31 && ch < 253) || ch == '\t' || ch == '\n' || ch == '\r')) {
                byteArr[i] = ' ';
            }
        }
        return new String(byteArr);
    }

    public static String getTxtNVL(String ptxt, String vlrNvl) {
        if (ptxt == null) {
            ptxt = vlrNvl;
        }
        return ptxt;
    }

    public static Double getTxtNVL(Double ptxt, Double vlrNvl) {
        if (ptxt == null) {
            ptxt = vlrNvl;
        }
        return ptxt;
    }

    public static Long getTxtNVL(Long ptxt, Long vlrNvl) {
        if (ptxt == null) {
            ptxt = vlrNvl;
        }
        return ptxt;
    }

    public static Integer getTxtNVL(Integer ptxt, Integer vlrNvl) {
        if (ptxt == null) {
            ptxt = vlrNvl;
        }
        return ptxt;
    }

    /**
     *
     * @param ptxt
     * @param tipo
     * @return si la cadena de carcteres tiene alguno de estos datos
     */
    public static boolean validarTextoDocNFact(String ptxt, int tipo) {
        String matc = "^[0-9-]+";
        if (tipo == 1) {//numero con guiones
            matc = "^[0-9-]+";
        } else {
            if (tipo == 2) {
                matc = "^[_A-Za-z0-9-]+";
            } else {
                if (tipo == 3) {
                    matc = "^[A-Za-z]+";
                } else {
                    matc = "^[0-9]+";
                }
            }
        }
        if (ptxt == null) {
            return false;
        } else {
            if (ptxt.isEmpty()) {
                return false;
            } else {
                return ptxt.matches(matc);
            }
        }

    }
}
