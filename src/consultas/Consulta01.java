package consultas;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import static utils.Conexion.CONTROLLER;
import static utils.Conexion.PASSWORD;
import static utils.Conexion.SERVER;
import static utils.Conexion.USER;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class Consulta01 {
    
    private static ResourceSet result;
    private static ResourceIterator iterator;
    private static XPathQueryService service;
    private static Resource resource;
    private static Database bd;
    private static Collection collection;
    
    public static void main(String[] args) throws ClassNotFoundException {
        
        try {
            
            Class clase = Class.forName(CONTROLLER);
            
            bd = (Database) clase.newInstance();
            bd.setProperty("create-database", "true");
            
            DatabaseManager.registerDatabase(bd);
            
            try {
                
                collection = DatabaseManager.getCollection(SERVER, USER, PASSWORD);
                service = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
                service.setProperty("pretty", "true");
                service.setProperty("encoding", "ISO-8859-1");
                service.setProperty("indent", "yes");
                
                String xquery = "for $prestamos in //prestamo\n" +
                        "group by $i := $prestamos/codigoLibro\n" +
                        "return concat(\"El libro \", $i, ' se ha prestado: ', count($prestamos), ' veces.')";
                
                result = service.query(xquery);
                
                System.out.println("Para cada libro, obtener el número de veces que ha sido prestado,\nes decir el nº total de préstamos");
                
                iterator = result.getIterator();
                if (!iterator.hasMoreResources()) {
                    System.out.println("0 Registros consultados.");
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
            
        } catch (InstantiationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (XMLDBException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
    }
    
}
