package ua.orders.servlets;

import ua.orders.entity.Good;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ua.orders.servlets.ContextListenerServlet.goodsDAOImpl;

public class GoodsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = req.getParameter("command");
        String name = req.getParameter("name");
        String cost = req.getParameter("cost");
        String id = req.getParameter("id");

        switch (command) {
            case "All Goods":
                List<Good> goodsList = goodsDAOImpl.getAll(Good.class);
                req.setAttribute("lstGoods", goodsList);
                RequestDispatcher rd1 = getServletContext().getRequestDispatcher("/viewgood.jsp");
                rd1.forward(req, resp);
                break;
            case "Add":
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/addgood.jsp");
                rd.forward(req, resp);
                break;
            case "Apply":
                if (id != null) {
                    goodsDAOImpl.update(new Good(Integer.parseInt(id), name, Double.parseDouble(cost)));
                }
                break;
            case "Delete":
                if (id != null) {

                    goodsDAOImpl.delete(Integer.parseInt(id));
                }
                break;
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String cost = req.getParameter("cost");
        if (name != null && cost != null) {
            goodsDAOImpl.add(new Good(name, Double.parseDouble(cost)));
        }
        List<Good> goodsList = goodsDAOImpl.getAll(Good.class);
        req.setAttribute("lstGoods", goodsList);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewgood.jsp");
        rd.forward(req, resp);

    }



    }