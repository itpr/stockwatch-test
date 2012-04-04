package com.stock.server.entities;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.PrePersist;

public class DatastoreObject implements Serializable
{
	@Id
	private Long id;
	private Integer version = 0;
	
	/**
	 * Auto-increment version # whenever persisted
	 */
	@PrePersist
	void onPersist()
	{
		this.version++;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}
}
