package com.june.dto;

public class Person {
	private long id;
    private String name;

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

	/**
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id 要设置的 id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
