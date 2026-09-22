package com.mycompany.mundowumpus.view;

import com.mycompany.mundowumpus.model.EstadoJogo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class StatusView extends VBox {

    private final Label titulo;
    private final Label fase;
    private final Label posicao;
    private final Label movimentos;
    private final ProgressBar barraMovimentos;
    private final Label pontuacao;
    private final Label ouro;
    private final Label flecha;
    private final Label brisa;
    private final Label fedor;
    private final Label brilho;
    private final Label mensagem;

    public StatusView() {

        setSpacing(12);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_LEFT);
        setPrefWidth(250);

        setStyle(
            "-fx-background-color: #172027;"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: #37474F;"
            + "-fx-border-radius: 12;"
            + "-fx-border-width: 1;"
        );

        // Título
        titulo = new Label("MUNDO DE WUMPUS");

        titulo.setStyle(
            "-fx-font-size: 20px;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: white;"
        );

        // Fase
        fase = criarInformacao();

        // Informações
        posicao = criarInformacao();
        movimentos = criarInformacao();
        pontuacao = criarInformacao();
        ouro = criarInformacao();
        flecha = criarInformacao();
        brisa = criarInformacao();
        fedor = criarInformacao();
        brilho = criarInformacao();
        mensagem = criarInformacao();

        // Barra de progresso dos movimentos
        barraMovimentos = new ProgressBar(0);

        barraMovimentos.setPrefWidth(210);
        barraMovimentos.setPrefHeight(12);

        // Adicionando os elementos
        getChildren().addAll(
            titulo,

            criarSeparador(),

            criarTituloSecao("🎮 FASE"),
            fase,

            criarSeparador(),

            criarTituloSecao("📍 POSIÇÃO"),
            posicao,

            criarSeparador(),

            criarTituloSecao("📊 ESTATÍSTICAS"),
            movimentos,
            barraMovimentos,
            pontuacao,

            criarSeparador(),

            criarTituloSecao("🎒 INVENTÁRIO"),
            ouro,
            flecha,

            criarSeparador(),

            criarTituloSecao("👁 PERCEPÇÕES"),
            brisa,
            fedor,
            brilho,

            criarSeparador(),

            criarTituloSecao("💬 MENSAGEM"),
            mensagem
        );
    }

    private Label criarInformacao() {

        Label label = new Label();

        label.setWrapText(true);

        label.setStyle(
            "-fx-font-size: 14px;"
            + "-fx-text-fill: #ECEFF1;"
        );

        return label;
    }

    private Label criarTituloSecao(String texto) {

        Label label = new Label(texto);

        label.setStyle(
            "-fx-font-size: 13px;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: #90CAF9;"
        );

        return label;
    }

    private Label criarSeparador() {

        Label separador = new Label("──────────────────");

        separador.setStyle(
            "-fx-text-fill: #455A64;"
        );

        return separador;
    }

    // Define qual fase está sendo jogada
    public void definirFase(int numeroFase) {

        fase.setText("Fase " + numeroFase);
    }

    public void atualizar(EstadoJogo estado) {

        // Posição do agente
        posicao.setText(
            "Linha: " + (estado.getLinhaAgente() + 1)
            + "\nColuna: " + (estado.getColunaAgente() + 1)
        );

        // Quantidade de movimentos
        movimentos.setText(
            "Movimentos: " + estado.getMovimentos() + " / 50"
        );

        // Atualiza a barra de progresso
        double progresso = estado.getMovimentos() / 50.0;

        barraMovimentos.setProgress(progresso);

        // Pontuação
        pontuacao.setText(
            "Pontuação: " + estado.getPontuacao()
        );

        // Inventário
        ouro.setText(
            "💰 Ouro: "
            + (estado.possuiOuro() ? "SIM" : "NÃO")
        );

        flecha.setText(
            "🏹 Flecha: "
            + (estado.possuiFlecha() ? "SIM" : "NÃO")
        );

        // Percepções
        brisa.setText(
            "💨 Brisa: "
            + (estado.temBrisa() ? "SIM" : "NÃO")
        );

        fedor.setText(
            "👃 Fedor: "
            + (estado.temFedor() ? "SIM" : "NÃO")
        );

        brilho.setText(
            "✨ Brilho: "
            + (estado.temBrilho() ? "SIM" : "NÃO")
        );

        // Mensagem
        mensagem.setText(
            estado.getMensagem()
        );

        // Estilo da mensagem quando a partida termina
        if (estado.partidaTerminada()) {

            switch (estado.getResultado()) {

                case VITORIA:

                    mensagem.setStyle(
                        "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #FFD54F;"
                    );

                    break;

                case MORTE:

                    mensagem.setStyle(
                        "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #EF9A9A;"
                    );

                    break;

                case LIMITE_EXPLORACAO:

                    mensagem.setStyle(
                        "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #FFCC80;"
                    );

                    break;

                default:
                    break;
            }

        } else {

            mensagem.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-text-fill: #ECEFF1;"
            );
        }
    }
}