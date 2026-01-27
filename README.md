# Avaliação III de Padrões de Projeto - 2025.2

## Alunos

- Andrey Gomes da Silva Nascimento
- Gabriel Nascimento Miranda dos Santos
- Lara Carolina Brito dos Santos

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
│                                                                     │
│ Contexto (Context):                                                 │
│   └─ Autenticador                                                   │
│      • Recebe estratégia do cliente                                 │
│      • Executa método autenticar() usando a estratégia definida     │
│      • Delega geração de número para a estratégia                   │
│                                                                     │
│ Estratégia (Strategy):                                              │
│   └─ AutenticadorStrategy (interface)                               │
│      • Define contrato: gerarNumero(Documento)                      │
│      • Todas as estratégias concretas implementam esta interface    │
│                                                                     │
│ Estratégias Concretas (Concrete Strategies):                        │
│   ├─ PadraoStrategy                                                 │
│   │   • Gera: "DOC-" + timestamp                                    │
│   │   • Usado como fallback quando tipo não é reconhecido           │
│   │                                                                 │
│   ├─ CriminalStrategy                                               │
│   │   • Gera: "CRI-" + ano + "-" + hashCode                         │
│   │   • Para documentos de investigação criminal                    │
│   │                                                                 │
│   ├─ PessoalStrategy                                                │
│   │   • Gera: "PES-" + dia_do_ano + "-" + hash_proprietario         │
│   │   • Para documentos pessoais                                    │
│   │                                                                 │
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

Para implementar um sistema de edição e autenticação de documentos com fluxos reversíveis e com a possibilidade de aplicar decorators (como assinatura, proteção, urgente), os padrões **Command** e **Composite** permitem:

- **Execução reversível**: Comandos podem ser desfeitos (undo) e refeitos (redo)
- **Fluxo controlado**: Execução em blocos com macros (Composite)
- **Decomposição de operações**: Edição, assinatura e decoração como comandos separados
- **Histórico completo**: Rastreamento de todas as operações com log persistente
- **Consolidação**: Capacidade de limpar histórico de comandos

### **Identificação e Papel das Classes:**

```text
┌─────────────────────────────────────────────────────────────────────┐
│                      PADRÃO COMMAND                                 │
├─────────────────────────────────────────────────────────────────────┤
│ Command (Interface):                                                │
│   └─ Command                                                        │
│      • Define contrato: execute(), undo(), getDescription()         │
│      • Método default: getDocumentoAfetado() → Para UI              │
│                                                                     │
│ Comandos Concretos (Concrete Commands):                             │
│   ├─ CriarDocumentoCommand                                          │
│   │   • Execute: Adiciona documento ao repositório                  │
│   │   • Undo: Remove documento do repositório                       │
│   │   • getDocumentoAfetado(): Retorna documento criado (execute)   │
│   │                          ou anterior (undo)                     │
│   │                                                                 │
│   ├─ EditarConteudoCommand                                          │
│   │   • Execute: Salva conteúdo anterior e aplica novo conteúdo     │
│   │   • Undo: Restaura conteúdo anterior                            │
│   │   • getDocumentoAfetado(): Retorna documento editado            │
│   │                                                                 │
│   ├─ AssinarCommand                                                 │
│   │   • Execute: Cria AssinaturaDecorator e atualiza repositório    │
│   │   • Undo: Remove AssinaturaDecorator do repositório             │
│   │   • getDocumentoAfetado(): Retorna documento original           │
│   │                                                                 │
│   ├─ ProtegerCommand                                                │
│   │   • Execute: Cria DocumentoConfidencial                         │
│   │   • Undo: Remove proteção do documento                          │
│   │   • getDocumentoAfetado(): Retorna documento original           │
│   │                                                                 │
│   └─ TornarUrgenteCommand                                           │
│       • Execute: Cria SeloUrgenciaDecorator                         │
│       • Undo: Remove SeloUrgenciaDecorator                          │
│       • getDocumentoAfetado(): Retorna documento original           │
│                                                                     │
│ Invoker (Invocador):                                                │
│   └─ CommandHistory                                                 │
│      • Mantém duas pilhas: history (undo) e redoStack (redo)        │
│      • Método execute(): Executa comando e adiciona ao histórico    │
│      • Método undo(): Desfaz último comando da pilha history        │
│      • Método redo(): Refaz último comando da pilha redoStack       │
│      • Método consolidate(): Limpa ambas as pilhas                  │
│      • Métodos de acesso: getLastUndone(), getLastExecuted()        │
│      • Log persistente: Registra todas as operações em arquivo      │
│                                                                     │
│ Receiver (Receptor):                                                │
│   └─ GerenciadorDocumentoModel                                      │
│      • adicionarDocumentoAoRepositorio(): Adiciona ao repositório   │
│      • removerDocumentoDoRepositorio(): Remove do repositório       │
│      • atualizarRepositorio(): Substitui documento (decorators)     │
│      • setDocumentoAtual(): Define documento atual da aplicação     │
└─────────────────────────────────────────────────────────────────────┘
```

``` text
┌─────────────────────────────────────────────────────────────────────┐
│                      PADRÃO COMPOSITE                               │
├─────────────────────────────────────────────────────────────────────┤
│ Component (Componente):                                             │
│   └─ Command (interface)                                            │
│      • Define interface comum para comandos simples e compostos     │
│                                                                     │
│ Composite (Composto):                                               │
│   └─ MacroCommand                                                   │
│      • Mantém lista de comandos (List<Command>)                     │
│      • Execute: Executa todos os comandos na ordem                  │
│      • Undo: Desfaz todos os comandos na ordem reversa              │
│      • getDocumentoAfetado(): Retorna documento do último comando   │
│      • Permite agrupar operações complexas                          │
│                                                                     │
│ Leaf (Folha):                                                       │
│   └─ Todos os comandos concretos                                    │
│      • Implementam operações atômicas                               │
│      • Não têm sub-comandos                                         │
│                                                                     │
│ Macros Implementadas:                                               │
│   ├─ "Alterar e Assinar"                                            │
│   │   • EditarConteudoCommand + AssinarCommand                      │
│   │   • Edita conteúdo e assina o documento em uma operação         │
│   │   • Undo: Desfaz assinatura e depois edição                     │
│   │                                                                 │
│   └─ "Priorizar"                                                    │
│       • TornarUrgenteCommand + AssinarCommand                       │
│       • Marca como urgente e assina o documento                     │
│       • Undo: Desfaz assinatura e depois urgência                   │
└─────────────────────────────────────────────────────────────────────┘
```
