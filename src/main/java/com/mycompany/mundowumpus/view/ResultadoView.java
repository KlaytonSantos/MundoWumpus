package com.mycompany.mundowumpus.view;

import com.mycompany.mundowumpus.model.EstadoJogo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ResultadoView extends VBox {

    private final Label titulo;
    private final Label subtitulo;
    private final Label pontuacao;
    private final Label movimentos;

    private final Button principal;
    private final Button menu;

    public ResultadoView(
            EstadoJogo estado,
            boolean ultimaFase) {

        setSpacing(20);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);

        setStyle(
            "-fx-background-color: #101820;"
        );

        titulo = new Label();

        titulo.setStyle(
            "-fx-font-size: 42px;"
            + "-fx-font-weight: bold;"
        );

        subtitulo = new Label();

        subtitulo.setStyle(
            "-fx-font-size: 18px;"
            + "-fx-text-fill: #ECEFF1;"
        );

        pontuacao = new Label();

        pontuacao.setStyle(
            "-fx-font-size: 18px;"
            + "-fx-text-fill: #FFD54F;"
        );

        movimentos = new Label();

        movimentos.setStyle(
            "-fx-font-size: 18px;"
            + "-fx-text-fill: #90CAF9;"
        );

        principal = new Button();
        menu = new Button("🏠 VOLTAR AO MENU");

        configurarResultado(
            estado,
            ultimaFase
        );

        configurarBotao(principal);
        configurarBotao(menu);

        getChildren().addAll(
            titulo,
            subtitulo,
            pontuacao,
            movimentos,
            principal,
            menu
        );
    }

    private void configurarResultado(
            EstadoJogo estado,
            boolean ultimaFase) {

        switch (estado.getResultado()) {

            case VITORIA:

                titulo.setText(
                    "🏆 VITÓRIA!"
                );

                titulo.setStyle(
                    "-fx-font-size: 42px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-text-fill: #FFD54F;"
                );

                if (ultimaFase) {

                    subtitulo.setText(
                        "Você conquistou todas as fases!"
                    );

                    principal.setText(
                        "🏠 VOLTAR AO MENU"
                    );

                } else {

                    subtitulo.setText(
                        "Fase concluída! Prepare-se para o próximo desafio."
                    );

                    principal.setText(
                        "▶  CONTINUAR"
                    );
                }

                break;

            case MORTE:

                titulo.setText(
                    "💀 VOCÊ MORREU!"
                );

                titulo.setStyle(
                    "-fx-font-size: 42px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-text-fill: #EF5350;"
                );

                subtitulo.setText(
                    "O perigo foi maior que a sua exploração."
                );

                principal.setText(
                    "🔄 TENTAR NOVAMENTE"
                );

                break;

            case LIMITE_EXPLORACAO:

                titulo.setText(
                    "⏳ LIMITE ATINGIDO!"
                );

                titulo.setStyle(
                    "-fx-font-size: 42px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-text-fill: #FFB74D;"
                );

                subtitulo.setText(
                    "Você atingiu o limite de 50 movimentos."
                );

                principal.setText(
                    "🔄 TENTAR NOVAMENTE"
                );

                break;

            default:

                titulo.setText(
                    "FIM DE JOGO"
                );

                subtitulo.setText(
                    "A partida foi encerrada."
                );

                principal.setText(
                    "🔄 TENTAR NOVAMENTE"
                );

                break;
        }

        pontuacao.setText(
            "⭐ Pontuação final: "
            + estado.getPontuacao()
        );

        movimentos.setText(
            "👣 Movimentos realizados: "
            + estado.getMovimentos()
        );
    }

    private void configurarBotao(Button botao) {

        botao.setPrefWidth(280);
        botao.setPrefHeight(48);

        botao.setStyle(
            "-fx-font-size: 14px;"
            + "-fx-font-weight: bold;"
            + "-fx-background-radius: 8;"
            + "-fx-cursor: hand;"
        );
    }

    public Button getPrincipal() {
        return principal;
    }

    public Button getMenu() {
        return menu;
    }
}