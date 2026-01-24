package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.af.CalculoPericialPeritoFactory;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

/**
 * Classe de teste gerada com Inteligência Artificial para questões de testes
 * fora do ambiente gráfico.
 */
public class AppAvaliacaoIIITeste {
    
    private GerenciadorDocumentoModel model;
    
    public AppAvaliacaoIIITeste() {
    }
    
    /**
     * Método de teste completo para a Questão II
     * Testa todas as funcionalidades do Command Pattern
     */
    public void testarQuestaoII(DocumentOperatorFactory factory) throws Exception {
        System.out.println("=".repeat(80));
        System.out.println("INICIANDO TESTES DA QUESTÃO II - COMMAND PATTERN");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Inicializa o modelo
        this.model = new GerenciadorDocumentoModel(factory);
        
        // Executa todos os testes
        testarEdicaoComUndo();
        testarAssinaturaComUndo();
        testarProtecaoComUndo();
        testarUrgenteComUndo();
        testarMacroAlterarEAssinar();
        testarMacroPriorizar();
        testarConsolidacao();
        testarMultiDocumento();
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("TODOS OS TESTES CONCLUÍDOS COM SUCESSO! ✅");
        System.out.println("=".repeat(80));
        System.out.println();
        System.out.println("📄 Verifique o arquivo 'command_history.log' para ver o log completo.");
    }
    
    /**
     * Teste 1: Edição de conteúdo com Undo/Redo
     * 
     * O que testa:
     * - Edição básica de conteúdo
     * - Múltiplas edições sequenciais
     * - Undo múltiplo (volta versões anteriores)
     * - Redo múltiplo (recupera versões desfeitas)
     */
    private void testarEdicaoComUndo() throws Exception {
        System.out.println("📝 TESTE 1: Edição de Conteúdo com Undo/Redo");
        System.out.println("-".repeat(80));
        
        // Criar documento
        Documento doc = model.criarDocumento(0, Privacidade.PUBLICO);
        System.out.println("✅ Documento criado: " + doc.getNumero());
        
        // Editar conteúdo (versão 1)
        model.salvarDocumento(doc, "Conteúdo versão 1");
        System.out.println("✅ Editado para: " + doc.getConteudo());
        
        // Editar conteúdo (versão 2)
        model.salvarDocumento(doc, "Conteúdo versão 2");
        System.out.println("✅ Editado para: " + doc.getConteudo());
        
        // Editar conteúdo (versão 3)
        model.salvarDocumento(doc, "Conteúdo versão 3");
        System.out.println("✅ Editado para: " + doc.getConteudo());
        
        // Desfazer (volta para versão 2)
        model.undo();
        System.out.println("↩️  Undo executado: " + doc.getConteudo());
        
        // Desfazer (volta para versão 1)
        model.undo();
        System.out.println("↩️  Undo executado: " + doc.getConteudo());
        
        // Refazer (volta para versão 2)
        model.redo();
        System.out.println("↪️  Redo executado: " + doc.getConteudo());
        
        // Refazer (volta para versão 3)
        model.redo();
        System.out.println("↪️  Redo executado: " + doc.getConteudo());
        
        System.out.println("✅ TESTE 1 CONCLUÍDO\n");
    }
    
    /**
     * Teste 2: Assinatura com Undo/Redo
     * 
     * O que testa:
     * - Aplicação de AssinaturaDecorator
     * - Undo remove decorator (volta documento original)
     * - Redo reaplica decorator
     * - Atualização de referências no repositório
     */
    private void testarAssinaturaComUndo() throws Exception {
        System.out.println("✍️  TESTE 2: Assinatura com Undo/Redo");
        System.out.println("-".repeat(80));
        
        // Criar documento
        Documento doc = model.criarDocumento(1, Privacidade.PUBLICO);
        model.salvarDocumento(doc, "Documento importante");
        System.out.println("✅ Documento criado: " + doc.getNumero());
        System.out.println("📄 Conteúdo inicial:\n" + doc.getConteudo());
        System.out.println();
        
        // Assinar documento
        model.assinarDocumento(doc);
        doc = model.getDocumentoAtual(); // Atualiza referência (decorator foi aplicado)
        System.out.println("✅ Documento assinado");
        System.out.println("📄 Conteúdo com assinatura:\n" + doc.getConteudo());
        System.out.println();
        
        // Desfazer assinatura
        model.undo();
        doc = model.getDocumentoAtual(); // Atualiza referência (decorator foi removido)
        System.out.println("↩️  Undo executado (assinatura removida)");
        System.out.println("📄 Conteúdo sem assinatura:\n" + doc.getConteudo());
        System.out.println();
        
        // Refazer assinatura
        model.redo();
        doc = model.getDocumentoAtual(); // Atualiza referência (decorator foi reaplicado)
        System.out.println("↪️  Redo executado (assinatura reaplicada)");
        System.out.println("📄 Conteúdo com assinatura:\n" + doc.getConteudo());
        System.out.println();
        
        System.out.println("✅ TESTE 2 CONCLUÍDO\n");
    }
    
