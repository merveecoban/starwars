package arayuz;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Resimler {
    private static File file_luke = new File("resimler/Luke-Skywalker-01-icon.png");
    private static File file_luke2 = new File("resimler/Luke-Skywalker-02-icon.png");
    private static File file_yoda = new File("resimler/Yoda-01-icon.png");
    private static File file_yoda2 = new File("resimler/Yoda-02-icon.png");
    private static File file_dartWader = new File("resimler/Vader-03-icon.png");
    private static File file_KyloRen = new File("resimler/kylo-ren.png");
    private static File file_stormTrooper = new File("resimler/Stormtrooper-02-icon.png");
    private static File file_full_health = new File("resimler/Full-Heart-icon.png");
    private static File file_half_health = new File("resimler/Half-Heart-icon.png");
    private static File file_no_health = new File("resimler/no-Heart-icon.png");
    private static File file_cup = new File("resimler/competition.png");

    private static Image img_luke = new Image(file_luke.toURI().toString());
    private static Image img_luke2 = new Image(file_luke2.toURI().toString());
    private static Image img_yoda = new Image(file_yoda.toURI().toString());
    private static Image img_yoda2 = new Image(file_yoda2.toURI().toString());
    private static Image img_dartWader = new Image(file_dartWader.toURI().toString());
    private static Image img_KyloRen = new Image(file_KyloRen.toURI().toString());
    private static Image img_stormTrooper = new Image(file_stormTrooper.toURI().toString());
    private static Image img_full_health = new Image(file_full_health.toURI().toString());
    private static Image img_half_health = new Image(file_half_health.toURI().toString());
    private static Image img_no_health = new Image(file_no_health.toURI().toString());
    private static Image img_cup = new Image(file_cup.toURI().toString());

    public static ImageView luke = new ImageView(img_luke);
    public static ImageView luke2 = new ImageView(img_luke2);
    public static ImageView yoda = new ImageView(img_yoda);
    public static ImageView yoda2 = new ImageView(img_yoda2);
    public static ImageView dartWader = new ImageView(img_dartWader);
    public static ImageView kyloRen = new ImageView(img_KyloRen);
    public static ImageView cup = new ImageView(img_cup);

    public static ImageView stormTrooper() {
        return new ImageView(img_stormTrooper);
    }

    public static ImageView full_health() {
        return new ImageView(img_full_health);
    }

    public static ImageView half_health() {
        return new ImageView(img_half_health);
    }


    public static ImageView no_health() {
        return new ImageView(img_no_health);
    }
}
