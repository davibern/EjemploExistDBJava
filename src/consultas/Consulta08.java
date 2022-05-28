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
public class Consulta08 {
    
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
            
            String xquery = "update replace //libro[@id=3]/fechaPublicacion\n" +
                            "with <fechaPublicacion>2009</fechaPublicacion>";
            
            result = service.query(xquery);
            
            System.out.println("Actualizar la fecha de publicación del libro 3 a 2009");
            
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
