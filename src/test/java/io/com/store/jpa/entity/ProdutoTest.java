package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.dao.CategoriaDAO;
import io.com.store.jpa.dao.PedidoDAO;
import io.com.store.jpa.dao.ProdutoDAO;
import io.com.store.jpa.dao.repository.CategoriaRepository;
import io.com.store.jpa.dao.repository.ProdutoRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoTest extends TestCase {

    public void testDeveriaCadastarProduto() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("store-jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.close();

        assertNotNull(produto.getId());
    }

    public void testDeveriaCadastarProdutoUsandoProdutoDAO() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        em.getTransaction().begin();
        produtoRepository.salvar(produto);
        em.getTransaction().commit();
        em.close();

        assertNotNull(produto.getId());
    }

    public void testDeveriaCadastarProdutoComCategoria() {

        Categoria smartphone = new Categoria("SMARTPHONE");


        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .categoria(smartphone)
                .build();


        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        CategoriaRepository categoriaRepository = new CategoriaDAO(em);

        em.getTransaction().begin();
        categoriaRepository.salvar(smartphone);
        produtoRepository.salvar(produto);
        em.getTransaction().commit();
        em.close();

        assertNotNull(produto.getId());
    }

    public void testCicloDeVidaJPA() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("store-jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        //IRÁ INSERIR UM NOVO REGISTRO NO BANCO DE DADOS (INSERT)
        entityManager.persist(produto);

        //IRÁ FAZER UM UPDATE NO BANCO DE DADOS (UPDATE)
        produto.setNome("GALAXY S21+ ULTRA");

        entityManager.flush();
        entityManager.clear();

        //IRÁ DEVOLVER O OBJETO NO ESTADO MANAGED (SELECT)
        produto = entityManager.merge(produto);

        //IRÁ FAZER UM UPDATE NO BANCO DE DADOS (UPDATE)
        produto.setDescricao("SMARTPHONE SAMSUMG GALAXY S21+ ULTRA 256GB 12GB RAM");
        entityManager.flush();

        //IRÁ COLOCAR NO ESTADO DE DETACHED
        entityManager.clear();

        //IRÁ DEVOLVER O OBJETO NO ESTADO MANAGED (SELECT)
        produto = entityManager.merge(produto);

        //IRÁ COLOCAR O OBJETO NO ESTADO REMOVED (DELETE)
        entityManager.remove(produto);

        //IRÁ FAZER UM COMMIT NO BANCO DE DADOS
        entityManager.flush();

        assertNotNull(produto.getId());
    }

    public void testDeveriaBucarPorId() {

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        em.getTransaction().begin();
        produtoRepository.salvar(produto);

        Produto produtoBD = produtoRepository.buscarPorId(produto.getId());
        em.close();

        assertNotNull(produto);
        assertEquals(produtoBD.getNome(), produto.getNome());
        assertEquals(produtoBD.getDescricao(), produto.getDescricao());
        assertEquals(produtoBD.getPreco(), produto.getPreco());

    }

    public void testDeveriaBucarTodos() {

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.buscarTodos().forEach(pedido -> pedidoDAO.excluir(pedido));

        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        produtoRepository.buscarTodos().forEach(p -> produtoRepository.excluir(p));

        em.flush();
        em.getTransaction().commit();

        Produto galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        Produto galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .build();

        em.getTransaction().begin();
        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);
        em.flush();

        List<Produto> produtos = produtoRepository.buscarTodos();
        em.close();

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

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        Produto galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+ BY NAME")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        Produto galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .build();

        em.getTransaction().begin();
        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);

        List<Produto> produtos = produtoRepository.buscarPorNome("GALAXY S21+ BY NAME");
        em.close();

        assertNotNull(produtos);
        assertEquals(1, produtos.size());

        Produto galaxyS21PlusBD = produtos.get(0);

        assertEquals(galaxyS21PlusBD.getNome(), galaxyS21Plus.getNome());
        assertEquals(galaxyS21PlusBD.getDescricao(), galaxyS21Plus.getDescricao());
        assertEquals(galaxyS21PlusBD.getPreco(), galaxyS21Plus.getPreco());

    }

    public void testDeveriaExcluiurProduto() {

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        Produto galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        Produto galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .build();

        em.getTransaction().begin();
        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);

        produtoRepository.excluir(galaxyS21Plus);

        Produto galaxyS21PlusBD = produtoRepository.buscarPorId(galaxyS21Plus.getId());
        Produto galaxyS21UltraBD = produtoRepository.buscarPorId(galaxyS21Ultra.getId());
        em.close();

        assertNull(galaxyS21PlusBD);
        assertNotNull(galaxyS21UltraBD);
    }

    public void testeDeveriaBuscarProdutoPorCategoria() {

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        Produto galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .nomeCategoria("CELULARES TOP")
                .build();

        Produto galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .nomeCategoria("CELULARES TOP")
                .build();

        Produto macbookPro = ProdutoBuilder.init()
                .nome("MACBOOK PRO")
                .descricao("NOTEBOOK APPLE MACBOOK PRO 16")
                .preco("10000.00")
                .nomeCategoria("INFORMATICA")
                .build();

        em.getTransaction().begin();
        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);
        produtoRepository.salvar(macbookPro);

        List<Produto> produtos = produtoRepository.buscarPorNomeCategoria(galaxyS21Plus.getNomeCategoria());
        em.close();

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

    public void testDeveriaBuscarPrecoDoProdutoComNome() {
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        Produto galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+ MAX TOP")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        Produto galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .build();

        em.getTransaction().begin();
        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);

        BigDecimal preco = produtoRepository.buscarPrecoPorNome(galaxyS21Plus.getNome());
        em.close();

        assertNotNull(preco);
        assertEquals(galaxyS21Plus.getPreco(), preco);
    }

    public void testDeveriaBuscarPrecoDoProdutoComNomeNamedQuery() {

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        em.getTransaction().begin();

        Produto galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+ NAMED QUERY")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        Produto galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .build();

        produtoRepository.salvar(galaxyS21Plus);
        produtoRepository.salvar(galaxyS21Ultra);

        BigDecimal preco = produtoRepository.buscarPrecoPorNomeNamedQuery(galaxyS21Plus.getNome());
        em.close();

        assertNotNull(preco);
        assertEquals(galaxyS21Plus.getPreco(), preco);
    }

    public void testDeveriaSalvarLivro(){

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        em.getTransaction().begin();

        Livro livro = new Livro("BRDDA780808180KV", "", 530);
        produtoRepository.salvar(livro);
        em.close();

        assertNotNull(livro.getId());

    }

    public void testDeveriaSalvarProdutoDeInformatica(){

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        em.getTransaction().begin();

        Informatica celular = new Informatica("SAMSUMG", "Galaxy S21 PLUS Master");
        produtoRepository.salvar(celular);
        em.close();

        assertNotNull(celular.getId());

    }

}
