package uiMain;

public class App {


    public static void main(String[] args) {
        Usuario user = null; // Variable para almacenar instancia del usuario
        int opcion = 0; // Variable para almacenar la opción seleccionada

        // Función que imprime un separador visual en la consola
        separador();

        // Función que muestra un mensaje de bienvenida en color morado y negrita
        aviso(negrita(colorTexto("Bienvenido al programa", "morado")));

        // Otro separador
        separador();

        // Bucle principal del programa
        do {
            user = gestionUsuario.getUser(); // Obtiene la información del usuario

            if (user == null) {
                // Si no hay un usuario registrado

                // Muestra un mensaje de aviso
                aviso("¡No hay sesión iniciada!");

                // Salto de línea
                salto();

                // Ofrece dos opciones al usuario: Iniciar Sesión o Salir
                identacion("1. Iniciar Sesión.");
                identacion("2. Salir.");

                // Salto de línea
                salto();

                // Pide al usuario que ingrese una opción
                promptIn("Opcion:");
                opcion = inputI();

                // Separador grande
                separadorGrande();

                switch (opcion) {

                    case 1:
                        // Iniciar Sesión

                        int intentos = 0; // Variable para contar los intentos

                        do {
                            // Muestra un mensaje de inicio de sesión en color morado
                            identacion(negrita(colorTexto("Iniciar sesión", "morado")), 4);
                            salto();

                            // Pide al usuario que ingrese su correo
                            printNegrita("Mail:");
                            String mail = inputS();

                            // Pide al usuario que ingrese su contraseña
                            printNegrita("Contraseña:");
                            String contrasena = inputS();

                            // Separador grande
                            separadorGrande();

                            // Intenta iniciar sesión con el correo y la contraseña proporcionados
                            user = gestionUsuario.iniciarSesion(mail, contrasena);

                            if (user == null) {
                                // Si la sesión no se inicia con éxito, muestra un mensaje de error
                                salto();
                                aviso(colorTexto("Usuario inválido o no existe, intente nuevamente", "rojo"));
                                salto();
                                intentos++;
                            }

                        } while (user == null && intentos < 3);

                        if (intentos >= 3) {
                            break;
                        }

                        salto();
                        // Muestra un mensaje de sesión iniciada con éxito en color verde
                        aviso(colorTexto("Sesión iniciada con éxito", "verde"));
                        salto();
                        // Muestra un mensaje de bienvenida personalizado
                        titulo(colorTexto(("Bienvenido " + user.getNombre() + " :)"), "morado"));
                        salto();

                        continuar();
                        break;

                    case 2:
                        // Si el usuario selecciona la opción 2, se sale del programa
                        System.out.println(colorTexto("Saliendo del programa. ¡Adios!", "morado"));
                        separadorGrande();
                        System.exit(0);

                    default:
                        // Si la opción ingresada no es válida, se establece user a null
                        user = null;
                        aviso(colorTexto("Opción incorrecta", "rojo"));
                        break;
                }
            }

            while (opcion != 6 && user != null) {
                // Bucle que se ejecuta mientras la opción no sea 6 (Salir) y haya un usuario
                // registrado

                // Separador grande
                separadorGrande();

                // Muestra un menú en color morado
                identacion(negrita(colorTexto("> - Menú - <", "morado")), 4);
                salto();
                identacion("1. Comprar vuelo");
                identacion("2. Reasignar vuelo");
                identacion("3. Cancelar vuelo");
                identacion("4. Gestion cuenta");
                identacion("5. Check in");
                identacion("6. Salir");
                separador();

                // Pide al usuario que seleccione una opción
                promptIn("Seleccione una opción (1-5): ");
                opcion = inputI();
                salto();

                switch (opcion) {
                    case 1:
                        // Opción 1: Comprar vuelo

                        salto();
                        seleccionado("Comprar vuelo");
                        separadorGrande();

                        // Llama a la función comprarVuelo pasando el objeto de usuario como argumento
                        comprarVuelo(user);
                        separadorGrande();
                        break;

                    case 2:
                        // Opción 2: Reasignar vuelo

                        salto();
                        seleccionado("Reasignar vuelo");
                        separadorGrande();

                        // Llama a la función reasignarVuelo pasando el objeto de usuario como argumento
                        reasignarVuelo(user);
                        separadorGrande();
                        break;

                    case 3:
                        // Opción 3: Cancelar vuelo

                        seleccionado("Cancelar vuelo");
                        separadorGrande();

                        // Llama a la función cancelarVuelo pasando el objeto de usuario como argumento
                        cancelarVuelo(user);
                        separadorGrande();
                        break;

                    case 4:
                        // Opción 4: Gestion de cuenta

                        seleccionado("Gestion de cuenta");
                        separadorGrande();

                        // Llama a la función gestionCuenta pasando el objeto de usuario como argumento
                        gestionCuenta(user);

                        // Actualiza el objeto de usuario después de la gestión de la cuenta
                        user = gestionUsuario.getUser();
                        separadorGrande();
                        break;

                    case 5:
                        // Opción 5: Check in

                        seleccionado("Check in");

                        // Actualiza el objeto de usuario después de realizar el check-in
                        user = gestionUsuario.getUser();
                        separadorGrande();

                        // Llama a la función checkin pasando el objeto de usuario como argumento
                        checkin(user);
                        separadorGrande();
                        user = gestionUsuario.getUser();
                        break;

                    case 6:
                        // Opción 6: Salir

                        separadorGrande();
                        // Muestra un mensaje de despedida en color morado y sale del programa
                        System.out.println(negrita(colorTexto("Saliendo del programa. ¡Adios!", "morado")));
                        System.exit(0);

                    default:
                        // Si la opción no es válida, muestra un mensaje de error en rojo
                        System.out.println(
                                colorTexto("Opción no válida. Por favor, seleccione una opción válida (1-5).", "rojo"));
                        break;
                }
            }
        } while (true); // El programa se ejecuta en un bucle infinito
    }

    
    private static void reasignarVuelo(Usuario user) {

        // Obtener el historial de boletos del usuario
        ArrayList<Boleto> historial = user.getHistorial();

        identacion(negrita(colorTexto("Información de los vuelos:", "morado")));
        salto();

        // Iterar a través del historial de boletos
        for (int i = 0; i < historial.size(); i++) {
            Boleto boleto = historial.get(i);
            // Mostrar información de cada boleto en la lista
            identacion(i + ". " + boleto.getInfo(), 2);
        }

        separador();

        promptIn("Por favor, seleccione el número del vuelo deseado: ");
        int indexVueloTemp = inputI();
        // Obtener el boleto seleccionado por el usuario
        Boleto boletoSelec = historial.get(indexVueloTemp);

        separadorGrande();
        System.out.println("Vuelo seleccionado, información detallada:");
        salto();
        identacion(boletoSelec.getInfo());

        separador();

        if (!boletoSelec.getCheckInRealizado()) {
            promptIn("Está seguro de reasignar el vuelo? (Escriba 1 para Confirmar, 0 para Cancelar):");
            int confirmacionTemp = inputI();

            if (confirmacionTemp == 1) {
                // Limpiar
                boletoSelec.resetEquipaje();
                Asiento asientoPrevio = boletoSelec.getAsiento();
                asientoPrevio.desasignarBoleto();
                user.reasignarBoleto(boletoSelec);
                boletoSelec.resetEquipaje();
                // - - - - - - - -
            } else {
                System.out.println(colorTexto("Proceso cancelado, hasta luego!", "rojo"));
                return;

            }

            separadorGrande();
            // Solicitar al usuario el origen del vuelo.
            String origen = boletoSelec.getOrigen();
            identacion(colorTexto("Origen: ", "morado") + origen);

            // Solicitar al usuario el destino del vuelo.
            String destino = boletoSelec.getDestino();
            identacion(colorTexto("Destino: ", "morado") + destino);

            // Ingrese la cantidad de vuelos a generar?
            // Generar una lista de n vuelos con el origen y destino proporcionados.
            ArrayList<Vuelo> vuelos = Vuelo.generarVuelos(5, origen, destino);

            separador();

            // Mostrar información sobre los vuelos generados.
            identacion("Vuelo - Origen - Destino");// Por mejorar
            salto();

            for (int i = 0; i < vuelos.size(); i++) {
                Vuelo vuelo = vuelos.get(i);
                identacion(vuelo.getInfo(), 2);
            }

            separador();

            // Solicitar al usuario que seleccione un vuelo y se selecciona.
            promptIn("Por favor, seleccione el número del vuelo deseado: ");
            int indexVuelo = inputI();
            Vuelo vuelo = vuelos.get(indexVuelo);

            // Generar asientos VIP y económicos para el vuelo seleccionado.
            vuelo.generarAsientos(3, 5, 100);

            // Crear un boleto para el usuario con el origen, destino y vuelo seleccionados.
            boletoSelec.setVuelo(vuelo);
            separador();

            // Mostrar los tipos de asientos disponibles y sus precios
            // System.out.println("Tipos de asientos disponibles:");

            // Mostrar información sobre los asientos disponibles en el vuelo.
            identacion(negrita(colorTexto("Asientos disponibles:", "morado")));
            ArrayList<Asiento> asientos = vuelo.getAsientos();
            salto();

            for (Asiento asiento : asientos) {
                identacion(asiento.getInfo(), 2);
            }

            // Solicitar al usuario que seleccione un número de asiento.
            salto();
            promptIn(
                    "Por favor, seleccione el número del asiento deseado, a este valor se le agregara un sobrecargo del 10%: ");
            int indexAsiento = inputI();
            Asiento asiento = asientos.get(indexAsiento - 1);
            boletoSelec.reasignarAsiento(asiento);

            // Si se selecciona y es valido se prosigue...

            // Se muestra una previsualizacion del precio
            separador();
            System.out.println("Previsualización del precio: " + colorTexto("$" + boletoSelec.getValor(), "verde")
                    + " ,se agregará un recargo extra del 10%");
            separador();

            // Preguntar al usuario si desea añadir equipaje.
            promptIn("¿Desea añadir equipaje? Tiene derecho a llevar maximo 5 maletas. (1 si / 0 no)");
            int opcion = inputI();

            int cMaletas = 0;

            if (opcion == 1) {
                // Cada vez q se agrega un equipaje se va mostrando una previsualizacion del
                // precio..
                // Segun la cantidad de equipaje y los precios de cada uni
                int exit = 1;
                int c = 0;
                do {
                    c += 1;
                    separador();
                    // Solicitar información sobre el equipaje a agregar.

                    promptIn("Peso de la maleta (max 250Kg): ");
                    int peso = inputI();

                    promptIn("Ancho de la maleta (max 250cm): ");
                    int ancho = inputI();

                    promptIn("Largo de la maleta (max 250cm): ");
                    int largo = inputI();

                    promptIn("Alto de la maleta (max 250cm): ");
                    int alto = inputI();

                    // Agregar una maleta al boleto y mostrar el nuevo valor del boleto.
                    Maleta maleta = new Maleta(c, peso, largo, ancho, alto);
                    if (maleta.verificarRestricciones()) {

                        maleta.asignarBoleto(boletoSelec);
                        boletoSelec.addEquipaje(maleta);
                        cMaletas += 1;

                        separador();
                        System.out.println(negrita(colorTexto("Nuevo valor del boleto:", "morado")));
                        System.out.println((colorTexto("-> $" + boletoSelec.getValor(), "verde")));
                        salto();
                        promptIn("¿Desea agregar otro equipaje o continuar? (1 para Sí, 0 para No)");
                        exit = inputI();

                    } else {
                        salto();
                        promptError("La maleta excede las especificaciones maximas, intente nuevamente");
                        salto();
                    }

                } while (exit == 1 && cMaletas != 5);
            }

            salto();
            printNegrita("Maletas agregadas con exito, cantidad de maletas: " + cMaletas);
            continuar();
            // !!! Error !!! Error !!! Error !!!

            // Mostrar los detalles de la compra y solicitar confirmación.
            separadorGrande();

            promptOut("¿Desea finalizar la compra? Los detalles son los siguientes:");
            salto();
            identacion(boletoSelec.getInfo());

            separadorGrande();
            promptIn("Confirmar (Escriba 1 para Confirmar, 0 para Cancelar)");
            int confirmacion = inputI();

            separador();

            if (confirmacion == 1) {
                // Comprobar si el usuario tiene suficiente dinero y, si es así, realizar la
                // compra.
                if (user.getDinero() - boletoSelec.getValor() >= 0) {
                    user.comprarBoletoReasig(boletoSelec);
                    boletoSelec.setStatus("Reasignado");
                    boletoSelec.asignarAsiento(asiento);
                    System.out.println(negrita(colorTexto("Boleto comprado con éxito. Detalles:", "morado")));
                    identacion(boletoSelec.getInfo());

                } else {
                    System.out.println(colorTexto("Dinero insuficiente. Compra cancelada.", "rojo"));
                }
            } else {
                System.out.println(colorTexto("Compra cancelada.", "rojo"));
            }
        } else {
            separador();
            System.out.println(colorTexto(
                    "Usted ya realizo el Check-in para este vuelo por lo tanto no es posible reasignar el vuelo",
                    "rojo"));
            continuar();
        }

    }


}
