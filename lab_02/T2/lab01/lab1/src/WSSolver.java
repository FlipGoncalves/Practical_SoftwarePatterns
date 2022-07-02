import java.util.*;

import java.io.*;

public class WSSolver{
    
    //function to test that the puzzle is a sware
    public static boolean isQuadrado(ArrayList<String> sopa){ 
        int n=sopa.size();
        int n1=sopa.get(1).length();

        return n==n1;
    }


    //function to know where is the word
    //Basicaly the function receive the x,y position and the direction to go and search the word on the puzzle
    //when the word is found the value of the variable -finded- change to -true-
    //This function is used on -solve- function with all posible directions and will return -true- when the correct direction is use
    //that's basically what this part of the algorithm is for
    public static boolean find(int x, int y, String palavra, String direction, int n, char [][]sopa){

        boolean finded=true;
        String temp="";

        if(direction.equalsIgnoreCase("right")){
            if(y+(palavra.length()-1)<n){
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x][y+i];
                }
                
            }
            else{
                finded=false;
            }   
       }
       if(direction.equalsIgnoreCase("left")){
            if(y-(palavra.length()-1)>=0){
              
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x][y-i];
                    
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("Down")){
            if(x+(palavra.length()-1)<n){
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x+i][y];
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("Up")){
            if(x-(palavra.length()-1)>=0){
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x-i][y];
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("UpRight")){
            if((x-(palavra.length()-1)>=0) && (y+(palavra.length()-1)<n)){
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x-i][y+i];
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("UpLeft")){
            if((x-(palavra.length()-1)>=0) && (y-(palavra.length()-1)>=0)){
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x-i][y-i];
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("DownRight")){
            if((x+(palavra.length()-1)<n) && (y+(palavra.length()-1)<n)){
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x+i][y+i];
                }
            }
            else{
                finded=false;
            }   
        }

        if(direction.equalsIgnoreCase("DownLeft")){
            if((x+(palavra.length()-1)<n) && (y-(palavra.length()-1)>=0)){
                for (int i = 0; i < palavra.length(); i++) {
                    temp=temp+sopa[x+i][y-i];
                }
            }
            else{
                finded=false;
            }   
        }


       if(!temp.equalsIgnoreCase(palavra)){
            finded=false;
       }
        return finded;
    }

    //function to solve the problem
    public static void solve(char [][]sopa, Palavra palavrasToFind){
        for (int i = 0; i < sopa.length; i++) {
    
            for(int j=0; j < sopa.length; j++){
                if(palavrasToFind.getFinded()==false){
                    String a= palavrasToFind.getName().charAt(0) + "";
                    String b=sopa[i][j]+"";
                    if(b.equalsIgnoreCase(a)){
                    
                        if(find(i, j, palavrasToFind.getName(),"Right",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("Right");
                            palavrasToFind.setFinded(true);
                            break;
                        }
                        if(find(i, j, palavrasToFind.getName(),"Left",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("Left");
                            palavrasToFind.setFinded(true);
                            break;
                        }
                        if(find(i, j, palavrasToFind.getName(),"Up",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("Up");
                            palavrasToFind.setFinded(true);
                            break;
                        }
                        if(find(i, j, palavrasToFind.getName(),"Down",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("Down");
                            palavrasToFind.setFinded(true);
                            break;
                        }
                        if(find(i, j, palavrasToFind.getName(),"UpRight",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("UpRight");
                            palavrasToFind.setFinded(true);
                            break;
                        }
                        if(find(i, j, palavrasToFind.getName(),"UpLeft",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("UpLeft");
                            palavrasToFind.setFinded(true);
                            break;
                        }
                        if(find(i, j, palavrasToFind.getName(),"DownRight",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("DownRight");
                            palavrasToFind.setFinded(true);
                            break;
                        }

                        if(find(i, j, palavrasToFind.getName(),"DownLeft",sopa.length, sopa)){
                            palavrasToFind.setX(i);
                            palavrasToFind.setY(j);
                            palavrasToFind.setDirection("DownLeft");
                            palavrasToFind.setFinded(true);
                            break;
                        }
                    }
                }
            }
            
        }
    }

    //function create the sopa
    public static char[][] createSopa(ArrayList<Palavra> palavras,char[][] sopa){
        char [][]grelha=new char[sopa.length][sopa.length];
        for (int i = 0; i < grelha.length; i++) {
            for (int j = 0; j < grelha.length; j++) {
                grelha[i][j]='*';
            }
        }

        for (Palavra pl : palavras) {
            if(pl.getFinded()==true){
                for (int i = 0; i < pl.getName().length(); i++) {
                    //for (int j = 0; j < pl.getSize(); j++) {
                        if (pl.getDirection().equalsIgnoreCase("right")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()][pl.getY()+i]=Character.toUpperCase(a);
                            
                        }
                        if (pl.getDirection().equalsIgnoreCase("left")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()][pl.getY()-i]=Character.toUpperCase(a);
                            
                        }
                        if (pl.getDirection().equalsIgnoreCase("up")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()-i][pl.getY()]=Character.toUpperCase(a);
                            
                        }
                        if (pl.getDirection().equalsIgnoreCase("down")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()+i][pl.getY()]=Character.toUpperCase(a);
                            
                        }
                        if (pl.getDirection().equalsIgnoreCase("UpRight")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()-i][pl.getY()+i]=Character.toUpperCase(a);
                            
                        }
                        if (pl.getDirection().equalsIgnoreCase("UpLeft")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()-i][pl.getY()-i]=Character.toUpperCase(a);
                            
                        }
                        if (pl.getDirection().equalsIgnoreCase("DownRight")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()+i][pl.getY()+i]=Character.toUpperCase(a);
                            
                        }
                        if (pl.getDirection().equalsIgnoreCase("DownLeft")) {
                            char a=pl.getName().charAt(i);
                            grelha[pl.getX()+i][pl.getY()-i]=Character.toUpperCase(a);
                            
                        }

            } 
            }
        }

        return grelha;
    }

    


    public static void main(String[] args) {
        File ficheiro; String nome;
        //Palavra ola = new Palavra();
        ArrayList<String> letras=new ArrayList<String>();
        ArrayList<Palavra> palavras=new ArrayList<Palavra>();

        Scanner input = new Scanner(System.in);
        
        do{
            System.out.print("Insira o nome do ficheiro que contem o puzzle: ");
            nome= input.nextLine();
            ficheiro = new File(nome);
            System.out.println();
            if(!ficheiro.exists()){
                System.out.println("...Ficheiro nao encontrado...");
                //System.out.println("Para sair pressione 1: ");
            }
            /*
            if(nome=="sair"){
                System.out.println("ola");
                System.exit(0);
            }*/
            System.out.println();
        }while(!ficheiro.exists());

      

        try{	
			BufferedReader ler = new BufferedReader(new FileReader(nome));
			while(ler.ready()){
				String linha = ler.readLine();
                boolean verifica = false;
                for(int i=0; i<linha.length(); i++){
                    if(linha.charAt(i)==' ' || linha.charAt(i)==';' || linha.charAt(i)==','){
                        verifica=true;
                    }
                }
                if(verifica==false){
                    letras.add(linha);
                }
                else{
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
			}
			ler.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}

        if(!isQuadrado(letras)){
            System.out.println("O puzzle deve ser quadrado");
            System.exit(0);
        };
        if(letras.size()>40){
            System.out.println("O tamanho do puzzle deve ser no maximo de 40x40");
            System.exit(0);
        };

        char sopa[][]=new char[letras.size()][letras.size()];
        for (int i = 0; i < letras.size(); i++) {
            for (int j = 0; j < letras.get(i).length(); j++) {
                sopa[i][j]=letras.get(i).charAt(j);
            }
        }
        
        
        for (Palavra toFind : palavras) {
            solve(sopa, toFind);
        }

        System.out.print("Insira o nome do ficheiro em que será guardada a sopa de palavras: ");
        String ficheiroDestino= input.nextLine();
        System.out.println();
        input.close();


        try {
            FileWriter fileWriter = new FileWriter(ficheiroDestino);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            System.out.format("%-15s %-15s %-15s %-15s %15s\n","Palavra", "Tamanho", "Localizacao", "Direção", "Encontrada");
            printWriter.printf("%-15s %-15s %-15s %-15s %15s\n","Palavra", "Tamanho", "Localizacao", "Direção", "Encontrada");
            
            for (Palavra toFind : palavras) {
                System.out.println(toFind);
                printWriter.print(toFind+"\n");
            }
            System.out.println();
            printWriter.print("\n");
            char [][] grelha=createSopa(palavras,sopa);
            for(int i = 0; i < sopa.length; i++) {
                for (int j = 0; j < sopa.length; j++) {
                    System.out.print(grelha[i][j] + " ");
                    printWriter.print(grelha[i][j]);
                }
                System.out.println();
                printWriter.print("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Falha ao esrever no arquivo");
        }
    
    }
       
}
