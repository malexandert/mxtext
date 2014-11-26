package mxtext {
	class GapBuffer (val limit : Int) {
		
		var buffer = new Array[Char](limit)
		var gap_start = 0
		var gap_end = limit
		assert(this.gapbuf_empty)
		
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

		def gapbuf_empty = {
			assert(this.is_gapbuf)
			this.gap_start == 0 && this.gap_end == (this.buffer length) 
		}
		
		def gapbuf_full = {
		  	assert(this.is_gapbuf)
		  	this.gap_start == this.gap_end 
		}
		
		def gapbuf_at_left = {
		  	assert(this.is_gapbuf)
		  	this.gap_start == 0
		}
		
		def gapbuf_at_right = {
		  assert(this.is_gapbuf)
		  this.gap_end == this.limit
		}
		
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
		
		def gapbuf_insert (c : Char) = {
		    assert(this.is_gapbuf)
		    if (!this.gapbuf_full) {
		    	this.buffer(this.gap_start) = c
		    	this.gap_start += 1 
		    }
		}
		
		def gapbuf_delete = {
		  assert(this.is_gapbuf)
		  if (!this.gapbuf_empty) {
			  this.buffer(this.gap_start - 1) = 0
			  this.gap_start -= 1
		  }
		}
	}
}
