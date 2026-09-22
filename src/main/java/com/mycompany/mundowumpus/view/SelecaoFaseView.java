package com.mycompany.mundowumpus.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SelecaoFaseView extends VBox {

    private final Label titulo;
    private final Label subtitulo;

    private final Button fase1;
    private final Button fase2;
    private final Button fase3;

    private final Button voltar;

    public SelecaoFaseView() {

        setSpacing(15);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);

        setStyle(
            "-fx-background-color: #101820;"
        );

        /*
         * TÍTULO
         */
        titulo =
                new Label("SELEÇÃO DE FASE");

        titulo.setStyle(
            "-fx-font-size: 34px;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: #FFD54F;"
        );

        /*
         * SUBTÍTULO
         */
        subtitulo =
                new Label(
                    "Escolha o desafio que deseja enfrentar"
                );

        subtitulo.setStyle(
            "-fx-font-size: 16px;"
            + "-fx-text-fill: #90CAF9;"
        );

        /*
         * BOTÕES DAS FASES
         */
        fase1 =
                criarBotao(
                    "FASE 1",
                    "🌱  AVENTURA"
                );

        fase2 =
                criarBotao(
                    "FASE 2",
                    "⚔  PERIGO"
                );

        fase3 =
                criarBotao(
                    "FASE 3",
                    "💀  SOBREVIVÊNCIA"
                );

        /*
         * BOTÃO VOLTAR
         */
        voltar =
                new Button(
                    "🏠  VOLTAR AO MENU"
                );

        configurarBotao(
            voltar,
            "#37474F"
        );

        getChildren().addAll(
            titulo,
            subtitulo,
            criarEspaco(10),
            fase1,
            fase2,
            fase3,
            criarEspaco(10),
            voltar
        );
    }

    private Button criarBotao(
            String numero,
            String nome) {

        Button botao =
                new Button(
                    numero + "     " + nome
                );

        configurarBotao(
            botao,
            "#263238"
        );

        return botao;
    }

    private void configurarBotao(
            Button botao,
            String cor) {

        botao.setPrefWidth(360);
        botao.setPrefHeight(55);

        botao.setStyle(
            "-fx-font-size: 15px;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: white;"
            + "-fx-background-color: " + cor + ";"
            + "-fx-background-radius: 8;"
            + "-fx-border-color: #546E7A;"
            + "-fx-border-radius: 8;"
            + "-fx-border-width: 1;"
            + "-fx-cursor: hand;"
        );

        botao.setOnMouseEntered(event -> {

            botao.setStyle(
                "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
                + "-fx-background-color: #455A64;"
                + "-fx-background-radius: 8;"
                + "-fx-border-color: #90CAF9;"
                + "-fx-border-radius: 8;"
                + "-fx-border-width: 2;"
                + "-fx-cursor: hand;"
            );
        });

        botao.setOnMouseExited(event -> {

            botao.setStyle(
                "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
                + "-fx-background-color: " + cor + ";"
                + "-fx-background-radius: 8;"
                + "-fx-border-color: #546E7A;"
                + "-fx-border-radius: 8;"
                + "-fx-border-width: 1;"
                + "-fx-cursor: hand;"
            );
        });
    }

    private Label criarEspaco(
            double altura) {

        Label espaco =
                new Label();

        espaco.setPrefHeight(
            altura
        );

        return espaco;
    }

    public Button getFase1() {
        return fase1;
    }

    public Button getFase2() {
        return fase2;
    }

    public Button getFase3() {
        return fase3;
    }

    public Button getVoltar() {
        return voltar;
    }
}