package comunicaciones;

import models.Propietario;
import models.Reserva;
import models.Usuario;
import models.Vivienda;

public class PlantillaTelegram {
  /*
   * %%0A
   * Al poner eso en el mensaje hace un salto de linea al mostrarlo en telegram
   * */
  public static String messageTelegramViviendaAdd(Vivienda v, Propietario p) {
    return String.format("""
            Se creo una nueva vivienda.%%0ACreada por: %s.%%0AID DE LA VIVIENDA: %s.%%0ATITULO: %s.%%0ALOCALIDAD: %s.%%0APROVINCIA: %s.
            """, p.getNombre().toUpperCase(), v.getId(), v.getTitulo(), v.getLocalidad(), v.getProvincia());
  }

  public static String messageTelegramReservaAdd(Reserva r, Usuario u) {
    return String.format("""
                    Se creo una nueva reserva. %%0ACreada por: %s. %%0AID DE LA RESERVA: %s. %%0AID DE LA VIVIENDA: %s.""",
            u.getNombre().toUpperCase(), r.getId(), r.getIdVivienda());
  }


  public static String messageTelegramReservaCancelada(Reserva r, Usuario u) {
    return String.format("""
                    Se cancelo una reserva. %%0ACancelada por %s. %%0AID DE LA RESERVA: %s. %%0AID DE LA VIVIENDA: %s""",
            u.getNombre().toUpperCase(), r.getId(), r.getIdVivienda());
  }


}
