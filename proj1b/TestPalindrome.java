import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator OffByOne = new OffByOne();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void isPalindromeTest(){
        assertTrue( palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("ice"));
        assertFalse(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void isGeneralizedPalindromeTest(){
        assertFalse(palindrome.isPalindrome("noon", OffByOne));
        assertTrue(palindrome.isPalindrome("flake", OffByOne));
    }
}
