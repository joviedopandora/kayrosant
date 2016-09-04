/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author breyner
 */
public class ManejoFecha {

    public ManejoFecha() {
    }

    public static String getFechaFormato(String formato, Date fechaformat) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(fechaformat);

    }

    public static int getDiasDiferencia(Date FecIn, Date FecFin) {
        Long diferencia = 0L;
        // FecFin = new Date(FecFin.getYear(),FecFin.getMonth() , FecFin.getDate());
        diferencia = (FecFin.getTime() - FecIn.getTime());

        return (int) Math.floor(diferencia / (1000 * 60 * 60 * 24));
    }

    public static int getMinDiferencia(Date FecIn, Date FecFin) {
        Long diferencia = 0L;
        // FecFin = new Date(FecFin.getYear(),FecFin.getMonth() , FecFin.getDate());
        diferencia = (FecFin.getTime() - FecIn.getTime());

        return (int) Math.floor(diferencia / (1000 * 60));
    }

    public static int getHoraDiferencia(Date FecIn, Date FecFin) {
        Long diferencia = 0l;
        // FecFin = new Date(FecFin.getYear(),FecFin.getMonth() , FecFin.getDate());
        diferencia = (FecFin.getTime() - FecIn.getTime());

        return (int) Math.floor(diferencia / (1000 * 60 * 60));
    }

    /**
     * retorna la diferencia en años entre las fechas
     *
     * @param FecIn
     * @param FecFin
     * @return
     */
    public static int getYearDiferencia(Date FecIn, Date FecFin) {

        int diff_year = FecFin.getYear() - FecIn.getYear();
        int diff_month = FecFin.getMonth() - FecIn.getMonth();
        int diff_day = FecFin.getDate() - FecIn.getDate();
        //Si está en ese año pero todavía no los ha cumplido
        if (diff_month < 0 || (diff_month == 0 && diff_day < 0)) {
            diff_year -= 1; //no aparecían los dos guiones del postincremento :|
        }
        return diff_year;
    }

    public static int getMesDiferencia(Date FecIn, Date FecFin) {
        FecIn = formatearFecha(FecIn);
        FecFin = formatearFecha(FecFin);
        int year1 = Integer.valueOf(getFechaFormato("yyyy", FecIn));
        int year2 = Integer.valueOf(getFechaFormato("yyyy", FecFin));

        int mes1 = Integer.valueOf(getFechaFormato("MM", FecIn));
        int mes2 = Integer.valueOf(getFechaFormato("MM", FecFin));
        int cantMesesEnntreF = 0;
        if (year1 > year2) {
            int cantYear = 12 * (year1 - year2);
            cantMesesEnntreF = cantYear + (mes1 - mes2);

        } else {
            if (year2 > year1) {
                int cantYear = 12 * (year2 - year1);
                cantMesesEnntreF = cantYear + (mes2 - mes1);

            } else {
                if (mes1 > mes2) {
                    cantMesesEnntreF = mes1 - mes2;
                } else {
                    cantMesesEnntreF = mes2 - mes1;
                }
            }
        }


        return cantMesesEnntreF;
    }

    /**
     *
     * @param FecIn
     * @param FecFin
     * @return 1 si FecIn es mayor que FecFin, 2 si FecFin es mayor que FecIn y
     * 0 si son iguales
     */
    public static int compararDates(Date FecIn, Date FecFin) {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaIni = dateFormat.format(FecIn);
            String fechaFin = dateFormat.format(FecFin);
            Date fIni = dateFormat.parse(fechaIni);
            Date fFin = dateFormat.parse(fechaFin);
            if (fIni.after(fFin)) {
                return 1;
            } else {
                if (fIni.before(fFin)) {
                    return 2;
                } else {
                    return 0;
                }
            }

        } catch (ParseException ex) {
            return 3;
        }

    }

    public static Date formatearFecha(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaI = dateFormat.format(fecha);
        try {
            return (Date) dateFormat.parse(fechaI);
        } catch (ParseException ex) {

            Logger.getLogger(ManejoFecha.class.getName()).log(Level.SEVERE, null, ex);
            return fecha;
        }
    }

    public static String formatearFechaString(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaI = dateFormat.format(fecha);
        return fechaI;
    }

    public static boolean compararTimes(Date f, Date a) {


//            SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        // Date a = dfm.parse("2011/04/29 16:00:00 ");

        if (a.getHours() == f.getHours()) {
            if (a.getMinutes() == f.getMinutes()) {
                if (a.getSeconds() <= f.getSeconds()) {
                    return true;
                }
            } else {
                if (a.getMinutes() < f.getMinutes()) {
                    return true;
                }
            }
        } else {
            if (a.getHours() < f.getHours()) {
                return true;
            }
        }

        return false;
    }
    public static Date sumarDias(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
        
    }
}
