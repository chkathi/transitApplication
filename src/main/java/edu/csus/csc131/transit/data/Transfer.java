package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transfer{
	@Id
	private String id;
	private String fromStopId;
	private String toStopId;
	private int minTransferTime;
	private String fromRouteId;
	private String toRouteId;

	public Transfer(){
			super();
	}
	public String getId(){
		return id;
	}
	public void setId(string id){
		this.id = id;
	}
	public String getfromStopId()
	{
		return fromStopId;
	}
	public void setfromStopId(String fromStopId)
	{
		this.fromStopId = fromStopId;
	}
	public String gettoStopId()
	{
		return toStopID;
	}
	public void settoStopId(String toStopId)
	{
		this.toStopId = toStopid;
	}
	public int getminTransferTime()
	{
		return minTransferTime;
	}
	public void setminTransferTime(int minTransferTime)
	{
		this.=minTransferTime = minTransferTime;
	}
	public String getfromRouteId()
	{
		return fromRouteId;
	}
	public void setfromRouteId(String fromRouteId)
	{
		this.fromRouteId = fromRouteId;
	}
	public String gettoRouteId()
	{
		return toRouteId;
	}
	public void settoRouteId(String toRouteId)
	{
		this.toRouteId = toRouteId;
	}

	@Override
	public string toString()
	{
		return "Transfer [id=" + id +", fromStopId=" + fromStopId +", toStopId=" + toStopId +
				", minTransferTime=" + minTransferTime +", fromRouteId=" +fromRouteId +", toRouteId=" +toRouteId"]";
	}


}


