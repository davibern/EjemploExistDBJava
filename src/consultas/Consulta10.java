package consultas;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class Consulta10 {
    
    private static final String CONTROLLER = "org.exist.xmldb.DatabaseImpl";
    private static final String SERVER = "xmldb:exist://localhost:8080/exist/xmlrpc/db/biblioteca";
    private static final String USER = "admin";
    private static final String PASSWORD = "";
    private static ResourceSet result;
    private static ResourceIterator iterator;
    private static XPathQueryService service;
    private static Resource resource;
    private static Database db;
    private static Collection collection;
    
    public static void main(String[] args) {
        try {
            Class clase = Class.forName(CONTROLLER);
            
            db = (Database) clase.newInstance();
            db.setProperty("create-database", "true");
            
            DatabaseManager.registerDatabase(db);
            
            collection = DatabaseManager.getCollection(SERVER,USER, PASSWORD);
            
            service = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
            service.setProperty("pretty", "true");
            service.setProperty("encoding", "ISO-8859-1");
            service.setProperty("indent", "yes");
            
            String xquery = "update insert\n" +
                            "  <libro id=\"02\">\n" +
                            "    <nombre>El Señor de los Anillos</nombre>\n" +
                            "    <autor>JRR Tolkien</autor>\n" +
                            "    <editorial>Planeta</editorial>    \n" +
                            "    <fechaPublicacion>1954</fechaPublicacion>\n" +
                            "    <ISBN>122-81-2234</ISBN>\n" +
                            "    <disponible>no</disponible>\n" +
                            "    <emprestado>si</emprestado>\n" +
                            "  </libro>\n" +
                            "into //libro[@id=\"1\"]";
            
            result = service.query(xquery);
            
            System.out.println("Añadir el libro 2 detrás del 1");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Consulta04.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Consulta04.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Consulta04.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consulta04.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (collection != null) {
                try {
                    collection.close();
                } catch (XMLDBException ex) {
                    Logger.getLogger(Consulta04.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
