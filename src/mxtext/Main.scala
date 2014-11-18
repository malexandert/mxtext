package mxtext

object Main {
	
	def main(args : Array[String]) {
	  var c = new GapBuffer(5)
	  for (i <- c.buffer) {
	    if (i == 0) println("null")
	  }
	  c.buffer(1) = 'a'
	  for (i <- c.buffer) {
	    if (i == 0) println("null")
	    else println(i)
	  }
	  c.buffer(1) = 0
	  for (i <- c.buffer) {
	    if (i == 0) println("null")
	    else println(i)
	  }
	}
}