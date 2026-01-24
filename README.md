# Avaliação III de Padrões de Projeto - 2025.2

## Alunos

- Andrey Gomes da Silva Nascimento
- Gabriel Nascimento Miranda dos Santos

---

## Detalhamento da Implementação das Questões

---

## **Questão I - Strategy Pattern**

### **Padrão Utilizado:**

**[Strategy](https://refactoring.guru/pt-br/design-patterns/strategy)**

### **Motivo da Escolha:**

Com a aplicação em seu estado inicial, o gerenciamento de diferentes tipos de documentos estava preso a múltiplos `if/else` condicionais, violando o princípio **Open-Closed (OCP)** e dificultando a manutenção e extensão do código.

A implementação do padrão **Strategy** resolve este problema ao:

- **Eliminar condicionais**: Substitui `if/else` por polimorfismo
- **Facilitar extensão**: Novas estratégias podem ser adicionadas sem modificar código existente
- **Respeitar OCP**: Classes abertas para extensão, fechadas para modificação
- **Aumentar testabilidade**: Cada estratégia pode ser testada isoladamente
- **Melhorar legibilidade**: Lógica de autenticação encapsulada em classes específicas

### **Identificação e Papel das Classes:**

```text
┌─────────────────────────────────────────────────────────────────────┐
│                      PADRÃO STRATEGY                                │
├─────────────────────────────────────────────────────────────────────┤
│ Cliente (Client):                                                   │
│   └─ GerenciadorDocumentoModel                                      │
│      • Define qual estratégia usar baseado no tipo de documento     │
│      • Mantém mapa de estratégias (0→Criminal, 1→Pessoal, etc)      │
│                                                                      │
│ Contexto (Context):                                                 │
│   └─ Autenticador                                                   │
│      • Recebe estratégia do cliente                                 │
│      • Executa método autenticar() usando a estratégia definida     │
│      • Delega geração de número para a estratégia                   │
│                                                                      │
│ Estratégia (Strategy):                                              │
│   └─ AutenticadorStrategy (interface)                               │
│      • Define contrato: gerarNumero(Documento)                      │
│      • Todas as estratégias concretas implementam esta interface    │
│                                                                      │
│ Estratégias Concretas (Concrete Strategies):                        │
│   ├─ PadraoStrategy                                                 │
│   │   • Gera: "DOC-" + timestamp                                    │
│   │   • Usado como fallback quando tipo não é reconhecido           │
│   │                                                                  │
│   ├─ CriminalStrategy                                               │
│   │   • Gera: "CRI-" + ano + "-" + hashCode                         │
│   │   • Para documentos de investigação criminal                    │
│   │                                                                  │
│   ├─ PessoalStrategy                                                │
│   │   • Gera: "PES-" + dia_do_ano + "-" + hash_proprietario         │
│   │   • Para documentos pessoais                                    │
│   │                                                                  │
│   └─ ExportacaoStrategy                                             │
│       • Gera: "SECURE-" + hash (sigiloso) ou "PUB-" + hash (público)│
│       • Baseado na privacidade do documento                         │
└─────────────────────────────────────────────────────────────────────┘
```

---

## **Questão II - Command & Composite Pattern**

### **Padrão Utilizado:**

**[Command](https://refactoring.guru/pt-br/design-patterns/command)** e **[Composite](https://refactoring.guru/pt-br/design-patterns/composite)**

### **Motivo da Escolha:**

Para implementar um sistema de edição e autenticação de documentos com fluxos reversíveis e com a possibilidade de aplicar decorators (como assinatura, proteção, urgente), o padrão **Command** permite:

- **Execução reversível**: Comandos podem ser desfeitos (undo)
- **Fluxo controlado**: Execução em blocos com macros
- **Decomposição de operações**: Edição e autenticação como comandos separados

### **Identificação e Papel das Classes:**

```text
┌─────────────────────────────────────────────────────────────────────┐
│                      PADRÃO COMMAND & COMPOSITE                    │
├─────────────────────────────────────────────────────────────────────┤
│ Comando (Command):                                                  │
│   └─ EditarConteudoCommand                                          │
│      • Executa: model.salvarDocumento(doc, "novo texto")         │
│      • Cria: new EditarConteudoCommand(doc, "novo texto")          │
│      • Executa: doc.getCommandHistory().execute(cmd)              │
│      • Chama: cmd.execute()                                        │
│      • Salva: conteudoAnterior = "texto antigo"                   │
│      • Aplica: doc.setConteudo("novo texto")                     │
│      • Adiciona cmd à pilha de undo                              │
│      • Registra no log: "EXECUTAR: Editar conteúdo..."            │
│                                                                      │
│ Comando Decorator (Decorator):                                     │
│   └─ AssinaturaDecorator                                          │
│      • Gera: documentoDecorado = new AssinaturaDecorator(documentoOriginal)│
│      • Substitui: repositorio = [documentoDecorado] ← Substituiu!│
│      • Undo: repositorio = [documentoOriginal] ← Voltou!          │
│                                                                      │
│ Macro (Macro):                                                      │
│   └─ MacroCommand                                                 │
│      • Cria: MacroCommand com EditarConteudo + Assinar            │
│      • Fluxo comum (editar → assinar) vira uma ação               │
│      • Undo: Desfaz as duas operações de uma vez                 │
│                                                                      │
│ Model (Model):                                                      │
│   └─ Cria comandos: Instancia com parâmetros corretos            │
│   └─ Delega execução: Para doc.getCommandHistory().execute()      │
│   └─ Implementa macros: Cria MacroCommands                        │
│   └─ Gerencia repositório: Método atualizarRepositorio() para decorators│
│                                                                      │
│ UI (UI):                                                              │
│   └─ Botões novos: Undo, Redo, Macros, Consolidar                 │
│   └─ Atualiza referências: Após decorators (this.atual = controller.getDocumentoAtual())│
│   └─ Refresh visual: Mostra mudanças ao usuário                   │
│   └─ Tratamento de erros: Try-catch com mensagens amigáveis       │
└─────────────────────────────────────────────────────────────────────┘
```
