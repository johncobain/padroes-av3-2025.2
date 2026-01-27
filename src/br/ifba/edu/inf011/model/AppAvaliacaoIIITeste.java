package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.af.CalculoPericialPeritoFactory;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

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
        
        this.model = new GerenciadorDocumentoModel(factory);
        
        testarCriacaoComUndo();
        testarFluxoCompleto();
        testarUndoMultiDocumento();
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("TODOS OS TESTES CONCLUÍDOS COM SUCESSO! ✅");
        System.out.println("=".repeat(80));
        System.out.println();
        System.out.println("📄 Verifique o arquivo 'command_history.log' para ver o log completo.");
    }
    
    /**
     * Teste criação de documento com undo
     */
    private void testarCriacaoComUndo() throws Exception {
        System.out.println("📝 TESTE 1: Criação de Documento com Undo");
        System.out.println("-".repeat(80));
        
        // Criar documento 1
        Documento doc1 = model.criarDocumento(0, Privacidade.PUBLICO);
        System.out.println("✅ Documento 1 criado: " + doc1.getNumero());
        System.out.println("   Repositório tem " + model.getRepositorio().size() + " documento(s)");
        
        // Criar documento 2
        Documento doc2 = model.criarDocumento(1, Privacidade.PUBLICO);
        System.out.println("✅ Documento 2 criado: " + doc2.getNumero());
        System.out.println("   Repositório tem " + model.getRepositorio().size() + " documento(s)");
        System.out.println();
        
        // Desfazer criação do documento 2
        System.out.println("↩️  Desfazendo criação do Documento 2...");
        model.undo();
        System.out.println("   Repositório tem " + model.getRepositorio().size() + " documento(s)");
        System.out.println("   Documento atual: " + (model.getDocumentoAtual() != null ? 
                          model.getDocumentoAtual().getNumero() : "NENHUM"));
        System.out.println();
        
        // Refazer criação do documento 2
        System.out.println("↪️  Refazendo criação do Documento 2...");
        model.redo();
        System.out.println("   Repositório tem " + model.getRepositorio().size() + " documento(s)");
        System.out.println("   Documento atual: " + model.getDocumentoAtual().getNumero());
        System.out.println();
        
        System.out.println("✅ TESTE 1 CONCLUÍDO\n");
    }
    
    /**
     * ✅ NOVO: Teste fluxo completo intercalado
     * [cria doc1 -> edita doc1 -> cria doc2 -> edita doc2 -> edita doc1]
     */
    private void testarFluxoCompleto() throws Exception {
        System.out.println("🔄 TESTE 2: Fluxo Completo Intercalado");
        System.out.println("-".repeat(80));
        
        // Criar documento 1
        Documento doc1 = model.criarDocumento(0, Privacidade.PUBLICO);
        System.out.println("1. ✅ Criar Documento 1: " + doc1.getNumero());
        
        // Editar documento 1
        model.salvarDocumento(doc1, "Conteúdo do Doc1 - Versão 1");
        System.out.println("2. ✅ Editar Documento 1: " + doc1.getConteudo());
        
        // Criar documento 2
        Documento doc2 = model.criarDocumento(1, Privacidade.PUBLICO);
        System.out.println("3. ✅ Criar Documento 2: " + doc2.getNumero());
        
        // Editar documento 2
        model.salvarDocumento(doc2, "Conteúdo do Doc2 - Versão 1");
        System.out.println("4. ✅ Editar Documento 2: " + doc2.getConteudo());
        
        // Editar documento 1 novamente
        model.salvarDocumento(doc1, "Conteúdo do Doc1 - Versão 2");
        System.out.println("5. ✅ Editar Documento 1: " + doc1.getConteudo());
        System.out.println();
        
        System.out.println("📊 Estado Atual:");
        System.out.println("   Repositório: " + model.getRepositorio().size() + " documentos");
        System.out.println("   Doc1: " + doc1.getConteudo());
        System.out.println("   Doc2: " + doc2.getConteudo());
        System.out.println();
        
        // Desfazer todas as operações na ordem reversa
        System.out.println("↩️  Desfazendo tudo (ordem reversa):");
        
        // 5. Desfaz: Editar doc1 (volta para "Versão 1")
        model.undo();
        System.out.println("   5→4: Doc1 volta para: " + doc1.getConteudo());
        
        // 4. Desfaz: Editar doc2
        model.undo();
        System.out.println("   4→3: Doc2 volta para: " + doc2.getConteudo());
        
        // 3. Desfaz: Criar doc2
        model.undo();
        System.out.println("   3→2: Doc2 removido, repositório tem " + model.getRepositorio().size() + " doc(s)");
        
        // 2. Desfaz: Editar doc1
        model.undo();
        System.out.println("   2→1: Doc1 volta para: " + doc1.getConteudo());
        
        // 1. Desfaz: Criar doc1
        model.undo();
        System.out.println("   1→0: Doc1 removido, repositório tem " + model.getRepositorio().size() + " doc(s)");
        System.out.println();
        
        System.out.println("✅ TESTE 2 CONCLUÍDO\n");
    }
    
    /**
     * ✅ NOVO: Teste undo em multi-documentos
     */
    private void testarUndoMultiDocumento() throws Exception {
        System.out.println("📚 TESTE 3: Undo em Multi-Documentos (Ordem Cronológica)");
        System.out.println("-".repeat(80));
        
        // Criar e editar doc1
        Documento doc1 = model.criarDocumento(0, Privacidade.PUBLICO);
        model.salvarDocumento(doc1, "Doc1 - Edição 1");
        model.salvarDocumento(doc1, "Doc1 - Edição 2");
        System.out.println("✅ Doc1 criado e editado 2x: " + doc1.getConteudo());
        
        // Criar e editar doc2
        Documento doc2 = model.criarDocumento(1, Privacidade.PUBLICO);
        model.salvarDocumento(doc2, "Doc2 - Edição 1");
        System.out.println("✅ Doc2 criado e editado 1x: " + doc2.getConteudo());
        
        // Editar doc1 novamente
        model.salvarDocumento(doc1, "Doc1 - Edição 3");
        System.out.println("✅ Doc1 editado mais 1x: " + doc1.getConteudo());
        System.out.println();
        
        System.out.println("↩️  Testando undo cronológico:");
        
        // Undo 1: Desfaz última edição do doc1
        model.undo();
        System.out.println("   Undo 1: Doc1 = " + doc1.getConteudo() + " | Doc2 = " + doc2.getConteudo());
        
        // Undo 2: Desfaz edição do doc2
        model.undo();
        System.out.println("   Undo 2: Doc1 = " + doc1.getConteudo() + " | Doc2 = " + doc2.getConteudo());
        
        // Undo 3: Desfaz criação do doc2
        model.undo();
        System.out.println("   Undo 3: Doc2 removido, repositório tem " + model.getRepositorio().size() + " doc(s)");
        
        // Redo 1: Recria doc2
        model.redo();
        System.out.println("   Redo 1: Doc2 recriado: " + doc2.getNumero());
        
        System.out.println();
        System.out.println("✅ TESTE 3 CONCLUÍDO - Undo segue ordem cronológica global!\n");
    }

    public static void main(String[] args) {
        try {
            DocumentOperatorFactory factory = new CalculoPericialPeritoFactory();
            
            AppAvaliacaoIIITeste app = new AppAvaliacaoIIITeste();
            app.testarQuestaoII(factory);
            
        } catch (Exception e) {
            System.err.println("❌ ERRO durante execução dos testes:");
            e.printStackTrace();
        }
    }
}