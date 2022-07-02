

public class Palavra{
    private String name;
    private int size;
    private int x; 
    private int y;
    private String direction;
    private boolean finded;
    
    public Palavra(String name){
        this.name=name;
        this.size=name.length();
        this.finded=false;
    }


    public String getName(){
        return this.name;       
    }

    public void setName(String name){
       this.name=name;       
    }

    public int getSize(){
        return this.size;       
    }

    public void setSize(int size){
       this.size=size;       
    }

    public int getX(){
        return this.x;       
    }

    public void setX(int x){
       this.x=x;       
    }

    public int getY(){
        return this.y;       
    }

    public void setY(int y){
       this.y=y;       
    }

    public String getDirection(){
        return this.direction;       
    }

    public void setDirection(String direction){
       this.direction=direction;       
    }

    public void setFinded(boolean finded){
        this.finded=finded;
    }

    public boolean getFinded(){
        return this.finded;
    }

    public String toString() {
        if(this.finded==false){
            return String.format("%-15s %-15d %-15s %-15s %15s",name, size, "-", "-", "Nao");
            
        }
        else{
            return String.format("%-15s %-15d %-15s %-15s %15s",name, size, (x+1)+","+(y+1), direction,"Sim");
        }        
    }
}
