import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Arvore {

    private No raiz;

    public Arvore() {
        this.raiz = null;
    }

    public No getRaiz() {
        return raiz;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }

    public void adicionar(int valor) {
        No novoValor = new No(valor);

        if (raiz == null) {
            raiz = novoValor;
        } else {
            No atual = this.raiz;

            while (true) {
                if (novoValor.getValor() < atual.getValor()) {
                    if (atual.getEsquerda() != null) {
                        atual = atual.getEsquerda();
                    } else {
                        atual.setEsquerda(novoValor);
                        break;
                    }
                } else {
                    if (atual.getDireita() != null) {
                        atual = atual.getDireita();
                    } else {
                        atual.setDireita(novoValor);
                        break;
                    }
                }
            }
        }
    }

    public boolean remover(int valor) {
        No atual = raiz;
        No paiAtual = null;

        while (atual != null && atual.getValor() != valor) {
            paiAtual = atual;
            if (valor < atual.getValor()) {
                atual = atual.getEsquerda();
            } else {
                atual = atual.getDireita();
            }
        }

        if (atual == null) {
            return false;
        }

        if (atual.getEsquerda() != null && atual.getDireita() != null) {
            No substituto = atual.getDireita();
            No paiSubstituto = atual;

            while (substituto.getEsquerda() != null) {
                paiSubstituto = substituto;
                substituto = substituto.getEsquerda();
            }

            atual.setValor(substituto.getValor());

            atual = substituto;
            paiAtual = paiSubstituto;
        }

        No filho = null;
        if (atual.getEsquerda() != null) {
            filho = atual.getEsquerda();
        } else if (atual.getDireita() != null) {
            filho = atual.getDireita();
        }

        if (paiAtual == null) {
            raiz = filho;
        } else {
            if (paiAtual.getEsquerda() == atual) {
                paiAtual.setEsquerda(filho);
            } else {
                paiAtual.setDireita(filho);
            }
        }

        return true;
    }

    public ArrayList<Integer> buscarDFSComCaminho(int valor) {
        ArrayList<Integer> visitados = new ArrayList<>();
        buscarDFSRec(raiz, valor, visitados);
        return visitados;
    }

    private boolean buscarDFSRec(No atual, int valor, ArrayList<Integer> visitados) {
        if (atual == null)
            return false;
        visitados.add(atual.getValor());

        if (atual.getValor() == valor)
            return true;

        return buscarDFSRec(atual.getEsquerda(), valor, visitados)
                || buscarDFSRec(atual.getDireita(), valor, visitados);
    }

    public ArrayList<Integer> buscarBFSComCaminho(int valor) {
        ArrayList<Integer> visitados = new ArrayList<>();
        if (raiz == null)
            return visitados;

        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            No atual = fila.poll();
            visitados.add(atual.getValor());

            if (atual.getValor() == valor)
                break;

            if (atual.getEsquerda() != null)
                fila.add(atual.getEsquerda());
            if (atual.getDireita() != null)
                fila.add(atual.getDireita());
        }

        return visitados;
    }
}
