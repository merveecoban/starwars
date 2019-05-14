package arayuz;

import javafx.scene.control.Button;
import karakter.*;


public class Bilgisayar extends Button {


    public Bilgisayar(KotuKarakter kotuKarakter) {
        super("");

        int w = 50, y = 50;
        setPrefSize(w, y);
        setMaxSize(w, y);
        setMinSize(w, y);
        setStyle("-fx-background-color: white; -fx-text-fill: orangered;");

        if (kotuKarakter instanceof DarthVader) {
            setGraphic(Resimler.dartWader);
        } else if (kotuKarakter instanceof KyloRen) {
            setGraphic(Resimler.kyloRen);
        } else {
            setGraphic(Resimler.stormTrooper());
        }
    }


}
