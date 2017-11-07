package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the specialty database table. 
 * 
 * @author Grmahun Redda
 * @since 6/20/2017
 */
@Entity
@Table(name="speciality")
@NamedQuery(name="Speciality.findAll", query="SELECT s FROM Speciality s")
public class Speciality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	private BigInteger count;

	private double percent;

	@Column(name="primary_speciality")
	private String primarySpeciality;

	//bi-directional many-to-one association to ReportingOptionLookup
	@ManyToOne
	@JoinColumn(name="reporting_option_id")
	private ReportingOptionLookup reportingOptionLookup;

	//bi-directional many-to-one association to YearLookup
	@ManyToOne
	@JoinColumn(name="year_id")
	private YearLookup yearLookup;

	public Speciality() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getCount() {
		return this.count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public double getPercent() {
		return this.percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getPrimarySpeciality() {
		return this.primarySpeciality;
	}

	public void setPrimarySpeciality(String primarySpeciality) {
		this.primarySpeciality = primarySpeciality;
	}

	public ReportingOptionLookup getReportingOptionLookup() {
		return this.reportingOptionLookup;
	}

	public void setReportingOptionLookup(ReportingOptionLookup reportingOptionLookup) {
		this.reportingOptionLookup = reportingOptionLookup;
	}

	public YearLookup getYearLookup() {
		return this.yearLookup;
	}

	public void setYearLookup(YearLookup yearLookup) {
		this.yearLookup = yearLookup;
	}

}