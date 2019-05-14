package arayuz;

import araclar.DosyaCozumleyici;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import karakter.*;

import java.util.ArrayList;
import java.util.List;

public class Arayuz extends Application {

    private IyiKarakter iyiKarakter;
    private Oyuncu oyuncu;
    private AnchorPane root = new AnchorPane();
    private Stage stage;
    private int[][] haritaArr;
    private GridPane harita;
    private boolean siraOyuncudami = true;
    private boolean oyuncuKazandimi = false;
    private List<KotuKarakter> kotuKarakterler = new ArrayList<>();
    private BorderPane anaPanel;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        root.setBackground(Background.EMPTY);
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.setTitle("Starwars");
        stage.show();

        karakterSecimEkraniniOlustur();

        root.setOnKeyPressed(e -> {
            if (siraOyuncudami) {
                kotuKarakterlerinEskiGuzergahiniTemizle();
                int x = -1, y = -1;
                if (e.getCode() == KeyCode.UP) {
                    x = iyiKarakter.getX() - 1;
                    y = iyiKarakter.getY();
                } else if (e.getCode() == KeyCode.DOWN) {
                    x = iyiKarakter.getX() + 1;
                    y = iyiKarakter.getY();
                } else if (e.getCode() == KeyCode.RIGHT) {
                    x = iyiKarakter.getX();
                    y = iyiKarakter.getY() + 1;
                } else if (e.getCode() == KeyCode.LEFT) {
                    x = iyiKarakter.getX();
                    y = iyiKarakter.getY() - 1;
                }
                // System.out.println(" karakterin  Yeni locaksyonu: " + iyiKarakter.getLokasyon() + " haritadaki alan: " + haritaArr[iyiKarakter.getY()][iyiKarakter.getX()]);
                if (gidilebilirmi(x, y)) {
                    iyiKarakter.setLokasyon(new Lokasyon(x, y));
                    siraOyuncudami = false;
                    kotukarakterleriHareketEttir();
                    hariyatiCiz();
                    siraOyuncudami = true;
                }
            }
        });
    }

    private void kotuKarakterlerinEskiGuzergahiniTemizle() {
        for (int i = 0; i < haritaArr.length; i++) {
            for (int j = 0; j < haritaArr[i].length; j++) {
                if (haritaArr[i][j] == 2) {
                    haritaArr[i][j] = DosyaCozumleyici.haritaIlkHali[i][j];
                }
            }
        }
    }


    private void kotukarakterleriHareketEttir() {
        for (KotuKarakter kotuKarakter : kotuKarakterler) {
            List<arayuz.Node> path = kotuKarakter.enKısaYol(DosyaCozumleyici.haritaIlkHali, iyiKarakter);
            System.out.println(kotuKarakter.getLokasyon());

            for (int i = 1; i < path.size(); i++) {
                arayuz.Node node = path.get(i);
                System.out.println(node.getX() + " " + node.getY());
                haritaArr[node.getY()][node.getX()] = 2; // burasi kotu karakterin gidecegi yol olarak isaretlendi; amac ekranda o yolu kirmizi gostermek
            }




            if (path.size() == 1) {
                kotuKarakter.setLokasyon(new Lokasyon(path.get(0).getX(), path.get(0).getY()));
            } else {
                if (kotuKarakter instanceof KyloRen) {//iki kare yuruyor
                    kotuKarakter.setLokasyon(new Lokasyon(path.get(2).getX(), path.get(2).getY()));
                } else {
                    kotuKarakter.setLokasyon(new Lokasyon(path.get(1).getX(), path.get(1).getY()));
                }
            }

            boolean yakalandimi = yakaladimiKontrolEt(kotuKarakter);
            if (yakalandimi) {
                break;
            }
            System.out.println("--------------------------------------\n");
        }

    }

    private boolean yakaladimiKontrolEt(KotuKarakter kotuKarakter) {
        if (kotuKarakter.getX() == iyiKarakter.getY() && kotuKarakter.getY() == iyiKarakter.getX()) {
            iyiKarakter.canDus();
            if (iyiKarakter.can == 0) {
                uyariGoster("Game Over", "Üzgünüm Oyunu Kaybettiniz...", Alert.AlertType.ERROR);
                karakterSecimEkraniniOlustur();
            } else {
                anaPanel.setTop(kalanCanPaneliniOlustur());
                oyunuBastanBaslat();
            }
            return true;
        }
        return false;
    }

    private void oyunuBastanBaslat() {
        iyiKarakter.setLokasyon(new Lokasyon(5, 6));
        kotuKarakterlerinEskiGuzergahiniTemizle();
        haritayiOlustur();
    }

    private boolean gidilebilirmi(int x, int y) {
        if (x == 9 && y == 14) {
            uyariGoster("Tebrikler", "Oyunu Kazandınız!!!", Alert.AlertType.INFORMATION);
            karakterSecimEkraniniOlustur();
            return false;
        } else if (x > haritaArr.length - 1) {
            return false;
        } else if (y > haritaArr[0].length - 1) {
            return false;
        } else if (x < 0) {
            return false;
        } else if (y < 0) {
            return false;
        } else if (haritaArr[x][y] == 0) {
            return false;
        }
        return true;
    }

    private void karakterSecimEkraniniOlustur() {
        root.getChildren().clear();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        GridPane.setHgrow(vBox, Priority.ALWAYS);
        HBox hBox = new HBox(20);

        Label label = new Label("Karakter Seçiniz");
        label.setFont(new Font("Arial", 40));
        vBox.getChildren().add(label);

        final ToggleGroup group = new ToggleGroup(); // radio buttonlardan biri seçilirse diğerinin seçimi kalksın diye
        RadioButton rbLuke = new RadioButton("Luke Skywalker");
        rbLuke.setPadding(new Insets(0, 0, 0, 10));
        RadioButton rbYoda = new RadioButton("Master Yoda");
        rbLuke.setToggleGroup(group);
        rbYoda.setToggleGroup(group);
        rbLuke.setGraphic(Resimler.luke);
        rbYoda.setGraphic(Resimler.yoda);
        hBox.getChildren().add(rbLuke);
        hBox.getChildren().add(rbYoda);
        vBox.getChildren().add(hBox);


        Button btnBasla = new Button("Oyuna Başla");
        btnBasla.setOnAction(e -> {
            if (rbLuke.isSelected()) {
                iyiKarakter = new LukeSkywalker();
            } else {
                iyiKarakter = new MasterYoda();
            }

            oyuncu = new Oyuncu(iyiKarakter);

            anaEkraniOlustur();
        });
        vBox.getChildren().add(btnBasla);

        root.getChildren().add(vBox);
    }

    private void anaEkraniOlustur() {
        root.getChildren().clear();

        stage.setHeight(700);

        anaPanel = new BorderPane();
        anaPanel.setTop(kalanCanPaneliniOlustur());
        haritayiOlustur();
        anaPanel.setCenter(harita);
        HBox.setHgrow(anaPanel, Priority.ALWAYS);


        root.getChildren().add(anaPanel);
    }

    private void haritayiOlustur() {
        DosyaCozumleyici dosyaCozumleyici = new DosyaCozumleyici();
        this.kotuKarakterler = dosyaCozumleyici.getKotuKarakterler();
        int[][] harita = dosyaCozumleyici.getHarita();
        this.haritaArr = harita;

        hariyatiCiz();
    }

    private void hariyatiCiz() {
        if (harita == null) {
            harita = new GridPane();
        } else {
            harita.getChildren().clear();
        }


        for (int i = 0; i < haritaArr.length; i++) {
            for (int j = 0; j < haritaArr[i].length; j++) {
                /**
                 * kotu karakterleri ekle
                 */
                for (KotuKarakter kotuKarakter : kotuKarakterler) {
                    if (i == kotuKarakter.getY() && j == kotuKarakter.getX()) {
                        harita.add(new Bilgisayar(kotuKarakter), j, i, 1, 1);
                    }
                }
                if (i == iyiKarakter.getLokasyon().getX() && j == iyiKarakter.getLokasyon().getY()) {
                    harita.add(oyuncu, j, i, 1, 1);
                } else if (haritaArr[i][j] == 0 && !kotukarakterBurdami(i, j)) {
                    harita.add(new Duvar(j, i), j, i, 1, 1);
                } else if ((haritaArr[i][j] == 1 || haritaArr[i][j] == 2) && !kotukarakterBurdami(i, j)) {
                    harita.add(new Yol(j, i, haritaArr[i][j] == 2), j, i, 1, 1);
                }
            }
        }

        harita.add(Resimler.cup, 14, 9, 1, 1);

        harita.setPadding(new Insets(10, 10, 10, 10));
        HBox.setHgrow(harita, Priority.ALWAYS);
    }

    private boolean kotukarakterBurdami(int i, int j) {
        for (KotuKarakter kotuKarakter : kotuKarakterler) {
            if (j == kotuKarakter.getX() && i == kotuKarakter.getY()) {
                return true;
            }
        }
        return false;
    }

    private HBox kalanCanPaneliniOlustur() {
        Label label2 = new Label("Canlar");
        label2.setStyle("-fx-background-color: green; -fx-text-fill: white;");


        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        HBox hBox = new HBox(region1, label2);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        if (iyiKarakter.can == 3) {
            hBox.getChildren().add(Resimler.full_health());
            hBox.getChildren().add(Resimler.full_health());
            hBox.getChildren().add(Resimler.full_health());
        } else if (iyiKarakter.can == 2.5) {
            hBox.getChildren().add(Resimler.half_health());
            hBox.getChildren().add(Resimler.full_health());
            hBox.getChildren().add(Resimler.full_health());
        } else if (iyiKarakter.can == 2) {
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.full_health());
            hBox.getChildren().add(Resimler.full_health());
        } else if (iyiKarakter.can == 1.5) {
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.half_health());
            hBox.getChildren().add(Resimler.full_health());
        } else if (iyiKarakter.can == 1) {
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.full_health());
        } else if (iyiKarakter.can == 0.5) {
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.half_health());
        } else {
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.no_health());
            hBox.getChildren().add(Resimler.no_health());
        }
        return hBox;
    }


    public static void main(String[] args) {
        Application.launch(args);
    }


    /**
     * duvarlar ve yollarin genel adi yapi
     * ilgili kordinatlardaki yapiyi bulmak icin
     */
    private Node yapiyiGetir(GridPane gridPane, int satir, int sutun) {
        for (Node child : gridPane.getChildren()) {
            if (GridPane.getRowIndex(child) == satir && GridPane.getColumnIndex(child) == sutun) {
                return child;
            }
        }
        return null;
    }

    private void uyariGoster(String hataBasligi, String hata, Alert.AlertType alertType) {
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(hataBasligi);
        errorAlert.setContentText(hata);
        errorAlert.showAndWait();
    }
}
