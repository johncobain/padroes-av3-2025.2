# Avaliação III de Padrões de Projeto - 2025.2

## Alunos

- Andrey Gomes da Silva Nascimento
- Gabriel Nascimento Miranda dos Santos

## Detalhamento da Implementação das Questões

### Questão 1

Padrão Utilizado: **_[Strategy](https://refactoring.guru/pt-br/design-patterns/strategy)_**

## Motivo:
Com a aplicação em seu estado inicial, o gerenciamento de diferentes tipos de documentos estava preso diante do uso de mútiplos ifs/elses, então a implementação do padrão Strategy é uma boa escolha para solucionar isso. Pois, com o uso do padrão,
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
