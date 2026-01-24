# Avaliação III de Padrões de Projeto - 2025.2

## Alunos

- Andrey Gomes da Silva Nascimento
- Gabriel Nascimento Miranda dos Santos

## Detalhamento da Implementação das Questões

### Questão 1

Padrão Utilizado: **_[Strategy](https://refactoring.guru/pt-br/design-patterns/strategy)_**

## Motivo:
Com a aplicação em seu estado inicial, o gerenciamento de diferentes tipos de documentos estava preso diante do uso de múltiplos ifs/elses, então a implementação do padrão Strategy é uma boa escolha para solucionar isso. Pois, com o uso do padrão,
pode-se adicionar novas estratégias de autenticação com modificações mínimas no código, assim guardando o princípio de aberto e fechado (OCP), que antes era violado.

## Identificação e papel das classes:
```text
Cliente:          - GerenciadorDocumentoModel (Define a estratégia desejada) 
Strategy:         - AuthStrategy              (Interface com os contratos para autenticação) 
Context:          - Autenticador              (Quem usa a estratégia definida pelo Cliente) 
ConcreteStrategy: - DefaultAuthStrategy       (Implementação com informações padrão) 
ConcreteStrategy: - CriminalAuthStrategy      (Implementação para documentos criminais) 
ConcreteStrategy: - PessoalAuthStrategy       (Implementação para documentos pessoais) 
ConcreteStrategy: - ExportAuthStrategy        (Implementação baseados na privacidade)
```
### Questão 2

Padrão Utilizado: **_[Command](https://refactoring.guru/pt-br/design-patterns/command)_** e **_[Composite](https://refactoring.guru/pt-br/design-patterns/composite)_**

Motivo:

Identificação e papel das classes:

fluxo de execução do comando EditarConteudoCommand:
1. UI chama: model.salvarDocumento(doc, "novo texto")
2. Model cria: new EditarConteudoCommand(doc, "novo texto")
3. Model executa: doc.getCommandHistory().execute(cmd)
4. CommandHistory chama: cmd.execute()
5. EditarConteudoCommand:
   - Salva: conteudoAnterior = "texto antigo"
   - Aplica: doc.setConteudo("novo texto")
6. CommandHistory: Adiciona cmd à pilha de undo
7. CommandHistory: Registra no log: "EXECUTAR: Editar conteúdo..."



fluxo decorators:
Antes:
repositorio = [documentoOriginal]
atual = documentoOriginal

Execute:
documentoDecorado = new AssinaturaDecorator(documentoOriginal)
repositorio = [documentoDecorado]  ← Substituiu!
atual = documentoDecorado

Undo:
repositorio = [documentoOriginal]  ← Voltou!
atual = documentoOriginal


Macro
Exemplo: Macro "Alterar e Assinar"
1. Execute:
   - Edita conteúdo: "Versão 1" → "Versão 2"
   - Assina: adiciona AssinaturaDecorator

2. Undo (ordem reversa):
   - Primeiro: Remove assinatura (AssinaturaDecorator)
   - Depois: Restaura conteúdo ("Versão 2" → "Versão 1")

Se fizéssemos na ordem normal:
   - Restauraria conteúdo primeiro
   - Depois tentaria remover assinatura
   - PROBLEMA: A assinatura pode referenciar o conteúdo!



Explicações dos métodos chave:

salvarDocumento()

O que faz: Cria EditarConteudoCommand e executa via CommandHistory
Por quê: Transforma edição em comando reversível com log
assinarDocumento()

O que faz: Cria AssinarCommand passando this (o model)
Por quê this?: Comando precisa atualizar repositório (trocar referências)
Detalhe: Decorator envolve documento, precisamos substituir no repositório
undo() / redo()

O que faz: Delega para atual.getCommandHistory()
Por quê no atual?: Cada documento tem histórico próprio
Multi-documento: Undo afeta apenas documento selecionado
macroAlterarEAssinar()

O que faz: Cria MacroCommand com EditarConteudo + Assinar
Por quê útil: Fluxo comum (editar → assinar) vira uma ação
Undo: Desfaz as duas operações de uma vez
atualizarRepositorio()

O que faz: Substitui documento antigo por novo na lista
Por quê necessário: Decorators criam novos objetos
Usado por: Todos os comandos de decorator (Assinar, Proteger, TornarUrgente)