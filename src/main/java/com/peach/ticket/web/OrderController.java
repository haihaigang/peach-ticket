package com.peach.ticket.web;

import com.peach.ticket.domain.Order;
import com.peach.ticket.domain.User;
import com.peach.ticket.service.OrderService;
import com.peach.ticket.web.response.ResponseCode;
import com.peach.ticket.web.response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hgwang on 2017/2/13.
 */
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    public ResponseVo remove(@PathVariable Long id) {
        this.orderService.remove(id);

        return new ResponseVo(ResponseCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseVo getOne(@PathVariable Long id) {
        Order order = this.orderService.findOne(id);
        if (order == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到当前的订单");
        }

        return new ResponseVo(order);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseVo findAll(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size,
            @RequestParam(required = false, defaultValue = "0") long userId,
            @RequestParam(required = false, defaultValue = "-1") int status
    ) {
        Page<Order> orders = this.orderService.findAll(page, size ,userId, status);

        return new ResponseVo(orders);
    }

    @RequestMapping(value = "/{id}/confirm", method = RequestMethod.POST)
    public ResponseVo confirm(@PathVariable Long id) {
        boolean isOk = this.orderService.confirm(id);

        if(!isOk){
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "确认订单失败，订单不存在或当前非未支付订单");
        }

        return new ResponseVo(ResponseCode.OK, "确认成功");
    }

    @RequestMapping(value = "/{id}/pay", method = RequestMethod.POST)
    public ResponseVo pay(@PathVariable Long id) {
        boolean isOk = this.orderService.pay(id);

        if(!isOk){
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "支付订单失败，订单不存在或当前订单已支付");
        }

        return new ResponseVo(ResponseCode.OK, "支付成功");
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    public ResponseVo cancel(@PathVariable Long id) {
        boolean isOk = this.orderService.cancel(id);

        if(!isOk){
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "取消订单失败，订单不存在或当前非未支付订单");
        }

        return new ResponseVo(ResponseCode.OK, "取消成功");
    }

    @RequestMapping(value = "/{id}/use", method = RequestMethod.POST)
    public ResponseVo use(@PathVariable Long id) {
        boolean isOk = this.orderService.use(id);

        if(!isOk){
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "使用订单失败，订单不存在或当前已支付订单");
        }

        return new ResponseVo(ResponseCode.OK, "使用成功");
    }

    @RequestMapping(value = "/{id}/invalid", method = RequestMethod.POST)
    public ResponseVo invalid(@PathVariable Long id) {
        boolean isOk = this.orderService.use(id);

        if(!isOk){
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "作废订单失败，订单不存在或当前非已支付的订单");
        }

        return new ResponseVo(ResponseCode.OK, "作废成功");
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseVo submit(
            HttpServletRequest request,
            @RequestParam Long startStationId,
            @RequestParam Long endStationId,
            @RequestParam String fangan,
            @RequestParam String fanganCN
    ) {
        User user =(User)request.getSession().getAttribute("User");

        Order order = new Order();
        order.setStartStationId(startStationId);
        order.setEndStationId(endStationId);
        order.setFangan(fangan);
        order.setFanganCN(fanganCN);
        order.setUserId(user.getId());

        this.orderService.create(order);

        return new ResponseVo(ResponseCode.OK, "订单创建成功");
    }

    @RequestMapping(value = "/findByUser", method = RequestMethod.GET)
    public ResponseVo findAllByUser(
            HttpServletRequest request,
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size,
            @RequestParam(required = false, defaultValue = "-1") int status
    ) {
        User user = (User)request.getSession().getAttribute("User");

        Page<Order> orders = this.orderService.findByUser(page, size, user.getId(), status);

        return new ResponseVo(orders);
    }
}
