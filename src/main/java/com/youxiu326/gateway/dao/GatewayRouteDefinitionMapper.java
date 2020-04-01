package com.youxiu326.gateway.dao;

import com.youxiu326.gateway.model.GatewayRouteDefinition;

import java.util.List;

public interface GatewayRouteDefinitionMapper {
    int deleteByPrimaryKey(String id);

    int insert(GatewayRouteDefinition record);

    int insertSelective(GatewayRouteDefinition record);

    GatewayRouteDefinition selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GatewayRouteDefinition record);

    int updateByPrimaryKey(GatewayRouteDefinition record);

    List<GatewayRouteDefinition> queryAllRoutes();
}