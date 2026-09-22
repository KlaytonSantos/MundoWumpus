package com.mycompany.mundowumpus.view;

import com.mycompany.mundowumpus.model.Agente;
import com.mycompany.mundowumpus.model.Mundo;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapaView extends GridPane {

    private static final int TAMANHO_CELULA = 90;

    public MapaView() {

        setHgap(5);
        setVgap(5);

        setAlignment(Pos.CENTER);

        setStyle(
                "-fx-padding: 15;"
        );
    }

    public void atualizar(
            Mundo mundo,
            Agente agente,
            boolean partidaTerminada) {

        getChildren().clear();

        for (int linha = 0;
                linha < Mundo.TAMANHO;
                linha++) {

            for (int coluna = 0;
                    coluna < Mundo.TAMANHO;
                    coluna++) {

                StackPane celula = criarCelula(
                        mundo,
                        agente,
                        linha,
                        coluna,
                        partidaTerminada
                );

                add(
                        celula,
                        coluna,
                        linha
                );
            }
        }
    }

    private StackPane criarCelula(
            Mundo mundo,
            Agente agente,
            int linha,
            int coluna,
            boolean partidaTerminada) {

        Rectangle fundo =
                new Rectangle(
                        TAMANHO_CELULA,
                        TAMANHO_CELULA
                );

        fundo.setArcWidth(12);
        fundo.setArcHeight(12);

        String simbolo = "";

        boolean agenteEstaAqui =
                agente.getLinha() == linha
                && agente.getColuna() == coluna;

        boolean casaConhecida =
                agente.foiVisitada(
                        linha,
                        coluna
                );

        char elemento =
                mundo.getElemento(
                        linha,
                        coluna
                );

        /*
         * AGENTE
         */
        if (agenteEstaAqui) {

            fundo.setFill(
                    Color.web("#4A90E2")
            );

            fundo.setStroke(
                    Color.web("#1B4F72")
            );

            fundo.setStrokeWidth(3);

            simbolo = "🧍";

        /*
         * PARTIDA TERMINADA
         *
         * Revelamos o mapa inteiro.
         */
        } else if (partidaTerminada) {

            configurarCelulaRevelada(
                    fundo,
                    elemento
            );

            simbolo =
                    obterSimboloElemento(
                            elemento
                    );

        /*
         * CASA JÁ EXPLORADA
         */
        } else if (casaConhecida) {

            configurarCelulaVisitada(
                    fundo,
                    elemento
            );

            simbolo =
                    obterSimboloElemento(
                            elemento
                    );

        /*
         * CASA DESCONHECIDA
         */
        } else {

            fundo.setFill(
                    Color.web("#263238")
            );

            fundo.setStroke(
                    Color.web("#455A64")
            );

            simbolo = "❔";
        }

        Label texto =
                new Label(simbolo);

        texto.setStyle(
                "-fx-font-size: 30px;"
        );

        StackPane celula =
                new StackPane();

        celula.setPrefSize(
                TAMANHO_CELULA,
                TAMANHO_CELULA
        );

        celula.getChildren().addAll(
                fundo,
                texto
        );

        return celula;
    }

    /*
     * Configuração de uma casa que já foi visitada.
     */
    private void configurarCelulaVisitada(
            Rectangle fundo,
            char elemento) {

        switch (elemento) {

            case Mundo.POCO:

                fundo.setFill(
                        Color.web("#5D4037")
                );

                fundo.setStroke(
                        Color.web("#3E2723")
                );

                break;

            case Mundo.WUMPUS:

                fundo.setFill(
                        Color.web("#7B1FA2")
                );

                fundo.setStroke(
                        Color.web("#4A148C")
                );

                break;

            case Mundo.OURO:

                fundo.setFill(
                        Color.web("#F9A825")
                );

                fundo.setStroke(
                        Color.web("#F57F17")
                );

                break;

            default:

                fundo.setFill(
                        Color.web("#ECEFF1")
                );

                fundo.setStroke(
                        Color.web("#90A4AE")
                );
        }

        fundo.setStrokeWidth(2);
    }

    /*
     * Configuração utilizada quando a partida terminou.
     *
     * Nesse momento todo o mapa pode ser revelado.
     */
    private void configurarCelulaRevelada(
            Rectangle fundo,
            char elemento) {

        configurarCelulaVisitada(
                fundo,
                elemento
        );
    }

    private String obterSimboloElemento(
            char elemento) {

        switch (elemento) {

            case Mundo.POCO:
                return "🕳️";

            case Mundo.WUMPUS:
                return "👹";

            case Mundo.OURO:
                return "💰";

            default:
                return "";
        }
    }
}