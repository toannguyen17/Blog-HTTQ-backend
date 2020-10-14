package com.httq.dto.post;

import java.util.List;

public class PostPageProfile {
	private List<reViewPost2> content;
	private boolean empty;
	private boolean first;
	private boolean last;
	private Integer number;
	private Integer numberOfElements;
	private Integer size;
	private Integer totalElements;
	private Integer totalPages;

	public PostPageProfile(){
	}

	public List<reViewPost2> getContent() {
		return content;
	}

	public void setContent(List<reViewPost2> content) {
		this.content = content;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
