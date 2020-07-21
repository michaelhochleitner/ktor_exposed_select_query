package com.example

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DB(){

    var value = 0

    fun writeSessions(){
        Database.connect("jdbc:mysql://localhost:3306/user", driver = "com.mysql.jdbc.Driver",
                 user = "user", password = "r39UAmBBubXysL5H")

        transaction {
        // print sql to std-out
        addLogger(StdOutSqlLogger)

        SchemaUtils.create (Sessions)

        // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
        val stPeteId = Sessions.insert {
          it[session_id] = "155924f2-31ff-4c2c-a8fa-7fb0afba0ff8"
          it[name] = "St. Petersburg"
        } get Sessions.id

        // 'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
        println("Cities: ${Sessions.selectAll()}")
      }
    }

    object Sessions: IntIdTable() {
        val session_id = varchar("sessionId", 50)
        val name = varchar("name", 50)
    }

    fun readRecord(): Int {
        Database.connect("jdbc:mysql://localhost:3306/exposed_user", driver = "com.mysql.jdbc.Driver",
                 user = "exposed_user", password = "9ZRCDELwDJQTD4Bb")
        transaction {
            // print sql to std-out
            addLogger(StdOutSqlLogger)
            value = Sessions.select{ Sessions.session_id eq "155924f2-31ff-4c2c-a8fa-7fb0afba0ff8"}.first()[Sessions.id].value
        }
        return value
    }

}
