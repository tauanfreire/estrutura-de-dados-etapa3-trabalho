public class No {

    private int valor;
    private No direita;
    private No esquerda;

    public No(int valor) {
        this.valor = valor;
        this.direita = null;
        this.esquerda = null;
    }

    public No getDireita() {
        return direita;
    }

    public void setDireita(No direita) {
        this.direita = direita;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

}
