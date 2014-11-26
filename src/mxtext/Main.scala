package mxtext

object Main {
	
	def main(args : Array[String]) {
	    var c = new GapBuffer(16)
	    var test_string = "space race<<<<<<<<^p<<the >>^p>>>>>>>>!!<<<<<<<^^^^^great"
	    GapBufferTest.process_gapbuf_string(c, test_string) 
	    println("")
	    DLLTest.process_dll_string("steady", "^<<<<^>>^")
	    println("")
	    var tbuf = new TextBuffer()
	    TextBufferTest.process_tbuf_string(tbuf, "The quick brown fox jumped over the lazy dog<<<<<<<<<<<<<<<<<<^^^^^^leaped")
	}
	
}

object GapBufferTest {
	
	def visualize_gapbuf (g : GapBuffer) = {
	    for (i <- 0 until g.gap_start)
	        print(g.buffer(i))
	    print('[')
	    for (i <- g.gap_start until g.gap_end)
	        print('.')
	    print(']')
	    for (i <- g.gap_end until g.limit)
	    	print(g.buffer(i))
	}
	
	def process_gapbuf_char(g : GapBuffer, c : Char) = c match {
	  	case '<' => 
	  	  print("<= : ")
	  	  g.gapbuf_backward
	  	case '>' =>
	  	  print("=> : ")
	  	  g.gapbuf_forward
	  	case '^' =>
	  	  print("del: ")
	  	  g.gapbuf_delete
	  	case char =>
	  	  print("'")
	  	  print(char)
	  	  print("'")
	  	  g.gapbuf_insert(char)
	}
	
	def process_gapbuf_string (g : GapBuffer, s : String) = {
		print("=: ")
		visualize_gapbuf(g)
		println("")
		for (i <- 0 until s.length()) {
			process_gapbuf_char(g, s(i))
			visualize_gapbuf(g)
			println("")
		}
	}
}

object DLLTest {
	
	def visualize_dll(dll : DoublyLinkedList[_]) = {
		var bufPoint = dll.start
		while (bufPoint != dll.end) {
			if (bufPoint == dll.start) print("START")
			else if (bufPoint == dll.point) print("[" + bufPoint.data.get + "]")
			else print(bufPoint.data.get)
			print(" <--> ")
			bufPoint = bufPoint.next.get
		}
		println("END")
	}
	
	def process_dll_char(dll : DoublyLinkedList[_], c : Char) = c match {
		case '<' => 
	  	  print("<= : ")
	  	  dll.dll_pt_backward
	  	case '>' =>
	  	  print("=> : ")
	  	  dll.dll_pt_forward
	  	case '^' =>
	  	  print("del: ")
	  	  dll.dll_pt_delete
	} 
	
	def process_dll_string (s : String, instr : String) = {
		var dll = new DoublyLinkedList[Char](s(0))
		var last = dll.point
		for (i <- 1 until s.length()) {
			var node = new Node[Char](s(i))
			node.prev = Some(last)
			node.next = last.next 
			last.next.get.prev = Some(node)
			last.next = Some(node)
			last = node
			dll.point = last
		}
		
		print("=: ")
		visualize_dll(dll)
		for (i <- 0 until instr.length()) {
			process_dll_char(dll, instr(i))
			visualize_dll(dll)
		}
	}
}

object TextBufferTest {
	
	def gapbuf_as_string (g : GapBuffer) = {
		var gap_string = ""
	    for (i <- 0 until g.gap_start)
	        gap_string += g.buffer(i)
	    gap_string += '['
	    for (i <- g.gap_start until g.gap_end)
	        gap_string += '.'
	    gap_string += ']'
	    for (i <- g.gap_end until g.limit)
	    	gap_string += g.buffer(i)
	    gap_string
	}
	
	def visualize_tbuf(tbuf : TextBuffer) = {
		var bufPoint = tbuf.buffer.start
		while (bufPoint != tbuf.buffer.end) {
			if (bufPoint == tbuf.buffer.start) print("START")
			else if (bufPoint == tbuf.buffer.point) print("_" + gapbuf_as_string(bufPoint.data.get) + "_")
			else print(gapbuf_as_string(bufPoint.data.get))
			print(" <--> ")
			bufPoint = bufPoint.next.get
		}
		println("END")
	}
	
	def process_tbuf_char(tbuf : TextBuffer, c : Char) = c match {
		case '<' => 
	  	  print("<= : ")
	  	  tbuf.backward
	  	case '>' =>
	  	  print("=> : ")
	  	  tbuf.forward
	  	case '^' =>
	  	  print("del: ")
	  	  tbuf.delete
	  	case char =>
	  	  print("'")
	  	  print(char)
	  	  print("'")
	  	  tbuf.insert(char)
	}
	
	def process_tbuf_string (tbuf : TextBuffer, s : String) = {
		print("=: ")
		visualize_tbuf(tbuf)
		println("")
		for (i <- 0 until s.length()) {
			process_tbuf_char(tbuf, s(i))
			visualize_tbuf(tbuf)
		}
	}
	
}