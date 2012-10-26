package com.test.model;

import java.sql.Blob;


public class Service_tsi_report_fj {
	private String tsif_ticket_id;
	private Integer tsif_serial_no;
	private String tsif_fj_path;
	private byte[] tsif_fj;
	private long tsif_fj_size;
	private String tsif_fj_name;
	public String getTsif_ticket_id() {
		return tsif_ticket_id;
	}
	public void setTsif_ticket_id(String tsif_ticket_id) {
		this.tsif_ticket_id = tsif_ticket_id;
	}
	public Integer getTsif_serial_no() {
		return tsif_serial_no;
	}
	public void setTsif_serial_no(Integer tsif_serial_no) {
		this.tsif_serial_no = tsif_serial_no;
	}
	public String getTsif_fj_path() {
		return tsif_fj_path;
	}
	public void setTsif_fj_path(String tsif_fj_path) {
		this.tsif_fj_path = tsif_fj_path;
	}
	public byte[] getTsif_fj() {
		return tsif_fj;
	}
	public void setTsif_fj(byte[]  tsif_fj) {
		this.tsif_fj = tsif_fj;
	}
	public long getTsif_fj_size() {
		return tsif_fj_size;
	}
	public void setTsif_fj_size(long tsif_fj_size) {
		this.tsif_fj_size = tsif_fj_size;
	}
	public String getTsif_fj_name() {
		return tsif_fj_name;
	}
	public void setTsif_fj_name(String tsif_fj_name) {
		this.tsif_fj_name = tsif_fj_name;
	}
}
