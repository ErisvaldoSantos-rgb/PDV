# Sistema de Frente de Caixa - PDV (Ponto de Venda)

Este é um sistema desktop de Ponto de Venda (PDV) desenvolvido em **Java**, utilizando a biblioteca **Swing** para a interface gráfica. O projeto foi estruturado seguindo o padrão arquitetural **MVC (Model-View-Controller)**, garantindo uma separação clara entre a lógica de negócio, a interface do utilizador e o controlo do fluxo da aplicação.

A interface gráfica foi modernizada com uma paleta de cores em tons de azul e cinza claro, cantos arredondados nos componentes e botões estáticos sem efeitos de *hover*, proporcionando um visual profissional, limpo e consistente.

---

## Funcionalidades

- **Autenticação de Operadores:** Tela de login segura com validação em memória (utilizador padrão: `admin` / senha: `123`).
- **Lançamento de Itens:** Introdução de produtos por código e quantidade, com cálculo automático de subtotal.
- **Gestão de Produtos:** Menu para cadastrar novos produtos manualmente e pesquisar produtos existentes por nome.
- **Painel Financeiro em Tempo Real:** Exibição clara do Total da Venda, Valor Pago e cálculo instantâneo do Troco.
- **Fluxo de Caixa Completo:** Opções para iniciar uma Nova Venda, Cancelar a Venda atual ou Finalizar.
- **Emissão de Cupão Fiscal:** Geração automatizada de um cupão fiscal simplificado no terminal de texto após a conclusão de cada venda.
- **Histórico:** Consulta do histórico de vendas realizadas durante a sessão.

---

##  Estrutura do Projeto (MVC)

O projeto está dividido nos seguintes pacotes:

```text
src/
│
├── model/                  # Camada de Entidades (Dados e Regras de Negócio)
│   ├── Operador.java       # Representa o utilizador do sistema (login/senha)
│   ├── Produto.java        # Contém dados do produto (código, preço, stock) e baixa de stock
│   ├── ItemVenda.java      # Associa um produto à quantidade lançada e calcula o subtotal
│   └── Venda.java          # Reúne os itens da venda atual e calcula o valor total
│
├── view/                   # Camada de Interface Gráfica (Swing)
│   ├── TelaLogin.java      # Tela inicial de autenticação com design de cards arredondados
│   └── TelaPDV.java        # Tela principal do operador de caixa, menus e resumo financeiro
│
├── controller/             # Camada de Controlo (Mediação e Eventos)
│   ├── Autenticacao.java   # Gere a lista de operadores permitidos e valida o acesso
│   └── VendaController.java# Coordena o fluxo do PDV, escuta os botões da View e atualiza o Model
│
└── service/                # Camada de Serviços Auxiliares
    └── Caixa.java          # Lógica matemática complementar (ex: cálculo do troco)
