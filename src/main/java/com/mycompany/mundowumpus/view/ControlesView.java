package com.mycompany.mundowumpus.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlesView extends HBox {

    private final Button reiniciar;
    private final Button pausar;
    private final Button menu;

    public ControlesView() {

        // Espaçamento entre os botões
        setSpacing(15);

        // Espaçamento interno
        setPadding(new Insets(15));

        // Centraliza os botões
        setAlignment(Pos.CENTER);

        // Fundo da área de controles
        setStyle(
            "-fx-background-color: #172027;"
            + "-fx-border-color: #37474F;"
            + "-fx-border-width: 1 0 0 0;"
        );

        // =====================================================
        // BOTÕES
        // =====================================================

        reiniciar = new Button("🔄 REINICIAR");

        pausar = new Button("⏸ PAUSAR");

        menu = new Button("🏠 MENU");

        // Configura os botões
        configurarBotao(reiniciar);
        configurarBotao(pausar);
        configurarBotao(menu);

        // Adiciona os botões
        getChildren().addAll(
            reiniciar,
            pausar,
            menu
        );
    }

    // =========================================================
    // CONFIGURAÇÃO DOS BOTÕES
    // =========================================================

    private void configurarBotao(Button botao) {

        botao.setPrefWidth(180);

        botao.setPrefHeight(42);

        botao.setStyle(
            "-fx-font-size: 14px;"
            + "-fx-font-weight: bold;"
            + "-fx-background-radius: 8;"
            + "-fx-cursor: hand;"
        );
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public Button getReiniciar() {

        return reiniciar;
    }

    public Button getPausar() {

        return pausar;
    }

    public Button getMenu() {

        return menu;
    }
}