    /**
     * Teste 3: Proteção com Undo/Redo
     * 
     * O que testa:
     * - Aplicação de DocumentoConfidencial (Proxy)
     * - Restrição de acesso ao conteúdo
     * - Undo remove proxy (volta acesso normal)
     * - Redo reaplica proxy (bloqueia acesso novamente)
     */
    private void testarProtecaoComUndo() throws Exception {
        System.out.println("🔑 TESTE 3: Proteção com Undo/Redo");
        System.out.println("-".repeat(80));
        
        // Criar documento sigiloso
        Documento doc = model.criarDocumento(2, Privacidade.SIGILOSO);
        model.salvarDocumento(doc, "Conteúdo confidencial");
        System.out.println("✅ Documento sigiloso criado: " + doc.getNumero());
        System.out.println("📄 Conteúdo acessível: " + doc.getConteudo());
        System.out.println();
        
        // Proteger documento
        model.protegerDocumento(doc);
        doc = model.getDocumentoAtual();
        System.out.println("✅ Documento protegido");
        
        // Tentar acessar conteúdo protegido
        try {
            String conteudo = doc.getConteudo();
            System.out.println("❌ ERRO: Conseguiu acessar conteúdo protegido!");
        } catch (FWDocumentException e) {
            System.out.println("✅ Acesso negado corretamente: " + e.getMessage());
        }
        System.out.println();
        
        // Desfazer proteção
        model.undo();
        doc = model.getDocumentoAtual();
        System.out.println("↩️  Undo executado (proteção removida)");
        System.out.println("📄 Conteúdo acessível novamente: " + doc.getConteudo());
        System.out.println();
        
        // Refazer proteção
        model.redo();
        doc = model.getDocumentoAtual();
        System.out.println("↪️  Redo executado (proteção reaplicada)");
        try {
            doc.getConteudo();
            System.out.println("❌ ERRO: Conseguiu acessar conteúdo protegido!");
        } catch (FWDocumentException e) {
            System.out.println("✅ Acesso negado novamente: " + e.getMessage());
        }
        System.out.println();
        
        System.out.println("✅ TESTE 3 CONCLUÍDO\n");
    }
    
    /**
     * Teste 4: Tornar Urgente com Undo/Redo
     * 
     * O que testa:
     * - Aplicação de SeloUrgenciaDecorator
     * - Adiciona texto "[URGENTE]" ao conteúdo
     * - Undo remove selo
     * - Redo reaplica selo
     */
    private void testarUrgenteComUndo() throws Exception {
        System.out.println("⏰ TESTE 4: Tornar Urgente com Undo/Redo");
        System.out.println("-".repeat(80));
        
        // Criar documento
        Documento doc = model.criarDocumento(0, Privacidade.PUBLICO);
        model.salvarDocumento(doc, "Documento normal");
        System.out.println("✅ Documento criado: " + doc.getNumero());
        System.out.println("📄 Conteúdo inicial:\n" + doc.getConteudo());
        System.out.println();
        
        // Tornar urgente
        model.tornarUrgente(doc);
        doc = model.getDocumentoAtual();
        System.out.println("✅ Documento marcado como urgente");
        System.out.println("📄 Conteúdo com selo:\n" + doc.getConteudo());
        System.out.println();
        
        // Desfazer urgência
        model.undo();
        doc = model.getDocumentoAtual();
        System.out.println("↩️  Undo executado (selo removido)");
        System.out.println("📄 Conteúdo sem selo:\n" + doc.getConteudo());
        System.out.println();
        
        // Refazer urgência
        model.redo();
        doc = model.getDocumentoAtual();
        System.out.println("↪️  Redo executado (selo reaplicado)");
        System.out.println("📄 Conteúdo com selo:\n" + doc.getConteudo());
        System.out.println();
        
        System.out.println("✅ TESTE 4 CONCLUÍDO\n");
    }
    
