package main

case class Contact (name: String, lastName: String, phones: List[String]) {
  
  def equals(that: Contact): Boolean = {
    that != null && name == that.name && lastName == that.lastName
  }
  
  override def toString() = {
    name + " " + lastName + " -> " + phones.mkString (", ")
  }
  
  def destroy() = {
    Contact.destroy(this)
  }
  
}

object Contact {
  
  def all(): Seq[Contact] = {
    ContactDao.all()
  }
  
  def search(queryString: String): Seq[Contact] = {
    ContactDao.search(queryString)
  }
  
  def build(name: String, lastName: String, phones: List[String]) = {
    Contact(name, lastName, phones)
  }
  
  def create(name: String, lastName: String, phones: List[String]) = {
    val contact = Contact(name, lastName, phones)
    ContactDao.insert(contact)
    contact
  }
  
  def createAll(contacts: Seq[Contact]) = {
	ContactDao.bulkInsert(contacts)
  }
  
  def destroy(contact: Contact) = {
    ContactDao.destroy(contact)
  }
  
  def dropDB() = {
    ContactDao.dropDB()
  }
  
}