package mxtext

/**
 * A generic doubly linked list implementation
 */
class DoublyLinkedList[T] (init : T) {
	
	var start = new Node[T]()
	var end = new Node[T]()
	var point = new Node[T](init)
	
	start.next = Some(point)
	end.prev = Some(point)
	point.prev = Some(start)
	point.next = Some(end)

	
	def dll_min = start.next.get == point && end.prev.get  == point
	
	def dll_pt_at_left = {
		this.point == this.start.next.get
	}
	
	def dll_pt_at_right = {
		this.point == this.end.prev.get
	}
	
	def dll_pt_forward = {
		assert(!this.dll_pt_at_right)
		this.point = this.point.next.get
	}
	
	def dll_pt_backward = {
		assert(!this.dll_pt_at_left)
		this.point = this.point.prev.get
	}
	
	def dll_pt_delete = {
		assert(!this.dll_min)
		if (this.point.prev.get == this.start) {
			this.dll_pt_forward
			this.point.prev.get.next = None
			this.point.prev.get.prev = None
			this.point.prev = Some(this.start)
			this.start.next = Some(this.point)
		} else {
			this.dll_pt_backward
			this.point.next.get.prev = None
			this.point.next = Some(this.point.next.get.next.get)
			this.point.next.get.prev.get.next = None
			this.point.next.get.prev = Some(this.point)
		}
	}
}

class Node[T]() {
	
	var prev : Option[Node[T]] = None
	var next : Option[Node[T]] = None
	var data : Option[T] = None
	
	def this(data : T) = {
		this()
		this.data = Some(data)
	}
	
}