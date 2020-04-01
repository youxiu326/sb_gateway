package com.youxiu326.gateway.controller;

import com.youxiu326.gateway.configuration.GatewayServiceHandler;
import com.youxiu326.gateway.model.GatewayRouteDefinition;
import com.youxiu326.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 *  1.直接在数据库添加路由配置信息，手动刷新，使配置信息立即生效；
 *
 *  2.前端页面增、删、改路由配置信息，并使配置信息立即生效；
 *
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private GatewayServiceHandler gatewayServiceHandler;

    @Autowired
    private GatewayRouteService gatewayRouteService;

    /**
     * 刷新路由配置
     * @return
     */
    @GetMapping("/refresh")
    public String refresh() throws Exception {
        return this.gatewayServiceHandler.loadRouteConfig();
    }

    /**
     * 增加路由记录
     *
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestBody GatewayRouteDefinition gatewayRouteDefinition) throws Exception {
        gatewayRouteService.add(gatewayRouteDefinition);
        // 刷新路由
        gatewayServiceHandler.loadRouteConfig();
        return "success";
    }

    @PostMapping("/update")
    public String update(@RequestBody GatewayRouteDefinition gatewayRouteDefinition) throws Exception {
        gatewayRouteService.update(gatewayRouteDefinition);
        // 刷新路由
        gatewayServiceHandler.loadRouteConfig();
        return "success";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws Exception {
        gatewayRouteService.delete(id);
        // 删除路由并刷新路由
        gatewayServiceHandler.deleteRoute(id);
        return "success";
    }

    @GetMapping("/routes")
    public List<GatewayRouteDefinition> routes() throws Exception {
        return gatewayRouteService.queryAllRoutes();
    }

}