package DAOs;

import static DAOs.DAOGenerico.em;
import Entidades.Categoria;
import java.util.ArrayList;
import java.util.List;

public class DAOCategoria extends DAOGenerico<Categoria> {

    public DAOCategoria() {
        super(Categoria.class);
    }

    public int autoIdCategoria() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idCategoria) FROM Categoria e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Categoria> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Categoria e WHERE e.nomeCategoria LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Categoria> listById(int id) {
        return em.createQuery("SELECT e FROM Categoria e WHERE e.idCategoria = :id").setParameter("id", id).getResultList();
    }

    public List<Categoria> listInOrderNome() {
        return em.createQuery("SELECT e FROM Categoria e ORDER BY e.nomeCategoria").getResultList();
    }

    public List<Categoria> listInOrderId() {
        return em.createQuery("SELECT e FROM Categoria e ORDER BY e.idCategoria").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Categoria> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdCategoria() + "-" + lf.get(i).getNomeCategoria());
        }
        return ls;
    }
}
