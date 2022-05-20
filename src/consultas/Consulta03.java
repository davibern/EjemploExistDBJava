package consultas;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import utils.Utilidades;
import static utils.Utilidades.PASSWORD;
import static utils.Utilidades.SERVER;
import static utils.Utilidades.USER;
import static utils.Utilidades.mensaje;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class Consulta03 {
    
    private static ResourceSet result;
    private static ResourceIterator iterator;
    private static XPathQueryService service;
    private static Resource resource;
    private static Database db;
    private static Collection collection;
    
    public static void main(String[] args) {
        
        try {
            
            Class clase = Class.forName(Utilidades.CONTROLLER);
            
            db = (Database) clase.newInstance();
            db.setProperty("create-database", "true");
            
            DatabaseManager.registerDatabase(db);
            
            collection = DatabaseManager.getCollection(SERVER, USER, PASSWORD);
            
            service = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
            service.setProperty("pretty", "true");
            service.setProperty("encoding", "ISO-8859-1");
            service.setProperty("indent", "yes");
            
            String xquery = "let $libros := //libro\n" +
                            "return concat(\"Existen \", count(distinct-values($libros/nombre)), \" libros en la biblioteca\")";
            
            result = service.query(xquery);
            
            System.out.println(mensaje("Número de libros únicos en la biblioteca"));
            
            iterator = result.getIterator();
            if (!iterator.hasMoreResources()) {
                System.out.println("0 registros consultados");
            } else {
                while (iterator.hasMoreResources()) {
                    resource = iterator.nextResource();
                    String value = (String) resource.getContent();
                    System.out.println(value);
                }
                System.out.println("\nConsulta realizada con éxito");
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (XMLDBException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (collection != null) {
                try {
                    collection.close();
                } catch (XMLDBException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
        
    }
    
}
