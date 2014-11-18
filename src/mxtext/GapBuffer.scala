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
		}
	}
}
