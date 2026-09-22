package com.mycompany.mundowumpus.model;

/**
 * Representa o mundo do jogo Mundo de Wumpus.
 *
 * O mundo é responsável pelas regras relacionadas ao mapa:
 * posições dos elementos, limites, percepções e flecha.
 *
 * Cada fase possui um mapa fixo.
 */
public class Mundo {

    // Tamanho do mapa: 5 linhas x 5 colunas
    public static final int TAMANHO = 7;

    // Elementos possíveis no mapa
    public static final char VAZIO = '.';
    public static final char POCO = 'P';
    public static final char WUMPUS = 'W';
    public static final char OURO = 'O';

    // Número de fases disponíveis
    public static final int QUANTIDADE_FASES = 3;

    // Fase atual
    private final int fase;

    // Matriz que representa os elementos existentes no mundo
    private final Celula[][] mapa;

    /**
     * Cria um novo mundo utilizando a Fase 1.
     *
     * Este construtor é mantido para preservar compatibilidade
     * com o código existente.
     */
    public Mundo() {
        this(1);
    }

    /**
     * Cria um novo mundo para a fase informada.
     *
     * @param fase número da fase
     */
    public Mundo(int fase) {

        if (fase < 1 || fase > QUANTIDADE_FASES) {
            throw new IllegalArgumentException(
                    "Fase inválida: " + fase
            );
        }

        this.fase = fase;

        mapa = new Celula[TAMANHO][TAMANHO];

        criarMapa();
    }

    /**
     * Cria todas as células e posiciona os elementos
     * de acordo com a fase selecionada.
     */
    private void criarMapa() {

        // Primeiro criamos todas as células vazias
        for (int linha = 0; linha < TAMANHO; linha++) {

            for (int coluna = 0; coluna < TAMANHO; coluna++) {

                Posicao posicao =
                        new Posicao(linha, coluna);

                mapa[linha][coluna] =
                        new Celula(posicao);
            }
        }

        // Seleciona o mapa da fase
        switch (fase) {

            case 1:
                criarFase1();
                break;

            case 2:
                criarFase2();
                break;

            case 3:
                criarFase3();
                break;

            default:
                // Essa situação não deve acontecer,
                // pois a fase já foi validada no construtor.
                throw new IllegalStateException(
                        "Fase não configurada."
                );
        }
    }

    /**
     * Fase 1.
     *
     * É o mapa que já utilizávamos anteriormente.
     */
    private void criarFase1() {

        // Poços
        mapa[1][2].setElemento(POCO);
        mapa[3][1].setElemento(POCO);

        // Wumpus
        mapa[2][3].setElemento(WUMPUS);

        // Ouro
        mapa[4][4].setElemento(OURO);
    }

    /**
     * Fase 2.
     *
     * Possui uma disposição diferente dos elementos,
     * aumentando a necessidade de exploração.
     */
    private void criarFase2() {

        // Poços
        mapa[1][1].setElemento(POCO);
        mapa[2][4].setElemento(POCO);
        mapa[3][2].setElemento(POCO);

        // Wumpus
        mapa[3][4].setElemento(WUMPUS);

        // Ouro
        mapa[4][2].setElemento(OURO);
    }

    /**
     * Fase 3.
     *
     * Possui uma configuração mais perigosa,
     * exigindo maior cuidado durante a exploração.
     */
    private void criarFase3() {

        // Poços
        mapa[1][2].setElemento(POCO);
        mapa[2][1].setElemento(POCO);
        mapa[2][4].setElemento(POCO);
        mapa[4][2].setElemento(POCO);

        // Wumpus
        mapa[3][3].setElemento(WUMPUS);

        // Ouro
        mapa[4][4].setElemento(OURO);
    }

    /**
     * Retorna a fase atual.
     *
     * @return número da fase
     */
    public int getFase() {
        return fase;
    }

