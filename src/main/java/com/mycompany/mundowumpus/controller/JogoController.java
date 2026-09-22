package com.mycompany.mundowumpus.controller;

import com.mycompany.mundowumpus.model.Agente;
import com.mycompany.mundowumpus.model.EstadoJogo;
import com.mycompany.mundowumpus.model.Mundo;
import com.mycompany.mundowumpus.view.MapaView;
import com.mycompany.mundowumpus.view.StatusView;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class JogoController {

    private final Mundo mundo;
    private final Agente agente;
    private final MapaView mapaView;
    private final StatusView statusView;

    private Timeline timeline;

    private static final int LIMITE_MOVIMENTOS = 50;

    private EstadoJogo.Resultado resultado =
            EstadoJogo.Resultado.EM_ANDAMENTO;

    private String mensagem =
            "Partida em andamento...";

    private boolean pausado = false;

    // Ação executada quando a partida termina
    private Runnable aoFinalizar;

    // Evita que a tela de resultado seja chamada mais de uma vez
    private boolean partidaFinalizada = false;

    public JogoController(
            Mundo mundo,
            Agente agente,
            MapaView mapaView,
            StatusView statusView) {

        this.mundo = mundo;
        this.agente = agente;
        this.mapaView = mapaView;
        this.statusView = statusView;
    }

    // =========================================================
    // DEFINE AÇÃO DO FIM DA PARTIDA
    // =========================================================

    public void definirAoFinalizar(Runnable aoFinalizar) {

        this.aoFinalizar = aoFinalizar;
    }

    // =========================================================
    // INICIAR
    // =========================================================

    public void iniciar() {

        agente.observar(
                mundo,
                mundo.temBrisa(
                        agente.getLinha(),
                        agente.getColuna()
                ),
                mundo.temFedor(
                        agente.getLinha(),
                        agente.getColuna()
                ),
                mundo.temBrilho(
                        agente.getLinha(),
                        agente.getColuna()
                )
        );

        atualizarTela();

        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> realizarMovimento()
                )
        );

        timeline.setCycleCount(
                Timeline.INDEFINITE
        );

        timeline.play();

        pausado = false;
    }

    // =========================================================
    // REALIZAR MOVIMENTO
    // =========================================================

    private void realizarMovimento() {

        if (pausado || partidaFinalizada) {
            return;
        }

        // =====================================================
        // ATUALIZA PERCEPÇÕES
        // =====================================================

        agente.observar(
                mundo,
                mundo.temBrisa(
                        agente.getLinha(),
                        agente.getColuna()
                ),
                mundo.temFedor(
                        agente.getLinha(),
                        agente.getColuna()
                ),
                mundo.temBrilho(
                        agente.getLinha(),
                        agente.getColuna()
                )
        );

        // =====================================================
        // EXPLORAÇÃO
        // =====================================================

        if (!agente.possuiOuro()) {

            tentarUsarFlecha();

            if (!partidaTerminada()) {

                agente.moverExplorando(mundo);
            }

        } else {

            // =================================================
            // RETORNO PELO CAMINHO CONHECIDO
            // =================================================

            agente.retornarPeloCaminho();
        }

        // =====================================================
        // PROCESSA A POSIÇÃO ATUAL
        // =====================================================

        processarPosicaoAtual();

        // =====================================================
        // VERIFICA VITÓRIA
        // =====================================================

        verificarVitoria();

        // =====================================================
        // VERIFICA LIMITE
        // =====================================================

        verificarLimiteDeMovimentos();

        // =====================================================
        // ATUALIZA A TELA
        // =====================================================

        atualizarTela();

        // =====================================================
        // FINALIZA SE NECESSÁRIO
        // =====================================================

        if (partidaTerminada()) {

            finalizarPartida();
        }
    }

    // =========================================================
    // TENTAR USAR FLECHA
    // =========================================================

    private void tentarUsarFlecha() {

        // Não possui flecha
        if (!agente.possuiFlecha()) {
            return;
        }

        // Só dispara se sentir fedor
        if (!mundo.temFedor(
                agente.getLinha(),
                agente.getColuna())) {

            return;
        }

        /*
         * O método retorna um char:
         *
         * W = cima
         * S = baixo
         * A = esquerda
         * D = direita
         */
        char direcao =
                agente.escolherDirecaoDaFlecha(mundo);

        // Gasta a flecha
        agente.usarFlecha();

        // Aplica o custo da flecha
        agente.alterarPontuacao(
                Agente.CUSTO_FLECHA
        );

        // Tenta atingir o Wumpus
        boolean acertou =
                mundo.atirarFlecha(
                        agente.getLinha(),
                        agente.getColuna(),
                        direcao
                );

        if (acertou) {

            mensagem =
                    "Você acertou o Wumpus!";

            agente.alterarPontuacao(
                    Agente.BONUS_WUMPUS
            );

        } else {

            mensagem =
                    "A flecha errou o alvo.";
        }
    }

    // =========================================================
    // PROCESSAR POSIÇÃO ATUAL
    // =========================================================

    private void processarPosicaoAtual() {

        int linha =
                agente.getLinha();

        int coluna =
                agente.getColuna();

        char elemento =
                mundo.getElemento(
                        linha,
                        coluna
                );

        // =====================================================
        // POÇO
        // =====================================================

        if (elemento == Mundo.POCO) {

            agente.morrer();

            resultado =
                    EstadoJogo.Resultado.MORTE;

            mensagem =
                    "Você caiu em um poço!";

            agente.alterarPontuacao(
                    Agente.PENALIDADE_MORTE
            );

            return;
        }

        // =====================================================
        // WUMPUS
        // =====================================================

        if (elemento == Mundo.WUMPUS) {

            agente.morrer();

            resultado =
                    EstadoJogo.Resultado.MORTE;

            mensagem =
                    "O Wumpus encontrou você!";

            agente.alterarPontuacao(
                    Agente.PENALIDADE_MORTE
            );

            return;
        }

        // =====================================================
        // OURO
        // =====================================================

        if (elemento == Mundo.OURO) {

            agente.pegarOuro();

            mundo.removerElemento(
                    linha,
                    coluna
            );

            agente.alterarPontuacao(
                    Agente.BONUS_OURO
            );

            mensagem =
                    "Você encontrou o ouro!";
        }
    }

    // =========================================================
    // VERIFICAR VITÓRIA
    // =========================================================

    private void verificarVitoria() {

        // Precisa possuir o ouro
        if (!agente.possuiOuro()) {
            return;
        }

        // Precisa retornar à posição inicial
        if (agente.getLinha() != 0) {
            return;
        }

        if (agente.getColuna() != 0) {
            return;
        }

        resultado =
                EstadoJogo.Resultado.VITORIA;

        mensagem =
                "Você encontrou o ouro e voltou para a entrada!";

        agente.alterarPontuacao(
                Agente.BONUS_VITORIA
        );
    }

    // =========================================================
    // VERIFICAR LIMITE DE MOVIMENTOS
    // =========================================================

    private void verificarLimiteDeMovimentos() {

        if (agente.getQuantidadeDeMovimentos()
                >= LIMITE_MOVIMENTOS) {

            if (!partidaTerminada()) {

                resultado =
                        EstadoJogo.Resultado.LIMITE_EXPLORACAO;

                mensagem =
                        "O limite de 50 movimentos foi atingido.";
            }
        }
    }

    // =========================================================
    // VERIFICA SE A PARTIDA TERMINOU
    // =========================================================

    private boolean partidaTerminada() {

        return resultado !=
                EstadoJogo.Resultado.EM_ANDAMENTO;
    }

    // =========================================================
    // ATUALIZAR TELA
    // =========================================================

    private void atualizarTela() {

        mapaView.atualizar(
                mundo,
                agente,
                partidaTerminada()
        );

        EstadoJogo estado =
                new EstadoJogo(
                        agente,
                        mundo,
                        mensagem,
                        partidaTerminada(),
                        resultado
                );

        statusView.atualizar(
                estado
        );
    }

    // =========================================================
    // FINALIZAR PARTIDA
    // =========================================================

    private void finalizarPartida() {

        // Evita executar duas vezes
        if (partidaFinalizada) {
            return;
        }

        partidaFinalizada = true;

        // Para o movimento automático
        if (timeline != null) {

            timeline.stop();
        }

        pausado = false;

        /*
         * Pequeno intervalo para o jogador conseguir
         * visualizar o último estado do mapa antes
         * da tela de resultado aparecer.
         */
        PauseTransition pausa =
                new PauseTransition(
                        Duration.seconds(1)
                );

        pausa.setOnFinished(event -> {

            if (aoFinalizar != null) {

                aoFinalizar.run();
            }
        });

        pausa.play();
    }

    // =========================================================
    // PARAR
    // =========================================================

    public void parar() {

        if (timeline != null) {

            timeline.stop();
        }

        pausado = false;
    }

    // =========================================================
    // PAUSAR
    // =========================================================

    public void pausar() {

        if (timeline != null
                && !partidaTerminada()) {

            timeline.pause();

            pausado = true;
        }
    }

    // =========================================================
    // CONTINUAR
    // =========================================================

    public void continuar() {

        if (timeline != null
                && !partidaTerminada()) {

            timeline.play();

            pausado = false;
        }
    }

    // =========================================================
    // VERIFICA SE ESTÁ PAUSADO
    // =========================================================

    public boolean estaPausado() {

        return pausado;
    }

    // =========================================================
    // OBTÉM O ESTADO ATUAL
    // =========================================================

    public EstadoJogo getEstadoAtual() {

        return new EstadoJogo(
                agente,
                mundo,
                mensagem,
                partidaTerminada(),
                resultado
        );
    }
}