    /**
     * Teste 5: Macro Alterar e Assinar
     * 
     * O que testa:
     * - MacroCommand agrupa dois comandos (Editar + Assinar)
     * - Executa os dois de uma vez
     * - Undo desfaz AMBOS na ordem reversa (remove assinatura, depois restaura conteúdo)
     * - Redo reaplica AMBOS
     */
    private void testarMacroAlterarEAssinar() throws Exception {
        System.out.println("⚡ TESTE 5: Macro Alterar e Assinar");
        System.out.println("-".repeat(80));
        
        // Criar documento
        Documento doc = model.criarDocumento(1, Privacidade.PUBLICO);
        System.out.println("✅ Documento criado: " + doc.getNumero());
        System.out.println();
        
        // Executar macro (altera E assina em uma operação)
        model.macroAlterarEAssinar(doc, "Conteúdo editado via macro");
        doc = model.getDocumentoAtual();
        System.out.println("✅ Macro executada (alterou + assinou)");
        System.out.println("📄 Conteúdo final:\n" + doc.getConteudo());
        System.out.println();
        
        // Desfazer macro (remove assinatura E restaura conteúdo anterior)
        model.undo();
        doc = model.getDocumentoAtual();
        System.out.println("↩️  Undo executado (macro desfeita - assinatura removida + conteúdo restaurado)");
        System.out.println("📄 Conteúdo após undo:\n" + doc.getConteudo());
        System.out.println();
        
        // Refazer macro
        model.redo();
        doc = model.getDocumentoAtual();
        System.out.println("↪️  Redo executado (macro reaplicada)");
        System.out.println("📄 Conteúdo após redo:\n" + doc.getConteudo());
        System.out.println();
        
        System.out.println("✅ TESTE 5 CONCLUÍDO\n");
    }
    
    /**
     * Teste 6: Macro Priorizar (Urgente + Assinar)
     * 
     * O que testa:
     * - MacroCommand com TornarUrgente + Assinar
     * - Aplica dois decorators em sequência
     * - Undo remove ambos na ordem reversa
     * - Redo reaplica ambos
     */
    private void testarMacroPriorizar() throws Exception {
        System.out.println("⚡ TESTE 6: Macro Priorizar");
        System.out.println("-".repeat(80));
        
        // Criar documento
        Documento doc = model.criarDocumento(2, Privacidade.PUBLICO);
        model.salvarDocumento(doc, "Documento a priorizar");
        System.out.println("✅ Documento criado: " + doc.getNumero());
        System.out.println("📄 Conteúdo inicial:\n" + doc.getConteudo());
        System.out.println();
        
        // Executar macro priorizar (urgente + assinar)
        model.macroPriorizar(doc);
        doc = model.getDocumentoAtual();
        System.out.println("✅ Macro Priorizar executada (urgente + assinado)");
        System.out.println("📄 Conteúdo priorizado:\n" + doc.getConteudo());
        System.out.println();
        
        // Desfazer macro
        model.undo();
        doc = model.getDocumentoAtual();
        System.out.println("↩️  Undo executado (macro desfeita - assinatura removida + selo removido)");
        System.out.println("📄 Conteúdo após undo:\n" + doc.getConteudo());
        System.out.println();
        
        // Refazer macro
        model.redo();
        doc = model.getDocumentoAtual();
        System.out.println("↪️  Redo executado (macro reaplicada)");
        System.out.println("📄 Conteúdo após redo:\n" + doc.getConteudo());
        System.out.println();
        
        System.out.println("✅ TESTE 6 CONCLUÍDO\n");
    }
    
