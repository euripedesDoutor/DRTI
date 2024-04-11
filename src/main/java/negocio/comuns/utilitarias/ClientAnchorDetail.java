package negocio.comuns.utilitarias;

public class ClientAnchorDetail {

    private int fromIndex;
    private int toIndex;
    private int inset;

    public ClientAnchorDetail(int fromIndex, int toIndex, int inset) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.inset = inset;
    }

    public int getFromIndex() {
        return (this.fromIndex);
    }

    public int getToIndex() {
        return (this.toIndex);
    }

    public int getInset() {
        return (this.inset);
    }
}