    /**
     * Verifica se uma posição está dentro dos limites do mapa.
     *
     * @param linha linha que será verificada
     * @param coluna coluna que será verificada
     * @return true se a posição estiver dentro do mapa
     */
    public boolean estaDentroDoMapa(int linha, int coluna) {

        return linha >= 0
                && linha < TAMANHO
                && coluna >= 0
                && coluna < TAMANHO;
    }

    /**
     * Retorna uma célula do mapa.
     *
     * @param linha linha da célula
     * @param coluna coluna da célula
     * @return célula correspondente
     */
    public Celula getCelula(int linha, int coluna) {

        return mapa[linha][coluna];
    }

    /**
     * Retorna o elemento existente em uma posição.
     *
     * @param linha linha da posição
     * @param coluna coluna da posição
     * @return elemento existente
     */
    public char getElemento(int linha, int coluna) {

        return mapa[linha][coluna].getElemento();
    }

    /**
     * Remove o elemento de uma célula, deixando-a vazia.
     *
     * @param linha linha da célula
     * @param coluna coluna da célula
     */
    public void removerElemento(int linha, int coluna) {

        mapa[linha][coluna].setElemento(VAZIO);
    }

    /**
     * Marca uma célula como visitada pelo agente.
     *
     * @param linha linha da célula
     * @param coluna coluna da célula
     */
    public void marcarVisitada(int linha, int coluna) {

        mapa[linha][coluna].marcarVisitada();
    }

    /**
     * Verifica se existe determinado elemento em uma das quatro
     * células vizinhas.
     *
     * @param linha linha da posição atual
     * @param coluna coluna da posição atual
     * @param procurado elemento procurado
     * @return true se o elemento estiver em uma célula vizinha
     */
    private boolean existeVizinho(
            int linha,
            int coluna,
            char procurado) {

        int[][] direcoes = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        for (int[] direcao : direcoes) {

            int linhaVizinha =
                    linha + direcao[0];

            int colunaVizinha =
                    coluna + direcao[1];

            if (estaDentroDoMapa(
                    linhaVizinha,
                    colunaVizinha)
                    && mapa[linhaVizinha][colunaVizinha]
                            .getElemento() == procurado) {

                return true;
            }
        }

        return false;
    }

    /**
     * Verifica se existe um poço em uma célula vizinha.
     */
    public boolean temBrisa(int linha, int coluna) {

        return existeVizinho(linha, coluna, POCO);
    }

    /**
     * Verifica se existe um Wumpus em uma célula vizinha.
     */
    public boolean temFedor(int linha, int coluna) {

        return existeVizinho(linha, coluna, WUMPUS);
    }

    /**
     * Verifica se existe ouro em uma célula vizinha.
     */
    public boolean temBrilho(int linha, int coluna) {

        return existeVizinho(linha, coluna, OURO);
    }

    /**
     * Faz a flecha percorrer uma linha reta até sair do mapa.
     *
     * Se encontrar o Wumpus, ele é removido do mapa.
     *
     * @param linha linha atual do agente
     * @param coluna coluna atual do agente
     * @param direcao direção do disparo
     * @return true se a flecha acertar o Wumpus
     */
    public boolean atirarFlecha(
            int linha,
            int coluna,
            char direcao) {

        int variacaoLinha = 0;
        int variacaoColuna = 0;

        switch (direcao) {

            case 'W':
                variacaoLinha = -1;
                break;

            case 'S':
                variacaoLinha = 1;
                break;

            case 'A':
                variacaoColuna = -1;
                break;

            case 'D':
                variacaoColuna = 1;
                break;

            default:
                return false;
        }

        int linhaDaFlecha =
                linha + variacaoLinha;

        int colunaDaFlecha =
                coluna + variacaoColuna;

        while (estaDentroDoMapa(
                linhaDaFlecha,
                colunaDaFlecha)) {

            if (mapa[linhaDaFlecha][colunaDaFlecha]
                    .getElemento() == WUMPUS) {

                removerElemento(
                        linhaDaFlecha,
                        colunaDaFlecha);

                return true;
            }

            linhaDaFlecha += variacaoLinha;
            colunaDaFlecha += variacaoColuna;
        }

        return false;
    }
}

