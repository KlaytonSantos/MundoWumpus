# 🐉 Mundo Wumpus

Um jogo desenvolvido em **Java** utilizando **JavaFX**, inspirado no clássico problema do **Mundo de Wumpus**, com foco na aplicação de conceitos de **Inteligência Artificial**, lógica de agentes e desenvolvimento de interfaces gráficas.

O projeto foi desenvolvido como parte de uma atividade acadêmica do curso de **Sistemas de Informação**.

## 🎮 Sobre o jogo

No **Mundo Wumpus**, o jogador controla um agente que precisa explorar um ambiente desconhecido em busca do **ouro**, evitando perigos como **poços** e o **Wumpus**.

Durante a exploração, o agente utiliza as percepções encontradas nas casas do mapa para tomar decisões e tentar alcançar o objetivo.

O projeto também possui um sistema de movimentação autônoma, no qual o agente pode explorar o ambiente utilizando sua lógica de decisão.

## 🧠 Conceitos utilizados

O desenvolvimento do projeto envolve conceitos relacionados a:

* Inteligência Artificial
* Agentes inteligentes
* Tomada de decisão
* Busca em ambientes
* Percepção de estados
* Lógica de exploração
* Orientação a objetos
* Desenvolvimento de interfaces gráficas

## ✨ Funcionalidades

Atualmente, o projeto possui:

* 🗺️ Mapa do Mundo Wumpus
* 🤖 Agente com movimentação autônoma
* 🪙 Sistema de coleta de ouro
* 💨 Percepção de brisa próxima aos poços
* 👹 Percepção da presença do Wumpus
* ✨ Percepção do brilho do ouro
* 🏹 Sistema de flecha para enfrentar o Wumpus
* 💀 Sistema de morte ao entrar em um poço ou encontrar o Wumpus
* 🏆 Sistema de vitória
* 📊 Sistema de pontuação
* 🔢 Controle do número de movimentos
* 📍 Atualização da posição do agente durante a exploração

## 🛠️ Tecnologias

| Tecnologia | Utilização                          |
| ---------- | ----------------------------------- |
| Java       | Linguagem principal                 |
| JavaFX     | Interface gráfica                   |
| Maven      | Gerenciamento e execução do projeto |
| Git        | Controle de versão                  |
| GitHub     | Hospedagem do código                |

## 📋 Requisitos

Para executar o projeto, é necessário ter instalado:

* **Java JDK 21**
* **Maven**
* IDE compatível com projetos Java/Maven

O projeto utiliza **JavaFX 21**.

## ▶️ Executando o projeto

Clone o repositório:

```bash
git clone git@github.com:KlaytonSantos/MundoWumpus.git
```

Entre na pasta:

```bash
cd MundoWumpus
```

Execute o projeto utilizando Maven:

```bash
mvn javafx:run
```

Também é possível abrir o projeto diretamente em uma IDE compatível com Maven, como o **NetBeans**.

## 🕹️ Como jogar

O agente pode explorar o mapa de forma autônoma.

Durante a exploração, diferentes percepções indicam possíveis perigos:

| Percepção | Significado                          |
| --------- | ------------------------------------ |
| 💨 Brisa  | Existe um poço em uma casa adjacente |
| 👹 Wumpus | O Wumpus está próximo                |
| ✨ Brilho  | O ouro está na casa atual            |
| 🪙 Ouro   | O agente pode coletar o ouro         |

O objetivo é **encontrar o ouro e retornar à posição inicial**, evitando os perigos existentes no mapa.

## 📁 Estrutura do projeto

A organização do projeto busca separar os principais componentes do jogo, incluindo:

* **Agente** — responsável pelo comportamento e movimentação do personagem.
* **Mundo** — responsável pelo ambiente e elementos do mapa.
* **MapaView** — responsável pela representação visual do mundo.
* **Sistema de pontuação** — controla a pontuação obtida durante a exploração.
* **Elementos do mapa** — representam perigos, ouro e demais componentes do ambiente.

## 🚧 Desenvolvimento

O projeto encontra-se em desenvolvimento e novas funcionalidades podem ser adicionadas ao longo da evolução do jogo.

Entre as possibilidades de expansão estão:

* 🎬 Tela inicial
* 🎯 Seleção de fases
* 🔄 Reinício do jogo
* 📈 Diferentes níveis de dificuldade
* 🗺️ Novos mapas
* 🤖 Aprimoramento da inteligência do agente
* 🎮 Modo de controle manual
* 🏅 Melhorias no sistema de pontuação
* 🎨 Aprimoramentos visuais e de interface

## 👨‍💻 Autor

**Klayton Marcos Corrêa dos Santos**

Estudante de **Sistemas de Informação**.

---

⭐ Projeto desenvolvido para fins acadêmicos e de aprendizado em Java, JavaFX e Inteligência Artificial.
