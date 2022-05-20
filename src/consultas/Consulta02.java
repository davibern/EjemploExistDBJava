package consultas;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import static utils.Utilidades.CONTROLLER;
import static utils.Utilidades.PASSWORD;
import static utils.Utilidades.SERVER;
import static utils.Utilidades.USER;
import static utils.Utilidades.mensaje;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class Consulta02 {
    
    private static ResourceSet result;
    private static ResourceIterator iterator;
    private static XPathQueryService service;
    private static Resource resource;
    private static Database db;
    private static Collection collection;
    
    public static void main(String[] args) throws Exception {
        
        Class clase = Class.forName(CONTROLLER);
        
        db = (Database) clase.newInstance();
        db.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(db);
        
        try {
            
            collection = DatabaseManager.getCollection(SERVER, USER, PASSWORD);
            
            service = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
            service.setProperty("pretty", "true");
            service.setProperty("encoding", "ISO-8859-1");
            service.setProperty("indent", "yes");
            
            String xquery = "for $lib in //libro\n" +
                            "    where $lib/fechaPublicacion >= 2003 and $lib/fechaPublicacion <=2020\n" +
                            "    let $datos := concat($lib/fechaPublicacion, \" - \", $lib/nombre)\n" +
                            "return $datos";
            
            result = service.query(xquery);
            
            System.out.println(mensaje("Año de publicación y nombre del libro"));
            
            iterator = result.getIterator();
            if (!iterator.hasMoreResources()) {
                System.out.println("0 Registros consultados");
            } else {
                while (iterator.hasMoreResources()) {
                    resource = iterator.nextResource();
                    String value = (String) resource.getContent();
                    System.out.println(value);
                }
                System.out.println("\nConsulta realizada con éxito.");
            }
            
        } catch (XMLDBException e) {
            System.err.println("No se ha podido ejecutar la consulta. Detalles: " + e.getMessage());
        } finally {
            if (collection != null) {
                try {
                    collection.close();
                } catch (XMLDBException e) {
                    System.err.println("No se ha podido cerrar la conexión. Detalles: " + e.getMessage());
                }
            }
        }
        
    }
    
    
}
