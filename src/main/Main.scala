package main

import scala.io.Source
import java.io.File

object Main {
  
  var exit = false
  
  def printMenu(warn: Boolean = false) {
    if(warn){
      println("Input couldn't recognized!")
    }
    println("########## MENU ##########")
    println("1) Import XML File")
    println("2) Search in contact list")
    println("3) Print All")
    println("4) Remove All")
    println("5) About")
    println("9) Exit")
    println("##########################")
  }
  
  def search() = {
    print("> Search: ")
    val list = Contact.search(Console.readLine())
    println(list.length + " record(s) found")
    list.foreach(contact =>
      println(contact)
    )
  }
  
  def printAll() = {
    Contact.all().foreach(c =>
      println(c)
    )
  }
  
  def dropDB() = {
    Contact.dropDB()
  }
  
  def printAbout() = {
    println("...This is an application for managing contacts from console...")
  }
  
  def importMenu() = {
    print("> Path of the xml file: ")
    val importer = new Importer(Console.readLine())
    if(importer.isOk()){
      importer.run()
    }
    else{
      println("File not found")
    }
  }
	
  def main(args: Array[String]) = {
    
    println("Welcome to 'Easy Content Management Tool'")
    
    printMenu()
    
    while(!exit){
      print("> Menu: ")
      Console.readLine() match {
        case "1" => importMenu()
        case "2" => search()
        case "3" => printAll()
        case "4" => dropDB()
        case "5" => printAbout() 
        case "9" => exit = true
        case _ => printMenu(true)
      }
    }
    
    println("Bye bye...")
    
  }
  
}