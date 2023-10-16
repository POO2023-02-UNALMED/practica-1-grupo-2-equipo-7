package uiMain;

import java.util.Scanner;

import gestorAplicacion.Aerolinea.*;
import gestorAplicacion.Cuenta.GestionUsuario;
import gestorAplicacion.Cuenta.Usuario;

import java.util.ArrayList;

public class App {

    public static GestionUsuario gestionUsuario = new GestionUsuario();

    public static void main(String[] args) {

        Usuario user = null;
        int opcion = 0;

        aviso("Bienvenido al programa");
        salto();
        // separador();
        // El usuario se debe serializar?

        do {
            user = gestionUsuario.getUser();

            if (user == null) {

                aviso("¡No hay sesión iniciada!");

                // Desea iniciar sesion o registrarse:
                identacion("1. Iniciar Sesión.");
                identacion("2. Registrarse.");

                opcion = inputI();

                separadorGrande();
                salto();

                switch (opcion) {
                    case 1:

                        // Iniciar sesion
                        do {
                            identacion("Iniciar sesión", 4);
                            salto();

                            System.out.println("Mail: ");
                            String mail = inputS();

                            System.out.println("Contraseña: ");
                            String contrasena = inputS();

                            salto(2);
                            separadorGrande();

                            user = gestionUsuario.iniciarSesion(mail, contrasena);
                            if (user == null) {
                                salto();
                                aviso("Usuario inválido, intente nuevamente");
                                salto();
                            }
                        } while (user == null);

                        salto();
                        aviso("Sesión iniciada con éxito");
                        salto();
                        break;

                    case 2:
                        // Registrar
                        do {
                            salto();
                            identacion("Registrarse", 4);
                            salto();

                            System.out.println("Nombre: ");
                            String nombre = inputS();

                            System.out.println("Mail: ");
                            String mail = inputS();

                            System.out.println("Contraseña: ");
                            String contrasena = inputS();
                            salto(2);
                            separadorGrande();

                            user = gestionUsuario.registrarUsuario(nombre, mail, contrasena);
                            if (user == null) {
                                salto();
                                aviso("El correo ya se encuentra registrado, intente nuevamente");
                                salto();
                            }
                        } while (user == null);

                        salto();
                        System.out.println("Usuario registrado con éxito");
                        salto();

                        break;

                    default:
                        user = null;
                        aviso("Opción incorrecta");
                        break;

                }

                /* Espacio para iniciar sesion cargando cuenta o creando y guardando */
            }

            while (opcion != 5 && user != null) {
                // System.out.println(user);

                titulo("Bienvenido " + user.getNombre() + ":)");
                salto();
                separadorGrande();
                salto();
                identacion("Menú", 4);
                salto();
                identacion("1. Comprar vuelo");
                identacion("2. Reasignar vuelo");
                identacion("3. Cancelar vuelo");
                identacion("4. Ver cuenta");
                identacion("5. Salir");
                salto(2);
                separadorGrande();

                prompt("Seleccione una opción (1-5): ");
                opcion = inputI();

                switch (opcion) {
                    case 1:
                        salto();
                        System.out.println(" - - - > Ha seleccionado la opción Comprar vuelo < - - -");
                        salto();
                        comprarVuelo(user);
                        separadorGrande();
                        break;

                    case 2:
                        salto();
                        System.out.println(" - - - > Ha seleccionado la opción Reasignar vuelo < - - -");
                        salto();
                        reasignarVuelo(user);
                        separadorGrande();
                        break;

                    case 3:
                        System.out.println(" - - - > Ha seleccionado la opción Cancelar vuelo < - - -");
                        System.out.println("");
                        cancelarVuelo(user);
                        separadorGrande();
                        break;

                    case 4:
                        System.out.println(" - - - > Ha seleccionado la opción Ver cuenta < - - -");
                        salto();
                        gestionCuenta(user);
                        user = gestionUsuario.getUser();
                        separadorGrande();
                        break;

                    case 5:
                        System.out.println("Saliendo del programa. ¡Adios!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opción no válida. Por favor, seleccione una opción válida (1-5).");
                        break;

                }
            }

        } while (true);

    }

