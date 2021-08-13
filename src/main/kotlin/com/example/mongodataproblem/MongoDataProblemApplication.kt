package com.example.mongodataproblem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

@SpringBootApplication
class MongoDataProblemApplication

fun main(args: Array<String>) {
    runApplication<MongoDataProblemApplication>(*args)
}

interface DataModelRepository
    : ReactiveMongoRepository<DataModel, String>

@Document(collection = "my-model")
@TypeAlias("DataModel")
data class DataModel(
    @Id
    val dataId: String,
    val customObject: CommonModel,
)

sealed class CommonModel

@TypeAlias("DataModel.IntegerValue")
data class IntegerValue(val intValue: Int) : CommonModel()

@TypeAlias("DataModel.FloatValue")
data class FloatValue(val floatValue: Float) : CommonModel()

@TypeAlias("DataModel.NoValue")
object NoValue : CommonModel() {
    override fun equals(other: Any?) = other is NoValue
    override fun hashCode() = 1
}
