package arayuz;


public class Yol extends Node {
    public Yol(int x, int y , boolean kirmiziCiz) {
        super(x,y);
        if(kirmiziCiz){
            setStyle("-fx-background-color: red; -fx-text-fill: red;");
        }else{
            setStyle("-fx-background-color: white; -fx-text-fill: black;");
        }
    }
}
