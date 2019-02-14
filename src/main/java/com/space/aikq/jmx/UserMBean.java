package com.space.aikq.jmx;

import java.time.LocalTime;
import java.util.Date;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 11:41
 */
public interface UserMBean {

	public void setId(Integer id);

	public Integer getId();

	public void setName(String name);

	public String getName();

	public void setBirthDate(Date date);

	public Date getBirthDate();

	public void setTime(LocalTime time);

	public LocalTime getTime();

	public void printUserInfo();

	public Date currentDate();

	public void setTest(Test test);

	public Test getTest();
}