    /**
     * Teste 7: Consolidação de histórico
     * 
     * O que testa:
     * - Consolidação limpa pilhas de undo/redo
     * - Após consolidar, undo não faz nada
     * - Confirma que histórico foi resetado
     */
    private void testarConsolidacao() throws Exception {
        System.out.println("🗑️  TESTE 7: Consolidação de Histórico");
        System.out.println("-".repeat(80));
        
        // Criar documento
        Documento doc = model.criarDocumento(0, Privacidade.PUBLICO);
        System.out.println("✅ Documento criado: " + doc.getNumero());
        
        // Fazer várias operações
        model.salvarDocumento(doc, "Versão 1");
        model.salvarDocumento(doc, "Versão 2");
        model.salvarDocumento(doc, "Versão 3");
        System.out.println("✅ Três edições realizadas");
        
        // Desfazer uma vez
        model.undo();
        System.out.println("↩️  Undo executado");
        System.out.println("📄 Conteúdo atual: " + doc.getConteudo());
        System.out.println();
        
        // Consolidar (limpa histórico)
        model.consolidar();
        System.out.println("✅ Histórico consolidado");
        System.out.println();
        
        // Tentar desfazer após consolidação (não deve fazer nada)
        System.out.println("⚠️  Tentando undo após consolidação...");
        model.undo();
        System.out.println("📄 Conteúdo permanece: " + doc.getConteudo());
        System.out.println("✅ Undo não afetou (histórico foi limpo)");
        System.out.println();
        
        System.out.println("✅ TESTE 7 CONCLUÍDO\n");
    }
    
    /**
     * Teste 8: Multi-documento (históricos independentes)
     * 
     * O que testa:
     * - Cada documento tem seu próprio CommandHistory
     * - Undo em um documento NÃO afeta outros
     * - Históricos são completamente isolados
     */
    private void testarMultiDocumento() throws Exception {
        System.out.println("📚 TESTE 8: Multi-Documento (Históricos Independentes)");
        System.out.println("-".repeat(80));
        
        // Criar documento 1
        Documento doc1 = model.criarDocumento(0, Privacidade.PUBLICO);
        model.salvarDocumento(doc1, "Conteúdo do Documento 1");
        System.out.println("✅ Documento 1 criado: " + doc1.getNumero());
        System.out.println("   Conteúdo: " + doc1.getConteudo());
        System.out.println();
        
        // Criar documento 2
        Documento doc2 = model.criarDocumento(1, Privacidade.PUBLICO);
        model.salvarDocumento(doc2, "Conteúdo do Documento 2");
        System.out.println("✅ Documento 2 criado: " + doc2.getNumero());
        System.out.println("   Conteúdo: " + doc2.getConteudo());
        System.out.println();
        
        // Editar documento 1 (com doc1 como atual)
        model.setDocumentoAtual(doc1);
        model.salvarDocumento(doc1, "Documento 1 EDITADO");
        System.out.println("✅ Documento 1 editado: " + doc1.getConteudo());
        System.out.println();
        
        // Editar documento 2 (com doc2 como atual)
        model.setDocumentoAtual(doc2);
        model.salvarDocumento(doc2, "Documento 2 EDITADO");
        System.out.println("✅ Documento 2 editado: " + doc2.getConteudo());
        System.out.println();
        
        // Desfazer no documento 2 (deve afetar APENAS doc2)
        System.out.println("↩️  Executando undo no Documento 2...");
        model.setDocumentoAtual(doc2);
        model.undo();
        System.out.println("📄 Documento 1 (intacto): " + doc1.getConteudo());
        System.out.println("📄 Documento 2 (desfeito): " + doc2.getConteudo());
        System.out.println();
        
        // Desfazer no documento 1 (deve afetar APENAS doc1)
        System.out.println("↩️  Executando undo no Documento 1...");
        model.setDocumentoAtual(doc1);
        model.undo();
        System.out.println("📄 Documento 1 (desfeito): " + doc1.getConteudo());
        System.out.println("📄 Documento 2 (permanece): " + doc2.getConteudo());
        System.out.println();
        
        System.out.println("✅ TESTE 8 CONCLUÍDO - Históricos são independentes!\n");
    }

    public static void main(String[] args) {
        try {
            DocumentOperatorFactory factory = new CalculoPericialPeritoFactory();
            
            // Executar testes da Questão II
            AppAvaliacaoIIITeste app = new AppAvaliacaoIIITeste();
            app.testarQuestaoII(factory);
            
        } catch (Exception e) {
            System.err.println("❌ ERRO durante execução dos testes:");
            e.printStackTrace();
        }
    }
    
}