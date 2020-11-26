package com.imooc.kafka.admin;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.ConfigResource;

import java.util.*;

/**
 * kafka client 基本操作
 *
 * @author pengfei.zhao
 * @date 2020/11/26 18:56
 */
public class AdminSample {
    public static final String ERU_TOPIC = "eru-topic";

    public static void main(String[] args) throws Exception {
        //createTopic();

        //delTopic();

        //listTopics();

        //alterConfig();

        incrPartitions(2);

        describeTopics();
    }

    /**
     * 增加 partitions 数量
     * @param partitions 数量
     */
    private static void incrPartitions(int partitions) {
        Map<String, NewPartitions> map = new HashMap<>();
        NewPartitions increase = NewPartitions.increaseTo(2);
        map.put(ERU_TOPIC, increase);
        AdminClient adminClient = adminClient();
        adminClient.createPartitions(map);
    }

    /**
     * 修改config信息
     */
    private static void alterConfig() throws Exception {
        AdminClient adminClient = adminClient();
        Map<ConfigResource, Collection<AlterConfigOp>> configMaps = new HashMap<>();
        ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, ERU_TOPIC);
        AlterConfigOp alterConfigOp =
                new AlterConfigOp(new ConfigEntry("preallocate", "false"), AlterConfigOp.OpType.SET);
        configMaps.put(resource, Arrays.asList(alterConfigOp));

        AlterConfigsResult result = adminClient.incrementalAlterConfigs(configMaps);
        result.all().get();
    }

    /**
     * 描述topic
     */
    private static void describeTopics() throws Exception {
        AdminClient adminClient = adminClient();
        DescribeTopicsResult topicsResult = adminClient.describeTopics(Arrays.asList(ERU_TOPIC));
        Map<String, TopicDescription> descriptionMap = topicsResult.all().get();
        descriptionMap.entrySet().stream().forEach((entry) -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }

    /**
     * 删除topic
     */
    private static void delTopic() {
        AdminClient adminClient = adminClient();
        DeleteTopicsResult result = adminClient.deleteTopics(Arrays.asList(ERU_TOPIC));
        System.out.println(result);
    }

    /**
     * 创建 topic
     */
    public static void createTopic() {
        AdminClient adminClient = adminClient();
        // 副本因子
        Short rf = 1;
        NewTopic newTopic = new NewTopic(ERU_TOPIC, 1, rf);
        CreateTopicsResult result = adminClient.createTopics(Arrays.asList(newTopic));
        System.out.println(JSON.toJSONString(result));
    }

    /**
     * 列出所有的 topic
     */
    public static void listTopics() throws Exception {
        AdminClient adminClient = adminClient();
        ListTopicsResult result = adminClient.listTopics();
        Set<String> names = result.names().get();
        KafkaFuture<Map<String, TopicListing>> mapKafkaFuture = result.namesToListings();
        names.stream().forEach(System.out::println);
    }

    public static AdminClient adminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "152.136.159.147:9092");
        return AdminClient.create(properties);
    }
}
