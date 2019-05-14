package karakter;


import arayuz.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class DarthVader extends KotuKarakter {
    public DarthVader(Lokasyon lokasyon) {
        super(lokasyon);
    }

    public List<Node> enKısaYol(int[][] harita, IyiKarakter iyiKarakter) {
        setHarita(harita);
        setBaslangicNoktasi(new Node(getX(), getY()));
        setBitisNoktasi(new Node(iyiKarakter.getY(), iyiKarakter.getX()));
        this.searchArea = new Node[harita.length][harita[0].length];
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        this.closedSet = new HashSet<>();


        return enKisaYoluBull();
    }

}
