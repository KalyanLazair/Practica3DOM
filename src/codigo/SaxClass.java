/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Marta
 */
public class SaxClass {
    //Vars de instancia que vamos a utilizar.
    ManejadorSAX sh;
    SAXParser parser;
    File ficheroXML;
    //Método que abre el archivo con Sax.
    public int abrirSax(File fichero){
       try{
           SAXParserFactory factory= SAXParserFactory.newInstance();
           //Se crea un objeto SAXParser para interpretar el documento XML. Esta es la parte que lee el documento.
           parser=factory.newSAXParser();
           //Creamos una instancia del manejador que será el que recorra el documento secuencialmente.
           sh=new ManejadorSAX();
           ficheroXML=fichero;
           
           return 0;
       }catch(Exception e){
           e.printStackTrace();
           return -1;
       }
    
    }
    
    //El manejador se mantiene a la escucha de los eventos y cuando detecta un evento lo captura.
    
    class ManejadorSAX extends DefaultHandler{
            int ultimoElemento;
            String cadena_resultado="";
            String aux="";
            
            public ManejadorSAX(){
                ultimoElemento=0;
            }
            //Hacemos el override de Start y establecemos lo que va a hacer el programa cuando encuentre estos elementos.
            @Override public void startElement(String uri, String localName, String qName, Attributes atts)throws SAXException{
                if(qName.equals("libro")){
                    cadena_resultado= cadena_resultado + "\nPublicado en;" + atts.getValue(atts.getQName(0))+ "\n"; 
                    ultimoElemento=1;
                }else if(qName.equals("titulo")){
                    ultimoElemento=2;
                    cadena_resultado=cadena_resultado+ "\nEl título es;";
                }else if(qName.equals("autor")){
                    ultimoElemento=3;
                    cadena_resultado=cadena_resultado + "\nEl autor es;";
                }else if(qName.equals("libros")){
                    aux="Se va a imprimir un libro";
                    cadena_resultado= aux + cadena_resultado;
                }
            }
            //Cuando se detecta el final de un elemento libro, se pone una linea discontinua en la salida.
            
            @Override public void endElement(String uri, String localName, String qName)throws SAXException{
                if(qName.equals("libro")){
                   System.out.println("He encontrado el final de un elemento");
                   cadena_resultado= cadena_resultado + "\n ---------------------";
                }
            }
            
            //Cuando se detecta una cadena de texto posterior a uno de los elementos título o autor, guarda ese texto en la variable correspondiente.
            
            @Override public void characters(char[] ch,int start, int longitud)throws SAXException{
                 if(ultimoElemento==2){
                    for(int i=start; i<longitud+start;i++){
                       cadena_resultado=cadena_resultado+ch[i];
                    }
                 }else if(ultimoElemento==3){
                    for(int i=start; i<longitud+start;i++){
                       cadena_resultado=cadena_resultado+ch[i];
                    }                 
                 }
            }           

     }
    //Recorremos el SAX.
    public String recorrerSax(File fXML, ManejadorSAX sh, SAXParser parser){
        try{
            parser.parse(fXML,sh);
            return sh.cadena_resultado;
        }catch(SAXException e){
            e.printStackTrace();
            return "error al parsear con SAX";
        }catch(Exception e){
            e.printStackTrace();
            return "error al parsear con SAX";
        }        
    
    }
    
    
    
    
    
}
