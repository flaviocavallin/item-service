package com.example.repository

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.example.domain.Item
import com.example.util.logger
import java.util.*

//https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBTableMapper.html
//DynamoDB uses eventually consistent reads, unless you specify otherwise. Read operations (such as GetItem, Query, and Scan) provide a ConsistentRead parameter. If you set this parameter to true, DynamoDB uses strongly consistent reads during the operation.
class ItemRepository(private val amazonDynamoDB: AmazonDynamoDB, private val tableName: String) {

    companion object {
        private val LOG = logger()
    }

    private val mapper: DynamoDBMapper

    init {
        val mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride(tableName))
                .build()
        this.mapper = DynamoDBMapper(amazonDynamoDB, mapperConfig)
    }

    fun save(item: Item) {
        LOG.info("before saving item= $item");
        this.mapper.save(item);
        LOG.info("after saving item= $item");
    }

    fun getItem(id: String): Item? {
        LOG.info("getItem id=$id")

        var item: Item? = null

        val av = HashMap<String, AttributeValue>()
        av[":v1"] = AttributeValue().withS(id)

        val queryExp: DynamoDBQueryExpression<Item> = DynamoDBQueryExpression<Item>()
                .withKeyConditionExpression("id = :v1")
                .withExpressionAttributeValues(av)

        LOG.info("before mapper query")

        val result: PaginatedQueryList<Item> = mapper.query(Item::class.java, queryExp)

        LOG.info("Result=$result")

        if (result.size > 0) {
            item = result[0]
            LOG.info("Items - get(): item - $item")
        } else {
            LOG.info("Items - get(): item - Not Found.")
        }
        return item
    }

    fun getItemById(id: String): Item? {
        return this.mapper.load(Item::class.java, id)
    }

    fun getItemConsistentlyById(id: String): Item? {
        val config = DynamoDBMapperConfig.builder()
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build()

        return this.mapper.load(Item::class.java, id, config)
    }

    fun deleteItem(item: Item) {
        this.mapper.delete(item)
    }

    fun getTableName(): String {
        return this.tableName
    }

    fun getMapper(): DynamoDBMapper {
        return this.mapper
    }
}