package com.example.repository

import com.example.IntegrationTest
import com.example.domain.Item
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ItemRepositoryIntTest : IntegrationTest() {

    @Test
    fun `should return saved item`() {
        val item = Item("item1", "description1")
        itemRepository.save(item)

        val itemReturned = itemRepository.getItem(item.id!!)!!

        Assertions.assertThat(item.id).isEqualTo(itemReturned.id)
        Assertions.assertThat(item.name).isEqualTo(itemReturned.name)
        Assertions.assertThat(item.description).isEqualTo(itemReturned.description)
    }

    @Test
    fun `should update item given an existing item`() {
        val item = Item("item2", "description2")
        itemRepository.save(item)

        val updatedItem = item.copy(description = "new description")

        itemRepository.save(updatedItem)

        Assertions.assertThat(updatedItem.id).isEqualTo(item.id)
        Assertions.assertThat(updatedItem.name).isEqualTo(item.name)
        Assertions.assertThat(updatedItem.description).isEqualTo("new description")
    }

    @Test
    fun `should delete item given an existing item`() {
        val item = Item("item3", "description3")
        itemRepository.save(item)

        Assertions.assertThat(itemRepository.getItem(item.id!!)).isNotNull()

        itemRepository.deleteItem(item)

        Assertions.assertThat(itemRepository.getItem(item.id!!)).isNull()
    }

    @Test
    fun `should return item given an id in consistent way`() {
        val item = Item("item4", "description4")
        itemRepository.save(item)

        val returnedItem = itemRepository.getItemConsistentlyById(item.id!!)!!

        Assertions.assertThat(item.id).isEqualTo(returnedItem.id)
        Assertions.assertThat(item.name).isEqualTo(returnedItem.name)
        Assertions.assertThat(item.description).isEqualTo(returnedItem.description)
    }

    @Test
    fun `should return item given an id with eventual consistent`() {
        val item = Item("item5", "description5")
        itemRepository.save(item)

        val returnedItem = itemRepository.getItemById(item.id!!)!!

        Assertions.assertThat(item.id).isEqualTo(returnedItem.id)
        Assertions.assertThat(item.name).isEqualTo(returnedItem.name)
        Assertions.assertThat(item.description).isEqualTo(returnedItem.description)
    }

}