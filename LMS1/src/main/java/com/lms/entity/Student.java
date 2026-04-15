package com.lms.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@Column(name = "seat_no")
	private int seatNo;
	private String name;
	@ManyToOne
	@JoinColumn(name = "section_id")
	private Section section;
	private int year;
	private String fatherName;
	private String email;
	
	
	public Student(int seatNo, String newName, String section, int year, String fatherName, String email){};
	public Student(int seatNo, String name, Section section, int year, String fatherName, String email) {
		
		this.seatNo = seatNo;
		this.name = name;
		this.section = section;
		this.year = year;
		this.fatherName = fatherName;
		this.email = email;
	}

	public Student() {

	}

	public int getSeatNo() {
		return seatNo;
	}
	
	public String getName() {
		return name;
	}
	
	public Section getSectionsection() {
		return section;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getFather() {
		return fatherName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setSeatNo(int seatNo) { this.seatNo = seatNo; }

	public void setName(String name) { this.name = name; }

	public void setSection(Section section) { this.section = section; }

	public void setYear(int year) { this.year = year; }

	public void setFatherName(String fatherName) { this.fatherName = fatherName; }

	public void setEmail(String email) { this.email = email; }




	@Override
	public String toString() {
	    return "Student [seatNo=" + seatNo + ", name=" + name + ", section=" + section + ", year=" + year + ", father name=" + fatherName + ", email=" + email + "]";
	}

	
	

}
