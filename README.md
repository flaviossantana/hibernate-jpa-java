![](https://www.alura.com.br/assets/api/cursos/persistencia-jpa-introducao-hibernate.svg)
# Persistência com JPA/Hibernate

Java Persistence API – JPA é uma coleção de classes e métodos voltados para armazenar persistentemente as vastas quantidades de dados em um banco de dados.  Com base no JPA vários FrameWorks são desenvolvidos (como o EclipseLink, Hibernet e TopLink) com o objetivo de proporcionar uma interação com um banco de dados relacional, evitando com que o desenvolvedor gaste tempo com o desenvolvimento de códigos voltados para a manipulação dos dados presentes no banco de dados.

![](https://i0.wp.com/www.dfilitto.com.br/wp-content/uploads/2015/06/jpa_provider.png?resize=320%2C163)

Em outras palavras o JPA proporciona meios de armazenar os dados presentes nos objetos implementados no sistema desenvolvido dentro das entidades no banco de dados. A imagem a seguir mostra a arquitetura de nível de classe JPA. Ele exibe as classes e interfaces de JPA.

![](https://i0.wp.com/www.dfilitto.com.br/wp-content/uploads/2015/06/jpa_class_level_architecture.png?resize=320%2C189)

## Hibernate e JPA
- Gavin King começou a pensar em uma maneira de simplificar o código e criou essa biblioteca (Hibernate) e em 2001 fez o lançamento. Mas, se percebermos, ela não tem nada a ver com o Java, ela é uma biblioteca que surgiu no mercado e que ele distribuiu gratuitamente. Assim nasceu o Hibernate, uma biblioteca famosa por fazer persistência no banco de dados como alternativa ao JDBC e ao EJB 2.
- JPA é uma especificação e Hibernate é uma de suas implementações

![](https://1.bp.blogspot.com/-u5_RQw9hGGI/VB0HCo4XOoI/AAAAAAAAB3s/EaCTto93c48/s1600/jpa.png)

## EclipseLink
O EclipseLink é a implementação de referência da JPA. Sempre que surge uma nova versão da JPA, o EclipseLink já está implementando, pois, é nele que são feitos os testes e ele sai com a nova versão da JPA. Ele é a implementação de referência, porém, o Hibernate é a principal, a mais popular no mercado. Por isso, neste curso trabalharemos com o Hibernate como implementação da JPA.
![](https://pbs.twimg.com/media/Ffg4Hg3X0AE49zP?format=png&name=900x900)

## Ciclo de Vida de uma Entidade
- Existem principalmente quatro estados do ciclo de vida do Hibernate:
  - Transitório
  - Persistente
  - Desanexado
  - Removido

![](https://github.com/flaviossantana/hibernate-jpa-java/blob/main/src/main/resources/asset/ciclo-e-vida-jpa.png?raw=true)  

## One to One (@OneToOne)
- A anotação para mapear uma única entidade para uma única outra entidade é @OneToOne.
![](http://www.tidicas.com.br/wp-content/uploads/2021/03/diagram_class_onetoone.png)

## Many to One (@ManyToOne)
- Relação ManyToOne entre entidades: onde uma entidade é referenciada com outra entidade que contém valores únicos. Em bancos de dados relacionais, esses relacionamentos são aplicáveis usando chave estrangeira/chave primária entre as tabelas.
```java
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Categoria categoria;
```

![](http://www.tidicas.com.br/wp-content/uploads/2021/03/diagram_class_manytoone.png)

## One to Many (@OneToMany)
- Neste relacionamento, cada linha de uma entidade é referenciada a muitos registros filho em outra entidade. O importante é que os registros de filhos não podem ter vários pais.
```java
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();
```
![](http://www.tidicas.com.br/wp-content/uploads/2021/03/diagram_class_onetomany.png)

## Many to Many (@ManyToMany)
- O relacionamento muitos para muitos é onde uma ou mais linhas de uma entidade são associadas a mais de uma linha em outra entidade.
![](http://www.tidicas.com.br/wp-content/uploads/2021/03/diagram_class_manytomany.png)

## JPA Query Com Retonro de um NOVO (new) Objeto
```java
    @Override
    public List<RelatorioVendasVO> relatorioDeProdutoQuantidadeEUltimaVenda() {

        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT new io.com.store.jpa.vo.RelatorioVendasVO(prd.nome, SUM(itn.quantidade), MAX(pdd.data)) ");
        jpql.append("FROM Pedido pdd ");
        jpql.append("JOIN pdd.itens itn ");
        jpql.append("JOIN itn.produto prd ");
        jpql.append("GROUP BY prd.nome ");
        jpql.append("ORDER BY SUM(itn.quantidade) DESC ");

        return entityManager
                .createQuery(jpql.toString(), RelatorioVendasVO.class)
                .getResultList();
    }
```

## Evitando Joins descenessários em anotações com ToOne
- Para evitar que o Hibernate faça joins desnecessários, podemos usar a anotação @Fetch(FetchMode.JOIN) para evitar que o Hibernate faça joins desnecessários.
```java
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;
```

## Herança com JPA
- A herança é uma das características mais importantes da orientação a objetos. Ela permite que classes compartilhem atributos e métodos, mas também permite que classes diferentes compartilhem atributos e métodos. A herança é uma das características mais importantes da orientação a objetos. Ela permite que classes compartilhem atributos e métodos, mas também permite que classes diferentes compartilhem atributos e métodos.
### JOINED:
- Cria uma tabela para cada classe concreta, e uma tabela para a classe abstrata. Cada tabela concreta contém os atributos da classe concreta e a chave estrangeira da tabela da classe abstrata. A tabela da classe abstrata contém os atributos da classe abstrata e a chave primária.


- Classe PAI:
```java
@Entity
@Table(name = "produtos")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "produto.buscarPrecoPorNome", query = "SELECT p.preco FROM Produto p WHERE p.nome = :nome")
public class Produto {}
```
- Classe FILHA:
```java
@Entity
@Table(name = "livros")
public class Livro extends Produto {}
```

### SINGLE_TABLE: 
- Cria uma tabela para a classe abstrata e uma coluna para cada classe concreta. A coluna da classe concreta contém o tipo da classe concreta. A tabela da classe abstrata contém os atributos da classe abstrata e a chave primária.


- Classe PAI:
```java
@Entity
@Table(name = "clientes")
@DiscriminatorValue("CLT")
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente {}
``` 
- Classe FILHA:
```java
@Entity
@DiscriminatorValue("VDD")
public class Vendedor extends Cliente {}
```

#### Referências
- https://github.com/flaviossantana/hibernate-jpa-java
- https://dfilitto.com.br/desenvolvimento/jpa-o-que-e-para-que-serve-como-implementar-um-sistema
- https://acervolima.com/ciclo-de-vida-do-hibernate
- https://www.tidicas.com.br/?p=2058

