package model;

public class Model {

    private static final Model _instance= new Model();

    public static Model instance() {
        return _instance;
    }

    private FirebaseModel firebaseModel = new FirebaseModel();

    private Model() {

    }

    public interface LoginListener{
        void onComplete();
    }

    public void loginUser(String email, String password,LoginListener callback){
        firebaseModel.loginUser(email,password,callback);

    }


}
