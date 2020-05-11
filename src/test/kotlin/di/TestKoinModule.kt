package di

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.example.DynamoDBContainer
import com.example.repository.ItemRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val testModule: (dynamoDBContainer: DynamoDBContainer) -> Module = { dynamoDBContainer ->
    module(override = true) {
        single {
            AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                    AwsClientBuilder.EndpointConfiguration("http://localhost:" + dynamoDBContainer.connectionPort(), Regions.US_EAST_1.getName()))
                    .build()
        }

        single {
            ItemRepository(get(), "item-table-test")
        }
    }
}