package araclar;

import karakter.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DosyaCozumleyici {
    private final String haritaDosyasiStr = "Harita.txt";
    private int[][] harita;
    public static int[][] haritaIlkHali;
    private List<KotuKarakter> kotuKarakterler;

    private HashMap<Character, Lokasyon> kapilar = new HashMap<>();//  kapilarin yerlerini tutar a kapisi => 0,5

    public DosyaCozumleyici() {
        kapilar.put('A', new Lokasyon(0, 5));
        kapilar.put('B', new Lokasyon(4, 0));
        kapilar.put('C', new Lokasyon(12, 0));
        kapilar.put('D', new Lokasyon(13, 5));
        kapilar.put('E', new Lokasyon(4, 10));

        kotuKarakterler = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(haritaDosyasiStr))) {

            String sCurrentLine;
            List<List<Integer>> harita = new ArrayList<>();
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.startsWith("Karakter")) { //kotu karakter ekleme
                    sCurrentLine = sCurrentLine.replaceAll("Karakter:", "").replaceAll("Kapi:", "");
                    String[] split = sCurrentLine.split(",");
                    String karakterStr = split[0];
                    Character kapiChar = split[1].trim().charAt(0);

                    Lokasyon lokasyon = kapilar.get(kapiChar);

                    if (karakterStr.contains("Darth") || karakterStr.toLowerCase().contains("darth")) {
                        kotuKarakterler.add(new DarthVader(lokasyon));
                    } else if (karakterStr.contains("Kylo") || karakterStr.toLowerCase().contains("kylo")) {
                        kotuKarakterler.add(new KyloRen(lokasyon));
                    } else if (karakterStr.contains("Storm") || karakterStr.toLowerCase().contains("storm")) {
                        kotuKarakterler.add(new Stormtrooper(lokasyon));
                    }

                } else { //haritayi ekleme
                    if (sCurrentLine.trim().length() > 0) {
                        List<Integer> satir = new ArrayList<>();
                        String[] split = sCurrentLine.trim().split("\t");
                        for (String s : split) {
                            satir.add(Integer.parseInt(s));
                        }
                        harita.add(satir);
                    }
                }
            }


            this.harita = new int[harita.size()][harita.get(0).size()];
            this.haritaIlkHali = new int[harita.size()][harita.get(0).size()];
            for (int i = 0; i < harita.size(); i++) {
                for (int j = 0; j < harita.get(i).size(); j++) {
                    this.harita[i][j] = harita.get(i).get(j);
                    haritaIlkHali[i][j] = harita.get(i).get(j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int[][] getHarita() {
        return harita;
    }

    public List<KotuKarakter> getKotuKarakterler() {
        return kotuKarakterler;
    }
}
