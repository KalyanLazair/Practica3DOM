/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import JavaLibros.Libros;
import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
/**
 *
 * @author Marta
 */
public class JaxBClass {
    
       Libros misLibros;
    
    public int abrirJAXB(File fichero) {
        JAXBContext contexto;
        try {
          //Crea una instancia JAXB
           contexto = JAXBContext.newInstance(Libros.class);
          //Crea un objeto Unmarsheller.
           Unmarshaller u=contexto.createUnmarshaller();
          //Deserializa (unmarshal) el fichero
           misLibros=(Libros) u.unmarshal(fichero);
           return 0;
        } catch (Exception ex) {
           ex.printStackTrace();
           return -1;
        }
    }
    //Método que sirve para recorrer el archivo XML que hemos abierto a través de JaxB.
    public String recorrerJAXB(){
        
        String cadena_resultado="";
            //Crea una lista con objetos de tipo libro.
        List<Libros.Libro> losLibros=misLibros.getLibro();
        //Recorre la lista para sacar los valores. Los valores los obtenemos a partir de la clase Java que ha creado.
        for (int i=0; i<losLibros.size(); i++){
              cadena_resultado = cadena_resultado+"\n" + "Publicado en: " + losLibros.get(i).getPublicadoEn();
              cadena_resultado= cadena_resultado + "\n" +"El Título es: " + losLibros.get(i).getTitulo();
              cadena_resultado= cadena_resultado + "\n" +"El Autor es: " + losLibros.get(i).getAutor();
              cadena_resultado= cadena_resultado + "\n" + "La Editorial es: " + losLibros.get(i).getEditorial();
              cadena_resultado = cadena_resultado +"\n ------------------------";
        }
            return cadena_resultado;
    }

    
    
    
    
}
