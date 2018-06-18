package Entidades;

import Entidades.Categoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-30T10:13:28")
@StaticMetamodel(Produto.class)
public class Produto_ { 

    public static volatile SingularAttribute<Produto, Integer> idProduto;
    public static volatile SingularAttribute<Produto, Double> precoProduto;
    public static volatile SingularAttribute<Produto, Categoria> categoriaIdCategoria;
    public static volatile SingularAttribute<Produto, Integer> quantidadeProduto;
    public static volatile SingularAttribute<Produto, String> nomeProduto;

}