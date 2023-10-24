package uiMain;

import java.util.Scanner;

// Clase para funciones de estética

public class Estetica {

    // Función para cambiar el color del texto
    public static String colorTexto(String text, String color) {
        // Códigos ANSI para colores
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String WHITE = "\u001B[37m";
        String BLUE = "\u001B[36m"; // Cambiado a cyan
        String PURPLE = "\u001B[35m";

        switch (color.toLowerCase()) {
            case "rojo":
                return RED + text + RESET;
            case "verde":
                return GREEN + text + RESET;
            case "amarillo":
                return YELLOW + text + RESET;
            case "blanco":
                return WHITE + text + RESET;
            case "azul":
                return BLUE + text + RESET;
            case "morado":
                return PURPLE + text + RESET;
            default:
                return text;
        }
    }

    // Función para establecer el texto en negrita
    public static String negrita(String text) {
        String NEGRITA = "\u001B[1m";
        String RESET = "\u001B[0m";
        return NEGRITA + text + RESET;
    }

    // Función para imprimir texto en negrita
    public static void printNegrita(String text) {
        System.out.println(negrita(text));
    }

    // Función para imprimir un salto de línea
    public static void salto() {
        System.out.print("\n");
    }

    // Función para imprimir múltiples saltos de línea
    public static void salto(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("\n");
        }
    }

    // Función para mostrar un aviso en un formato especial
    public static void aviso(String text) {
        System.out.println(negrita(("> > > " + text + " < < <")));
    }

    // Función para agregar indentación antes del texto
    public static void identacion(String text, int n) {
        String cadena = "";
        for (int i = 0; i < n; i++) {
            cadena += "    ";
        }
        System.out.println(cadena + text);
    }

    // Función para agregar una sola nivel de indentación antes del texto
    public static void identacion(String text) {
        System.out.println("    " + text);
    }

    // Función para mostrar un título en un formato especial
    public static void titulo(String text) {
        System.out.println(negrita("# = = = " + text + " = = = #"));
    }

    // Función para mostrar un prompt de entrada en color azul
    public static void promptIn(String text) {
        System.out.println(negrita(colorTexto(("> " + text), "azul")));
    }

    // Función para mostrar un prompt de salida en color morado
    public static void promptOut(String text) {
        System.out.println(negrita(colorTexto(("> " + text), "morado")));
    }

    // Función para mostrar un mensaje de error en color rojo
    public static void promptError(String text) {
        System.out.println((colorTexto(("> " + text), "rojo")));
    }

    // Función para obtener una cadena de entrada del usuario
    public static String inputS() {
        System.out.print("  > ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        // scanner.close();
        return s;
    }

    // Función para obtener un número entero de entrada del usuario
    public static int inputI() {
        System.out.print("  > ");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        // scanner.close();
        return n;
    }

    // Función para obtener un número decimal de entrada del usuario
    public static double inputD() {
        System.out.print("  > ");
        Scanner scanner = new Scanner(System.in);
        double n = scanner.nextDouble();
        // scanner close();
        return n;
    }

    // Función para indicar al usuario que presione Enter para continuar
    public static void continuar() {
        promptOut("Presione enter para continuar");
        System.out.print("  >_");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
    }

    // Función para mostrar un separador
    public static void separador() {
        salto();
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        salto();
    }

    // Función para mostrar un separador grande
    public static void separadorGrande() {
        salto();
        System.out.println("+ = = = = = = = = = = = = = = = = = = = = = = = +");
        salto();
    }

    // Función para mostrar un mensaje de selección en color morado
    public static void seleccionado(String text) {
        System.out.println(negrita(colorTexto((" - - - > Seleccion: " + text + " < - - -"), "morado")));
    }
}
