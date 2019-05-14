package karakter;

public abstract class IyiKarakter extends Karakter {
    public double can = 3;

    public IyiKarakter() {
        super(new Lokasyon(5, 6));
    }

    public abstract void canDus();

}
