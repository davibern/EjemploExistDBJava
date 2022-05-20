package utils;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class Utilidades {
    
    public static final String CONTROLLER = "org.exist.xmldb.DatabaseImpl";
    public static final String SERVER = "xmldb:exist://localhost:8080/exist/xmlrpc/db/biblioteca";
    public static final String USER = "admin";
    public static final String PASSWORD = "";
    
    public static String mensaje(String texto) {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------------------------------\n")
                .append(texto + "\n")
                .append("----------------------------------------------------------------");
        return sb.toString();
    }
    
}
