/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author Marta
 */
public class DomClass {
    
       Document doc=null;
    
    
    public int abrirDOM(File fichero){
       
      
       try{
           DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); //Creamos un objeto DocumentBuilderFactory.
           factory.setIgnoringComments(true); //Ignoramos los comentarios que pueda contener el XML
           factory.setIgnoringElementContentWhitespace(true); //Ignoramos los espacios en blanco que tenga el documento.
           //Creamos un objeto DocumentBUilder para cargar en él la estructura de árbol del DOM.
           DocumentBuilder builder=factory.newDocumentBuilder();
           //Interpretamos (parseamos) el documento XML y generamos el DOM equivalente.
           doc=builder.parse(fichero);
           
           return 0;
    
       
       }catch(Exception e){
           e.printStackTrace();
           return -1;
       }        
    }
    //Método que recorre el árbol.
    public String recorrerDom(Document doc){
            String[] datos_nodo=null; //Creamos un array donde vamos a guardar los datos del XML.
            String salida="";
            Node node;  //Declaramos los nodos.
            
            Node raiz=doc.getFirstChild(); //Establecemos el nodo raíz.
            NodeList nodelist=raiz.getChildNodes(); //Obtenemos los nodos hijos del nodo padre.
            
            for(int i=0; i<nodelist.getLength();i++){
                   node=nodelist.item(i);
                   //Comprobamos los nodos guardados en la lista de nodos, en caso de que sea de tipo elemento, ejecuta.
                   if(node.getNodeType()==Node.ELEMENT_NODE){
                      //Llamamos al método procesar libro. Guardamos el contenido en el array.
                       datos_nodo=procesarLibro(node);
                       
                       salida=salida+"\n"+"Pubicado en;" + datos_nodo[0];
                       salida=salida+"\n"+"El autor es;" + datos_nodo[2];      
                       salida=salida+"\n"+"El titulo es;" + datos_nodo[1];
                       salida=salida+"\n"+"--------------------------";
                   }
            }
            
            
            return salida;
    }
    //Método para procesar el XML.
    protected String[] procesarLibro(Node n){
        String datos[]= new String[3]; //Declaramos un array con tres posiciones para guardar los datos de los nodos hijo, que son tres.
        Node ntemp=null;
        int contador=1;
        //Obtenemos el primer valor y lo guardamos en el array.
        datos[0]=n.getAttributes().item(0).getNodeValue();
        NodeList nodos=n.getChildNodes(); //Creamos la lista para guardar los nodos texto de cada nodo elemento.
        
        for(int i=0; i<nodos.getLength(); i++){
            ntemp=nodos.item(i); //asignamos valor del elemento en posición i.
            
            if(ntemp.getNodeType()==Node.ELEMENT_NODE){
                 datos[contador]=ntemp.getChildNodes().item(0).getNodeValue(); //Obtenemos el valor del nodo texto y lo guardamos.
                 contador++;
            }
        }
        
        return datos;
    }
    
    //Método para añadir un nuevo nodo al DOM.
    
    public int annadirDom(Document doc, String titulo, String autor, String anno){
        try{
             Node nTitulo=doc.createElement("Titulo");
             Node nTitulo_text= doc.createTextNode(titulo);
             //añadimos el nodo de texto creado como hijo del elemento título.
             nTitulo.appendChild(nTitulo_text);
             //Autor.
             Node nAutor=doc.createElement("Autor");
             Node nAutor_text=doc.createTextNode(autor);
             //añadimos el nodo de texto.
             nAutor.appendChild(nAutor_text);
             //Creamos un nodo de tipo libro.
             Node nLibro=doc.createElement("Libro");
             //Añadimos el atributo.
             ((Element)nLibro).setAttribute("publicado",anno);
             //Se añade a este nodo Libro el nodo autor y titulo creado antes.
             nLibro.appendChild(nTitulo);
             nLibro.appendChild(nAutor);
             
             //Obtenemos el primer nodo del documento y le añadimos como hijo el nodo Libro
             Node raiz=doc.getChildNodes().item(0);
             raiz.appendChild(nLibro);
            
            return 0;
        }catch(Exception e){
           e.printStackTrace();
           return -1;
        }
 
    }
    
    //Método que nos permite guardar el archivo.
    
    public int guardarDomComoFile(String nombreArchivo){
  
           try{
                    
            File archivo_xml=new File(nombreArchivo);
            
            OutputFormat format= new OutputFormat(doc); //especificamos el formato de salida.
            //Especificamos que la salida esté indentada
            format.setIndenting(true);
            //Escribe el contenido en el file.
            XMLSerializer serializer=new XMLSerializer(new FileOutputStream(archivo_xml),format);
            
            serializer.serialize(doc);
            
            System.out.println(nombreArchivo);
            
            return 0;
            
        
        }catch(Exception e){
            System.out.println("No se ha guardado el archivo");
            return -1;
        }

          
        }
    
    //Método para cambiar el valor de un nodo texto ya existente.
    
    public void modificaTitulo(Document doc, String entrada, String nuevoTitulo){
         String datos_nodo[]=null;
         Node node;

         //Obtenemos la raíz.
         Node raiz=doc.getFirstChild();
         //Obtenemos en una lista todos los nodos hijos del nodo raíz.
         NodeList lista=raiz.getChildNodes();
         //Procesamos los nodos hijo.
         
         for(int i=0; i<lista.getLength();i++){
             node=lista.item(i);
             
             if(node.getNodeType()==Node.ELEMENT_NODE){
                String[] datos=new String[3];
                Node ntemp=null;
                int contador=1;
                //Valor del primer atributo del nodo.
                datos[0]=node.getAttributes().item(0).getNodeValue();
                //Obtenemos los nodos hijos del nodo libro.
                NodeList nodos=node.getChildNodes();
                //Recorremos la lista de nodos hijo para obtener su valor del nodo texto.
                for(int j=0;j<nodos.getLength();j++){
                   ntemp=nodos.item(i); //Asignamos el valor del nodo en posición i y comparamos.Si es un tipo elemento, ejecuta el código.
                   if(ntemp.getNodeType()==Node.ELEMENT_NODE){
                      datos[contador]=ntemp.getChildNodes().item(0).getNodeValue(); //Obtenemos los valores del nodo texto.
                      contador++;
                     //Hacemos la comprobación. Si se corresponde el dato introducido con un título, se hace el cambio.
                      if(datos[i].equals(entrada)){       
                          ntemp.setTextContent(nuevoTitulo);
                      }                    
                     
                       
                   }
                  
                }      
             
             }
    }
    
    
    
}

}
