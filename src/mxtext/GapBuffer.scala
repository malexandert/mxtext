package mxtext 

/**
 * A GapBuffer is a way of storing contiguous characters in
 * not necessarily contiguous blocks of memory
 */
class GapBuffer (val limit : Int) {
	
	var buffer = new Array[Char](limit)
	var gap_start = 0
	var gap_end = limit
	assert(this.gapbuf_empty)
	
	/**
	 * Checks if a given GapBuffer holds the variants
	 * of a gap buffer
	 */
	def is_gapbuf : Boolean = {
		if (this == null) {
			return false
		} 
		if (this.limit <= 0) {
			return false
		} 
		if (this.limit != (this.buffer length)) {
		  return false
		}
		if (this.gap_start < 0 || this.gap_end > limit || this.gap_start > this.gap_end ) {
			return false
		}
		return true
	}

	/**
	 * Checks if a GapBuffer is empty
	 */
	def gapbuf_empty = {
		assert(this.is_gapbuf)
		this.gap_start == 0 && this.gap_end == (this.buffer length) 
	}
	
	/**
	 * Checks if a GapBuffer is full
	 */
	def gapbuf_full = {
	  	assert(this.is_gapbuf)
	  	this.gap_start == this.gap_end 
	}
	
	/**
	 * Checks if the gap is at the left end of the GapBuffer
	 */
	def gapbuf_at_left = {
	  	assert(this.is_gapbuf)
	  	this.gap_start == 0
	}
	
	/**
	 * Checks if the gap is at the right end of the GapBuffer
	 */
	def gapbuf_at_right = {
	  assert(this.is_gapbuf)
	  this.gap_end == this.limit
	}
	
	/**
	 * Moves the gap one character to the right. If the gap
	 * is already at the right of the buffer, an assertion error
	 * is thrown
	 */
	def gapbuf_forward = {
	  assert(this.is_gapbuf)
	  if (!this.gapbuf_at_right && !this.gapbuf_full) {
		  this.buffer(this.gap_start) = this.buffer(this.gap_end) 
		  this.buffer(this.gap_end) = 0
		  this.gap_end = this.gap_end + 1
		  this.gap_start = this.gap_start + 1
	  } else if (!this.gapbuf_at_right && this.gapbuf_full) {
	      this.gap_start += 1
	      this.gap_end += 1
	  }
	}
	
	/**
	 * Moves the gap one character to the left. If the gap
	 * is already at the left of the buffer, an assertion error
	 * is thrown
	 */
	def gapbuf_backward = {
	    assert(this.is_gapbuf)
	    if (!this.gapbuf_at_left && !this.gapbuf_full) {
		    this.buffer(this.gap_end - 1 ) = this.buffer(this.gap_start - 1) 
		    this.buffer(this.gap_start - 1) = 0
		    this.gap_end -= 1
		    this.gap_start -= 1
	  } else if (!this.gapbuf_at_left && this.gapbuf_full) {
		  	this.gap_start -= 1
		  	this.gap_end -= 1
	  }
	}
	
	/**
	 * Inserts a character into the GapBuffer at the position before
	 * the start of the gap if there is room left in the buffer
	 */
	def gapbuf_insert (c : Char) = {
	    assert(this.is_gapbuf)
	    if (!this.gapbuf_full) {
	    	this.buffer(this.gap_start) = c
	    	this.gap_start += 1 
	    }
	}
	
	/**
	 * Deletes the character before the gap in the GapBuffer if the 
	 * buffer is non-empty
	 */
	def gapbuf_delete = {
	  assert(this.is_gapbuf)
	  if (!this.gapbuf_empty) {
		  this.buffer(this.gap_start - 1) = 0
		  this.gap_start -= 1
	  }
	}
}

