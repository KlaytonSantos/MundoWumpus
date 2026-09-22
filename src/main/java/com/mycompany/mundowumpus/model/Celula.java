package com.mycompany.mundowumpus.model;

/**
 * Representa uma célula do mapa do Mundo de Wumpus.
 *
 * Cada célula possui uma posição, um elemento e uma informação
 * sobre ter sido visitada ou não pelo agente.
 */
public class Celula {

    // Posição da célula dentro da matriz
    private final Posicao posicao;

    // Elemento existente na célula
    private char elemento;

    // Indica se o agente já passou por essa célula
    private boolean visitada;

    /**
     * Cria uma célula vazia.
     *
     * @param posicao posição da célula no mapa
     */
    public Celula(Posicao posicao) {
        this.posicao = posicao;
        this.elemento = '.';
        this.visitada = false;
    }

    /**
     * Retorna a posição da célula.
     *
     * @return posição da célula
     */
    public Posicao getPosicao() {
        return posicao;
    }

    /**
     * Retorna o elemento presente na célula.
     *
     * @return elemento da célula
     */
    public char getElemento() {
        return elemento;
    }

    /**
     * Define o elemento presente na célula.
     *
     * @param elemento elemento que será colocado na célula
     */
    public void setElemento(char elemento) {
        this.elemento = elemento;
    }

    /**
     * Verifica se a célula já foi visitada.
     *
     * @return true se foi visitada
     */
    public boolean isVisitada() {
        return visitada;
    }

    /**
     * Marca a célula como visitada.
     */
    public void marcarVisitada() {
        this.visitada = true;
    }
}