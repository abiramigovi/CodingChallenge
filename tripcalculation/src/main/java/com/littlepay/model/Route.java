package com.littlepay.model;

public class Route {
	
	private String from;
	
	public Route(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	private String to;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route that = (Route) o;
        return (from.equals(that.from) && to.equals(that.to)) ||
               (from.equals(that.to) && to.equals(that.from)); 
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }

}
