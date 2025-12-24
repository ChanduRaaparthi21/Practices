package com.secure.SpringSecurity.forTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IsPalindromeOrNotTest {

	private isPalindromeOrNot palindromeService;

	@BeforeEach
	void setUp() {
		palindromeService = new isPalindromeOrNot();
	}

	@Test
	@DisplayName("Should return true for a valid palindrome word")
	void testPalindromeWord() {
		assertTrue(palindromeService.isPalindrome("madam"));
	}

	@Test
	@DisplayName("Should return false for non-palindrome word")
	void testNonPalindromeWord() {
		assertFalse(palindromeService.isPalindrome("hello"));
	}

	@Test
	@DisplayName("Single character should be palindrome")
	void testSingleCharacter() {
		assertTrue(palindromeService.isPalindrome("a"));
	}

	@Test
	@DisplayName("Empty string should be palindrome")
	void testEmptyString() {
		assertTrue(palindromeService.isPalindrome(""));
	}

	@Test
	@DisplayName("Case-sensitive check should fail")
	void testCaseSensitivity() {
		assertFalse(palindromeService.isPalindrome("Madam"));
	}

	@Test
	@DisplayName("Numeric palindrome should return true")
	void testNumericPalindrome() {
		assertTrue(palindromeService.isPalindrome("121"));
	}

	@Test
	@DisplayName("Special characters palindrome")
	void testSpecialCharacters() {
		assertTrue(palindromeService.isPalindrome("@#@"));
	}

	@Test
	@DisplayName("Mixed characters palindrome")
	void testMixedCharacters() {
		assertTrue(palindromeService.isPalindrome("ab@ba"));
	}

	@Test
	@DisplayName("Null input should throw NullPointerException")
	void testNullInput() {
		assertThrows(NullPointerException.class, () -> palindromeService.isPalindrome(null));
	}
}
