package com.example.di

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.example.repository.ItemRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.koin.dsl.module

val prodModule = module {
    single {
        ObjectMapper()
    }

    single {
        AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build()
    }

    single {
        val tableName = System.getenv("ITEM_TABLE_NAME")
        ItemRepository(get(), tableName)
    }
}