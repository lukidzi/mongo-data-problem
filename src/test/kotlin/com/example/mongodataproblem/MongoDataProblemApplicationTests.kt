package com.example.mongodataproblem

import org.bson.Document
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@SpringBootTest
class MongoDataProblemApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Autowired
    private lateinit var dataModelRepository: DataModelRepository

    @Autowired
    private lateinit var mongoTemplate: ReactiveMongoTemplate

    @Test
    fun `should fail`() {

        val parse = Document.parse(
            """
            {
                "_id" : "myId",
                "customObject" : {
                            "intValue" : 10,
                            "_class" : "DataModel.IntegerValue"
                        },
                    },
                "_class" : "DataModel"
            }
        """.trimIndent()
        )
        val insert = mongoTemplate.save(parse, "my-model").block()
        val blockFirst = dataModelRepository.findAll().blockFirst()
    }

    @Test
    fun `should pass`() {
        dataModelRepository.save(
            DataModel(
                dataId = "id",
                customObject = IntegerValue(1)

            )
        )
        val blockFirst = dataModelRepository.findAll().blockFirst()
    }
}
