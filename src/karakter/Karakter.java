package karakter;

public class Karakter {
    private Lokasyon lokasyon;

    public Karakter(Lokasyon lokasyon) {
        this.lokasyon = lokasyon;
    }

    public Lokasyon getLokasyon() {
        return lokasyon;
    }

    public void setLokasyon(Lokasyon lokasyon) {
        this.lokasyon = lokasyon;
    }

    public void setX(int x) {
        lokasyon.setX(x);
    }

    public void setY(int y) {
        lokasyon.setY(y);
    }

    public int getX() {
        return lokasyon.getX();
    }

    public int getY() {
        return lokasyon.getY();
    }
}
