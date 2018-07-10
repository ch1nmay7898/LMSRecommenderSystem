



public class Experience implements Comparable<Experience>{
	
	private String id;
	private String companyName;
	private String companyLogo;
	private String title;
	private String location;
	private String summary;
	private String startDate;
	private String endDate;
	private int startmonth;
	private int startyear;
	private int endmonth;
	private int endyear;
	private boolean iscurrent;
	private long sortvalue;
	
	
	
	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public boolean isIscurrent() {
		return iscurrent;
	}

	public void setIscurrent(boolean iscurrent) {
		this.iscurrent = iscurrent;
	}

	public String getId() {
		return id;
	}
		
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getStartmonth() {
		return startmonth;
	}

	public void setStartmonth(int startmonth) {
		this.startmonth = startmonth;
	}

	public int getStartyear() {
		return startyear;
	}

	public void setStartyear(int startyear) {
		this.startyear = startyear;
	}

	public int getEndmonth() {
		return endmonth;
	}

	public void setEndmonth(int endmonth) {
		this.endmonth = endmonth;
	}

	public int getEndyear() {
		return endyear;
	}

	public void setEndyear(int endyear) {
		this.endyear = endyear;
	}

	public long getSortvalue() {
		return sortvalue;
	}

	public void setSortvalue(long sortvalue) {
		this.sortvalue = sortvalue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result
				+ ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Experience other = (Experience) obj;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(Experience o) {
		long compareQuantity = ((Experience) o).getSortvalue();
		long delta=compareQuantity - this.sortvalue;
		//descending order
		return delta>0?1:-1;
	}

	@Override
	public String toString() {
		return "Experience [id=" + id + ", companyName=" + companyName + ", companyLogo=" + companyLogo + ", title="
				+ title + ", location=" + location + ", summary=" + summary + ", startDate=" + startDate + ", endDate="
				+ endDate + ", startmonth=" + startmonth + ", startyear=" + startyear + ", endmonth=" + endmonth
				+ ", endyear=" + endyear + ", iscurrent=" + iscurrent + ", sortvalue=" + sortvalue + "]";
	}
	
	
}
