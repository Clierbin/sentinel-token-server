package com.gupaovip.sentinel.sentineltokenserver;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.ClusterRuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * ClassName:NacosDataSourceInitFunc
 * Package:com.gupaovip.sentinel.sentineltokenserver
 * description
 * Created by zhangbin on 2019/8/14.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/8/14 11:10
 */
public class NacosDataSourceInitFunc implements InitFunc {
    private final  String remoteAddress="192.168.5.178";// nacos 地址

    private final String groupId="SENTINEL_GROUP";// group ID

    private final String FLOW_POSTFIX="-flow-rules"; // Data Id  namespace+FLOW_POSTFIX
    // 意味着当前的token-server会从nacos上获取限流规则
    @Override
    public void init() throws Exception {

        ClusterFlowRuleManager.setPropertySupplier(namespace ->{
            // 从nacos 远程获取到source(List<FlowRule>)  然后设置能数据源
            ReadableDataSource<String, List<FlowRule>> rds=
                    new NacosDataSource<List<FlowRule>>(remoteAddress,groupId,namespace+FLOW_POSTFIX,
                            source -> JSON.parseObject(source,new TypeReference<List<FlowRule>>(){}));
            return rds.getProperty();
        });
    }
}
