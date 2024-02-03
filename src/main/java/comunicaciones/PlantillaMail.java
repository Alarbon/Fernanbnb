package comunicaciones;

public class PlantillaMail {
    public static String mensajeToken(String token) {
        return String.format("<body style=\"background-color: black \">\n" +
                "\n" +
                "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                "                <img width=\"100%%\" style=\"display:block; \"\n" +
                "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 0\">\n" +
                "                <p></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1\">\n" +
                "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                "                    <h2 style=\"color: #ff385c; text-align: center; \">¡¡Bienvenido a Fernanbnb!!</h2>\n" +
                "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                "                        Fernanbnb es una aplicación de alquileres de viviendas donde puedes subir propiedades para que\n" +
                "                        otros usuarios puedan reservarlas. Además, la aplicación ofrece características de seguridad\n" +
                "                        para garantizar un proceso de reserva seguro y sin problemas.</p>\n" +
                "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                "                        <li>Publica tu vivienda.</li>\n" +
                "                        <li>Busca viviendas disponibles.</li>\n" +
                "                        <li>Reserva la vivienda a buenos precios.</li>\n" +
                "                    </ul>\n" +
                "                    <p style=\"margin-top: 30px;\"></p>\n" +
                "                    <h2 style=\"color: #000000; text-align: center;margin: 2px; font-size: 15px;\">\n" +
                "                        El token para confirmar es el siguiente:</h2>\n" +
                "                    <div\n" +
                "                        style=\"width: 34%%; text-align: center; margin: 0px auto;background-color: #ff385c; border-radius: 5px;\">\n" +
                "                        <h3 style=\"padding: 10%%;  color: white; \">%S</h3>\n" +
                "                    </div>\n" +
                "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                "\n" +
                "                    </div>\n" +
                "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                "                        2023 ©</p>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>", token);
    }

    public static String mensajeTokenWeb(String idUsuario) {
        return String.format("<body style=\"background-color: black\">\n" +
                "\n" +
                "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                "                <img width=\"100%%\" style=\"display:block;\"\n" +
                "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 0\">\n" +
                "                <p></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1\">\n" +
                "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                "                    <h2 style=\"color: #ff385c; text-align: center;\">¡¡Bienvenido a Fernanbnb!!</h2>\n" +
                "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                "                        Fernanbnb es una aplicación de alquileres de viviendas donde puedes subir propiedades para que\n" +
                "                        otros usuarios puedan reservarlas. Además, la aplicación ofrece características de seguridad\n" +
                "                        para garantizar un proceso de reserva seguro y sin problemas.</p>\n" +
                "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                "                        <li>Publica tu vivienda.</li>\n" +
                "                        <li>Busca viviendas disponibles.</li>\n" +
                "                        <li>Reserva la vivienda a buenos precios.</li>\n" +
                "                    </ul>\n" +
                "                    <p style=\"margin-top: 30px;\"></p>\n" +
                "                    <h2 style=\"color: #000000; text-align: center;margin: 2px; font-size: 15px;\">\n" +
                "                        Haz click aquí para validar tu cuenta:</h2>\n" +
                "                    <div\n" +
                "                        style=\"width: 34%%; text-align: center; margin: 0px auto;background-color: #ff385c; border-radius: 5px;\">\n" +
                "                        <h3 style=\"padding: 10%%;  color: white;\"><a href=\"http://192.168.1.201:8080/fernanbnb_war_exploded/validaCuentaToken.jsp?id="+idUsuario+"\">Validar cuenta</a>\n" +
                "                        </h3>\n" +
                "                    </div>\n" +
                "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                "                    </div>\n" +
                "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                "                        2023 ©</p>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n");

    }

    public static String mensajeReservaPropietario(String nombreProp, String titulo, String descripcion, String localidad, String provincia, double precioNoche,
                                                   int huspedMax, String correoUser, String nombreUser, String fechaInicial, String fechaFinal, int numNoches, double PrecioTotal, int numHuesped) {
        return String.format("\n" +
                        "<body style=\"background-color: black \">\n" +
                        "\n" +
                        "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                        "                <img width=\"100%%\" style=\"display:block; \"\n" +
                        "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"padding: 0\">\n" +
                        "                <p></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1\">\n" +
                        "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                        "                    <h2 style=\"color: #ff385c; text-align: center; \">¡Su vivienda tiene una reserva!</h2>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Enhorabuena %s!, su vivienda con las siguientes características ha sido reservada:\n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Titulo:</b> %s.</li>\n" +
                        "                        <li><b>Descripción:</b> %s.</li>\n" +
                        "                        <li><b>Localidad:</b> %s.</li>\n" +
                        "                        <li><b>Provincia:</b> %s.</li>\n" +
                        "                        <li><b>Precio por noche:</b> %s€.</li>\n" +
                        "                        <li><b>Número de huéspedes máximo:</b> %s.</li>\n" +
                        "\n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        El contacto del usuario que ha reservado la vivienda es \"%s\" a nombre de \"%s\". <br>\n" +
                        "                        La vivienda ha sido reservada con las siguientes carácteristicas: \n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Fecha inicio:</b> %s.</li>\n" +
                        "                        <li><b>Fecha salida:</b> %s.</li>\n" +
                        "                        <li><b>Precio Total:</b> %s.</li>\n" +
                        "                        <li><b>Número de huéspedes:</b> %s.</li>\n" +
                        "                    \n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Si tiene algún problema  o crees que hay algun fallo en los datos, contacte con nosotros en \"fernanbnb.alarbon@gmail.com\"\n" +
                        "                    </p>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "\n" +
                        "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                        "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                        "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                        "\n" +
                        "                    </div>\n" +
                        "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                        "                        2023 ©</p>\n" +
                        "                </div>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "</body>", nombreProp, titulo, descripcion, localidad, provincia, precioNoche,
                huspedMax, correoUser, nombreUser, fechaInicial, fechaFinal, numNoches, PrecioTotal, numHuesped);
    }

    public static String mensajeReservaUsuario(String nombreUser, String titulo, String descripcion, String localidad, String provincia, double precioNoche,
                                               int huspedMax, String correoProp, String nombreProp, String fechaInicial, String fechaFinal, int numNoches, double precioTotal, int numHuesped) {
        return String.format("\n" +
                        "\n" +
                        "<body style=\"background-color: black \">\n" +
                        "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\\\">\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                        "                <img width=\"100%%\" style=\"display:block; \"\n" +
                        "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"padding: 0\">\n" +
                        "                <p></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1\">\n" +
                        "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                        "                    <h2 style=\"color: #ff385c; text-align: center; \">¡Has hecho una reserva!</h2>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Enhorabuena %s!, has hecho una reserva de la  vivienda con las siguientes características:\n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Titulo:</b> %s.</li>\n" +
                        "                        <li><b>Descripción:</b> %s.</li>\n" +
                        "                        <li><b>Localidad:</b> %s.</li>\n" +
                        "                        <li><b>Provincia:</b> %s.</li>\n" +
                        "                        <li><b>Precio por noche:</b> %s€.</li>\n" +
                        "                        <li><b>Número de huéspedes máximo:</b> %s.</li>\n" +
                        "\n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        El contacto del propietario de la vivienda es \"%s\" a nombre de \"%s\". <br>\n" +
                        "                       La reserva tiene las siguientes características: \n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Fecha inicio:</b> %s.</li>\n" +
                        "                        <li><b>Fecha salida:</b> %s.</li>\n" +
                        "                        <li><b>Número de huéspedes:</b> %s.</li>\n" +
                        "                        <li><b>Precio Total:</b> %s.</li>\n" +
                        "\n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Si tiene algún problema o crees que hay algun fallo en los datos, contacte con nosotros en\n" +
                        "                        \"fernanbnb.alarbon@gmail.com\"\n" +
                        "                    </p>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "\n" +
                        "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\\\">\n" +
                        "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                        "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                        "\n" +
                        "                    </div>\n" +
                        "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\"\n" +
                        "                        2023 ©</p>\n" +
                        "                </div>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "</body>\n", nombreUser, titulo, descripcion, localidad, provincia, String.format("%.2f", precioNoche),
                huspedMax, correoProp, nombreProp, fechaInicial, fechaFinal, numNoches, String.format("%.2f", precioTotal), numHuesped);


    }

    public static String mensajeCancelaReservaUsuario(String nombreUser, String titulo, String descripcion, String localidad, String provincia, double precioNoche,
                                                      int huspedMax, String correoProp, String nombreProp, String fechaInicial, String fechaFinal, int numNoches, double precioTotal, int numHuesped) {
        return String.format("\n" +
                        "\n" +
                        "<body style=\"background-color: black \">\n" +
                        "\n" +
                        "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                        "                <img width=\"100%%\" style=\"display:block; \"\n" +
                        "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"padding: 0\">\n" +
                        "                <p></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1\">\n" +
                        "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                        "                    <h2 style=\"color: #ff385c; text-align: center; \">¡Una reserva de su vivienda ha sido cancelada!</h2>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Tenemos algo que contarte %s!, tiene una reserva cancelada de la siguiente vivienda:\n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Titulo:</b> %s.</li>\n" +
                        "                        <li><b>Descripción:</b> %s.</li>\n" +
                        "                        <li><b>Localidad:</b> %s.</li>\n" +
                        "                        <li><b>Provincia:</b> %s.</li>\n" +
                        "                        <li><b>Precio por noche:</b> %s€.</li>\n" +
                        "                        <li><b>Número de huéspedes máximo:</b> %s.</li>\n" +
                        "\n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        El contacto del usuario que ha reservado la vivienda es \"%s\" a nombre de \"%s\". <br>\n" +
                        "                        La reserva cancelada tenia las siguientes características: \n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Fecha inicio:</b> %s.</li>\n" +
                        "                        <li><b>Fecha salida:</b> %s.</li>\n" +
                        "                        <li><b>Precio Total:</b> %s.</li>\n" +
                        "                        <li><b>Número de huéspedes:</b> %s.</li>\n" +
                        "                    \n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Si tiene algún problema  o crees que hay algun fallo en los datos, contacte con nosotros en \"fernanbnb.alarbon@gmail.com\"\n" +
                        "                    </p>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "\n" +
                        "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                        "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                        "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                        "\n" +
                        "                    </div>\n" +
                        "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                        "                        2023 ©</p>\n" +
                        "                </div>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "</body>\n", nombreUser, titulo, descripcion, localidad, provincia, String.format("%.2f", precioNoche),
                huspedMax, correoProp, nombreProp, fechaInicial, fechaFinal, numNoches, String.format("%.2f", precioTotal), numHuesped);
    }

    public static String mensajeCancelaReservaPropietario(String nombreProp, String titulo, String descripcion, String localidad, String provincia, double precioNoche,
                                                          int huspedMax, String correoUser, String nombreUser, String fechaInicial, String fechaFinal, int numNoches, double precioTotal, int numHuesped) {
        return String.format("\n" +
                        "<body style=\"background-color: black \">\n" +
                        "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                        "                <img width=\"100%%\" style=\"display:block; \"\n" +
                        "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"padding: 0\">\n" +
                        "                <p></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td style=\"background-color: #ecf0f1\">\n" +
                        "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                        "                    <h2 style=\"color: #ff385c; text-align: center; \">¡Una reserva de su vivienda ha sido cancelada!</h2>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Tenemos algo que contarte %s!, tiene una reserva cancelada de la siguente vivienda:\n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Titulo:</b> %s.</li>\n" +
                        "                        <li><b>Descripción:</b> %s.</li>\n" +
                        "                        <li><b>Localidad:</b> %s.</li>\n" +
                        "                        <li><b>Provincia:</b> %s.</li>\n" +
                        "                        <li><b>Precio por noche:</b> %s €.</li>\n" +
                        "                        <li><b>Número de huéspedes máximo:</b> %s.</li>\n" +
                        "\n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        El contacto del usuario que ha reservado la vivienda es \"%s\" a nombre de \"%s\". <br>\n" +
                        "                        La reserva cancelada tenia las siguientes características: \n" +
                        "                    </p>\n" +
                        "                    <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "                        <li><b>Fecha inicio:</b> %s.</li>\n" +
                        "                        <li><b>Fecha salida:</b> %s.</li>\n" +
                        "                        <li><b>Precio Total:</b> %s €.</li>\n" +
                        "                        <li><b>Número de huéspedes:</b> %s.</li>\n" +
                        "                    \n" +
                        "                    </ul>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                        "                        Si tiene algún problema  o crees que hay algun fallo en los datos, contacte con nosotros en \"fernanbnb.alarbon@gmail.com\"\n" +
                        "                    </p>\n" +
                        "                    <p style=\"margin-top: 30px;\"></p>\n" +
                        "\n" +
                        "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                        "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                        "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                        "\n" +
                        "                    </div>\n" +
                        "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                        "                        2023 ©</p>\n" +
                        "                </div>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "</body>", nombreProp, titulo, descripcion, localidad, provincia, String.format("%.2f", precioNoche),
                huspedMax, correoUser, nombreUser, fechaInicial, fechaFinal, numNoches, String.format("%.2f", precioTotal), numHuesped);
    }

    public static String mensajeListadoReservas(String nombreUser) {
        return String.format("<body style=\"background-color: black \">\n" +
                "\n" +
                "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                "                <img width=\"100%%\" style=\"display:block; \"\n" +
                "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 0\">\n" +
                "                <p></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1\">\n" +
                "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                "                    <h2 style=\"color: #ff385c; text-align: center; \">¡¡Aqui tiene sus reservas!!</h2>\n" +
                "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                "                        Hola %s aquí tiene el documento excel con el listado de reservas actuales. En la columna \"Reserva pendiente\" cuando pone false es que ya ha pasado la fecha,\n" +
                "                    si pone true es que esta pendiente aún.</p>\n" +
                "                   \n" +
                "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                "\n" +
                "                    </div>\n" +
                "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                "                        2023 ©</p>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>", nombreUser);
    }

    public static String mensajeListadoViviendasPropietario(String nombrePropietario) {
        return String.format("<body style=\"background-color: black \">\n" +
                "\n" +
                "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                "                <img width=\"100%%\" style=\"display:block; \"\n" +
                "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 0\">\n" +
                "                <p></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1\">\n" +
                "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                "                    <h2 style=\"color: #ff385c; text-align: center; \">¡¡Aqui tiene sus viviendas!!</h2>\n" +
                "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                "                        Hola %s aquí tiene el documento excel con el listado sus viviendas. Si tiene reservas es true es que\n" +
                "                         hay reservas si es false es que no hay.</p>\n" +
                "                   \n" +
                "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                "\n" +
                "                    </div>\n" +
                "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                "                        2023 ©</p>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>", nombrePropietario);
    }
    public static String mensajeResumenReservaUsuario(String nombreUsuario) {
        return String.format("<body style=\"background-color: black \">\n" +
                "\n" +
                "    <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                "                <img width=\"100%%\" style=\"display:block; \"\n" +
                "                    src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592033706054/LogoGmail.png\">\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 0\">\n" +
                "                <p></p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #ecf0f1\">\n" +
                "                <div style=\"color: #34495e; margin: 4%% 10%% 2%%; text-align: justify;font-family: sans-serif\">\n" +
                "                    <h2 style=\"color: #ff385c; text-align: center; \">¡¡Aqui tiene el resumen de su reserva!!</h2>\n" +
                "                    <p style=\"margin: 2px; font-size: 15px\">\n" +
                "                        Hola %s aquí tiene el resumen de su reserva. Para cualquier problema contacte con nosotros.</p>\n" +
                "                   \n" +
                "                    <div style=\"width: 100%%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                "                        <img style=\"padding: 0; width: 100%%; margin: 0px auto;\"\n" +
                "                            src=\"https://cdn.discordapp.com/attachments/1052443807112773724/1067485592432169030/pisito.png\">\n" +
                "\n" +
                "                    </div>\n" +
                "                    <p style=\"color: #000000; font-size: 13px; text-align: center;margin: 30px 0 0\">Fernanbnb Alarbon\n" +
                "                        2023 ©</p>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>", nombreUsuario);
    }
}
