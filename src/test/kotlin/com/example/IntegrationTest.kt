package com.example

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.*
import com.example.domain.Item
import com.example.repository.ItemRepository
import di.testModule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.properties.Delegates
import org.koin.test.get

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class IntegrationTest : KoinTest {

    private val dynamoDBContainer = DynamoDBContainer()
    private var dynamoDB: AmazonDynamoDB by Delegates.notNull()

    var itemRepository: ItemRepository by Delegates.notNull()

    init {
        dynamoDBContainer.start()

        startKoin {
            modules(listOf(testModule(dynamoDBContainer)))
        }
        dynamoDB = get()
        itemRepository = get()

        setUpDB()
    }

    private fun setUpDB() {
        dynamoDB.createTable(CreateTableRequest().withTableName(itemRepository.getTableName())
                .withKeySchema(KeySchemaElement().withKeyType(KeyType.HASH)
                        .withAttributeName("id")
//                        , KeySchemaElement().withKeyType(KeyType.RANGE)
//                                .withAttributeName("name")
                )
                .withAttributeDefinitions(
                        AttributeDefinition()
                                .withAttributeName("id")
                                .withAttributeType(ScalarAttributeType.S)
//                        ,AttributeDefinition()
//                                .withAttributeName("name")
//                                .withAttributeType(ScalarAttributeType.S)
                )
                .withProvisionedThroughput(
                        ProvisionedThroughput()
                                .withReadCapacityUnits(1L)
                                .withWriteCapacityUnits(1L)))
    }

    @BeforeEach
    fun setUp() {
        dynamoDB.deleteTable(DeleteTableRequest().withTableName(itemRepository.getTableName()))
        setupDB()
    }

    private fun setupDB() {
        dynamoDB.createTable(itemRepository.getMapper().generateCreateTableRequest(Item::class.java).withTableName(itemRepository.getTableName())
                .withBillingMode(BillingMode.PROVISIONED).withProvisionedThroughput(ProvisionedThroughput(1, 1)))
    }

    @AfterAll
    open fun tearDownAll() {
        dynamoDBContainer.stop()
    }

}