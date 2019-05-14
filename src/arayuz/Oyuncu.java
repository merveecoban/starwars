package arayuz;

import javafx.scene.control.Button;
import karakter.IyiKarakter;
import karakter.LukeSkywalker;


public class Oyuncu extends Button {



    public Oyuncu(IyiKarakter iyiKarakter) {
        super("");

        int w = 50,y= 50;
        setPrefSize(w,y);
        setMaxSize(w,y);
        setMinSize(w,y);
        setStyle("-fx-background-color: yellow; -fx-text-fill: yellow;");

        if (iyiKarakter instanceof LukeSkywalker) {
            setGraphic(Resimler.luke2);
        } else {
            setGraphic(Resimler.yoda2);
        }
    }


}
