import java.util.HashSet;

public class CharNode{
	
	char key;
	HashSet<CharNode> childNodes;
	
	public CharNode(char c) {
		key=c;
		childNodes=new HashSet<>();
	}
}