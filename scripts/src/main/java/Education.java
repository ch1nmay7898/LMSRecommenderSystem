

public class Education implements Comparable<Education>{

	private String id;

	private String degree;

	private String fieldOfStudy;
	private String summary;

	private String schoolName;
	private String schoolLogo;
	private String startDate;
	
	private String endDate;
	
	private boolean iscurrent;
	private int startmonth;
	private int endmonth;
	private int startyear;
	private int endyear;
	private long sortvalue;
	
	

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean isIscurrent() {
		return iscurrent;
	}

	public void setIscurrent(boolean iscurrent) {
		this.iscurrent = iscurrent;
	}

	public int getStartmonth() {
		return startmonth;
	}

	public void setStartmonth(int startmonth) {
		this.startmonth = startmonth;
	}

	public int getEndmonth() {
		return endmonth;
	}

	public void setEndmonth(int endmonth) {
		this.endmonth = endmonth;
	}

	public int getStartyear() {
		return startyear;
	}

	public void setStartyear(int startyear) {
		this.startyear = startyear;
	}

	public int getEndyear() {
		return endyear;
	}

	public void setEndyear(int endyear) {
		this.endyear = endyear;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((fieldOfStudy == null) ? 0 : fieldOfStudy.hashCode());
		result = prime * result + ((schoolName == null) ? 0 : schoolName.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		Education other = (Education) obj;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (fieldOfStudy == null) {
			if (other.fieldOfStudy != null)
				return false;
		} else if (!fieldOfStudy.equals(other.fieldOfStudy))
			return false;
		if (schoolName == null) {
			if (other.schoolName != null)
				return false;
		} else if (!schoolName.equals(other.schoolName))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public int compareTo(Education o) {
		
		long compareQuantity = ((Education) o).getSortvalue();
		long delta=compareQuantity - this.sortvalue;
		//descending order
		return delta>0?1:-1;
	}

	@Override
	public String toString() {
		return "Education [id=" + id + ", degree=" + degree + ", fieldOfStudy=" + fieldOfStudy + ", summary=" + summary
				+ ", schoolName=" + schoolName + ", schoolLogo=" + schoolLogo + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", iscurrent=" + iscurrent + ", startmonth=" + startmonth + ", endmonth="
				+ endmonth + ", startyear=" + startyear + ", endyear=" + endyear + ", sortvalue=" + sortvalue + "]";
	}
}
