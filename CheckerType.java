public enum CheckerType {
	
	GREEN(1),YELLOW(-1);
	
	final int direction;
	
	CheckerType(int dir)
	{
		direction = dir;
	}
}
