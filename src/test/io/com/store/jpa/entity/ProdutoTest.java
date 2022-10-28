package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.dao.CategoriaDAO;
import io.com.store.jpa.dao.ClienteDAO;
import io.com.store.jpa.dao.PedidoDAO;
import io.com.store.jpa.dao.ProdutoDAO;
import io.com.store.jpa.dao.repository.CategoriaRepository;
import io.com.store.jpa.dao.repository.ClienteRepository;
import io.com.store.jpa.dao.repository.PedidoRepository;
import io.com.store.jpa.dao.repository.ProdutoRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import io.com.store.jpa.entity.categoria.Categoria;
import io.com.store.jpa.entity.produto.Informatica;
import io.com.store.jpa.entity.produto.Livro;
import io.com.store.jpa.entity.produto.Produto;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoTest extends TestCase {

    private EntityManager em;
    private Produto galaxyS21Plus;
    private Produto galaxyS21Ultra;

    @Override
    public void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.em.getTransaction().begin();
        excluirDados();
        criarProdutoPadrao();
    }

    @Override
    protected void tearDown() throws Exception {
        this.em.getTransaction().commit();
    }

    private void excluirDados() {
        PedidoRepository pedidoRepository = new PedidoDAO(em);
        pedidoRepository.buscarTodos().forEach(pedido -> pedidoRepository.excluir(pedido));

        ClienteRepository clienteRepository = new ClienteDAO(em);
        clienteRepository.buscarTodos().forEach(cliente -> clienteRepository.excluir(cliente));

        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        produtoRepository.buscarTodos().forEach(produto -> produtoRepository.excluir(produto));
    }

    private void criarProdutoPadrao() {
        galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .persisted(em)
                .build();
        galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .persisted(em)
                .build();
    }

    public void testDeveriaCadastarProdutoUsandoProdutoDAO() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        produtoRepository.salvar(galaxyS21Plus);
        assertNotNull(galaxyS21Plus.getId());
    }

    public void testDeveriaCadastarProdutoComCategoria() {

        Categoria smartphone = new Categoria("SMARTPHONE");

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .categoria(smartphone)
                .build();

        CategoriaRepository categoriaRepository = new CategoriaDAO(em);
        categoriaRepository.salvar(smartphone);

        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        produtoRepository.salvar(produto);

        assertNotNull(produto.getId());
    }

    public void testDeveriaBucarPorId() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        Produto produtoBD = produtoRepository.buscarPorId(galaxyS21Plus.getId());

        assertNotNull(galaxyS21Plus);
        assertEquals(produtoBD.getNome(), galaxyS21Plus.getNome());
        assertEquals(produtoBD.getDescricao(), galaxyS21Plus.getDescricao());
        assertEquals(produtoBD.getPreco(), galaxyS21Plus.getPreco());
    }

    public void testDeveriaBucarTodos() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);

        List<Produto> produtos = produtoRepository.buscarTodos();

        assertNotNull(produtos);
        assertEquals(2, produtos.size());

        Produto galaxyS21PlusBD = produtos.get(0);

        assertEquals(galaxyS21PlusBD.getNome(), galaxyS21Plus.getNome());
        assertEquals(galaxyS21PlusBD.getDescricao(), galaxyS21Plus.getDescricao());
        assertEquals(galaxyS21PlusBD.getPreco(), galaxyS21Plus.getPreco());

        Produto galaxyS21UltraBD = produtos.get(1);

        assertEquals(galaxyS21UltraBD.getNome(), galaxyS21Ultra.getNome());
        assertEquals(galaxyS21UltraBD.getDescricao(), galaxyS21Ultra.getDescricao());
        assertEquals(galaxyS21UltraBD.getPreco(), galaxyS21Ultra.getPreco());
    }

    public void testDeveriaBucarNome() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);

        List<Produto> produtos = produtoRepository.buscarPorNome(galaxyS21Plus.getNome());

        assertNotNull(produtos);
        assertEquals(1, produtos.size());

        Produto galaxyS21PlusBD = produtos.get(0);

        assertEquals(galaxyS21PlusBD.getNome(), galaxyS21Plus.getNome());
        assertEquals(galaxyS21PlusBD.getDescricao(), galaxyS21Plus.getDescricao());
        assertEquals(galaxyS21PlusBD.getPreco(), galaxyS21Plus.getPreco());

    }

    public void testDeveriaExcluiurProduto() {
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);

        produtoRepository.excluir(galaxyS21Plus);

        Produto galaxyS21PlusBD = produtoRepository.buscarPorId(galaxyS21Plus.getId());
        Produto galaxyS21UltraBD = produtoRepository.buscarPorId(galaxyS21Ultra.getId());

        assertNull(galaxyS21PlusBD);
        assertNotNull(galaxyS21UltraBD);
    }

    public void testeDeveriaBuscarProdutoPorCategoria() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        CategoriaRepository categoriaRepository = new CategoriaDAO(em);
        Categoria celularesTop = new Categoria("CELULARES TOP");
        categoriaRepository.salvar(celularesTop);


        Produto galaxyS21PlusComCategoria = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .categoria(celularesTop)
                .build();

        Produto galaxyS21UltraComCategoria = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .categoria(celularesTop)
                .build();

        Produto macbookPro = ProdutoBuilder.init()
                .nome("MACBOOK PRO")
                .descricao("NOTEBOOK APPLE MACBOOK PRO 16")
                .preco("10000.00")
                .nomeCategoria("INFORMATICA")
                .build();

        produtoRepository.salvar(galaxyS21PlusComCategoria);
        produtoRepository.salvar(galaxyS21UltraComCategoria);
        produtoRepository.salvar(macbookPro);


        List<Produto> produtos = produtoRepository.buscarPorNomeCategoria(galaxyS21PlusComCategoria.getNomeCategoria());

        assertNotNull(produtos);
        assertEquals(2, produtos.size());

        Produto galaxyS21PlusBD = produtos.get(0);

        assertEquals(galaxyS21PlusBD.getNome(), galaxyS21PlusComCategoria.getNome());
        assertEquals(galaxyS21PlusBD.getDescricao(), galaxyS21PlusComCategoria.getDescricao());
        assertEquals(galaxyS21PlusBD.getPreco(), galaxyS21PlusComCategoria.getPreco());

        Produto galaxyS21UltraBD = produtos.get(1);

        assertEquals(galaxyS21UltraBD.getNome(), galaxyS21UltraComCategoria.getNome());
        assertEquals(galaxyS21UltraBD.getDescricao(), galaxyS21UltraComCategoria.getDescricao());
        assertEquals(galaxyS21UltraBD.getPreco(), galaxyS21UltraComCategoria.getPreco());

    }

    public void testDeveriaBuscarPrecoDoProdutoComNome() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        BigDecimal preco = produtoRepository.buscarPrecoPorNome(galaxyS21Plus.getNome());

        assertNotNull(preco);
        assertEquals(galaxyS21Plus.getPreco(), preco);
    }

    public void testDeveriaBuscarPrecoDoProdutoComNomeNamedQuery() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        BigDecimal preco = produtoRepository.buscarPrecoPorNomeNamedQuery(galaxyS21Plus.getNome());

        assertNotNull(preco);
        assertEquals(galaxyS21Plus.getPreco(), preco);
    }

    public void testDeveriaSalvarLivro() {

        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        Livro livro = new Livro("BRDDA780808180KV", "", 530);
        produtoRepository.salvar(livro);

        assertNotNull(livro.getId());
    }

    public void testDeveriaSalvarProdutoDeInformatica(){

        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        Informatica celular = new Informatica("SAMSUMG", "Galaxy S21 PLUS Master");
        produtoRepository.salvar(celular);

        assertNotNull(celular.getId());

    }

}
