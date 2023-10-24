package uiMain;

public class App {

    public static void main(String[] args) {

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
