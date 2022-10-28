package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.CategoriaRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import io.com.store.jpa.entity.categoria.Categoria;
import io.com.store.jpa.entity.categoria.CategoriaID;
import junit.framework.TestCase;

import javax.persistence.EntityManager;

public class CategoriaDAOTest extends TestCase {

    private EntityManager em;

    @Override
    public void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.em.getTransaction().begin();
    }

    @Override
    protected void tearDown() {
        this.em.getTransaction().commit();
    }


    public void testDeveriaSalvarUmaCategoria() {

        CategoriaRepository categoriaRepository = new CategoriaDAO(em);
        Categoria categoria = new Categoria("ELETRONICOS");
        categoriaRepository.salvar(categoria);

        assertNotNull(categoria.getId());
    }

    public void testDeveriaBuscarACategoriaPorID() {

        CategoriaRepository categoriaRepository = new CategoriaDAO(em);

        Categoria categoria = new Categoria("SMARTPHONES");
        categoriaRepository.salvar(categoria);

        Categoria categoriaBD = categoriaRepository.buscarPorId(new CategoriaID("SMARTPHONES", "DEFAULT"));

        assertEquals(categoriaBD.getId().getNome(), "SMARTPHONES");

    }

    public void testDeveriaInativarCategoria() {

        CategoriaRepository categoriaRepository = new CategoriaDAO(em);

        Categoria categoria = new Categoria("CELULARES");
        categoriaRepository.salvar(categoria);

        Categoria categoriaBD = categoriaRepository.buscarPorId(new CategoriaID("CELULARES", "DEFAULT"));
        categoriaBD.inativar();

        Categoria categoriaInativo = categoriaRepository.buscarPorId(new CategoriaID("CELULARES", "DEFAULT"));


        assertEquals(categoriaInativo.getAtivo(), false);

    }

    public void testDeveriaExcluirUmaCategoria() {
        CategoriaRepository categoriaRepository = new CategoriaDAO(em);

        Categoria categoria = new Categoria("ROUPAS MASCULINAS");
        categoriaRepository.salvar(categoria);

        Categoria categoriaBD = categoriaRepository.buscarPorId(new CategoriaID("ROUPAS MASCULINAS", "DEFAULT"));
        categoriaBD.inativar();

        categoriaRepository.excluir(categoriaBD);

        Categoria categoriaExcluir = categoriaRepository.buscarPorId(new CategoriaID("ROUPAS MASCULINAS", "DEFAULT"));

        assertNull(categoriaExcluir);
    }
}
