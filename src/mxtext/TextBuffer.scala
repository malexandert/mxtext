package mxtext

/**
 * A Text Buffer is a doubly linked list of gap buffers
 * used to store characters in a text editor
 */
class TextBuffer {
	
	var buffer = new DoublyLinkedList[GapBuffer](new GapBuffer(16))
	
	/**
	 * Checks if the text buffer is empty. An empty text buffer
	 * is a doubly linked list with one node, an empty gap buffer
	 */
	def is_empty = {
		this.buffer.dll_min && this.buffer.point.data.get.gapbuf_empty
	}
	
	/**
	 * Splits a node with a full ga buffer into two nodes, each with a half
	 * filled gap buffer and half of the characters in the original node, 
	 * based on where the gap was located
	 */
	def split = {
		assert(this.buffer.point.data.get.gapbuf_full)
		val original_buf = this.buffer.point.data.get
		var first_buf = new GapBuffer(16)
		var second_buf = new GapBuffer(16)
		val gapstart = this.buffer.point.data.get.gap_start
		val gapend = gapstart + 8
		if (gapstart < 8) {
			for (i <- 0 until gapstart) {
				first_buf.gapbuf_insert(original_buf.buffer(i))
			} 
			for (i <- gapstart until gapend) {
				first_buf.gapbuf_insert(0)
			}
			for (i <- gapend until 16) {
				first_buf.gapbuf_insert(original_buf.buffer(i-8))
			}
			for (i <- 16 until 32) {
				if (i < 24) second_buf.gapbuf_insert(0)
				else second_buf.gapbuf_insert(original_buf.buffer(i-16))
			}
			first_buf.gap_start = gapstart
			first_buf.gap_start = gapend
			second_buf.gap_start = 0
			second_buf.gap_end = 8
		} else {
			for (i <- 0 until 16) {
				if (i < 8) first_buf.gapbuf_insert(original_buf.buffer(i))
				else first_buf.gapbuf_insert(0)
			}
			for (i <- 8 until gapstart) {
				second_buf.gapbuf_insert(original_buf.buffer(i))
			} 
			for (i <- gapstart until gapend) {
				second_buf.gapbuf_insert(0)
			}
			for (i <- gapend until 24) {
				second_buf.gapbuf_insert(original_buf.buffer(i-8))
			}
			first_buf.gap_start = 8
			first_buf.gap_end = 16
			second_buf.gap_start = gapstart - 8
			second_buf.gap_end = gapend - 8
		}
		var first_point = new Node[GapBuffer](first_buf)
		var second_point = new Node[GapBuffer](second_buf)
		first_point.prev = Some(this.buffer.point)
		first_point.next = Some(second_point)
		second_point.prev = Some(first_point)
		second_point.next = this.buffer.point.next
		this.buffer.point.next.get.prev = Some(second_point)
		this.buffer.point.next = Some(first_point)
		this.buffer.dll_pt_delete
		
		if (gapstart < 8) this.buffer.point = first_point
		else this.buffer.point = second_point
	}
	
	/**
	 * Moves the cursor (gap) forward in the buffer. If the cursor is at the very
	 * end of the buffer, the method does nothing
	 */
	def forward = {
		if (!(this.buffer.point.next == Some(this.buffer.end) && this.buffer.point.data.get.gapbuf_at_right)) {
			if (this.buffer.point.data.get.gapbuf_at_right) {
				this.buffer.dll_pt_forward
			}
			this.buffer.point.data.get.gapbuf_forward
		}
	}
	
	/**
	 * Modes the cursor (gap) backward in the buffer. If the cursor is at the very
	 * beginning of the buffer, the method does nothing
	 */
	def backward = {
		if (!(this.buffer.point.prev == Some(this.buffer.start) && this.buffer.point.data.get.gapbuf_at_left)) {
			if (this.buffer.point.data.get.gapbuf_at_left) {
				this.buffer.dll_pt_backward
			}
			this.buffer.point.data.get.gapbuf_backward
		}
	}
	
	/**
	 * Inserts a character where the cursor is currently
	 */
	def insert (c : Char) = {
		if (this.buffer.point.data.get.gapbuf_full) {
			this.split
		}
		this.buffer.point.data.get.gapbuf_insert(c)
	}
	
	/**
	 * Deletes the character right before the cursor. If the text buffer is empty,
	 * this method does nothing
	 */
	def delete = {
		if (!this.is_empty) {
			if (this.buffer.point.data.get.gapbuf_at_left) {
				this.buffer.dll_pt_backward
			}
			this.buffer.point.data.get.gapbuf_delete
			if (this.buffer.point.data.get.gapbuf_empty) {
				this.buffer.dll_pt_delete
			}
		}
	}
	
}