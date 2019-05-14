package karakter;

import arayuz.Node;

import java.util.*;

public abstract class KotuKarakter extends Karakter {
    private int hvCost;
    private int diagonalCost;
    public Node[][] searchArea;
    public PriorityQueue<Node> openList;
    public Set<Node> closedSet;
    private Node bitisNoktasi;
    private int[][] harita;
    private Node baslangicNoktasi;
    private List<Node> enKisaYolListesi = new ArrayList<>();

    public KotuKarakter(Lokasyon lokasyon) {
        super(lokasyon);
    }

    public List<Node> enKısaYol(int[][] harita, IyiKarakter iyiKarakter) {
        this.harita = harita;
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
        duvarlariKoy();
        this.closedSet = new HashSet<>();


        return enKisaYoluBull();
    }

    /**
     * en kisa yol aramasi yapilacak bolgeleri siraya atiyor
     */
    public void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(j, i);
                node.setH(bitisNoktasi);
                this.searchArea[i][j] = node;
            }
        }
    }

    public void duvarlariKoy() {
        for (int i = 0; i < harita.length; i++) {
            for (int j = 0; j < harita[i].length; j++) {
                if (harita[i][j] == 0) {
                    duvarKoy(i, j);
                }
            }
        }
    }

    public List<Node> enKisaYoluBull() {
        openList.add(baslangicNoktasi);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {

                enKisaYolListesi = getEnKisaYol(currentNode);
                return enKisaYolListesi;
            } else {
                komsuYollariEkle(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    private void komsuYollariEkle(Node currentNode) {
        ustdekiKomsuyuEkle(currentNode);
        sagSolKomsulariEkle(currentNode);
        ustKomsuyuEkle(currentNode);
    }


    private void ustKomsuyuEkle(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            /**
             * çapraz gitmesi için
             */
//            if (col - 1 >= 0) {
//                checkNode(currentNode, col - 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
//            }
//            if (col + 1 < getSearchArea()[0].length) {
//                checkNode(currentNode, col + 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
//            }
            checkNode(currentNode, col, lowerRow, getHvCost());
        }
    }

    private void sagSolKomsulariEkle(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, getHvCost());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, middleRow, getHvCost());
        }
    }

    private void ustdekiKomsuyuEkle(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
//            if (col - 1 >= 0) {
//                checkNode(currentNode, col - 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
//            }
//            if (col + 1 < getSearchArea()[0].length) {
//                checkNode(currentNode, col + 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
//            }
            checkNode(currentNode, col, upperRow, getHvCost());
        }
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node komsu = getSearchArea()[row][col];
        if (!komsu.isDuvarmi() && !getClosedSet().contains(komsu)) {
            if (!getOpenList().contains(komsu)) {
                komsu.verileriDoldur(currentNode, cost);
                getOpenList().add(komsu);
            } else {
                boolean changed = komsu.dahaIyiYolVarmi(currentNode, cost);
                if (changed) {
                    // ekleme çıkarma yapıyoruz priority queue da yer değişimi için
                    getOpenList().remove(komsu);
                    getOpenList().add(komsu);
                }
            }
        }
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    private List<Node> getEnKisaYol(Node yol) {
        List<Node> path = new ArrayList<Node>();
        path.add(yol);
        Node enYakinYol;
        while ((enYakinYol = yol.getParentt()) != null) {
            path.add(0, enYakinYol);
            yol = enYakinYol;
        }
        return path;
    }

    private void duvarKoy(int row, int col) {
        this.searchArea[row][col].setDuvarmi(true);
    }

    public void setBaslangicNoktasi(Node baslangicNoktasi) {
        this.baslangicNoktasi = baslangicNoktasi;
    }

    public void setBitisNoktasi(Node bitisNoktasi) {
        this.bitisNoktasi = bitisNoktasi;
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(bitisNoktasi);
    }

    public int getHvCost() {
        return hvCost;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setHarita(int[][] harita) {
        this.harita = harita;
    }
}
