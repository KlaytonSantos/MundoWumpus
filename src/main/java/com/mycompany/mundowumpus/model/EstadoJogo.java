package com.mycompany.mundowumpus.model;

/**
 * Representa o estado atual da partida.
 *
 * Esta classe reúne as informações que poderão ser
 * consultadas pela interface gráfica.
 */
public class EstadoJogo {
    
    public enum Resultado{
        EM_ANDAMENTO,
        VITORIA,
        MORTE,
        LIMITE_EXPLORACAO
    }

    // Posição atual do agente
    private final int linhaAgente;
    private final int colunaAgente;

    // Informações da partida
    private final Resultado resultado;
    private final int movimentos;
    private final int pontuacao;

    // Itens e estado do agente
    private final boolean possuiOuro;
    private final boolean possuiFlecha;
    private final boolean agenteVivo;

    // Percepções atuais
    private final boolean brisa;
    private final boolean fedor;
    private final boolean brilho;

    // Estado da partida
    private final boolean partidaTerminada;

    // Mensagem que poderá ser mostrada na interface
    private final String mensagem;

    /**
     * Cria um novo estado do jogo.
     *
     * @param agente agente que está sendo consultado
     * @param mundo mundo atual
     * @param mensagem mensagem atual da partida
     * @param partidaTerminada indica se a partida terminou
     */
    public EstadoJogo(
            
            Agente agente,
            Mundo mundo,
            String mensagem,
            boolean partidaTerminada,
            Resultado resultado) {

        this.linhaAgente = agente.getLinha();
        this.colunaAgente = agente.getColuna();

        this.movimentos =
                agente.getQuantidadeDeMovimentos();

        this.pontuacao =
                agente.getPontuacao();

        this.possuiOuro =
                agente.possuiOuro();

        this.possuiFlecha =
                agente.possuiFlecha();

        this.agenteVivo =
                agente.estaVivo();

        this.brisa =
                mundo.temBrisa(
                        linhaAgente,
                        colunaAgente);

        this.fedor =
                mundo.temFedor(
                        linhaAgente,
                        colunaAgente);
        
        this.brilho = mundo.temBrilho(
                        linhaAgente,
                        colunaAgente);

        this.mensagem = mensagem;

        this.partidaTerminada = partidaTerminada;
        
        this.resultado = resultado;
    }

    /**
     * Retorna a linha atual do agente.
     *
     * @return linha do agente
     */
    public int getLinhaAgente() {

        return linhaAgente;
    }

    /**
     * Retorna a coluna atual do agente.
     *
     * @return coluna do agente
     */
    public int getColunaAgente() {

        return colunaAgente;
    }

    /**
     * Retorna a quantidade de movimentos.
     *
     * @return quantidade de movimentos
     */
    public int getMovimentos() {

        return movimentos;
    }

    /**
     * Retorna a pontuação.
     *
     * @return pontuação atual
     */
    public int getPontuacao() {

        return pontuacao;
    }
    /**
     * Retorna o Resultado da partida.
     *
     * @return resultado.
     */
    public Resultado getResultado(){
        
        return resultado;
    }

    /**
     * Verifica se o agente possui o ouro.
     *
     * @return true se possuir ouro
     */
    public boolean possuiOuro() {

        return possuiOuro;
    }

    /**
     * Verifica se o agente possui a flecha.
     *
     * @return true se possuir flecha
     */
    public boolean possuiFlecha() {

        return possuiFlecha;
    }

    /**
     * Verifica se o agente está vivo.
     *
     * @return true se estiver vivo
     */
    public boolean agenteVivo() {

        return agenteVivo;
    }

    /**
     * Verifica se existe brisa na posição atual.
     *
     * @return true se houver brisa
     */
    public boolean temBrisa() {

        return brisa;
    }

    /**
     * Verifica se existe fedor na posição atual.
     *
     * @return true se houver fedor
     */
    public boolean temFedor() {

        return fedor;
    }
    
    /**
     * Verifica se existe Brilho na posição atual.
     *
     * @return true se houver brilho
     */
    public boolean temBrilho() {
    
        return brilho;
    }

    /**
     * Verifica se a partida terminou.
     *
     * @return true se terminou
     */
    public boolean partidaTerminada() {

        return partidaTerminada;
    }

    /**
     * Retorna a mensagem atual.
     *
     * @return mensagem
     */
    public String getMensagem() {

        return mensagem;
    }
}