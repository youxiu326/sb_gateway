package com.youxiu326.gateway.service;

import com.youxiu326.gateway.model.GatewayRouteDefinition;

import java.util.List;

public interface GatewayRouteService{

    public Integer add(GatewayRouteDefinition gatewayRouteDefinition);

    public Integer update(GatewayRouteDefinition gatewayRouteDefinition);

    public Integer delete(String id);

    public List<GatewayRouteDefinition> queryAllRoutes();

}
