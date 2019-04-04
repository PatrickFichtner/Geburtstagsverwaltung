/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administration.web;

/**
 *
 * @author Patrick Fichtner
 */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Statische Hilfsmethoden
 */
public class WebUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * Stellt sicher, dass einer URL der Kontextpfad der Anwendung vorangestellt
     * ist. Denn sonst ruft man aus Versehen Seiten auf, die nicht zur eigenen
     * Webanwendung gehören.
     *
     * @param request HttpRequest-Objekt
     * @param url Die aufzurufende URL
     * @return Die vollständige URL
     */
    public static String appUrl(HttpServletRequest request, String url) {
        return request.getContextPath() + url;
    }

    /**
     * Anhängen eines Query-Parameters an eine vorhandene URL. Enthält die
     * URL noch keine Parameter, wird der Parameter als ?name=wert angehängt,
     * sonst als &name=wert.
     *
     * @param url Zu verändernde URL
     * @param param Name des Parameters
     * @param value Wert des Parameters
     * @return Verlängerte URL
     */
    public static String addQueryParameter(String url, String param, String value) {
        if (!url.contains("?")) {
            url += "?";
        } else {
            url += "&";
        }

        try {
            url += URLEncoder.encode(param, "utf-8") + "=" + URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return url;
    }

    /**
     * Formatiert ein Datum für die Ausgabe, z.B. 31.12.9999
     *
     * @param date Datum
     * @return String für die Ausgabe
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatUtilDate (java.util.Date date ) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Erzeugt ein Datumsobjekt aus dem übergebenen String, z.B. 03.06.1986
     *
     * @param input Eingegebener String
     * @return Datumsobjekt oder null bei einem Fehler
     */
    public static Date parseDate(String input) {
        try {
            java.util.Date date = DATE_FORMAT.parse(input);
            return new Date(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }  
}

