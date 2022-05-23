public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        LinkedListDeque d = new LinkedListDeque();
        for (int i = 0; i < word.length(); i++){
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        } else {
            Deque<Character> d = wordToDeque(word);
            return isPalindromeHelper(d);
        }
    }
    private boolean isPalindromeHelper(Deque<Character> d) {
        if(d.size() == 0 || d.size() == 1){
            return true;
        }
        else if(d.removeFirst() != d.removeLast()){
            return false;
        }
        else {
            return isPalindromeHelper(d);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        if (word == null){
            return false;
        }
        else if(cc == null){
            return isPalindrome(word);
        }
        else{
            Deque<Character> d = wordToDeque(word);
            return isPalindromeHelper(d, cc);
        }
    }

    private boolean isPalindromeHelper(Deque<Character> d, CharacterComparator cc){
        if(d.size() == 0 || d.size() == 1){
            return true;
        }
        else if(!(cc.equalChars(d.removeFirst(), d.removeLast()))){
            return false;
        }
        else {
            return isPalindromeHelper(d,cc);
        }
    }
}
