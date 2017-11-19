public class Move 
{
	private MoveType type;

    public MoveType getType() {
        return type;
    }

    private Checker check;

    public Checker getChecker() {
        return check;
    }

    public Move(MoveType type) {
        this(type,null);
    }

    

	public Move(MoveType type, Checker check) {
        this.type = type;
        this.check = check;
    }

}
