package main

import com.mongodb.casbah.Imports._

object ContactMongoConverter {
  
  def convertToMongoObject(contact: Contact): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "name" -> contact.name
    builder += "lastName" -> contact.lastName
    builder += "phones" -> MongoDBList(contact.phones: _*)
    builder.result()
  }
  
  def convertFromMongoObject(dbObject: DBObject): Contact = {
    val name = dbObject.getAsOrElse[String]("name", Converters.mongoFail)
    val lastName = dbObject.getAsOrElse[String]("lastName", Converters.mongoFail)
    val phones = dbObject.getAs[MongoDBList]("phones") match {
      case Some(phones) => phones collect {
        case phone: String => phone
      }
      case None => Converters.mongoFail
    }
    
    Contact(
      name = name,
      lastName = lastName,
      phones = phones.toList
    )
  }

}