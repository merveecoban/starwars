package arayuz;

import javafx.scene.control.Button;

/**
 * duvar ve yollar icin ana sinif
 */
public class Node extends Button {
    private int x;
    private int y;

    private int g;
    private int f;
    private int h;

    private Node enYakinYol; // mesela 2,2 de karakter var bunun 2 yani 1 ust 1 alt komsusu var , hedefe gidecek en yakin olani secip buna atiyoruz
    private boolean duvarmi = false;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        setDisable(true);
        setText(x + "," + y);
        setPrefSize(50, 50);
    }

    /**
     * heuristic hesaplama
     */
    public void setH(Node finalNode) {
        this.h = Math.abs(finalNode.y - y) + Math.abs(finalNode.x - x);
    }

    public void verileriDoldur(Node node, int cost) {
        int gCost = node.getG() + cost;
        setEnYakinYol(node);
        setG(gCost);
        finalCostuHesapla();
    }

    /**
     * elimizdeki nodedan gidebileceğimiz daha iyi bir yer varmi
     * mesela 2,2 de olan node ile 2,1 2,3 1,2 3,2 nodelarina gidebiliriz bunlardan hangisi daha iyi
     */
    public boolean dahaIyiYolVarmi(Node node, int cost) {
        int gCost = node.getG() + cost;
        if (gCost < getG()) {
            verileriDoldur(node, cost);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y;
    }


    /**
     * final cost
     */
    private void finalCostuHesapla() {
        int finalCost = getG() + getH();
        setF(finalCost);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setEnYakinYol(Node enYakinYol) {
        this.enYakinYol = enYakinYol;
    }

    public Node getParentt() {
        return enYakinYol;
    }

    public boolean isDuvarmi() {
        return duvarmi;
    }

    public void setDuvarmi(boolean duvarmi) {
        this.duvarmi = duvarmi;
    }

    public int getRow() {
        return y;
    }

    public int getCol() {
        return x;
    }
}