    private static void comprarVuelo(Usuario user) {
        /*
         * Podemos ir dejando una variable local q vaya llevando los valores, puede ser
         * una instancia
         * de boleto, y al final se asigna todo, se puede usar boleto para ir calculando
         * el precio y etc
         */
        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor ingrese el origen: ");
        String origen = scanner.nextLine();

        System.out.println("Por favor ingrese el destino: ");
        String destino = scanner.nextLine();

        // Ingrese la cantidad de vuelos a generar?

        // Despues de haber creado el orgien y el destino necesito generar los vuelos y
        // mostrarlos vuelo/precio por tipo de asiento
        // Generar - Mostrar vuelos
        ArrayList<Vuelo> vuelos = Aeropuerto.generarVuelos(5, origen, destino); // Cantidad de vuelos

        for (int i = 0; i < vuelos.size(); i++) {
            Vuelo vuelo = vuelos.get(i);
            // Mostrar y enumerar (En proceso)
            System.out.println(". . . . .");
            System.out.println(i);
            System.out.println(vuelo.getInfo());
        }

        System.out.println("Selecciona por favor el vuelo: ");
        int indexVuelo = scanner.nextInt();

        // Lee y genera los asientos
        Vuelo vuelo = vuelos.get(indexVuelo);
        vuelo.generarAsientos(3, 5);

        Boleto boleto = new Boleto(origen, destino, user, vuelo);
        System.out.println("Los tipos de asientos disponibles son los siguientes:");

        // Primero muestra los precios de cada tipo de asiento, luego
        // Muestra los asientos disponibles y su tipo:

        System.out.println("Los asientos disponibles son los siguientes:");
        ArrayList<Asiento> asientos = vuelo.getAsientos();

        for (Asiento asiento : asientos) {
            System.out.println(asiento.getInfo());
        }

        System.out.println("Seleccione el numero de asiento disponible");
        int indexAsiento = scanner.nextInt();
        Asiento asiento = asientos.get(indexAsiento - 1);
        boleto.setAsiento(asiento);
        // Si se selecciona y es valido se prosigue...
        // Se muestra una previsualizacion del precio

        System.out.println("Desea continuar?");
        // Si sí, sigue, sino, selecciona otro asiento

        System.out.println("Selecciona si va a añadir equipaje o no");
        int opcion = scanner.nextInt();
        // SI la respuesta es si, entonces agrega varios equipajes, sino, no

        System.out.println("...............");
        if (opcion == 1) {
            // Cada vez q se agrega un equipaje se va mostrando una previsualizacion del
            // precio..
            // Segun la cantidad de equipaje y los precios de cada uni
            int exit = 1;
            int c = 0;

            do {
                c += 1;
                System.out.println(c);
                System.out.println("...");
                System.out.println("> Ingrese el peso de la maleta");
                int peso = scanner.nextInt();

                System.out.println("> Ingrese el valor del ancho de la maleta");
                int ancho = scanner.nextInt();

                System.out.println("> Ingrese el valor del largo de la maleta");
                int largo = scanner.nextInt();

                System.out.println("> Ingrese el valor del alto de la maleta");
                int alto = scanner.nextInt();

                boleto.addEquipaje(new Maleta(c, peso, largo, ancho, alto, boleto));
                System.out.println("Valor nuevo del boleto: ");
                System.out.println("-> $" + boleto.getValor());

                System.out.println("Desea agregar un equipaje mas o continuar? 1/0");
                exit = scanner.nextInt();

            } while (exit == 1);
        }

        System.out.println("Desea finalizar la compra? los detalles serian:");
        // Se muestran todos los detalles de la compra y se pide la confirmacion para
        // pagar
        System.out.println("Mostrar el detalles");
        System.out.println(boleto.getInfo());

        System.out.println("Confirmar, escriba 1/0");
        int confirmacion = scanner.nextInt();

        if (confirmacion == 1) {
            if (user.getDinero() - boleto.getValor() >= 0) {
                user.comprarBoleto(boleto);
                boleto.asignarAsiento(asiento);
                System.out.println("Boleto comprado con exito, detalles:");
            }
        } else {
            System.out.println("cancelado ñao ñao");
        }
        // Si se confirma se efectua el pago y se asigna todo.
        // --- nota, no se asigna nada hasta q se haya pagado y verificado ---

    }

    private static void reasignarVuelo(Usuario user) {
        // Aquí puedes poner el código que deseas ejecutar para la Reasignar vuelo.
    }

    private static void cancelarVuelo(Usuario user) {
        // Aquí puedes poner el código que deseas ejecutar para la Cancelar vuelo.
    }

    private static void verCuenta(Usuario user) {
        // Aquí puedes poner el código que deseas ejecutar para la Ver cuenta.
    }
}
