package com.mycompany.mundowumpus.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representa o agente inteligente do Mundo de Wumpus.
 *
 * O agente é responsável por:
 * - guardar sua posição;
 * - registrar movimentos;
 * - controlar pontuação;
 * - armazenar percepções;
 * - estimar riscos;
 * - decidir seus movimentos;
 * - controlar ouro e flecha;
 * - retornar pelo caminho conhecido.
 */
public class Agente {

    // Pontuação utilizada pelas regras do jogo
    public static final int CUSTO_MOVIMENTO = -1;
    public static final int BONUS_OURO = 100;
    public static final int BONUS_WUMPUS = 50;
    public static final int PENALIDADE_MORTE = -100;
    public static final int BONUS_VITORIA = 200;
    public static final int CUSTO_FLECHA = -10;

    /*
     * Direções utilizadas pelo agente.
     *
     * 0 = cima
     * 1 = baixo
     * 2 = esquerda
     * 3 = direita
     */
    private static final int[][] DIRECOES = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    // Nomes das direções para exibição
    private static final String[] NOMES = {
        "CIMA",
        "BAIXO",
        "ESQUERDA",
        "DIREITA"
    };

    // Comandos correspondentes às direções
    private static final char[] COMANDOS = {
        'W',
        'S',
        'A',
        'D'
    };

    // Posição atual do agente
    private int linha;
    private int coluna;

    // Informações da partida
    private int quantidadeDeMovimentos;
    private int pontuacao;

    // Estado do agente
    private boolean vivo = true;
    private boolean possuiOuro;
    private boolean possuiFlecha = true;

    /*
     * Memória do agente.
     *
     * visitas registra quantas vezes cada posição foi visitada.
     * risco registra o grau de suspeita das células.
     */
    private final int[][] visitas;
    private final int[][] risco;
    private final boolean[][] brilhoPercebido;
    private final int[][] suspeitaWumpus;

    /*
     * Evita que a mesma percepção seja registrada várias vezes
     * na mesma posição.
     */
    private final boolean[][] percepcaoRegistrada;

    /*
     * Guarda o caminho conhecido entre a casa inicial
     * e a posição atual.
     */
    private final ArrayList<int[]> caminhoPercorrido
            = new ArrayList<>();

    // Sorteador utilizado nos empates
    private final Random sorteador = new Random();

    /**
     * Cria o agente na posição inicial [0][0].
     */
    public Agente() {

        visitas = new int[Mundo.TAMANHO][Mundo.TAMANHO];

        risco = new int[Mundo.TAMANHO][Mundo.TAMANHO];
        
        brilhoPercebido = new boolean[Mundo.TAMANHO][Mundo.TAMANHO];

        percepcaoRegistrada
                = new boolean[Mundo.TAMANHO][Mundo.TAMANHO];
        
        suspeitaWumpus =
        new int[Mundo.TAMANHO][Mundo.TAMANHO];

        // A casa inicial já é conhecida
        visitas[0][0] = 1;

        // Registra a casa inicial no caminho
        caminhoPercorrido.add(new int[]{0, 0});
    }

    /**
     * Registra as percepções sentidas pelo agente.
     *
     * Brisa aumenta o risco das casas vizinhas.
     * Fedor também aumenta o risco das casas vizinhas.
     *
     * @param mundo mundo atual
     * @param sentiuBrisa indica se o agente sentiu brisa
     * @param sentiuFedor indica se o agente sentiu fedor
     */
        public void observar(
            Mundo mundo,
            boolean sentiuBrisa,
            boolean sentiuFedor,
            boolean sentiuBrilho) {

        if (sentiuBrilho) {
            brilhoPercebido[linha][coluna] = true;
        }

        if (!sentiuBrisa && !sentiuFedor) {
            return;
        }

        if (percepcaoRegistrada[linha][coluna]) {
            return;
        }

        percepcaoRegistrada[linha][coluna] = true;

        for (int direcao = 0;
                direcao < DIRECOES.length;
                direcao++) {

            int linhaVizinha =
                    linha + DIRECOES[direcao][0];

            int colunaVizinha =
                    coluna + DIRECOES[direcao][1];

            if (!mundo.estaDentroDoMapa(
                    linhaVizinha,
                    colunaVizinha)) {

                continue;
            }

            /*
             * BRISA INDICA POSSÍVEL POÇO
             */
            if (sentiuBrisa) {

                if (visitas[linhaVizinha][colunaVizinha] == 0) {
                    risco[linhaVizinha][colunaVizinha]++;
                }
            }

            /*
             * FEDOR INDICA POSSÍVEL WUMPUS
             */
            if (sentiuFedor) {
                suspeitaWumpus[
                    linhaVizinha
                ][
                    colunaVizinha
                ]++;
            }
        }
    }

