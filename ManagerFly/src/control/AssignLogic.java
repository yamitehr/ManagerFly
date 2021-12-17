package control;

public class AssignLogic {

	private static AssignLogic _instance;

	private AssignLogic() {
	}

	public static AssignLogic getInstance() {
		if (_instance == null)
			_instance = new AssignLogic();
		return _instance;
	}
}
