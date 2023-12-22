package FireBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
public class ValidadorTokenFireBase {

    private static ValidadorTokenFireBase instance;

    private ValidadorTokenFireBase(){};

    public static ValidadorTokenFireBase getInstance() {
        if(instance == null){instance=new ValidadorTokenFireBase();}
        return instance;
    }

    public boolean validar(String token){
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            System.out.println("Token válido para el UID: " + uid);
           return true;
        } catch (FirebaseAuthException e) {
            System.out.println("Error al verificar el token: " + e.getMessage());
            return false;
        }
    }

}
