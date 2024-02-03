package comunicaciones;

import persistencia.Persistencia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Telegram {
  public static boolean enviarMensajeTelegram(String mensaje) {
    String direccion; //URL de la API de mi bot en mi conversacion
    String fijo = Persistencia.devuelveKeysComunicaciones("telegram.key");
    direccion = fijo + mensaje;//metemos el mensaje al final;
    URL url;
    boolean dev;
    dev = false;
    try {
      url = new URL(direccion);
      URLConnection con = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      dev = true;
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }//
    return dev;
  }
  public static boolean enviarMensajeTelegramWeb(String mensaje,String context) {
    String direccion; //URL de la API de mi bot en mi conversacion
    String fijo = Persistencia.devuelveKeysComunicacionesWeb("telegram.key",context);
    direccion = fijo + mensaje;//metemos el mensaje al final;
    URL url;
    boolean dev;
    dev = false;
    try {
      url = new URL(direccion);
      URLConnection con = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      dev = true;
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }//
    return dev;
  }


}
