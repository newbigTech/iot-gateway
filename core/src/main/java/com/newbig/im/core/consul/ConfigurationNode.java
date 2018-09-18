package com.newbig.im.core.consul;

import lombok.RequiredArgsConstructor;

/**
 * Data configuration node.
 *
 * @author caohao
 * @author panjuan
 */
@RequiredArgsConstructor
public final class ConfigurationNode {

    public static final String ROOT = "config";

    public static final String PROXY_NODE_PATH = ROOT + "/proxy";

    public static final String DATA_SOURCE_NODE_PATH = ROOT + "/datasource";

    public static final String SHARDING_NODE_PATH = ROOT + "/sharding";

    public static final String MASTER_SLAVE_NODE_PATH = ROOT + "/masterslave";

    public static final String RULE_NODE_PATH = "/rule";

    public static final String SERVER_CONFIG_NODE_PATH = "/server";

    public static final String CONFIG_MAP_NODE_PATH = "/configmap";

    public static final String SHARDING_RULE_NODE_PATH = SHARDING_NODE_PATH + RULE_NODE_PATH;

    public static final String SHARDING_CONFIG_MAP_NODE_PATH = SHARDING_NODE_PATH + CONFIG_MAP_NODE_PATH;

    public static final String SHARDING_PROPS_NODE_PATH = SHARDING_NODE_PATH + "/props";

    public static final String MASTER_SLAVE_RULE_NODE_PATH = MASTER_SLAVE_NODE_PATH + RULE_NODE_PATH;

    public static final String MASTER_SLAVE_CONFIG_MAP_NODE_PATH = MASTER_SLAVE_NODE_PATH + CONFIG_MAP_NODE_PATH;

    public static final String MASTER_SLAVE_PROPS_NODE_PATH = MASTER_SLAVE_NODE_PATH + "/props";

    public static final String PROXY_RULE_NODE_PATH = PROXY_NODE_PATH + RULE_NODE_PATH;

    public static final String PROXY_SERVER_CONFIG_NODE_PATH = PROXY_NODE_PATH + SERVER_CONFIG_NODE_PATH;

    private final String name;

    /**
     * Get node full path.
     *
     * @param node node name
     * @return node full path
     */
    public String getFullPath(final String node) {
        return String.format("/%s/%s", name, node);
    }
}
