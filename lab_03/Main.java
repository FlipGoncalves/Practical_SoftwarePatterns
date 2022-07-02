import java.io.*;
import java.util.*;

public class Main {
    public static Scanner sc = new Scanner(System.in) ;
    public static Scanner file_sc;
    public static void main(String args[]) throws FileNotFoundException {
        
        System.out.println("Escolha uma opção: (H para ajuda)");
        ArrayList<Voo> voos = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String parts[] = line.split(" ");

            switch(parts[0]) {
                case "H":
                    System.out.println("\nOpções: ");
                    System.out.println("H \t\t\t\t\t\t\t-> Opções do menu");
                    System.out.println("I + filename \t\t\t\t\t\t-> Indica que reservas não são possíveis de se realizar dentro do filename");
                    System.out.println("M + flight_code \t\t\t\t\t-> Exibe o mapa das reservas de um Voo");
                    System.out.println("F + flight_code + num_seats_exe + num_seats_tur \t-> Acrescenta um novo Voo");
                    System.out.println("R + flight_code + class + num_seats \t\t\t-> Acrescenta uma nova reserva a um Voo");
                    System.out.println("C + reservation_code \t\t\t\t\t-> Cancela a reserva (flight_code : reservation_number)");
                    System.out.println("Q \t\t\t\t\t\t\t-> termina");
                    break;

                case "I":
                    if (parts.length != 2) {
                        System.out.println("Erro nos inputs!");
                        break;
                    }
                    Voo voo_I = methodI(parts[1]);          // funcao retorna object Voo
                    if (voo_I != null)                      // se nao for null
                        voos.add(voo_I);                    // guarda-se o voo
                    break;
                
                case "M":
                    if (parts.length != 2) {
                        System.out.println("Erro nos inputs!");
                        break;
                    }
                    for (Voo voo: voos)                         // para todos os voos guardados
                        if (voo.getCode().equals(parts[1]))     // se o codigo for igual ao passado como argumento
                            methodM(voo);                       // funcao para o print
                    break;

                case "F":
                    String codigo = parts[1];
                    Voo voo_new = new Voo(codigo);                      // novo Voo
                    if (parts.length == 4) {                            // sendo igual a 3, temos classe executiva e classe turistica
                        String exec[] = parts[2].split("x");            // exe = (ex: 3x2)
                        String tur[] = parts[3].split("x");             // tur = (ex: 15x3)
                        voo_new.setLug_Exe(Integer.parseInt(exec[0]), Integer.parseInt(exec[1]));   // exec[0] = filas = (ex: 3x5) -> 3, exec[1] = lugares = (ex:3x5) -> 5
                        voo_new.setLug_Tur(Integer.parseInt(tur[0]), Integer.parseInt(tur[1]));     // tur[0] = filas = (ex: 3x5) -> 3, tur[1] = lugares = (ex:3x5) -> 5

                    } else if (parts.length == 3) {                     // sendo igual a 2, temos classe turistica
                        String tur[] = parts[2].split("x");             // tur = (ex: 15x3)
                        voo_new.setLug_Tur(Integer.parseInt(tur[0]), Integer.parseInt(tur[1]));     // tur[0] = filas = (ex: 3x5) -> 3, tur[1] = lugares = (ex:3x5) -> 5
                    } else {
                        System.out.println("Erro nos inputs!");
                        break;
                    }
                    voos.add(voo_new);                                  // guarda o voo
                    break;

                case "R":
                    if (parts.length != 4) {
                        System.out.println("Erro nos inputs!");
                        break; 
                    }
                    for (Voo voo: voos)
                        if (voo.getCode().equals(parts[1])) {       // qual voo tem aquele codigo, se nao existir nao faz nada
                            boolean done = voo.makeReservation(parts[2].charAt(0), Integer.parseInt(parts[3]));         // faz a reserva
                            if (done) {                             // se for bem feita
                                String[] reserva = voo.getReservas(voo.getCount()-1);       // guarda os lugares (ex: B3, C4, etc)
                                String send = "";                                           // string para guardar os lugares para o print
                                for (int i = 0; i < reserva.length; i++) {
                                    if (i == reserva.length-1)                              // se for o último valor do array entao nao colocamos " | "
                                        send += reserva[i];
                                    else
                                        send += reserva[i] + " | ";
                                }
                                System.out.format("%s:%s = %s", voo.getCode(), voo.getCount(), send);
                            } else {
                                System.out.format("Não foi possível obter lugares para a reserva: %c %d\n", parts[2].charAt(0), Integer.parseInt(parts[3]));
                            }
                        }
                    break;

                case "C":
                    if (parts.length != 2) {
                        System.out.println("Erro nos inputs!");
                        break; 
                    }
                    String arg[] = parts[1].split(":");             // codigo da reserva (ex: TP1920:7)
                    for (Voo voo: voos)
                        if (voo.getCode().equals(arg[0])) {         // voo com o codigo igual ao dado
                            voo.cancelReservation(Integer.parseInt(arg[1]));    // cancelamos a reserva com o count = 7
                        }
                    break;

                case "Q":
                    System.out.println("Exiting!");
                    System.exit(1);
                default:
                    System.out.println("Erro!");
            }

            System.out.println("\nEscolha uma opção: (H para ajuda)");
        }

	}

    public static Voo methodI(String filename) {
        try {
            file_sc = new Scanner(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.err.println("Erro no ficheiro");
            return null;
        }
        ArrayList<Boolean> reservations = new ArrayList<>();                    // guarda se a reserva foi feita ou nao
        ArrayList<String> reserve = new ArrayList<>();                          // guarda o tipo da reserva (ex: T 3)

        String line = file_sc.nextLine();                                       // Primeira linha -> cabeçalho com codigo do voo e classe executiva e/ou classe turistica
        String part[] = line.split(" ");                                        // index 0 -> codigo, index 1 -> executivo/turistico, index 2 - turistico (se houver index 2)
        
        Voo voo = new Voo(part[0].substring(1));                                // primeiro index contem ">code" por isso tiramos o ">"
        if (part.length == 2) {                                                 // se tiver length = 2 entao nao tem executiva
            int filas = Integer.parseInt(part[1].split("x")[0]);                // filas = (ex: 3x5) -> 3
            int lug = Integer.parseInt(part[1].split("x")[1]);                  // lugares = (ex: 3x5) -> 5
            voo.setLug_Tur(filas, lug);
        } else if (part.length == 3) {                                          // se tiver length = 3 entao tem classe executiva
            int filas_exe = Integer.parseInt(part[1].split("x")[0]);
            int lug_exe = Integer.parseInt(part[1].split("x")[1]);
            voo.setLug_Exe(filas_exe, lug_exe);
            int filas_tur = Integer.parseInt(part[2].split("x")[0]);
            int lug_tur = Integer.parseInt(part[2].split("x")[1]);
            voo.setLug_Tur(filas_tur, lug_tur);
        }

        while (file_sc.hasNextLine()) {
            line = file_sc.nextLine();                                          // ja nao e cabecalho -> line tem (ex: T 5)
            part = line.split(" ");             

            char classe = part[0].charAt(0);                                    // primeiro index é a classe (ex: T)
            boolean done = voo.makeReservation(classe, Integer.parseInt(part[1]));  // segundo index é o numero de lugares a reservar (ex: 5)
            reservations.add(done);                                             // guardamos se foi feita ou nao -> makeReservation retorna boolean
            reserve.add(line);                                                  // guardamos a reserva
        }   

        System.out.println(voo);                                                // print do voo

        for (int i = 0; i < reservations.size(); i++) {
            if (!reservations.get(i))                                           // se nao for feita -> return False entao fazemos print que nao foi possivel
                System.out.format("Não foi possível obter lugares para a reserva: %s\n", reserve.get(i));
        }    

        return voo;
    }

    public static void methodM(Voo voo) {
        int[][] turistico = voo.getTuristic();                                      // receber o vetor com as reservas turisticas
        int[][] executivo = voo.getExecutive();                                     // receber o vetor com as reservas executivas
        int length_x = 0, length_y = 0;
        if (executivo != null) {
            length_x = executivo.length;
            length_y = executivo[0].length;
        }
        int max_y = turistico.length > length_x ? turistico.length : length_x;      // maximo de colunas

        int index = 1;
        for (int i = 0; i < length_y + turistico[0].length; i++)                    // print do cabeçalho
            System.out.format("%4d", index++);
        System.out.println();
        index = 1;
        for (int i = 0; i < max_y; i++) {                                           // y
            System.out.print(String.valueOf((char)(index++ + 'A' - 1)) + "  ");     // print das letras
            for (int j = 0; j < length_y; j++) {                                    // print linha executivo
                if (i < length_x)                                                   // se for falso, é porque length_x = 0 -> executivo == null
                    System.out.print(executivo[i][j] + "   ");
                else System.out.print("    ");                                      // print vazio
            }
            for (int j = 0; j < turistico[0].length; j++)                           // print linha turistico
                System.out.print(turistico[i][j] + "   ");
            System.out.println();
        }
        
    }
}
