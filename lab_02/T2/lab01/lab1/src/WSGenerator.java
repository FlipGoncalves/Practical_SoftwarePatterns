import java.util.*;
import java.io.*;

public class WSGenerator {


    //function to find a direction that the word fit
    public static boolean find(int x, int y, String palavra, String direction, int n, char [][]sopa){

        boolean finded=true;

        if(direction.equalsIgnoreCase("right")){
            if(y+(palavra.length()-1)<n){
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x][y+i]=='*' || sopa[x][y+1]==palavra.charAt(i)){
                        
                    }else{
                        finded=false;
                    }   
                }
            }
            else{
                finded=false;
            }   
       }
       if(direction.equalsIgnoreCase("left")){
            if(y-(palavra.length()-1)>=0){
              
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x][y-i]=='*' || sopa[x][y-i]==palavra.charAt(i)){

                    }
                    else{
                        finded=false;
                    }
                    
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("Down")){
            if(x+(palavra.length()-1)<n){
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x+i][y]=='*' || sopa[x+i][y]==palavra.charAt(i)){

                    }else{
                        finded=false;
                    }
                    
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("Up")){
            if(x-(palavra.length()-1)>=0){
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x-i][y]=='*' || sopa[x-i][y]==palavra.charAt(i)){

                    }
                    else{
                        finded=false;
                    }
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("UpRight")){
            if((x-(palavra.length()-1)>=0) && (y+(palavra.length()-1)<n)){
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x-i][y+i]=='*' || sopa[x-i][y+i]==palavra.charAt(i)){

                    }else{
                        finded=false;
                    }                    
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("UpLeft")){
            if((x-(palavra.length()-1)>=0) && (y-(palavra.length()-1)>=0)){
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x-i][y-i]=='*' || sopa[x-i][y-i]==palavra.charAt(i)){

                    }else{
                        finded=false;
                    }
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("DownRight")){
            if((x+(palavra.length()-1)<n) && (y+(palavra.length()-1)<n)){
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x+i][y+i]=='*' || sopa[x+i][y+i]==palavra.charAt(i)){

                    }else{
                        finded=false;
                    }
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("DownLeft")){
            if((x+(palavra.length()-1)<n) && (y-(palavra.length()-1)>=0)){
                for (int i = 0; i < palavra.length(); i++) {
                    if(sopa[x+i][y-i]=='*' || sopa[x+i][y-i]==palavra.charAt(i)){

                    }else{
                        finded=false;
                    }
                    
                }
            }
            else{
                finded=false;
            }   
        }



        return finded;
    }


    //function to find a random number 
    public static int aleatorio(int n, LinkedList<Integer> numeros){
        
        Random all = new Random();
        int novo=all.nextInt(n);

        if(numeros.contains(novo)){
            if(numeros.size()>=n){
                return -1;
            }
            else{
                return aleatorio(n, numeros);
            }
        }else{
            return novo;
        }
    }

    //function to find a random array of 2 numbers 
    public static int[] aleatorio2(int n, LinkedList<int[]> posicao){
        
        Random all = new Random();
        int n1=all.nextInt(n);
        int n2=all.nextInt(n);
        int []pos={n1,n2};
        if(posicao.contains(pos)){
            if(posicao.size()>=n){
                return null;
            }
            else{
                return aleatorio2(n, posicao);
            }
        }else{
            return pos;
        }
    }

    //function to complete the sopa/puzzle with random letter
    public static char[][] completar(char [][]sopa){
        Random all = new Random();
        for (int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa.length; j++) {
                if(sopa[i][j]=='*'){
                    sopa[i][j]=(char)(65+all.nextInt(25));
                } 
            }
        }

        return sopa;
    }


    // function to add a word to the puzzle
    public static char[][] adicionar(int x,int y,String direction, char[][] sopa,String palavra){
         
        if (direction.equalsIgnoreCase("right")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x][y+i]=Character.toUpperCase(a);
            } 
          
        }
        if (direction.equalsIgnoreCase("left")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x][y-i]=Character.toUpperCase(a);
            }  
        }
        if (direction.equalsIgnoreCase("up")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x-i][y]=Character.toUpperCase(a);
            } 
            
        }
        if (direction.equalsIgnoreCase("down")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x+i][y]=Character.toUpperCase(a);
            } 
            
        }
        if (direction.equalsIgnoreCase("UpRight")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x-i][y+i]=Character.toUpperCase(a);
            } 
            
        }
        if (direction.equalsIgnoreCase("UpLeft")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x-i][y-i]=Character.toUpperCase(a);
            } 
            
        }
        if (direction.equalsIgnoreCase("DownRight")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x+i][y+i]=Character.toUpperCase(a);
            } 
            
        }
        if (direction.equalsIgnoreCase("DownLeft")) {
            for (int i = 0; i < palavra.length(); i++){
                char a=palavra.charAt(i);
                sopa[x+i][y-i]=Character.toUpperCase(a);
            }        
        }

        return sopa;
    }
    
    //function to solve the problem
    public static char[][] gerar(String palavra, int n, char[][] sopa){
        char [][]sopaNova=sopa;
        int pos[] = new int[2];String direcao="";
        LinkedList<int[]> cordenadas = new LinkedList<int[]>();
        LinkedList<Integer> posicoes = new LinkedList<Integer>();
        String []direcoes={"right","left","up","down","UpRight","UpLeft","DownRight","DownLeft"};
        boolean fit=false;
        for (int i = 0; i < n*n; i++) {
            
            pos=aleatorio2(n,cordenadas);
            cordenadas.add(pos);

            for (int j = 0; j < direcoes.length; j++) {
                int direc=aleatorio(direcoes.length, posicoes);
                if(direc==-1){
                    break;
                }
                direcao=direcoes[direc];
                posicoes.add(direc);
                
                fit = find(pos[0], pos[1], palavra, direcao, n, sopa);
                if(fit==true){
                    break;
                }
            }

            if(fit==true){
                break;
            }
        }
        if(fit==true){
            sopaNova=adicionar(pos[0],pos[1],direcao,sopa,palavra);
        }
        
        return sopaNova; 
    }


    public static void main(String[] args) {
        File ficheiro; String nome;
        ArrayList<Palavra> palavras=new ArrayList<Palavra>();

        Scanner input = new Scanner(System.in);
        
        do{
            System.out.print("Insira o nome do ficheiro que contem as palavras: ");
            nome= input.nextLine();
            ficheiro = new File(nome);
            System.out.println();
            if(!ficheiro.exists()){
                System.out.println("...Ficheiro nao encontrado...");
            }
            
            System.out.println();
        }while(!ficheiro.exists());

        try{	
			BufferedReader ler = new BufferedReader(new FileReader(nome));
			while(ler.ready()){
				String linha = ler.readLine();
                String temp="";
                    for(int i=0; i<linha.length(); i++){                                    
                        if((linha.charAt(i) >= 65 && linha.charAt(i)<=122)){
                            temp=temp+linha.charAt(i);
                        }                    
                        else{     
                            palavras.add(new Palavra(temp));
                            temp="";
                        }
                        if(i==(linha.length()-1) ){
                            if(temp!=""){
                                palavras.add(new Palavra(temp));
                                temp="";
                            }
                        }
                    }
            }
            ler.close();
        }catch(IOException ioe){
                ioe.printStackTrace();
        }
        
        System.out.print("Insira a dimensão da sopa de letras: ");
        int dimensao= input.nextInt();
        input.nextLine();
        System.out.println();
        System.out.print("Insira o nome do ficheiro em que será guardada a sopa de palavras: ");
        String ficheiroDestino= input.nextLine();
        System.out.println();
        input.close();
       

        char [][]sopa=new char[dimensao][dimensao];
        for (int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa.length; j++) {
                sopa[i][j]='*';
            }
        }

        for (Palavra palavra : palavras) {
            gerar(palavra.getName(), dimensao, sopa);
        }

        System.out.println("Sopa semi-pronta: ");
        for(int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa.length; j++) {
                System.out.print(sopa[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        completar(sopa);


        System.out.println("Sopa pronta: ");
        
        try{
        FileWriter fileWriter = new FileWriter(ficheiroDestino);
        for(int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa.length; j++) {
                System.out.print(sopa[i][j] + " ");
                fileWriter.write(sopa[i][j]);
            }
            System.out.println();
            fileWriter.write("\n");
        }
        int a=0;
        for (Palavra palavra : palavras){
            a++;
            fileWriter.write(palavra.getName()+";");
            if(a>4){
                a=0;
                fileWriter.write("\n");
            }
        }
        fileWriter.close();
        }catch (Exception e) {
            System.out.println("Falha ao escrever no arquivo");
        }
    }
}