    /**
     * Escolhe e realiza um movimento de exploração.
     *
     * Casas novas recebem bônus.
     * Casas com risco recebem penalidade.
     * Casas visitadas também recebem penalidade.
     *
     * @param mundo mundo atual
     * @return descrição da decisão tomada
     */
    public String moverExplorando(Mundo mundo) {

        int melhorNota = Integer.MIN_VALUE;
        int[] melhoresDirecoes = new int[4];
        int quantidadeDeMelhores = 0;

        for (int direcao = 0; direcao < DIRECOES.length; direcao++) {

            int novaLinha =
                    linha + DIRECOES[direcao][0];

            int novaColuna =
                    coluna + DIRECOES[direcao][1];

            if (!mundo.estaDentroDoMapa(novaLinha, novaColuna)) {
                continue;
            }

            int visitasCasa = visitas[novaLinha][novaColuna];
            int riscoCasa = risco[novaLinha][novaColuna];

            /*
             * Casas novas são preferidas.
             */
            int bonusCasaNova =
                    visitasCasa == 0 ? 100 : 0;

            /*
             * Casas com risco conhecido são penalizadas.
             */
            int penalidadeRisco =
                    riscoCasa * 150;

            /*
             * Evita ficar repetindo as mesmas casas.
             */
            int penalidadeVisitas =
                    visitasCasa * 25;

            int nota =
                    bonusCasaNova
                    - penalidadeRisco
                    - penalidadeVisitas;

            /*
             * Casa nova e sem risco conhecido.
             */
            if (visitasCasa == 0 && riscoCasa == 0) {
                nota += 100;
            }

            /*
             * Se já percebemos brilho nesta posição,
             * damos uma pequena preferência para explorá-la.
             */
            if (brilhoPercebido[novaLinha][novaColuna]) {
                nota += 80;
            }

            /*
             * Se estamos explorando uma região próxima
             * de uma posição onde percebemos brilho,
             * também damos uma pequena preferência.
             */
            if (existeBrilhoProximo(novaLinha, novaColuna)) {
                nota += 40;
            }

            if (nota > melhorNota) {

                melhorNota = nota;
                quantidadeDeMelhores = 0;

                melhoresDirecoes[quantidadeDeMelhores++] =
                        direcao;

            } else if (nota == melhorNota) {

                melhoresDirecoes[quantidadeDeMelhores++] =
                        direcao;
            }
        }

        int direcaoEscolhida =
                melhoresDirecoes[
                        sorteador.nextInt(quantidadeDeMelhores)
                ];

        linha += DIRECOES[direcaoEscolhida][0];
        coluna += DIRECOES[direcaoEscolhida][1];

        visitas[linha][coluna]++;

        registrarPosicaoNoCaminho();

        quantidadeDeMovimentos++;

        alterarPontuacao(CUSTO_MOVIMENTO);

        return NOMES[direcaoEscolhida]
                + " | risco=" + risco[linha][coluna]
                + " | visitas=" + visitas[linha][coluna]
                + " | nota=" + melhorNota;
    }
    
