/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOCategoria;
import DAOs.DAOProduto;
import Entidades.Categoria;
import Entidades.Produto;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jaque
 */
@WebServlet(name = "ProdutoServlet", urlPatterns = {"/produto"})
public class ProdutoServlet extends HttpServlet {

    Locale ptBr = new Locale("pt", "BR");
    NumberFormat formatoDinheiro = NumberFormat.getCurrencyInstance(ptBr);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nomeProduto = "";
        String submitCadastro = "";
        int categoriaId = 0;

        try (PrintWriter out = response.getWriter()) {
            submitCadastro = request.getParameter("ok");

            String resultado = "";
            if (submitCadastro == null) {
                //se nao veio do submit é lista
                //só precisa disso se a lista usa servlet, o primeiro jeito que vimos,
                //se sua lista usa JSTL ou scriplet pode ir direto p/ cadastro, sem esse if
                nomeProduto = request.getParameter("nomeProduto");
                if (nomeProduto == null || nomeProduto.equals("")) {
                    resultado = listaProdutosCadastrados();
                } else {
                    resultado = listaProdutoNome(nomeProduto);
                }
            } else {
                //parametros do form
                //aqui pq se passar do if não serão nulos
                
                //tudo que vem do formulario é string, por isso aqui alguns precisam de conversão
                categoriaId = Integer.parseInt(request.getParameter("categoria"));
                nomeProduto = request.getParameter("nome");
                int produtoQuantidade = Integer.parseInt(request.getParameter("quantidade"));
                Double produtoPreco = Double.parseDouble(request.getParameter("preco"));

                DAOProduto daoProduto = new DAOProduto();
                DAOCategoria daoCategoria = new DAOCategoria();
                Produto produto = new Produto();

                //busca a categoria do id selecionado no select do form
                //busca com o listById para criar um objeto de entidade completo, 
                //que é o parâmetro que o set de categoria pede
                Categoria categoria = daoCategoria.listById(categoriaId).get(0);

                //seta informacoes do produto na entidade
                
                //essa tabela nao tem id automatico no banco, então precisa setar
                //para nao pedir p/ usuario no formulario e correr o risco de repetição
                //use a função do dao p/ calcular o id
                produto.setIdProduto(daoProduto.autoIdProduto());
                produto.setNomeProduto(nomeProduto);
                produto.setPrecoProduto(produtoPreco);
                produto.setQuantidadeProduto(produtoQuantidade);
                //seta a categoria do produto, que vai gravar apenas o id como fk no produto  no banco
                //porém, aqui é orientado a objeto, então o categoria é um objeto da entidade categoria
                produto.setCategoriaIdCategoria(categoria);

                //insere o produto no banco
                daoProduto.inserir(produto);
                //faz a busca p/ direcionar p/ uma lista atualizada
                //só se sua lista usa servlet, se for com JSTL ou scriplet é só redirecionar
                resultado = listaProdutosCadastrados();
            }
            request.getSession().setAttribute("resultado", resultado);
            response.sendRedirect(request.getContextPath() + "/paginas/produto.jsp");
        }
    }

    protected String listaProdutoNome(String nomeProduto) {
        DAOProduto produto = new DAOProduto();
        String tabela = "";
        List<Produto> lista = produto.listByNome(nomeProduto);
        for (Produto l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getNomeProduto() + "</td>"
                    + "<td>" + formatoDinheiro.format(l.getPrecoProduto()) + "</td>"
                    + "<td>" + l.getQuantidadeProduto() + "</td>"
                    + "<td>" + l.getCategoriaIdCategoria().getNomeCategoria() + "</td>"
                    + "</tr>";
        }

        return tabela;
    }

    protected String listaProdutosCadastrados() {
        DAOProduto produto = new DAOProduto();
        String tabela = "";
        List<Produto> lista = produto.listInOrderNome();
        for (Produto l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getNomeProduto() + "</td>"
                    + "<td>" + formatoDinheiro.format(l.getPrecoProduto()) + "</td>"
                    + "<td>" + l.getQuantidadeProduto() + "</td>"
                    + "<td>" + l.getCategoriaIdCategoria().getNomeCategoria() + "</td>"
                    + "</tr>";
        }

        return tabela;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("teste doget");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("teste dopost");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
