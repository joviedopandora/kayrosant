/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Garcia Bosso
 */
public enum EnFormatDate {
    FORMAT_DATE_DD_MM_YYYY("dd-MM-yyyy"),
   FORMAT_DATE_YYYY_MM_DD("dd-MM-yyyy");
  private String format;
  
  private EnFormatDate(String format){
        this.format = format;
  }

    public String getFormat() {
        return format;
    }

    
  
}
