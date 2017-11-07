package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

/**
 * The persistent class for the template_file database table. 
 * 
 * @author Grmahun Redda
 * @since 6/15/2017
 */
@Entity
@Table(name="template_file")
public class TemplateFile implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;
	
	/** uploaded file content*/
	@Column(name = "uploaded_filecontent", nullable = true)
	@Lob @Basic(fetch = FetchType.LAZY)
	private byte[] uploadedFileContent;
	
	/** uploaded file name*/
	@Column(name = "uploaded_filename", nullable = true)
	private String uploadedFileName;
	
	/** uploaded file type*/
	@Column(name = "uploaded_filetype", nullable = true)
	private String uploadedFileType;
	
	/** uploaded file title*/
	@Column(name = "uploaded_title", nullable = true)
	private String uploadedTitle;
	
	/** uploaded file description*/
	@Column(name = "uploaded_description", nullable = true)
	private String uploadedDescription;
	
	@Column(name = "created_by",nullable = true)
	private String createdBy;
	
	@Column(name = "updated_by",nullable = true)
	private String updatedBy;
	
	@Column(name = "created_date", nullable = true)
	@OrderBy("created_date asc")
	private Date createdDate;
	
	@Column(name = "updated_date", nullable = true)
	@OrderBy("updated_date asc")
	private Date updatedDate;	
	
	@Column(name = "record_status",nullable = true)
	private int recordStatus;
	
	@Transient
	private MultipartFile uploadedFile;
	
	public MultipartFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(MultipartFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getUploadedFileContent() {
		return uploadedFileContent;
	}

	public void setUploadedFileContent(byte[] uploadedFileContent) {
		this.uploadedFileContent = uploadedFileContent;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getUploadedFileType() {
		return uploadedFileType;
	}

	public void setUploadedFileType(String uploadedFileType) {
		this.uploadedFileType = uploadedFileType;
	}
	public String getUploadedTitle() {
		return uploadedTitle;
	}

	public void setUploadedTitle(String uploadedTitle) {
		this.uploadedTitle = uploadedTitle;
	}

	public String getUploadedDescription() {
		return uploadedDescription;
	}

	public void setUploadedDescription(String uploadedDescription) {
		this.uploadedDescription = uploadedDescription;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}

}
