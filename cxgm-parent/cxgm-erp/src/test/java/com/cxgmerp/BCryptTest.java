package com.cxgmerp;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptTest {

	
	public static void main(String[] args) {
		String password = "123456";
		String pwt = BCrypt.hashpw(password, BCrypt.gensalt());//$2a$10$sET7VfqXGnDBVgILKgACIODXpH8YkT9TClSn3a09GM1tQKhKAcx3q
																//$2a$10$FBgayOm4m6gtEWjasiQSUur9cFwi.cHly/spyawmaPzt4qUppH6rW
		System.out.println(pwt);
	}
	
}
