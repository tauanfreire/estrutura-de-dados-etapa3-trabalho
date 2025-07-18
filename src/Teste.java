import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Teste {
    private Arvore arvore = new Arvore();
    private DefaultListModel<String> logModel = new DefaultListModel<>();
    private JTextArea areaPercurso;

    public Teste() {
        JFrame frame = new JFrame("Árvore Binária - Visualizador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(new Color(153, 204, 255));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel painelControles = new JPanel(new GridLayout(3, 3, 10, 10));
        painelControles.setBackground(new Color(153, 204, 255));

        JTextField campoValor = new JTextField();
        // campoValor.setBackground(new Color());
        painelControles.add(new JLabel("Valor:"));
        painelControles.add(campoValor);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(new Color(51, 153, 255));
        JButton btnRemover = new JButton("Remover");
        btnRemover.setBackground(new Color(51, 153, 255));
        JButton btnBuscar = new JButton("Buscar (DFS)");
        btnBuscar.setBackground(new Color(51, 153, 255));
        JButton btnBuscarLargura = new JButton("Buscar (BFS)");
        btnBuscarLargura.setBackground(new Color(51, 153, 255));
        JButton btnEmOrdem = new JButton("Mostrar Em Ordem");
        btnEmOrdem.setBackground(new Color(51, 153, 255));
        JButton btnPreOrdem = new JButton("Mostrar Pré-Ordem");
        btnPreOrdem.setBackground(new Color(51, 153, 255));
        JButton btnPosOrdem = new JButton("Mostrar Pós-Ordem");
        btnPosOrdem.setBackground(new Color(51, 153, 255));

        painelControles.add(btnAdicionar);
        painelControles.add(btnRemover);
        painelControles.add(btnBuscar);
        painelControles.add(btnBuscarLargura);
        painelControles.add(btnEmOrdem);
        painelControles.add(btnPreOrdem);
        painelControles.add(btnPosOrdem);

        painelPrincipal.add(painelControles, BorderLayout.NORTH);

        areaPercurso = new JTextArea();
        areaPercurso.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaPercurso.setEditable(false);
        JScrollPane scrollPercurso = new JScrollPane(areaPercurso);
        scrollPercurso.setBorder(BorderFactory.createTitledBorder("Visualização da Árvore / Percursos"));
        painelPrincipal.add(scrollPercurso, BorderLayout.CENTER);

        JList<String> listaLog = new JList<>(logModel);
        JScrollPane scrollLog = new JScrollPane(listaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log das Operações"));
        scrollLog.setPreferredSize(new Dimension(250, 0));
        painelPrincipal.add(scrollLog, BorderLayout.EAST);

        frame.setContentPane(painelPrincipal);
        frame.setVisible(true);

        btnAdicionar.addActionListener(e -> {
            int valor = lerValor(campoValor);
            arvore.adicionar(valor);
            log("Adicionado: " + valor);
            atualizarPercurso("Em ordem", arvore.getRaiz(), "in");
        });

        btnRemover.addActionListener(e -> {
            int valor = lerValor(campoValor);
            if (arvore.remover(valor)) {
                log("Removido: " + valor);
            } else {
                log("Valor " + valor + " não encontrado para remoção.");
            }
            atualizarPercurso("Em ordem", arvore.getRaiz(), "in");
        });

        btnBuscar.addActionListener(e -> {
            int valor = lerValor(campoValor);
            ArrayList<Integer> visitados = arvore.buscarDFSComCaminho(valor);
            log("DFS visitados: " + visitados);
            if (!visitados.isEmpty() && visitados.contains(valor)) {
                log("DFS: Valor " + valor + " encontrado.");
            } else {
                log("DFS: Valor " + valor + " não encontrado.");
            }
        });

        btnBuscarLargura.addActionListener(e -> {
            int valor = lerValor(campoValor);
            ArrayList<Integer> visitados = arvore.buscarBFSComCaminho(valor);
            log("BFS visitados: " + visitados);
            if (!visitados.isEmpty() && visitados.contains(valor)) {
                log("BFS: Valor " + valor + " encontrado.");
            } else {
                log("BFS: Valor " + valor + " não encontrado.");
            }
        });

        btnEmOrdem.addActionListener(e -> atualizarPercurso("Em ordem", arvore.getRaiz(), "in"));
        btnPreOrdem.addActionListener(e -> atualizarPercurso("Pré-ordem", arvore.getRaiz(), "pre"));
        btnPosOrdem.addActionListener(e -> atualizarPercurso("Pós-ordem", arvore.getRaiz(), "post"));
    }

    private int lerValor(JTextField campo) {
        try {
            return Integer.parseInt(campo.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Digite um número válido.");
            throw new RuntimeException("Valor inválido");
        }
    }

    private void atualizarPercurso(String tipo, No atual, String modo) {
        StringBuilder sb = new StringBuilder(tipo + ": ");
        ArrayList<Integer> lista = new ArrayList<>();

        switch (modo) {
            case "in" -> percorrerEmOrdem(atual, lista);
            case "pre" -> percorrerPreOrdem(atual, lista);
            case "post" -> percorrerPosOrdem(atual, lista);
        }

        for (int v : lista)
            sb.append(v).append(" ");
        areaPercurso.setText(sb.toString());
    }

    private void percorrerEmOrdem(No atual, ArrayList<Integer> lista) {
        if (atual != null) {
            percorrerEmOrdem(atual.getEsquerda(), lista);
            lista.add(atual.getValor());
            percorrerEmOrdem(atual.getDireita(), lista);
        }
    }

    private void percorrerPreOrdem(No atual, ArrayList<Integer> lista) {
        if (atual != null) {
            lista.add(atual.getValor());
            percorrerPreOrdem(atual.getEsquerda(), lista);
            percorrerPreOrdem(atual.getDireita(), lista);
        }
    }

    private void percorrerPosOrdem(No atual, ArrayList<Integer> lista) {
        if (atual != null) {
            percorrerPosOrdem(atual.getEsquerda(), lista);
            percorrerPosOrdem(atual.getDireita(), lista);
            lista.add(atual.getValor());
        }
    }

    private void log(String mensagem) {
        logModel.addElement(mensagem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Teste::new);
    }
}
