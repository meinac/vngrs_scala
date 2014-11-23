package main
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.mongodb.casbah.Imports._

object ContactDao {
  
  val uri = """mongodb://localhost:27017/"""
  val db = MongoClient(MongoClientURI(uri))( """vngrs""")
  val collection = db("contacts")
  
  def insert(contact: Contact) = {
    collection.insert(ContactMongoConverter.convertToMongoObject(contact))
  }
  
  def bulkInsert(contacts: Seq[Contact]) = {
    val builder = collection.initializeOrderedBulkOperation
    contacts.foreach{ c => 
      builder.insert(ContactMongoConverter.convertToMongoObject(c))
    }
    builder.execute()
  }
  
  def search(qString: String) = {
    val query = $or("name" $regex ("(?i).*" + qString + ".*"), "lastName" $regex ("(?i).*" + qString + ".*"))
    val result = for(
      obj <- collection.find(query)
    ) yield convertFromMongo(Option(obj)) match {
      case Some(contact) => contact 
    }
    result.toSeq
  }
  
  def all() = {
    collection.find().map{ obj => 
      convertFromMongo(Option(obj)) match {
        case Some(contact) => contact 
      }
    }.toSeq
  }
  
  def dropDB() = {
    collection.drop()
  }
  
  def destroy(contact: Contact) = {
    val obj = ContactMongoConverter.convertToMongoObject(contact)
    collection.remove(obj)
  }
  
  private def convertFromMongo(dbObj : Option[DBObject]) : Option[Contact]= {
    dbObj match {
      case Some(obj) => Some(ContactMongoConverter.convertFromMongoObject(obj))
      case None => None
    }
  }

}