        private boolean existeBrilhoProximo(
            int linhaAtual,
            int colunaAtual) {

        for (int linha = 0; linha < Mundo.TAMANHO; linha++) {

            for (int coluna = 0; coluna < Mundo.TAMANHO; coluna++) {

                if (!brilhoPercebido[linha][coluna]) {
                    continue;
                }

                int distancia =
                        Math.abs(linhaAtual - linha)
                        + Math.abs(colunaAtual - coluna);

                /*
                 * Consideramos próximas as posições que estejam
                 * a até duas casas de distância.
                 */
                if (distancia <= 2) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * Registra a posição atual no caminho conhecido.
     *
     * Caso o agente volte para uma posição que já esteja
     * no caminho, as posições posteriores são removidas.
     */
    private void registrarPosicaoNoCaminho() {

        int indiceEncontrado = -1;

        // Procura a posição atual no caminho
        for (int indice = 0;
                indice < caminhoPercorrido.size();
                indice++) {

            int[] posicao
                    = caminhoPercorrido.get(indice);

            if (posicao[0] == linha
                    && posicao[1] == coluna) {

                indiceEncontrado = indice;
                break;
            }
        }

        // Se ainda não estava no caminho, adiciona
        if (indiceEncontrado == -1) {

            caminhoPercorrido.add(
                    new int[]{linha, coluna});

        } else {

            /*
             * Remove o trecho posterior ao ponto
             * para manter somente o caminho atual.
             */
            while (caminhoPercorrido.size()
                    > indiceEncontrado + 1) {

                caminhoPercorrido.remove(
                        caminhoPercorrido.size() - 1);
            }
        }
    }

    /**
     * Retorna pelo caminho conhecido.
     *
     * @return descrição do movimento de retorno
     */
    public String retornarPeloCaminho() {

        // Já está na casa inicial
        if (caminhoPercorrido.size() <= 1) {

            return "RETORNO CONCLUÍDO: "
                    + "o agente está na casa inicial";
        }

        // Remove a posição atual
        caminhoPercorrido.remove(
                caminhoPercorrido.size() - 1);

        // Obtém a posição anterior
        int[] posicaoAnterior
                = caminhoPercorrido.get(
                        caminhoPercorrido.size() - 1);

        // Atualiza posição
        linha = posicaoAnterior[0];
        coluna = posicaoAnterior[1];

        // Registra visita
        visitas[linha][coluna]++;

        // Atualiza estatísticas
        quantidadeDeMovimentos++;

        alterarPontuacao(CUSTO_MOVIMENTO);

        return "MODO RETORNO | seguindo o histórico até ["
                + linha
                + "]["
                + coluna
                + "]";
    }

    /**
     * Escolhe uma direção para disparar a flecha.
     *
     * O agente procura entre as casas vizinhas desconhecidas
     * aquela que possui maior risco.
     *
     * @param mundo mundo atual
     * @return comando correspondente à direção escolhida
     */
    public char escolherDirecaoDaFlecha(Mundo mundo) {

     int maiorSuspeita = Integer.MIN_VALUE;

     int[] candidatas = new int[4];

     int quantidade = 0;

     for (int direcao = 0;
             direcao < DIRECOES.length;
             direcao++) {

         int novaLinha =
                 linha + DIRECOES[direcao][0];

         int novaColuna =
                 coluna + DIRECOES[direcao][1];

         if (!mundo.estaDentroDoMapa(
                 novaLinha,
                 novaColuna)) {
             continue;
         }

         int suspeita =
                 suspeitaWumpus[novaLinha][novaColuna];

         if (suspeita > maiorSuspeita) {

             maiorSuspeita = suspeita;

             quantidade = 0;

             candidatas[quantidade++] = direcao;

         } else if (suspeita == maiorSuspeita) {

             candidatas[quantidade++] = direcao;
         }
     }

     /*
      * Se nenhuma direção possuir suspeita específica
      * do Wumpus, usamos o risco geral como critério
      * secundário.
      */
     if (maiorSuspeita == 0) {

         int maiorRisco = Integer.MIN_VALUE;

         quantidade = 0;

         for (int direcao = 0;
                 direcao < DIRECOES.length;
                 direcao++) {

             int novaLinha =
                     linha + DIRECOES[direcao][0];

             int novaColuna =
                     coluna + DIRECOES[direcao][1];

             if (!mundo.estaDentroDoMapa(
                     novaLinha,
                     novaColuna)) {
                 continue;
             }

             int riscoCasa =
                     risco[novaLinha][novaColuna];

             if (riscoCasa > maiorRisco) {

                 maiorRisco = riscoCasa;

                 quantidade = 0;

                 candidatas[quantidade++] =
                         direcao;

             } else if (riscoCasa == maiorRisco) {

                 candidatas[quantidade++] =
                         direcao;
             }
         }
     }

     return COMANDOS[
             candidatas[
                     sorteador.nextInt(quantidade)
             ]
     ];
 }
    public void limparSuspeitaWumpus() {

    for (int linha = 0;
            linha < Mundo.TAMANHO;
            linha++) {

        for (int coluna = 0;
                coluna < Mundo.TAMANHO;
                coluna++) {

            suspeitaWumpus[linha][coluna] = 0;
            }
        }
    }
    /**
     * Realiza o sorteio utilizado quando o agente
     * encontra o Wumpus sem possuir a flecha.
     *
     * @return true se o agente conseguir matar o Wumpus
     */
    public boolean tentarMatarWumpus() {

        return sorteador.nextBoolean();
    }

    /**
     * Adiciona ou remove pontos da pontuação.
     *
     * @param pontos quantidade de pontos
     */
    public void alterarPontuacao(int pontos) {

        pontuacao += pontos;
    }

    /**
     * Faz o agente gastar sua flecha.
     */
    public void usarFlecha() {

        possuiFlecha = false;
    }

    /**
     * Marca o agente como morto.
     */
    public void morrer() {

        vivo = false;
    }

    /**
     * Faz o agente pegar o ouro.
     */
    public void pegarOuro() {

        possuiOuro = true;
    }

    // ==========================
    // MÉTODOS DE CONSULTA
    // ==========================

    public int getLinha() {

        return linha;
    }

    public int getColuna() {

        return coluna;
    }

    public int getQuantidadeDeMovimentos() {

        return quantidadeDeMovimentos;
    }

    public int getPontuacao() {

        return pontuacao;
    }

    public boolean estaVivo() {

        return vivo;
    }

    public boolean possuiOuro() {

        return possuiOuro;
    }

    public boolean possuiFlecha() {

        return possuiFlecha;
    }
    
    public boolean percebeuBrilhoNaPosicao(int linha, int coluna) {
    
        return brilhoPercebido[linha][coluna];
    }
    
    public boolean foiVisitada(int linha, int coluna) {
    
        return visitas[linha][coluna] > 0;
    }
    
}