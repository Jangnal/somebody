package com.example.myteamcproject.Mypage;

public class AttendanceDTO {
	
	private int Att_id;
	private String id;
	private String att_point;
	private String att_memo;
	private String att_continuity;
	private String att_Date;
	private String att_DateTime;

	public int getAtt_id() {
		return Att_id;
	}
	public void setAtt_id(int att_id) {
		Att_id = att_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAtt_point() {
		return att_point;
	}
	public void setAtt_point(String att_point) {
		this.att_point = att_point;
	}
	public String getAtt_memo() {
		return att_memo;
	}
	public void setAtt_memo(String att_memo) {
		this.att_memo = att_memo;
	}
	public String getAtt_continuity() {
		return att_continuity;
	}
	public void setAtt_continuity(String att_continuity) {
		this.att_continuity = att_continuity;
	}
	public String getAtt_Date() {
		return att_Date;
	}
	public void setAtt_Date(String att_Date) {
		this.att_Date = att_Date;
	}
	public String getAtt_DateTime() {
		return att_DateTime;
	}
	public void setAtt_DateTime(String att_DateTime) {
		this.att_DateTime = att_DateTime;
	}

}
