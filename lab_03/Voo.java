import java.util.*;

public class Voo {
    private String code;                                        // codigo
    private int[][] executive = null;                           // array para as reservas da classe executiva
    private int x_exe = 0;                                      // index x para o array executive
    private int y_exe = 0;                                      // index y para o array executive
    private int[][] turistic = null;                            // array para as reservas da classe turistic
    private int x_tur = 0;                                      // index x para o array turistic
    private int y_tur = 0;                                      // index y para o array turistic
    private int count = 0;                                      // numero da reserva
    ArrayList<String> reservation_place = new ArrayList<>();    // arraylist para guardar os lugares da reserva (ex: [A3, B4, C5], [D2, D3], etc)

    public Voo(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public int[][] getTuristic() {
        return this.turistic;
    }

    public int[][] getExecutive() {
        return this.executive;
    }

    public void setLug_Exe(int fila, int lugares) {
        executive = new int[lugares][fila];
    }

    public void setLug_Tur(int fila, int lugares) {
        turistic = new int[lugares][fila];
    }

    public int getCount() {
        return count;
    }

    public boolean makeReservation(char classe, int lugares) {                          // numero da reserva
        String reserva = "";
        int[][] array;
        int x, y, x_length, y_length;
        int x_final, y_final;
        if (classe == 'T') {                            // classe T -> guardamos nas variaveis os valores correspondentes ao turistic
            if (turistic == null)                       // se for null entao nao pode fazer a reserva
                return false;
            array = turistic;                           // turistic array
            x = x_tur;                                  // x index temporarios
            y = y_tur;                                  // y index temporarios
            y_length = turistic.length;                 // length em x
            x_length = turistic[0].length;              // length em y
            x_final = x_tur;                            // x index para o final
            y_final = y_tur;                            // y index para o final
        }
        else if (classe == 'E') {                       // mesmo raciocinio
            if (executive == null)
                return false;
            array = executive;
            x = x_exe;
            y = y_exe;
            y_length = executive.length;
            x_length = executive[0].length;
            x_final = x_exe;
            y_final = y_exe;
        }
        else                                            // sem classe satisfatoria
            return false;                               

        count++;                                // numero da reserva
        for (int i = 0; i < lugares; i++) {     // verifica se pode ser feita a reserva para todos os lugares
            if (y >= y_length) {                // se for demasiado para baixo, entao vai para o inicio e para a direita (verificado com o input "M code")
                y = 0;                          // inicio
                x++;                            // direita
            }
            if (x >= x_length) {                // se for para a direita toda, nao tem mais para onde ir, out of bounds
                count--;                        // desincrementar
                return false;
            }
            if (array[y][x] != 0) {             // not vazio
                count--;                        // desincrementar
                return false;  
            }
            y++;                                // para baixo
        }
        for (int i = 0; i < lugares; i++) {     // colocar a reserva
            if (y_final == y_length) {          // se for demasiado para baixo, entao vai para o inicio e para a direita (verificado com o input "M code")
                y_final = 0;                    // inicio
                x_final++;                      // direita
            }
            array[y_final][x_final] = count;    // guardamos o valor no array
            reserva += (y_final+1) + " " + (x_final+1) + ",";       // guardamos o lugar (ex: T 5 -> 3 3, 3 4, 4 0, 4 1, 4 2,) para depois fazer print dos lugares
            y_final++;                          // para baixo
        }
        if (classe == 'T') {                    // guardar os valores finais nas variaveis certas
            x_tur = x_final;
            y_tur = y_final;
        }
        else {
            x_exe = x_final;
            y_exe = y_final;
        }
        reservation_place.add(reserva);         // guardamos a reserva no array 
        return true;
    }

    public int getFreeSpaceTuristic() {
        int count = turistic.length * turistic[0].length;
        return count;
    }

    public int getFreeSpaceExecutive() {
        if (executive == null)
            return -1;
        int count = executive.length * executive[0].length;
        return count;
    }

    public String[] getReservas(int place) {
        String line = reservation_place.get(place-1);   // reserva (ex: 3 3, 4 4, 5 4, 3 2,)
        String parts[] = line.split(",");               // split por virgulas (ex: [3 3], [4 4], [5 4], [3 2])
        String res[] = new String[parts.length];        // array para passar de [3 3] para [C3]
        int i = 0;
        for (String strg: parts) {                      // para cada index no array
            String coords[] = strg.split(" ");          // split por espacos -> [3 3] = [3], [3]
            coords[0] = String.valueOf((char)(Integer.parseInt(coords[0]) + 'A' - 1));      // passamos o primeiro index para letra
            res[i++] = coords[1]+coords[9];             // guardamos tudo junto numa so string sem espacos -> 3A
        }
        return res;                                     // devolvemos o array
    }

    public void cancelReservation(int place) {
        int done = 0;                                               // guarda se foi feita a reserva ou nao
        if (executive != null) {                                    // se nao for null
            for (int i = 0; i < executive.length; i++)              // verificamos primeiro o executivo
                for (int j = 0; j < executive[0].length; j++) 
                    if (executive[i][j] == place) {                 // se for igual ao numero da reserva
                        executive[i][j] = 0;                        // fica igual a 0
                        done = 1;                                   // done = 1
                    }
        }
        if (done == 0) {                                            // done = 1 -> reserva era executive
            for (int i = 0; i < turistic.length; i++)               // verificamos para o turistico
                for (int j = 0; j < turistic[0].length; j++) 
                    if (turistic[i][j] == place) {                  // se for igual ao numero da reserva
                        turistic[i][j] = 0;                         // fica igual a 0
                        done = 1;                                   // done = 1 (so de precuacao)
                    }
        }
        count--;                                                    // desincrementamos
        reservation_place.remove(place-1);                          // removemos do array
    }

    public String toString() {
        int turistica = getFreeSpaceTuristic();
        int executiva = getFreeSpaceExecutive();
        String disponivel = "";
        if (executiva != -1)
            disponivel += executiva + " lugares em classe Executivo; ";
        return "Código de voo " + this.code + ". Lugares disponíveis: " + disponivel + turistica + " lugares em classe Turística";
    }
}