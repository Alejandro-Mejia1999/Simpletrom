import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        float[] memory = new float[1000];
        float acumulador = 0;
        int contadorDeInstrucciones = 0;
        int codigoDeOperacion = 0;
        int operando = 0;
        int registroDeInstruccion = 0;
        System.out.println("* Bienvenido a Simpletron! *");
        System.out.println("* Por favor, introduzca en su programa una instruccion *");
        System.out.println("* (o palabra de datos) a la vez. Yo le mostrare *");
        System.out.println("* el numero de ubicacion y un signo de interrogacion (?) *");
        System.out.println("* Entonces usted escribira la palabra para esa ubicacion. *");
        System.out.println("* Teclee -9999 para dejar de introducir su programa. *");
        // Carga del programa en memoria
        do {
            System.out.printf("%02d ? ", contadorDeInstrucciones);
            String instruccionHex = input.next();
            if (instruccionHex.equals("-9999")) {
                break;
            }

            if (instruccionHex.length() == 1) {
                instruccionHex = "0" + instruccionHex; // Asegurarse de que la instruccion tenga dos digitos
            }
            float instruccion = Float.parseFloat(instruccionHex);
            memory[contadorDeInstrucciones] = instruccion;
            contadorDeInstrucciones++;

        } while (contadorDeInstrucciones < 1000);
        System.out.println("* Se completo la carga del programa *");
        System.out.println("* Empieza la ejecucion del programa *");

        // Ejecución del programa en Simpletron
        contadorDeInstrucciones = 0;
        while (codigoDeOperacion != 43) {
            registroDeInstruccion = (int) memory[contadorDeInstrucciones];
            codigoDeOperacion = registroDeInstruccion / 100;
            operando = registroDeInstruccion % 100;

            switch (codigoDeOperacion) {
                case 10: // Leer
                    System.out.print("Ingrese una cadena: ");
                    String cadena = input.next();
                    for (int i = 0; i < cadena.length(); i++) {
                        char caracter = cadena.charAt(i);
                        int ascii = (int) caracter;
                        memory[operando + i] = ascii;
                    }
                    break;
                case 11: // Escribir
                    String output = "";
                    int address = operando;
                    while (memory[address] != 0) {
                        char character = (char) memory[address];
                        output += character;
                        address++;
                    }
                    System.out.println(output);
                    break;
                case 12: // Nueva linea
                    System.out.println(); // Agregar una nueva linea
                    break;

                case 13: // Almacenar cadena
                    System.out.print("Ingrese una cadena: ");
                    String cade = input.next();
                    int addres = 14;
                    for (int i = 0; i < cade.length(); i++) {
                        char caracter = cade.charAt(i);
                        int ascii = (int) caracter;
                        memory[addres + i] = ascii;
                    }
                    memory[addres + cade.length()] = 0; // Agregar un caracter nulo al final de la cadena
                    break;

                case 14: // Imprimir cadena
                    int addre = operando;
                    int length = memory[addre] / 2;
                    address++;
                    String output = "";
                    for (int i = 0; i < length; i++) {
                        int ascii = memory[address];
                        char character = (char) ascii;
                        output += character;
                        address++;
                    }
                    System.out.println(output);
                    break;

                case 20: // Cargar
                    acumulador = memory[operando];
                    break;
                case 21: // Almacenar
                    memory[operando] = acumulador;
                    break;
                case 30: // Sumar
                    acumulador += memory[operando];
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                case 31: // Restar
                    acumulador -= memory[operando];
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                case 32: // Dividir
                    if (memory[operando] == 0) {
                        System.out.println("* ERROR: Division por cero *");
                        System.exit(1);
                    }
                    acumulador /= memory[operando];
                    break;
                case 34: // Residuo
                    if (memory[operando] == 0) {
                        System.out.println("* ERROR: Division por cero *");
                        System.exit(1);
                    }
                    acumulador %= memory[operando];
                    break;
                case 35: // Exponenciacion
                    acumulador = (int) Math.pow(acumulador, memory[operando]);
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
            }

            contadorDeInstrucciones++;
        }

        input.close();
    }
}
