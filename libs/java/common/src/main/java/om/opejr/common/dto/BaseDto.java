package om.opejr.common.dto;

import java.io.Serializable;

public abstract class BaseDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7591831339921535906L;

	public abstract T convert();	
}
