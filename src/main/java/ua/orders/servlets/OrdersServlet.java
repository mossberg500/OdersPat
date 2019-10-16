package ua.orders.servlets;

import ua.orders.entity.Client;
import ua.orders.entity.Good;
import ua.orders.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ua.orders.servlets.ContextListenerServlet.clientsDAOImlp;
import static ua.orders.servlets.ContextListenerServlet.goodsDAOImpl;
import static ua.orders.servlets.ContextListenerServlet.ordersDAOImpl;

public class OrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        String date = req.getParameter("date");
        String clientId = req.getParameter("client");
        String id = req.getParameter("id");
        String[] goodsId = req.getParameterValues("goodsId");
        String[] goodsCheckId = req.getParameterValues("goodsCheckId");
        String[] goodsCount = req.getParameterValues("goodsCount");
        String[] goodsCost = req.getParameterValues("goodsCost");

        String page = "";
        switch (command) {
            case "Ok":
                if (!id.equals("")) {
                    // edit.
                    ordersDAOImpl.add(getNewOrderByParam(id, date, clientId, goodsId, goodsCheckId, goodsCount, goodsCost), true);
                } else if (date != null && !clientId.equals("")) {
                    // new order
                    ordersDAOImpl.add(getNewOrderByParam(null, date, clientId, goodsId, goodsCheckId, goodsCount, goodsCost), false);
                }
                page = "/index.jsp";
                break;
            case "Cancel":
                page = "/index.jsp";
                break;
            case "Edit":
                fillRequiredAttributes(req, resp, id, true);
                page = "/editOrder.jsp";
                break;
            case "New order":
                fillRequiredAttributes(req, resp, id, false);
                page = "/editOrder.jsp";
                break;
            case "Delete":
                if (id != null) {
                    ordersDAOImpl.delete(Integer.parseInt(id));
                }
                page = "/index.jsp";
                break;
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(page);
        rd.forward(req, resp);
    }

    private Order getNewOrderByParam(String id, String date, String clientId, String[] goodsId, String[] goodsCheckId, String[] goodsCount, String[] goodsCost) {
        int orderId = 0;
        if (id != null) {
            orderId = Integer.parseInt(id);
        }
        Order order = new Order(orderId, LocalDate.parse(date), new Client(Integer.parseInt(clientId), "", ""), 0);
        if (goodsCheckId!=null) {
            ArrayList<String> arrListgoodsCheckId = new ArrayList<String>(Arrays.asList(goodsCheckId));
            for (int i = 0; i < goodsId.length; i++) {
                if (arrListgoodsCheckId.contains(goodsId[i])) {
                    order.addGood(new Good(Integer.parseInt(goodsId[i]), "", Double.parseDouble(goodsCost[i]), Double.parseDouble(goodsCount[i])));
                }
            }
        }
        return order;
    }

    private void fillRequiredAttributes(HttpServletRequest req, HttpServletResponse resp, String orderId, boolean isEditOrder) {

        List<Client> clientsList = clientsDAOImlp.getAll(Client.class);
        List<Good> goodsList = goodsDAOImpl.getAll(Good.class);
        if (isEditOrder) {
            Order currentOrder = ordersDAOImpl.getOrderFromDB("orders.id = " + orderId);
            req.setAttribute("date", currentOrder.getDate());
            req.setAttribute("id", orderId);
            // look for & set current client
            for (Client client : clientsList) {
                if (client.getId() == currentOrder.getClient().getId()) {
                    client.setSelected();
                    break;
                }
            }
            // look for & check goods from order to edit
            List<Good> goodsFromCurentOrder = currentOrder.getGoods();
            for (Good goodCurrOrder : goodsFromCurentOrder) {
                for (Good goodAll : goodsList) {
                    if (goodCurrOrder.getId() == goodAll.getId()) {
                        goodAll.setChecked();
                        goodAll.setCost(goodCurrOrder.getCost());
                        goodAll.setCount(goodCurrOrder.getCount());
                    }
                }
            }
        }
        req.setAttribute("lstClients", clientsList);
        req.setAttribute("lstGoods", goodsList);
    }
}