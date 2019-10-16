package ua.orders.servlets;

import ua.orders.entity.Client;
import ua.orders.entity.Good;
import ua.orders.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ua.orders.servlets.ContextListenerServlet.clientsDAOImlp;
import static ua.orders.servlets.ContextListenerServlet.goodsDAOImpl;
import static ua.orders.servlets.ContextListenerServlet.ordersDAOImpl;

@WebServlet("/index.jsp")
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Order> ordersList = ordersDAOImpl.getAll();
        List<Good> goods = goodsDAOImpl.getGoods();
        System.out.println("goods.size()" + goods.size());

        if (goods.size() == 0) {
            getSomeDataAtStart();
            ordersList = ordersDAOImpl.getAll();
        }
        req.setAttribute("lstOrders", ordersList);

        List<Good> goodsList = goodsDAOImpl.getAll(Good.class);
        req.setAttribute("lstGoods", goodsList);

        List<Client> clientsList = clientsDAOImlp.getAll(Client.class);
        req.setAttribute("lstClients", clientsList);

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/list.jsp");
        rd.forward(req, resp);
    }

    private void getSomeDataAtStart() {

        Good g1 = new Good(1, "MS-2027K", 1000, 1);
        Good g2 = new Good(2, "IWSC 5085 CIS", 2500, 1);
        Good g3 = new Good(3, "GN-B392CVCA", 1500, 1);
        Good g4 = new Good(4, "GBF 80/UA", 14000, 1);

        goodsDAOImpl.add(g1);
        goodsDAOImpl.add(g2);
        goodsDAOImpl.add(g3);
        goodsDAOImpl.add(g4);

        Client cl1 = new Client("Ivanov Ivan", "380672223344");
        cl1.setId(1);
        Client cl2 = new Client("Petrov V.", "380502223345");
        Client cl3 = new Client("Fedorov Ev.", "380631123346");

        clientsDAOImlp.add(cl1);
        clientsDAOImlp.add(cl2);
        clientsDAOImlp.add(cl3);
    }

}
