package com.youxiu326.gateway.service.impl;

import com.youxiu326.gateway.dao.GatewayRouteDefinitionMapper;
import com.youxiu326.gateway.model.GatewayRouteDefinition;
import com.youxiu326.gateway.service.GatewayRouteService;
import com.youxiu326.gateway.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *  自定义service层，增、删、改、查数据库路由配置信息
 */
@Service
public class GatewayRouteServiceImpl implements GatewayRouteService {

    @Autowired
    private GatewayRouteDefinitionMapper gatewayRouteMapper;

    public Integer add(GatewayRouteDefinition gatewayRouteDefinition) {

        if (!gatewayRouteDefinition.getPredicates().isEmpty())
            gatewayRouteDefinition.setPredicatesJson(
                    JsonUtils.parseString(gatewayRouteDefinition.getPredicates()));

        if (!gatewayRouteDefinition.getFilters().isEmpty())
            gatewayRouteDefinition.setFiltersJson(
                    JsonUtils.parseString(gatewayRouteDefinition.getFilters()));

//        if (gatewayRouteDefinition.getId()!=null)
//            return gatewayRouteMapper.updateByPrimaryKeySelective(gatewayRouteDefinition);

        return gatewayRouteMapper.insertSelective(gatewayRouteDefinition);
    }

    public Integer update(GatewayRouteDefinition gatewayRouteDefinition) {

        if (!gatewayRouteDefinition.getPredicates().isEmpty())
            gatewayRouteDefinition.setPredicatesJson(
                    JsonUtils.parseString(gatewayRouteDefinition.getPredicates()));

        if (!gatewayRouteDefinition.getFilters().isEmpty())
            gatewayRouteDefinition.setFiltersJson(
                    JsonUtils.parseString(gatewayRouteDefinition.getFilters()));

        return gatewayRouteMapper.updateByPrimaryKeySelective(gatewayRouteDefinition);
    }

    public Integer delete(String id) {
        return gatewayRouteMapper.deleteByPrimaryKey(id);
    }

    public List<GatewayRouteDefinition> queryAllRoutes(){
        return gatewayRouteMapper.queryAllRoutes();
    }

}