package com.mycompany.mundowumpus;

import com.mycompany.mundowumpus.controller.JogoController;
import com.mycompany.mundowumpus.model.Agente;
import com.mycompany.mundowumpus.model.EstadoJogo;
import com.mycompany.mundowumpus.model.Mundo;
import com.mycompany.mundowumpus.view.ControlesView;
import com.mycompany.mundowumpus.view.MapaView;
import com.mycompany.mundowumpus.view.MenuView;
import com.mycompany.mundowumpus.view.ResultadoView;
import com.mycompany.mundowumpus.view.SelecaoFaseView;
import com.mycompany.mundowumpus.view.StatusView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MundoWumpus extends Application {

    private Stage stage;

    private Scene scene;

    private JogoController controller;

    private int faseAtual = 1;

    private static final int LARGURA = 900;

    private static final int ALTURA = 600;

    @Override
    public void start(Stage stage) {

        this.stage = stage;

        stage.setTitle("Mundo de Wumpus");

        stage.setMinWidth(1000);

        stage.setMinHeight(700);

        scene = new Scene(
            new BorderPane(),
            LARGURA,
            ALTURA
        );

        stage.setScene(scene);

        mostrarMenu();

        stage.show();

        stage.setMaximized(true);
    }

    // =========================================================
    // MENU PRINCIPAL
    // =========================================================

    private void mostrarMenu() {

        pararPartidaAtual();

        MenuView menuView =
                new MenuView();

        menuView.getJogar().setOnAction(event -> {

            iniciarJogo(1);
        });

        menuView.getEscolherFase().setOnAction(event -> {

            mostrarSelecaoFase();
        });

        menuView.getSair().setOnAction(event -> {

            pararPartidaAtual();

            stage.close();
        });

        scene.setRoot(menuView);
    }

    // =========================================================
    // SELEÇÃO DE FASE
    // =========================================================

    private void mostrarSelecaoFase() {

        pararPartidaAtual();

        SelecaoFaseView selecaoFaseView =
                new SelecaoFaseView();

        selecaoFaseView.getFase1().setOnAction(event -> {

            iniciarJogo(1);
        });

        selecaoFaseView.getFase2().setOnAction(event -> {

            iniciarJogo(2);
        });

        selecaoFaseView.getFase3().setOnAction(event -> {

            iniciarJogo(3);
        });

        selecaoFaseView.getVoltar().setOnAction(event -> {

            mostrarMenu();
        });

        scene.setRoot(selecaoFaseView);
    }

    // =========================================================
    // INICIAR JOGO
    // =========================================================

    private void iniciarJogo(int fase) {

        pararPartidaAtual();

        faseAtual = fase;

        // Cria o mundo
        Mundo mundo =
                new Mundo(fase);

        // Cria o agente
        Agente agente =
                new Agente();

        // Cria as Views
        MapaView mapaView =
                new MapaView();

        StatusView statusView =
                new StatusView();

        ControlesView controlesView =
                new ControlesView();

        // Mostra a fase atual
        statusView.definirFase(fase);

        // Estado inicial
        EstadoJogo estadoInicial =
                new EstadoJogo(
                    agente,
                    mundo,
                    "Partida iniciada.",
                    false,
                    EstadoJogo.Resultado.EM_ANDAMENTO
                );

        // Atualiza mapa
        mapaView.atualizar(
            mundo,
            agente,
            false
        );

        // Atualiza status
        statusView.atualizar(
            estadoInicial
        );

        // =====================================================
        // LAYOUT
        // =====================================================

        BorderPane root = new BorderPane();

        ScrollPane painelStatus = new ScrollPane();

        painelStatus.setContent(statusView);
        painelStatus.setFitToWidth(true);
        painelStatus.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );
        painelStatus.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        painelStatus.setPrefWidth(270);
        painelStatus.setMinWidth(270);
        painelStatus.setMaxWidth(270);

        painelStatus.setStyle(
                "-fx-background: #172027;"
                + "-fx-background-color: #172027;"
        );

        root.setCenter(mapaView);
        root.setRight(painelStatus);
        root.setBottom(controlesView);
        
        scene.setRoot(root);

        // =====================================================
        // CONTROLLER
        // =====================================================

        controller =
                new JogoController(
                    mundo,
                    agente,
                    mapaView,
                    statusView
                );

        // =====================================================
        // QUANDO A PARTIDA TERMINAR
        // =====================================================

        controller.definirAoFinalizar(() -> {

            mostrarResultado(
                controller.getEstadoAtual()
            );
        });

        // =====================================================
        // PAUSAR / CONTINUAR
        // =====================================================

        controlesView
            .getPausar()
            .setOnAction(event -> {

                if (controller.estaPausado()) {

                    controller.continuar();

                    controlesView
                        .getPausar()
                        .setText("⏸ PAUSAR");

                } else {

                    controller.pausar();

                    controlesView
                        .getPausar()
                        .setText("▶ CONTINUAR");
                }
            });

        // =====================================================
        // REINICIAR
        // =====================================================

        controlesView
            .getReiniciar()
            .setOnAction(event -> {

                iniciarJogo(faseAtual);
            });

        // =====================================================
        // MENU
        // =====================================================

        controlesView
            .getMenu()
            .setOnAction(event -> {

                mostrarMenu();
            });

        // =====================================================
        // INICIA A PARTIDA
        // =====================================================

        controller.iniciar();
    }

    // =========================================================
    // TELA DE RESULTADO
    // =========================================================

    private void mostrarResultado(EstadoJogo estado) {

    pararPartidaAtual();

    boolean ultimaFase =
            faseAtual == Mundo.QUANTIDADE_FASES;

    ResultadoView resultadoView =
            new ResultadoView(
                estado,
                ultimaFase
            );

    resultadoView.getPrincipal().setOnAction(event -> {

        if (estado.getResultado()
                == EstadoJogo.Resultado.VITORIA) {

            if (ultimaFase) {

                mostrarMenu();

            } else {

                iniciarJogo(faseAtual + 1);
            }

        } else {

            iniciarJogo(faseAtual);
        }
    });

    resultadoView.getMenu().setOnAction(event -> {
        mostrarMenu();
    });

    scene.setRoot(resultadoView);
}

    // =========================================================
    // PARAR PARTIDA
    // =========================================================

    private void pararPartidaAtual() {

        if (controller != null) {

            controller.parar();

            controller = null;
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        launch(args);
    }
}