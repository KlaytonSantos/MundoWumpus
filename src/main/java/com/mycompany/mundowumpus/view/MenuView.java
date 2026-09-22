package com.mycompany.mundowumpus.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuView extends VBox {

    private final Label titulo;
    private final Label subtitulo;
    private final Label descricao;
    private final Button jogar;
    private final Button escolherFase;
    private final Button sair;

    public MenuView() {

        setSpacing(18);
        setPadding(new Insets(50));
        setAlignment(Pos.CENTER);

        setStyle(
            "-fx-background-color: #101820;"
        );

        titulo = new Label("MUNDO DE WUMPUS");

        titulo.setStyle(
            "-fx-font-size: 42px;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: #FFD54F;"
        );

        subtitulo =
                new Label(
                    "EXPLORE • SOBREVIVA • ENCONTRE O OURO"
                );

        subtitulo.setStyle(
            "-fx-font-size: 16px;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: #90CAF9;"
        );

        descricao =
                new Label(
                    "Um mundo cheio de perigos espera por você.\n"
                    + "Use suas percepções, evite os poços,\n"
                    + "enfrente o Wumpus e encontre o ouro."
                );

        descricao.setAlignment(Pos.CENTER);

        descricao.setStyle(
            "-fx-font-size: 15px;"
            + "-fx-text-fill: #CFD8DC;"
        );

        jogar =
                new Button("▶  JOGAR");

        escolherFase =
                new Button("🗺  ESCOLHER FASE");

        sair =
                new Button("✖  SAIR");

        configurarBotao(
            jogar,
            "#2196F3"
        );

        configurarBotao(
            escolherFase,
            "#37474F"
        );

        configurarBotao(
            sair,
            "#37474F"
        );

        getChildren().addAll(
            titulo,
            subtitulo,
            criarEspaco(10),
            descricao,
            criarEspaco(15),
            jogar,
            escolherFase,
            sair
        );
    }

    private Label criarEspaco(double altura) {

        Label espaco = new Label();

        espaco.setPrefHeight(altura);

        return espaco;
    }

    private void configurarBotao(
            Button botao,
            String cor) {

        botao.setPrefWidth(280);
        botao.setPrefHeight(50);

        botao.setStyle(
            "-fx-font-size: 15px;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: white;"
            + "-fx-background-color: " + cor + ";"
            + "-fx-background-radius: 8;"
            + "-fx-cursor: hand;"
        );

        botao.setOnMouseEntered(event -> {

            botao.setStyle(
                "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
                + "-fx-background-color: #546E7A;"
                + "-fx-background-radius: 8;"
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
                + "-fx-cursor: hand;"
            );
        });
    }

    public Button getJogar() {
        return jogar;
    }

    public Button getEscolherFase() {
        return escolherFase;
    }

    public Button getSair() {
        return sair;
    }
}