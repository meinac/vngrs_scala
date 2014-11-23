package main

import com.mongodb.MongoException

object Converters {
	def mongoFail = throw new MongoException("Field not found")
}